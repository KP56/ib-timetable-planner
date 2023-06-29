package me.kp56.timetables.timetable.fix;

import me.kp56.timetables.configuration.Config;
import me.kp56.timetables.filters.Filter;
import me.kp56.timetables.filters.Period;
import me.kp56.timetables.run.Runner;
import me.kp56.timetables.students.Student;
import me.kp56.timetables.timetable.*;

import java.util.*;

public class FixerCache {
    private static final Config config = Config.getInstance();

    private List<List<Integer>> graph;
    private List<Group> allGroups;
    private Timetable reference;
    private Map<Subject, List<String>> teachersReference;

    public FixerCache(Timetable reference, Map<Subject, List<String>> teachersReference) {
        this();

        this.reference = reference;
        this.teachersReference = teachersReference;
    }

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
                        if (!s.isTeacher) {
                            if (!g2.students.contains(s)) {
                                continue group2Loop;
                            }
                        }
                    }

                    graph.get(i).add(j);
                }
            }
        }
    }

    public boolean fix(Timetable timetable, List<Integer> daysToCheck, int tries) {
        if (tries == config.getInteger("genetic.fix_tries")) {
            return false;
        }

        if (daysToCheck.isEmpty()) {
            return true;
        }

        //Creating a map containing information about the number of subjects left which we can use
        Map<Subject, Integer> subjectMap = new HashMap<>();

        //For each subject, prepare the initial map with the number of possible uses being equal to the limit of each enabled subject
        for (Subject s : Subject.values()) {
            if (!s.disabled()) {
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

                        for (Subject s2 : s.connectedTo) {
                            if (!dailyMap.containsKey(s2)) {
                                dailyMap.put(s2, 1);
                            } else {
                                dailyMap.replace(s2, dailyMap.get(s2) + 1);
                            }
                        }
                    }
                }

                if (subjectMap.isEmpty()) {
                    break;
                }

                if (timetable.days[day].size() >= 10) {
                    continueCounter++;
                    continue;
                }

                List<Group> groups = new ArrayList<>();
                List<Integer> unnormalizedIndexes = new ArrayList<>();
                for (int i = 0; i < allGroups.size(); i++) {
                    Group g = allGroups.get(i);
                    if (!g.isMaximal) continue;
                    if (!canAddGroup(g, subjectMap, dailyMap, day, timetable.days[day].size() + groups.size()))
                        continue;
                    //Taking into account running with reference
                    if (reference != null && ((g.subjects.size() != 1 || g.subjects.toArray(new Subject[0])[0] != Subject.Z_WYCH)
                            && !canAddGroup(g, subjectMap, dailyMap, day, timetable.days[day].size() + groups.size() + 1)))
                        continue;
                    unnormalizedIndexes.add(i);
                }

                if (unnormalizedIndexes.isEmpty()) {
                    for (int i = 0; i < allGroups.size(); i++) {
                        Group g = allGroups.get(i);
                        if (!canAddGroup(g, subjectMap, dailyMap, day, timetable.days[day].size() + groups.size()))
                            continue;
                        if (reference != null && ((g.subjects.size() != 1 || g.subjects.toArray(new Subject[0])[0] != Subject.Z_WYCH)
                                && !canAddGroup(g, subjectMap, dailyMap, day, timetable.days[day].size() + groups.size() + 1)))
                            continue;
                        unnormalizedIndexes.add(i);
                    }
                }

                if (unnormalizedIndexes.isEmpty()) {
                    continueCounter++;
                    continue;
                }

                int current = unnormalizedIndexes.get((int) (Math.random() * unnormalizedIndexes.size()));
                Group currentGroup = allGroups.get(current);
                updateMaps(currentGroup, subjectMap, dailyMap);
                if (groups.size() + 1 < 11 - timetable.days[day].size() && canAddGroup(currentGroup, subjectMap, dailyMap, day, timetable.days[day].size() + groups.size() + 1)) {
                    groups.add(currentGroup);
                    updateMaps(currentGroup, subjectMap, dailyMap);
                }

                groups.add(currentGroup);
                while (!graph.get(current).isEmpty() && groups.size() < 10 - timetable.days[day].size() && !subjectMap.isEmpty()) {
                    List<Integer> indexes = new ArrayList<>();
                    for (int i : graph.get(current)) {
                        Group g = allGroups.get(i);
                        if (!canAddGroup(g, subjectMap, dailyMap, day, timetable.days[day].size() + groups.size()))
                            continue;
                        if (reference != null && ((g.subjects.size() != 1 || g.subjects.toArray(new Subject[0])[0] != Subject.Z_WYCH)
                                && !canAddGroup(g, subjectMap, dailyMap, day, timetable.days[day].size() + groups.size() + 1)))
                            continue;
                        indexes.add(i);
                    }

                    if (indexes.isEmpty()) {
                        break;
                    }
                    current = indexes.get((int) (Math.random() * indexes.size()));
                    currentGroup = allGroups.get(current);
                    updateMaps(currentGroup, subjectMap, dailyMap);

                    //Checking if it's possible to add 2 groups in a row
                    if (groups.size() + 1 < 11 - timetable.days[day].size() && canAddGroup(currentGroup, subjectMap, dailyMap, day, timetable.days[day].size() + groups.size() + 1)) {
                        groups.add(currentGroup);
                        updateMaps(currentGroup, subjectMap, dailyMap);
                    }

                    groups.add(currentGroup);
                }
                timetable.days[day].addAll(groups);
            }

            if (continueCounter == daysToCheck.size()) {
                for (int i : daysToCheck) {
                    timetable.days[i] = new ArrayList<>();
                }
                return fix(timetable, daysToCheck, tries + 1);
            }
        }

        //Checking whether there exists a student which has no lessons on a particular day
        for (Student student : Student.getStudents()) {
            if (!student.isTeacher) {
                dayLoop:
                for (List<Group> groups : timetable.days) {
                    for (Group g : groups) {
                        if (g.students.contains(student)) {
                            continue dayLoop;
                        }
                    }

                    for (int i : daysToCheck) {
                        timetable.days[i] = new ArrayList<>();
                    }
                    return fix(timetable, daysToCheck, tries + 1);
                }
            }
        }

        return true;
    }

    private boolean canAddGroup(Group currentGroup, Map<Subject, Integer> subjectMap, Map<Subject, Integer> dailyMap, int day, int i) {
        if (reference != null) {
            if (reference.days[day].size() > i) {
                Set<Subject> referenceSubjects = reference.days[day].get(i).subjects;
                List<String> teachers = new ArrayList<>();

                for (Subject s : referenceSubjects) {
                    if (teachersReference.containsKey(s)) {
                        teachers.addAll(teachersReference.get(s));
                    }
                }

                for (Student s : Student.getStudents()) {
                    if (s.isTeacher) {
                        for (Subject sub : currentGroup.subjects) {
                            if (s.subjects.contains(sub))
                                teachers.add(s.name);
                        }
                    }
                }

                if (new HashSet<>(teachers).size() != teachers.size()) {
                    return false;
                }
            }
        }


        for (Filter f : Filter.getFilters()) {
            if (currentGroup.students.contains(Student.byName(f.person()))) {
                for (Period period : f.periods()) {
                    if (period.isWithin(day, i)) {
                        return false;
                    }
                }
            }
        }

        for (Subject s : currentGroup.subjects) {
            if (!subjectMap.containsKey(s) || (dailyMap.containsKey(s) && dailyMap.get(s) == config.getInteger("maximum_daily_subjects"))) {
                return false;
            }

            for (Subject s2 : s.connectedTo) {
                if (dailyMap.containsKey(s2) && dailyMap.get(s2) == config.getInteger("maximum_daily_subjects")) {
                    return false;
                }
            }
        }

        return true;
    }

    private void updateMaps(Group currentGroup, Map<Subject, Integer> subjectMap, Map<Subject, Integer> dailyMap) {
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

            for (Subject s2 : s.connectedTo) {
                if (!dailyMap.containsKey(s2)) {
                    dailyMap.put(s2, 1);
                } else {
                    dailyMap.replace(s2, dailyMap.get(s2) + 1);
                }
            }
        }
    }
}
