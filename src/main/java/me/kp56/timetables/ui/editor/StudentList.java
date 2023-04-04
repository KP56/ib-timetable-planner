package me.kp56.timetables.ui.editor;

import me.kp56.timetables.timetable.Student;
import me.kp56.timetables.ui.ScreenSize;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentList extends JPanel {

    private StudentEditor root;
    private DefaultListModel<Student> studentDefaultListModel = new DefaultListModel<>();
    private JList<Student> studentList = new JList<>();

    public StudentList(StudentEditor root) {
        this.root = root;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        studentList.setModel(studentDefaultListModel);
        studentList.setVisibleRowCount(-1);
        studentList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                root.studentDetails.showStudent(getSelected());
            }
        });

        add(new JScrollPane(studentList));

        JButton addStudentButton = new JButton("ADD");
        addStudentButton.setAlignmentX(CENTER_ALIGNMENT);
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = JOptionPane.showInputDialog(null, "Enter new student's name:", "Add student");
                if (name.isEmpty()) {
                    name = "Bezimienny";
                }
                Student newStudent = new Student(name);
                Student.students.add(newStudent);
                refresh();
                root.studentDetails.showStudent(newStudent);
            }
        });

        add(addStudentButton);

        setPreferredSize(new Dimension(ScreenSize.getInstance().getWidth() / 5, ScreenSize.getInstance().getHeight()));


        refresh();
    }

    public void refresh() {
        studentDefaultListModel.clear();
        for (Student student : Student.students) {
            studentDefaultListModel.addElement(student);
        }
    }

    public Student getSelected() {
        return studentList.getSelectedValue();
    }
}
