package me.kp56.timetables.students;

import me.kp56.timetables.timetable.Subject;

import java.io.*;
import java.util.*;

public class Student implements Serializable {
    public static List<Student> students = new ArrayList<>();

    public Set<Subject> subjects = new HashSet<>();
    public String name;
    public boolean isTeacher;

    public Student(String name) {
        this.name = name;
    }

    public Student(String name, Collection<Subject> subjects, boolean teacher) {
        this(name, subjects);

        this.isTeacher = teacher;
    }

    public Student(String name, Collection<Subject> subjects) {
        this.isTeacher = false;

        this.name = name;
        for (Subject subject : subjects) {
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
