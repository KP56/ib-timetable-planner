package me.kp56.timetables.ui.editor;

import me.kp56.timetables.ui.about.HelpMenu;
import me.kp56.timetables.ui.filters.FilterMenu;
import me.kp56.timetables.ui.run.RunMenu;
import me.kp56.timetables.ui.settings.SettingsEditor;
import me.kp56.timetables.ui.settings.SettingsMenu;

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

        ActionListener actionListener = actionEvent -> {
            switch (actionEvent.getActionCommand()) {
                case "Open" -> StudentsSerializer.getInstance().open();
                case "Save" -> StudentsSerializer.getInstance().save();
                case "Save As" -> StudentsSerializer.getInstance().saveAs();
            }
            root.studentSelector.refresh();
            root.studentDetails.showStudent(null);
        };

        open.addActionListener(actionListener);
        save.addActionListener(actionListener);
        saveAs.addActionListener(actionListener);

        studentsMenu.add(open);
        studentsMenu.add(save);
        studentsMenu.add(saveAs);

        add(studentsMenu);

        add(new FilterMenu());
        add(new SettingsMenu());
        add(new HelpMenu());
        add(new RunMenu());
    }

}