package me.kp56.timetables.timetable;

import me.kp56.timetables.configuration.Config;

import java.io.Serializable;
import java.util.*;

public class Group implements Serializable {
    private static final Config config = Config.getInstance();

    public Set<Subject> subjects;
    public Set<Student> students = new HashSet<>();

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
            if (!subject.disabled()) {
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
