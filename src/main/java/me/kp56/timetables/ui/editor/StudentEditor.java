package me.kp56.timetables.ui.editor;

import me.kp56.timetables.timetable.Student;
import me.kp56.timetables.ui.ScreenSize;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentEditor extends JSplitPane {
    StudentSelector studentSelector = new StudentSelector();
    JButton addStudentButton = new JButton("Add");
    JPanel studentList = new JPanel();
    StudentDetails studentDetails = new StudentDetails();
    public StudentEditor() {
        studentList.setLayout(new BoxLayout(studentList, BoxLayout.PAGE_AXIS));
        studentList.setPreferredSize(new Dimension(ScreenSize.getInstance().getWidth() / 5, ScreenSize.getInstance().getHeight()));
        addStudentButton.setAlignmentX(CENTER_ALIGNMENT);
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Student newStudent = new Student(JOptionPane.showInputDialog("Input name for student."));
                Student.students.add(newStudent);
                studentSelector.setSelectedValue(newStudent, true);
                studentSelector.refresh();
                studentDetails.showStudent(newStudent);
            }
        });

        studentSelector.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                studentDetails.showStudent(studentSelector.getSelectedValue());
            }
        });

        studentList.add(new JScrollPane(studentSelector));
        studentList.add(addStudentButton);
        this.setLeftComponent(studentList);

        studentDetails.deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Student.students.remove(studentSelector.getSelectedValue());
                studentSelector.refresh();
                studentDetails.showStudent(null);
            }
        });

        studentDetails.applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Student currentStudent = studentSelector.getSelectedValue();
                currentStudent.name = studentDetails.studentNameField.getText();
                currentStudent.subjects = studentDetails.subjectSelector.getSubjects();
                studentSelector.refresh();
                studentDetails.showStudent(currentStudent);
            }
        });
        this.setRightComponent(studentDetails);
    }
}
