package me.kp56.timetables.ui.editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {
    private StudentEditor root;


    public MenuBar(StudentEditor root) {
        JMenu studentsMenu = new JMenu("Students");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem saveAs = new JMenuItem("Save As");

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switch (actionEvent.getActionCommand()) {
                    case "Open":
                        StudentsSerializer.getInstance().open();
                        break;
                    case "Save":
                        StudentsSerializer.getInstance().save();
                        break;
                    case "Save As":
                        StudentsSerializer.getInstance().saveAs();
                        break;
                }
                root.studentSelector.refresh();
                root.studentDetails.showStudent(null);
            }
        };

        open.addActionListener(actionListener);
        save.addActionListener(actionListener);
        saveAs.addActionListener(actionListener);

        studentsMenu.add(open);
        studentsMenu.add(save);
        studentsMenu.add(saveAs);


        setOpaque(true);
        setBackground(Color.GRAY);
        add(studentsMenu);
    }

}