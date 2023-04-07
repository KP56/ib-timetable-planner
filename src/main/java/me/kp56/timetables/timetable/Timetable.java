package me.kp56.timetables.timetable;

import me.kp56.timetables.Main;
import me.kp56.timetables.configuration.Config;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Timetable {
    private static final Config config = Config.getInstance();
    private List<Group>[] days = new List[5];
    private static FixerCache cache;

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

    /*Calculates fitness of the timetable. The greater it is, the better the timetable is.
     * I am going to replace this one with a better implementation using fuzzy logic later on.*/
    public double fitness() {
        int noLessons = gaps();

        return 55 * Student.students.size() - noLessons;
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

    //Turns the timetable in the lowest possible number of random modifications into a correct one
    public void fix() {
        List<Integer> daysToCheck = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            daysToCheck.add(i);
        }

        fix(daysToCheck);
    }

    /*Turns a timetable without removing any current groups into the correct one.
     * Can only modify days which are on the daysToCheck list.*/
    public void fix(List<Integer> daysToCheck) {
        cache.fix(this, daysToCheck, 0);
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

    public static class FixerCache {
        private List<List<Integer>> graph;
        private List<List<Integer>> revGraph;
        private List<Group> allGroups;

        private List<Integer> revTopSort;
        private Integer[] longestPathFrom;

        public FixerCache() {
            allGroups = Group.generateAll();
            graph = new ArrayList<>();
            for (int i = 0; i < allGroups.size(); i++) {
                graph.add(new ArrayList<>());
            }

            //Creating a graph out of groups
            for (int i = 0; i < allGroups.size(); i++) {
                Group g = allGroups.get(i);
                group2Loop:
                for (int j = 0; j < allGroups.size(); j++) {
                    Group g2 = allGroups.get(j);
                    if (i != j) {
                        for (Student s : g.students) {
                            if (!g2.students.contains(s)) {
                                continue group2Loop;
                            }
                        }

                        graph.get(i).add(j);
                    }
                }
            }

            //Turning the graph into a Directed Acyclic Graph using DFS
            boolean[] vis = new boolean[graph.size()];
            for (int i = 0; i < graph.size(); i++) {
                if (!vis[i]) {
                    turnIntoDAGRec(i, vis);
                }
            }

            //Creating a reverse graph
            revGraph = new ArrayList<>();
            for (int i = 0; i < graph.size(); i++) {
                revGraph.add(new ArrayList<>());
            }

            for (int i = 0; i < graph.size(); i++) {
                for (int j : graph.get(i)) {
                    revGraph.get(j).add(i);
                }
            }

            //Computing Topological Sort of the reverse graph
            vis = new boolean[revGraph.size()];
            Stack<Integer> topSortSol = new Stack<>();
            for (int i = 0; i < revGraph.size(); i++) {
                if (!vis[i]) {
                    topSortRec(i, vis, topSortSol);
                }
            }

            revTopSort = new ArrayList<>();
            while (!topSortSol.isEmpty()) {
                revTopSort.add(topSortSol.pop());
            }

            //Finding the highest total number of groups of the path with that highest value from current vertex
            longestPathFrom = new Integer[graph.size()];
            for (int i = 0; i < graph.size(); i++) {
                longestPathFrom[i] = allGroups.get(i).subjects.size();
            }
            for (int i : revTopSort) {
                for (int j : graph.get(i)) {
                    longestPathFrom[i] = Math.max(longestPathFrom[i], longestPathFrom[j] + allGroups.get(i).subjects.size());
                }
            }

            //This longestPathFrom array will be used as a good heuristic function for finding the best group to choose next from the current one
        }

        private void topSortRec(int at, boolean[] vis, Stack<Integer> topSortSol) {
            vis[at] = true;

            for (int i : revGraph.get(at)) {
                if (!vis[i]) {
                    topSortRec(i, vis, topSortSol);
                }
            }

            topSortSol.add(at);
        }

        private void turnIntoDAGRec(int at, boolean[] vis) {
            vis[at] = true;

            for (int i : graph.get(at)) {
                if (!vis[i]) {
                    graph.get(i).remove((Integer) at);
                    turnIntoDAGRec(i, vis);
                }
            }
        }

        public void fix(Timetable timetable, List<Integer> daysToCheck, int tries) {
            if (tries == config.getInteger("genetic.fix_tries")) {
                for (int i = 0; i < 5; i++) {
                    timetable.days[i] = new ArrayList<>();
                }
                timetable.fix();
                return;
            }

            if (daysToCheck.isEmpty()) return;

            //Creating a map containing information about the number of subjects left which we can use
            Map<Subject, Integer> subjectMap = new HashMap<>();

            //For each subject, prepare the initial map with the number of possible uses being equal to the limit of each enabled subject
            for (Subject s : Subject.values()) {
                if (!config.getBoolean(s.name().toLowerCase(Locale.ROOT) + ".disabled")) {
                    subjectMap.put(s, s.limit);
                }
            }

            //Count the number of each subject in the current timetable and for each remove 1 from the number of usages left
            for (List<Group> groups : timetable.days) {
                for (Group g : groups) {
                    for (Subject s : g.subjects) {
                        subjectMap.replace(s, subjectMap.get(s) - 1);
                        if (subjectMap.get(s) == 0) {
                            subjectMap.remove(s);
                        }
                    }
                }
            }

            //Construct paths by making the probability of selecting an item proportional to the corresponding value of longestPathFrom
            while (!subjectMap.isEmpty()) {
                Collections.shuffle(daysToCheck);
                int continueCounter = 0;
                for (int day : daysToCheck) {
                    Map<Subject, Integer> dailyMap = new HashMap<>();
                    for (Group g : timetable.days[day]) {
                        for (Subject s : g.subjects) {
                            if (dailyMap.containsKey(s)) {
                                dailyMap.replace(s, dailyMap.get(s) + 1);
                            } else {
                                dailyMap.put(s, 1);
                            }
                        }
                    }

                    if (subjectMap.isEmpty()) {
                        break;
                    }

                    List<Group> groups = new ArrayList<>();
                    List<Integer> unnormalized = new ArrayList<>();
                    List<Integer> unnormalizedIndexes = new ArrayList<>();
                    groupLoop:
                    for (int i = 0; i < longestPathFrom.length; i++) {
                        Group g = allGroups.get(i);
                        for (Subject s : g.subjects) {
                            if (!subjectMap.containsKey(s) || (dailyMap.containsKey(s) && dailyMap.get(s) == config.getInteger("maximum_daily_subjects"))) {
                                continue groupLoop;
                            }
                        }
                        unnormalized.add(longestPathFrom[i]);
                        unnormalizedIndexes.add(i);
                    }
                    if (unnormalized.isEmpty()) {
                        continueCounter++;
                        continue;
                    }
                    List<Double> probabilities = Runner.normalizeProbabilities(unnormalized);
                    int current = unnormalizedIndexes.get(Runner.randomItem(probabilities));
                    Group currentGroup = allGroups.get(current);
                    for (Subject s : currentGroup.subjects) {
                        subjectMap.replace(s, subjectMap.get(s) - 1);
                        if (!dailyMap.containsKey(s)) {
                            dailyMap.put(s, 1);
                        } else {
                            dailyMap.replace(s, dailyMap.get(s) + 1);
                        }
                        if (subjectMap.get(s) == 0) {
                            subjectMap.remove(s);
                        }
                    }
                    groups.add(currentGroup);
                    while (!graph.get(current).isEmpty() && groups.size() < config.getInteger("maximum_daily_lessons") - timetable.days[day].size() && !subjectMap.isEmpty()) {
                        List<Integer> copy = new ArrayList<>();
                        List<Integer> indexes = new ArrayList<>();
                        groupLoop:
                        for (int i : graph.get(current)) {
                            Group g = allGroups.get(i);
                            for (Subject s : g.subjects) {
                                if (!subjectMap.containsKey(s) || (dailyMap.containsKey(s) && dailyMap.get(s) == config.getInteger("maximum_daily_subjects"))) {
                                    continue groupLoop;
                                }
                            }
                            copy.add(longestPathFrom[i]);
                            indexes.add(i);
                        }

                        if (copy.isEmpty()) {
                            break;
                        }
                        List<Double> newProbabilities = Runner.normalizeProbabilities(copy);
                        current = indexes.get(Runner.randomItem(newProbabilities));
                        currentGroup = allGroups.get(current);
                        for (Subject s : currentGroup.subjects) {
                            subjectMap.replace(s, subjectMap.get(s) - 1);
                            if (!dailyMap.containsKey(s)) {
                                dailyMap.put(s, 1);
                            } else {
                                dailyMap.replace(s, dailyMap.get(s) + 1);
                            }
                            if (subjectMap.get(s) == 0) {
                                subjectMap.remove(s);
                            }
                        }
                        groups.add(currentGroup);
                    }
                    timetable.days[day].addAll(groups);
                }

                if (continueCounter == daysToCheck.size()) {
                    for (int i : daysToCheck) {
                        timetable.days[i] = new ArrayList<>();
                    }
                    fix(timetable, daysToCheck, tries + 1);
                    return;
                }
            }
        }
    }

    public static class Group {
        private Set<Subject> subjects;
        private Set<Student> students = new HashSet<>();

        public Group(Set<Subject> subjects) {
            this.subjects = subjects;
            for (Student student : Student.students) {
                for (Subject subject : subjects) {
                    if (student.subjects.contains(subject)) {
                        if (!students.add(student)) {
                            throw new RuntimeException("Tried to create an invalid group");
                        }
                    }
                }
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Group group = (Group) o;
            return Objects.equals(subjects, group.subjects) && Objects.equals(students, group.students);
        }

        @Override
        public int hashCode() {
            return Objects.hash(subjects);
        }

        //Try to generate a group
        public static Group attempt(Set<Subject> subjects) {
            try {
                return new Group(subjects);
            } catch (RuntimeException e) {
                return null;
            }
        }

        //A recursive implementation of generating all possible groups of subjects
        private static void recursiveGen(Group current, Set<Group> groups) {
            for (Subject subject : Subject.values()) {
                if (!config.getBoolean(subject.name().toLowerCase(Locale.ROOT) + ".disabled")) {
                    if (!current.subjects.contains(subject)) {
                        Set<Subject> subjects = (Set<Subject>) ((HashSet<Subject>) current.subjects).clone();
                        subjects.add(subject);
                        Group newGroup = Group.attempt(subjects);

                        if (newGroup != null) {
                            if (groups.add(newGroup)) {
                                recursiveGen(newGroup, groups);
                            }
                        }
                    }
                }
            }
        }

        //Calls the recursive function in order to generate all possible groups
        public static List<Group> generateAll() {
            Set<Group> groups = new HashSet<>();
            recursiveGen(new Group(new HashSet<>()), groups);
            return new ArrayList<>(groups);
        }
    }

    public static enum Subject {
        HISTORY_SL(4),
        HISTORY_HL(config.getBoolean("history.connected") ? 2 : 6, config.getBoolean("history.connected") ? List.of(HISTORY_SL) : List.of()),
        ECONOMICS_SL(4),
        ECONOMICS_HL(config.getBoolean("economics.connected") ? 2 : 6, config.getBoolean("economics.connected") ? List.of(ECONOMICS_SL) : List.of()),
        POLISH_NO_EXAMINATION(2),
        POLISH_A_SL(4),
        POLISH_A_HL(config.getBoolean("polish_a.connected") ? 2 : 6, config.getBoolean("polish_a.connected") ? List.of(POLISH_A_SL) : List.of()),
        ENGLISH_A_SL(4),
        ENGLISH_A_HL(config.getBoolean("english_a.connected") ? 2 : 6, config.getBoolean("english_a.connected") ? List.of(ENGLISH_A_SL) : List.of()),
        ENGLISH_B_HL(6),
        GERMAN_B_SL(4),
        GERMAN_B_HL(config.getBoolean("german_b.connected") ? 2 : 6, config.getBoolean("german_b.connected") ? List.of(GERMAN_B_SL) : List.of()),
        SPANISH_B_SL(4),
        SPANISH_B_HL(config.getBoolean("spanish_b.connected") ? 2 : 6, config.getBoolean("spanish_b.connected") ? List.of(SPANISH_B_SL) : List.of()),
        FRENCH_B_SL(4),
        FRENCH_B_HL(config.getBoolean("french_b.connected") ? 2 : 6, config.getBoolean("french_b.connected") ? List.of(FRENCH_B_SL) : List.of()),
        BIOLOGY_SL(4),
        BIOLOGY_HL(config.getBoolean("biology.connected") ? 2 : 6, config.getBoolean("biology.connected") ? List.of(BIOLOGY_SL) : List.of()),
        PHYSICS_SL(4),
        PHYSICS_HL(config.getBoolean("physics.connected") ? 2 : 6, config.getBoolean("physics.connected") ? List.of(PHYSICS_SL) : List.of()),
        CHEMISTRY_SL(4),
        CHEMISTRY_HL(config.getBoolean("chemistry.connected") ? 2 : 6, config.getBoolean("chemistry.connected") ? List.of(CHEMISTRY_SL) : List.of()),
        COMPUTER_SCIENCE_SL(4),
        COMPUTER_SCIENCE_HL(config.getBoolean("computer_science.connected") ? 2 : 6, config.getBoolean("computer_science.connected") ? List.of(COMPUTER_SCIENCE_SL) : List.of()),
        GEOGRAPHY_SL(4),
        GEOGRAPHY_HL(config.getBoolean("geography.connected") ? 2 : 6, config.getBoolean("geography.connected") ? List.of(GEOGRAPHY_SL) : List.of()),
        MATH_AA_SL(4),
        MATH_AA_HL(config.getBoolean("math_aa.connected") ? 2 : 6, config.getBoolean("math_aa.connected") ? List.of(MATH_AA_SL) : List.of()),
        MATH_AI_SL(4),
        TOK(2),
        Z_WYCH(1),
        DSD(2);

        private final int limit;
        public List<Subject> connectedTo;

        Subject(int limit, List<Subject> connectedTo) {
            this.limit = limit;
            this.connectedTo = connectedTo;
        }

        Subject(int limit) {
            this.limit = limit;
            this.connectedTo = new ArrayList<>();
        }
    }
}
