package me.kp56.timetables.timetable;

import java.util.*;

public class Student {
    public static List<Student> students = new ArrayList<>();

    public Set<Timetable.Subject> subjects = new HashSet<>();
    public String name;

    public Student(String name) {
        this.name = name;
    }

    public Student(String name, Collection<Timetable.Subject> subjects) {
        this.name = name;
        for (Timetable.Subject subject : subjects) {
            this.subjects.add(subject);
            this.subjects.addAll(subject.connectedTo);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(subjects, student.subjects) && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjects, name);
    }

    @Override
    public String toString() {
        return name;
    }
}
