package me.kp56.timetables.ui.editor;

import me.kp56.timetables.students.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentDetails extends JPanel {
    private StudentEditor root;
    CardLayout cardLayout = new CardLayout();
    SubjectSelector subjectSelector = new SubjectSelector();
    TextField studentNameField = new TextField(50);


    public StudentDetails(StudentEditor root) {
        this.root = root;
        setLayout(cardLayout);


        JPanel studentEditor = new JPanel();
        GroupLayout studentEditorLayout = new GroupLayout(studentEditor);
        studentEditorLayout.setAutoCreateGaps(true);
        studentEditorLayout.setAutoCreateContainerGaps(true);
        studentEditor.setLayout(studentEditorLayout);


        JLabel nameLabel = new JLabel("Student's name:");
        JLabel subjectLabel = new JLabel("Subjects:");


        JPanel wrappedStudentNameField = new JPanel();
        wrappedStudentNameField.add(studentNameField);

        JButton applyButton = new JButton("Apply");
        JButton deleteButton = new JButton("Delete");
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Student currentStudent = root.studentSelector.getSelected();
                switch (actionEvent.getActionCommand()) {
                    case "Apply":
                        if (studentNameField.getText().isEmpty()) {
                            currentStudent.name = "Bezimienny";
                        } else {
                            currentStudent.name = studentNameField.getText();
                        }
                        currentStudent.subjects = subjectSelector.getSubjects();
                        break;
                    case "Delete":
                        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this student?", "Confirm deletion", JOptionPane.YES_NO_OPTION) == 0) {
                            Student.students.remove(root.studentSelector.getSelected());
                            currentStudent = null;
                        }
                        break;
                }
                root.studentSelector.refresh();
                showStudent(currentStudent);
            }
        };

        applyButton.addActionListener(actionListener);
        deleteButton.addActionListener(actionListener);

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
        add("student", studentEditor);

        JLabel studentNotSelectedLabel = new JLabel("Student not selected.");
        studentNotSelectedLabel.setAlignmentX(CENTER_ALIGNMENT);
        JPanel studentNotSelectedWrapper = new JPanel();
        studentNotSelectedWrapper.setLayout(new BoxLayout(studentNotSelectedWrapper, BoxLayout.PAGE_AXIS));
        studentNotSelectedWrapper.add(studentNotSelectedLabel);
        add("none", studentNotSelectedWrapper);

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

