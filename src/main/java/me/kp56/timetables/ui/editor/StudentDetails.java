package me.kp56.timetables.ui.editor;

import me.kp56.timetables.timetable.Student;
import me.kp56.timetables.ui.ScreenSize;

import javax.swing.*;
import java.awt.*;

public class StudentDetails extends JPanel {
    CardLayout cardLayout = new CardLayout();
    JPanel studentEditor = new JPanel();
    GroupLayout studentEditorLayout;
    SubjectSelector subjectSelector = new SubjectSelector();
    TextField studentNameField = new TextField(50);
    JButton applyButton = new JButton("Apply");
    JButton deleteButton = new JButton("Delete");

    public StudentDetails() {
        studentEditorLayout = new GroupLayout(studentEditor);
        studentEditorLayout.setAutoCreateGaps(true);
        studentEditorLayout.setAutoCreateContainerGaps(true);


        setLayout(cardLayout);
        studentEditor.setLayout(studentEditorLayout);


        JLabel nameLabel = new JLabel("Student's name:");
        JLabel subjectLabel = new JLabel("Subjects:");
        JPanel wrappedStudentNameField = new JPanel();
        wrappedStudentNameField.add(studentNameField);


        studentEditorLayout.setAutoCreateGaps(true);
        studentEditorLayout.setHorizontalGroup(
                studentEditorLayout.createSequentialGroup()
                        .addGroup(
                                studentEditorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(nameLabel)
                                        .addComponent(studentNameField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        )
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(
                                studentEditorLayout.createParallelGroup()
                                        .addComponent(subjectLabel)
                                        .addComponent(subjectSelector)
                                        .addGroup(
                                                studentEditorLayout.createSequentialGroup()
                                                        .addComponent(deleteButton)
                                                        .addComponent(applyButton)
                                        )
                        )
        );
        studentEditorLayout.setVerticalGroup(
                studentEditorLayout.createSequentialGroup()
                        .addGroup(
                                studentEditorLayout.createParallelGroup()
                                        .addComponent(nameLabel)
                                        .addComponent(subjectLabel)
                        ).addGroup(
                                studentEditorLayout.createParallelGroup()
                                        .addComponent(studentNameField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(subjectSelector)
                        )
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(
                                studentEditorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(deleteButton)
                                        .addComponent(applyButton)
                        )
        );

        this.add("student", studentEditor);

        JPanel notSelected = new JPanel();
        notSelected.add(new Label("Student not selected"), BorderLayout.CENTER);
        this.add("none", notSelected);
        showStudent(null);
    }

    public void showStudent(Student student) {
        if (student == null) {
            cardLayout.show(this, "none");
            return;
        }

        studentNameField.setText(student.name);
        subjectSelector.setSubjects(student.subjects);
        cardLayout.show(this, "student");
    }




}

