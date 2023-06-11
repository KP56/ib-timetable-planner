package me.kp56.timetables.timetable.combine;

import me.kp56.timetables.timetable.Subject;
import me.kp56.timetables.timetable.Timetable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Combiner {
    private List<Timetable> class1Timetables;
    private List<Timetable> class2Timetables;
    private Map<Subject, String> teachers1;
    private Map<Subject, String> teachers2;

    private Timetable res1;
    private Timetable res2;

    public Combiner(String class1, String class2, Map<Subject, String> teachers1, Map<Subject, String> teachers2) throws IOException {
        this.teachers1 = teachers1;
        this.teachers2 = teachers2;

        class1Timetables = collect("runs/" + class1);
        class2Timetables = collect("runs/" + class2);
    }

    private List<Timetable> collect(String pathStr) throws IOException {
        List<Timetable> timetables = new ArrayList<>();
        try (Stream<Path> stream = Files.walk(Paths.get(pathStr))) {
            stream.filter(Files::isRegularFile)
                    .forEach((path -> {
                        if (path.toString().endsWith(".timetable")) {
                            try {
                                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path.toFile()));
                                Timetable timetable = (Timetable) ois.readObject();
                                timetable.source = path.toString();
                                timetables.add(timetable);
                            } catch (IOException | ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }));
        }

        return timetables;
    }

    public Timetable getFirst() {
        return res1;
    }

    public Timetable getSecond() {
        return res2;
    }

    private List<String> extractTeachers(int day, int i, Timetable timetable, Map<Subject, String> teachers) {
        Map<Subject, List<String>> newMap = new HashMap<>();
        for (Map.Entry<Subject, String> entry : teachers.entrySet()) {
            newMap.put(entry.getKey(), List.of(entry.getValue().split(",")));
        }

        List<List<String>> l = new ArrayList<>(timetable.days[day].get(i).subjects.stream().map(newMap::get).filter(el -> el != null && !el.isEmpty() && (el.size() != 1 || !el.get(0).equals(""))).toList());
        List<String> returnVal = new ArrayList<>();
        for (List<String> l2 : l) returnVal.addAll(l2);
        return returnVal;
    }

    private boolean canCoexist(Timetable timetable1, Timetable timetable2) {
        for (int day = 0; day < 5; day++) {
            for (int i = 0; i < Math.min(timetable1.days[day].size(), timetable2.days[day].size()); i++) {
                List<String> teachers = extractTeachers(day, i, timetable1, teachers1);
                teachers.addAll(extractTeachers(day, i, timetable2, teachers2));
                //HashSet deletes all duplicates, so if the size of HashSet is lower than size of teachers then it means that there are duplicates
                if (new HashSet<>(teachers).size() != teachers.size()) {
                    return false;
                }
            }
        }

        return true;
    }

    public void combine() {
        double bestRes = 0;
        for (Timetable timetable1 : class1Timetables) {
            for (Timetable timetable2 : class2Timetables) {
                if (canCoexist(timetable1, timetable2)) {
                    double res = timetable1.fitness() + timetable2.fitness();
                    if (res > bestRes) {
                        bestRes = res;
                        res1 = timetable1;
                        res2 = timetable2;
                    }
                }
            }
        }
    }
}
