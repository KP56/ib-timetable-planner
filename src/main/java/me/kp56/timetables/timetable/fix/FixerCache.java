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
    private List<List<Integer>> revGraph;
    private List<Group> allGroups;

    private List<Integer> revTopSort;
    private Integer[] longestPathFrom;
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

                if (timetable.days[day].size() >= 10) {
                    continueCounter++;
                    continue;
                }

                List<Group> groups = new ArrayList<>();
                List<Integer> unnormalized = new ArrayList<>();
                List<Integer> unnormalizedIndexes = new ArrayList<>();
                for (int i = 0; i < longestPathFrom.length; i++) {
                    Group g = allGroups.get(i);
                    if (!g.isMaximal) continue;
                    if (!canAddGroup(g, subjectMap, dailyMap, day, timetable.days[day].size() + groups.size()))
                        continue;
                    unnormalized.add(longestPathFrom[i]);
                    unnormalizedIndexes.add(i);
                }

                if (unnormalized.isEmpty()) {
                    for (int i = 0; i < longestPathFrom.length; i++) {
                        Group g = allGroups.get(i);
                        if (!canAddGroup(g, subjectMap, dailyMap, day, timetable.days[day].size() + groups.size()))
                            continue;
                        unnormalized.add(longestPathFrom[i]);
                        unnormalizedIndexes.add(i);
                    }
                }

                if (unnormalized.isEmpty()) {
                    continueCounter++;
                    continue;
                }
                List<Double> probabilities = Runner.normalizeProbabilities(unnormalized);
                int current = unnormalizedIndexes.get(Runner.randomItem(probabilities));
                Group currentGroup = allGroups.get(current);
                updateMaps(currentGroup, subjectMap, dailyMap);
                if (groups.size() + 1 < 11 - timetable.days[day].size() && canAddGroup(currentGroup, subjectMap, dailyMap, day, timetable.days[day].size() + groups.size() + 1)) {
                    groups.add(currentGroup);
                    updateMaps(currentGroup, subjectMap, dailyMap);
                }

                groups.add(currentGroup);
                while (!graph.get(current).isEmpty() && groups.size() < 10 - timetable.days[day].size() && !subjectMap.isEmpty()) {
                    List<Integer> copy = new ArrayList<>();
                    List<Integer> indexes = new ArrayList<>();
                    for (int i : graph.get(current)) {
                        Group g = allGroups.get(i);
                        if (!canAddGroup(g, subjectMap, dailyMap, day, timetable.days[day].size() + groups.size()))
                            continue;
                        copy.add(longestPathFrom[i]);
                        indexes.add(i);
                    }

                    if (copy.isEmpty()) {
                        break;
                    }
                    List<Double> newProbabilities = Runner.normalizeProbabilities(copy);
                    current = indexes.get(Runner.randomItem(newProbabilities));
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
        for (Student student : Student.students) {
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

    private boolean containsEvenSubject(Group group) {
        for (Subject s : group.subjects) {
            if (s.limit % 2 == 0) {
                return true;
            }
        }

        return false;
    }

    private boolean canAddGroupForSecondTime(Group currentGroup, Map<Subject, Integer> subjectMap, Map<Subject, Integer> dailyMap) {
        for (Subject s : currentGroup.subjects) {
            if (Objects.equals(subjectMap.get(s), 1) || Objects.equals(dailyMap.get(s), config.getInteger("maximum_daily_subjects") - 1)) {
                return false;
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
                    teachers.addAll(teachersReference.get(s));
                }

                for (Student s : Student.students) {
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

        for (Student student : currentGroup.students) {
            Filter f = Filter.byName(student.name);

            if (f != null) {
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
        }
    }
}
