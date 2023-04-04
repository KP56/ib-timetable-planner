package me.kp56.timetables.ui.editor;

import me.kp56.timetables.ui.ScreenSize;

import javax.swing.*;

public class StudentEditor extends JFrame {
    JSplitPane splitPane = new JSplitPane(); // remove
    StudentList studentSelector;
    StudentDetails studentDetails;

    public StudentEditor() {
        studentSelector = new StudentList(this);
        splitPane.setLeftComponent(studentSelector);
        studentDetails = new StudentDetails(this);
        splitPane.setRightComponent(studentDetails);
        setJMenuBar(new MenuBar(this));
        setTitle("Add & Edit Students");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(ScreenSize.getInstance().getWidth(), ScreenSize.getInstance().getHeight());
        getContentPane().add(splitPane);





        this.setVisible(true);

    }


}
