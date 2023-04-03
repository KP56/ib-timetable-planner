package me.kp56.timetables.ui.editor;

import me.kp56.timetables.timetable.Student;

import javax.swing.*;
import java.awt.*;

public class StudentSelector extends JList<Student> {
    DefaultListModel<Student> studentDefaultListModel = new DefaultListModel<>();

    public DefaultListModel<Student> getStudentDefaultListModel() {
        return studentDefaultListModel;
    }

    public StudentSelector() {
        setModel(studentDefaultListModel);
        setVisibleRowCount(-1);
        refresh();
    }

    public void refresh() {
        studentDefaultListModel.clear();
        for (Student student: Student.students) {
            studentDefaultListModel.addElement(student);
        }
    }
}
