package me.kp56.timetables.timetable;

import me.kp56.timetables.configuration.Config;
import me.kp56.timetables.fuzzylogic.FuzzyContainer;
import me.kp56.timetables.fuzzylogic.FuzzyLogic;
import me.kp56.timetables.timetable.fix.FixerCache;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Timetable implements Serializable {
    private static final Config config = Config.getInstance();
    public List<Group>[] days = new List[5];
    private static FixerCache cache;

    //A field which can be set after loading the timetable from file to be able to extract timetable's path
    public transient String source;

    public static void init() {
        cache = new FixerCache();
    }

    public Timetable() {
        for (int i = 0; i < 5; i++) {
            days[i] = new ArrayList<>();
        }
    }

    public void saveToFile(String file, int gaps, double fitness) throws IOException {
        Path path = Path.of(file);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }

        if (!Files.isDirectory(path)) {
            System.out.println("Could not create a directory due to there being a file with the same name.");
            return;
        }

        //Gathering statistics about the timetable
        int worstGaps = worstGaps();

        double averageStart = 0;
        double averageEnd = 0;
        int worstStart = 0;
        int worstEnd = 0;

        for (Student student : Student.students) {
            for (List<Group> groups : days) {
                int start = 0;
                int end = groups.size() - 1;
                for (Group group : groups) {
                    if (group.students.contains(student)) {
                        break;
                    }
                    start++;
                }

                for (int i = groups.size() - 1; i >= 0; i--) {
                    if (groups.get(i).students.contains(student)) {
                        break;
                    }
                    end--;
                }

                worstStart = Math.max(worstStart, start);
                worstEnd = Math.max(worstEnd, end);
                averageStart += (double) start / (5 * Student.students.size());
                averageEnd += (double) end / (5 * Student.students.size());
            }
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(file + "/statistics.txt"));
        writer.write("Candidate: " + file);
        writer.newLine();
        writer.write("Fitness: " + fitness);
        writer.newLine();
        writer.write("Total lesson gaps: " + gaps);
        writer.newLine();
        writer.write("Average starting lesson: " + (averageStart + 1));
        writer.newLine();
        writer.write("Average ending lesson: " + (averageEnd + 1));
        writer.newLine();
        writer.write("Worst student gaps: " + worstGaps);
        writer.newLine();
        writer.write("Worst student lesson start: " + (worstStart + 1));
        writer.newLine();
        writer.write("Worst student lesson end: " + (worstEnd + 1));
        writer.close();

        //Writing the timetable to timetable.txt
        BufferedWriter timetableWriter = new BufferedWriter(new FileWriter(file + "/timetable.txt"));
        timetableWriter.newLine();
        timetableWriter.write("Timetable");
        timetableWriter.newLine();
        List<String> days = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
        for (int i = 0; i < 5; i++) {
            timetableWriter.write((i + 1) + ". " + days.get(i) + ":");
            timetableWriter.newLine();
            for (Group g : this.days[i]) {
                timetableWriter.write("- ");
                List<Subject> subjects = new ArrayList<>(g.subjects);
                timetableWriter.write(subjects.get(0).name());
                for (int j = 1; j < subjects.size(); j++) {
                    timetableWriter.write(", " + subjects.get(j).name());
                }
                timetableWriter.newLine();
            }
        }
        timetableWriter.close();

        //Writing a separate timetable for each student
        for (Student student : Student.students) {
            BufferedWriter studentTable = new BufferedWriter(new FileWriter(file + "/" + student.name + "-timetable.txt"));
            for (int i = 0; i < 5; i++) {
                studentTable.write((i + 1) + ". " + days.get(i) + ":");
                studentTable.newLine();
                for (Group g : this.days[i]) {
                    studentTable.write("- ");
                    List<Subject> subjects = new ArrayList<>(g.subjects);
                    boolean isFirst = true;
                    if (student.subjects.contains(subjects.get(0))) {
                        studentTable.write(subjects.get(0).name());
                        isFirst = false;
                    }
                    for (int j = 1; j < subjects.size(); j++) {
                        if (student.subjects.contains(subjects.get(j))) {
                            studentTable.write((isFirst ? "" : ", ") + subjects.get(j).name());
                            isFirst = false;
                        }
                    }
                    studentTable.newLine();
                }
            }
            studentTable.close();
        }

        //Serializing the timetable and saving to file
        saveSerialized(file);
    }

    private void saveSerialized(String file) {
        try {
            FileOutputStream fos = new FileOutputStream(file + "/binary-timetable.timetable");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Creates a new, slightly modified timetable based on this one
    public Timetable offspring() {
        Timetable newTimetable = new Timetable();
        int randomDay = (int) (Math.random() * 5);
        List<Integer> daysCleared = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            daysCleared.add(i);
        }
        if (Math.random() < 0.5) {
            for (int i = 0; i < randomDay; i++) {
                daysCleared.remove((Integer) i);
            }
            System.arraycopy(days, 0, newTimetable.days, 0, randomDay);
        } else {
            for (int i = randomDay; i < 5; i++) {
                daysCleared.remove((Integer) i);
            }
            System.arraycopy(days, randomDay, newTimetable.days, randomDay, 5 - randomDay);
        }

        for (int i = 0; i < 5; i++) {
            if (!newTimetable.days[i].isEmpty()) {
                newTimetable.days[i] = new ArrayList<>(newTimetable.days[i]);
            }
        }

        newTimetable.fix(daysCleared);

        return newTimetable;
    }

    public double fitness() {
        //Creating containers for daily evaluation
        FuzzyContainer startingLessons = new FuzzyContainer(11);
        startingLessons.modifyRecord(0, 2, 2, 6, 4, 4);
        startingLessons.modifyRecord(1, 0, 2, 3, 6, 7);
        startingLessons.modifyRecord(2, 0, 1, 1, 3, 13);
        startingLessons.modifyRecord(3, 0, 2, 2, 5, 9);
        startingLessons.modifyRecord(4, 0, 4, 4, 4, 6);
        startingLessons.modifyRecord(5, 2, 5, 4, 5, 2);
        startingLessons.modifyRecord(6, 5, 3, 6, 3, 1);
        startingLessons.modifyRecord(7, 8, 2, 5, 2, 1);
        startingLessons.modifyRecord(8, 10, 0, 4, 3, 1);
        startingLessons.modifyRecord(9, 10, 0, 4, 2, 2);
        startingLessons.modifyRecord(10, 10, 1, 4, 1, 2);

        FuzzyContainer endingLessons = new FuzzyContainer(11);
        endingLessons.modifyRecord(0, 4, 0, 5, 0, 8);
        endingLessons.modifyRecord(1, 4, 0, 4, 1, 8);
        endingLessons.modifyRecord(2, 1, 3, 2, 3, 8);
        endingLessons.modifyRecord(3, 1, 0, 3, 2, 11);
        endingLessons.modifyRecord(4, 0, 0, 2, 3, 13);
        endingLessons.modifyRecord(5, 0, 0, 0, 3, 15);
        endingLessons.modifyRecord(6, 0, 0, 0, 9, 9);
        endingLessons.modifyRecord(7, 0, 1, 7, 3, 7);
        endingLessons.modifyRecord(8, 4, 5, 7, 2, 0);
        endingLessons.modifyRecord(9, 7, 7, 3, 1, 0);
        endingLessons.modifyRecord(10, 10, 3, 4, 1, 0);

        FuzzyContainer totalLessons = new FuzzyContainer(11);
        totalLessons.modifyRecord(0, 2, 2, 5, 3, 6);
        totalLessons.modifyRecord(1, 1, 3, 3, 4, 7);
        totalLessons.modifyRecord(2, 1, 1, 3, 1, 12);
        totalLessons.modifyRecord(3, 1, 0, 0, 3, 14);
        totalLessons.modifyRecord(4, 0, 0, 0, 4, 14);
        totalLessons.modifyRecord(5, 0, 0, 0, 5, 13);
        totalLessons.modifyRecord(6, 0, 1, 3, 7, 7);
        totalLessons.modifyRecord(7, 1, 2, 8, 6, 1);
        totalLessons.modifyRecord(8, 5, 9, 4, 0, 0);
        totalLessons.modifyRecord(9, 11, 6, 1, 0, 0);
        totalLessons.modifyRecord(10, 14, 3, 1, 0, 0);

        FuzzyContainer totalGaps = new FuzzyContainer(10);
        totalGaps.modifyRecord(0, 0, 0, 0, 0, 18);
        totalGaps.modifyRecord(1, 0, 1, 3, 6, 8);
        totalGaps.modifyRecord(2, 1, 0, 3, 6, 8);
        totalGaps.modifyRecord(3, 1, 2, 3, 5, 7);
        totalGaps.modifyRecord(4, 3, 0, 7, 4, 4);
        totalGaps.modifyRecord(5, 3, 8, 2, 3, 2);
        totalGaps.modifyRecord(6, 7, 4, 4, 2, 1);
        totalGaps.modifyRecord(7, 9, 5, 1, 3, 0);
        totalGaps.modifyRecord(8, 11, 3, 4, 0, 0);
        totalGaps.modifyRecord(9, 11, 4, 3, 0, 0);

        FuzzyContainer gapsInARow = new FuzzyContainer(9);
        gapsInARow.modifyRecord(0, 0, 1, 8, 6, 3);
        gapsInARow.modifyRecord(1, 1, 1, 2, 8, 6);
        gapsInARow.modifyRecord(2, 1, 2, 5, 4, 6);
        gapsInARow.modifyRecord(3, 2, 3, 4, 3, 6);
        gapsInARow.modifyRecord(4, 7, 4, 1, 4, 2);
        gapsInARow.modifyRecord(5, 6, 3, 3, 4, 1);
        gapsInARow.modifyRecord(6, 8, 4, 2, 2, 1);
        gapsInARow.modifyRecord(7, 9, 1, 2, 4, 1);
        gapsInARow.modifyRecord(8, 8, 1, 2, 4, 1);

        //Weekly container
        FuzzyContainer weeklyGaps = new FuzzyContainer(16);
        weeklyGaps.modifyRecord(0, 1, 1, 2, 5, 4);
        weeklyGaps.modifyRecord(1, 0, 5, 0, 3, 5);
        weeklyGaps.modifyRecord(2, 0, 3, 2, 2, 6);
        weeklyGaps.modifyRecord(3, 0, 1, 1, 6, 5);
        weeklyGaps.modifyRecord(4, 0, 0, 2, 2, 9);
        weeklyGaps.modifyRecord(5, 0, 0, 3, 3, 7);
        weeklyGaps.modifyRecord(6, 0, 1, 2, 6, 4);
        weeklyGaps.modifyRecord(7, 0, 2, 4, 3, 4);
        weeklyGaps.modifyRecord(8, 2, 0, 7, 2, 2);
        weeklyGaps.modifyRecord(9, 2, 1, 7, 1, 2);
        weeklyGaps.modifyRecord(10, 2, 5, 3, 1, 2);
        weeklyGaps.modifyRecord(11, 4, 6, 0, 1, 2);
        weeklyGaps.modifyRecord(12, 6, 4, 0, 1, 2);
        weeklyGaps.modifyRecord(13, 10, 0, 0, 2, 1);
        weeklyGaps.modifyRecord(14, 10, 0, 0, 3, 0);
        weeklyGaps.modifyRecord(15, 10, 0, 0, 3, 0);

        double totalEvaluation = 0;
        List<Double> studentEvaluations = new ArrayList<>();
        for (Student student : Student.students) {
            //Evaluating each student seperately
            double studentEvaluation = 0;
            int weeklyGapsCount = 0;
            for (List<Group> groups : days) {
                //Evaluating each day using the day containers
                int start = 0;
                int end = groups.size() - 1;
                for (Group group : groups) {
                    if (group.students.contains(student)) {
                        break;
                    }
                    start++;
                }

                for (int j = groups.size() - 1; j >= 0; j--) {
                    if (groups.get(j).students.contains(student)) {
                        break;
                    }
                    end--;
                }

                List<Integer> gapsInARowList = new ArrayList<>();
                int gaps = 0;
                int prevGap = -2;
                for (int i = start; i <= end; i++) {
                    if (!groups.get(i).students.contains(student)) {
                        if (prevGap == i - 1) {
                            gapsInARowList.set(gapsInARowList.size() - 1, gapsInARowList.get(gapsInARowList.size() - 1) + 1);
                        } else {
                            gapsInARowList.add(1);
                        }
                        gaps++;
                        weeklyGapsCount++;
                        prevGap = i;
                    }
                }

                int gapGroups = gapsInARowList.size();

                if (end < start) {
                    System.out.println("Encountered a timetable with some student having no lessons at some day. Deprioritizing...");
                    return 0;
                }

                int totalLessonsCount = end - start + 1;

                List<List<Double>> parameters = new ArrayList<>();
                List<Double> firstGroup = new ArrayList<>();
                firstGroup.add((double) (start));
                firstGroup.add((double) (end));
                firstGroup.add((double) (totalLessonsCount - 1));
                firstGroup.add((double) (gaps));
                List<Double> secondGroup = new ArrayList<>();
                for (int i : gapsInARowList) {
                    secondGroup.add((double) i - 1d);
                }
                parameters.add(firstGroup);
                parameters.add(secondGroup);

                FuzzyLogic dailyLogic = new FuzzyLogic();
                dailyLogic.addGroup();
                dailyLogic.addGroup();
                dailyLogic.addContainer(0, startingLessons);
                dailyLogic.addContainer(0, endingLessons);
                dailyLogic.addContainer(0, totalLessons);
                dailyLogic.addContainer(0, totalGaps);

                for (int i = 0; i < gapsInARowList.size(); i++) {
                    dailyLogic.addContainer(1, gapsInARow);
                }

                studentEvaluation += (dailyLogic.evaluate(parameters) + gapGroups / 5d) / 5d;
            }
            studentEvaluation += weeklyGaps.evaluate(Math.min(15, weeklyGapsCount));
            totalEvaluation += studentEvaluation / Student.students.size();
            studentEvaluations.add(studentEvaluation);
        }

        double diffEvaluation = 0d;
        for (double d : studentEvaluations) {
            diffEvaluation += Math.abs(d - totalEvaluation);
        }
        diffEvaluation /= 10.2 * Student.students.size();

        return (totalEvaluation / 10.2) * (config.getInteger("fuzzylogic.rate") / 100d) + (45d * Student.students.size() - gaps())
                / (45d * Student.students.size()) * ((100d - config.getInteger("fuzzylogic.rate")) / 100d) + diffEvaluation * 0.2;
    }

    //Computes the total number of gaps which students have
    public int gaps() {
        int noLessons = 0;
        for (Student student : Student.students) {
            for (List<Group> groups : days) {
                int start = 0;
                int end = groups.size() - 1;
                for (Group group : groups) {
                    if (group.students.contains(student)) {
                        break;
                    }
                    start++;
                }

                for (int i = groups.size() - 1; i >= 0; i--) {
                    if (groups.get(i).students.contains(student)) {
                        break;
                    }
                    end--;
                }

                for (int i = start; i <= end; i++) {
                    if (!groups.get(i).students.contains(student)) {
                        noLessons++;
                    }
                }
            }
        }

        return noLessons;
    }

    //Calculates the highest number of gaps a student can have
    public int worstGaps() {
        int worstGaps = 0;
        for (Student student : Student.students) {
            int noLessons = 0;
            for (List<Group> groups : days) {
                int start = 0;
                int end = groups.size() - 1;
                for (Group group : groups) {
                    if (group.students.contains(student)) {
                        break;
                    }
                    start++;
                }

                for (int i = groups.size() - 1; i >= 0; i--) {
                    if (groups.get(i).students.contains(student)) {
                        break;
                    }
                    end--;
                }

                for (int i = start; i <= end; i++) {
                    if (!groups.get(i).students.contains(student)) {
                        noLessons++;
                    }
                }
            }

            worstGaps = Math.max(worstGaps, noLessons);
        }

        return worstGaps;
    }

    //Turns the timetable into a correct one
    public void fix() {
        fix(listWithAllDays());
    }

    /*Turns a timetable without removing any current groups into the correct one.
     * Can only modify days which are on the daysToCheck list.*/
    public void fix(List<Integer> daysToCheck) {
        if (!cache.fix(this, daysToCheck, 0)) {
            do {
                for (int i = 0; i < 5; i++) {
                    days[i] = new ArrayList<>();
                }
            } while (!cache.fix(this, listWithAllDays(), 0));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timetable timetable = (Timetable) o;
        return Arrays.equals(days, timetable.days);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(days);
    }

    private static List<Integer> listWithAllDays() {
        List<Integer> l = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            l.add(i);
        }
        return l;
    }
}
