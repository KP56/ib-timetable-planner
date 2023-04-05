package me.kp56.timetables.ui.editor;

import me.kp56.timetables.ui.about.HelpMenu;
import me.kp56.timetables.ui.editor.settings.SettingsEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {
    private StudentEditor root;



    public MenuBar(StudentEditor root) {
        setOpaque(true);
        setBackground(Color.GRAY);

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

        add(studentsMenu);

        JMenu settingsMenu = new JMenu("Settings");
        JMenuItem settings = new JMenuItem("Settings");
        settings.addActionListener((a) -> new SettingsEditor().setVisible(true));
        settingsMenu.add(settings);
        add(settingsMenu);
        add(new HelpMenu());
    }

}