package me.kp56.timetables.ui.about;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpMenu extends JMenu {
    public HelpMenu() {
        setText("Help");
        JMenuItem about = new JMenuItem("About");
        JMenuItem help = new JMenuItem("Help");
        JMenuItem licence = new JMenuItem("License");

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switch (actionEvent.getActionCommand()) {
                    case "License":
                        new HelpDialog("license.txt", "License").setVisible(true);
                        break;
                    case "About":
                        new HelpDialog("about.txt", "About").setVisible(true);
                        break;
                    case "Help":
                        new HelpDialog("help.txt", "Help").setVisible(true);
                        break;
                }
            }
        };

        about.addActionListener(actionListener);
        help.addActionListener(actionListener);
        licence.addActionListener(actionListener);

        add(help);
        add(about);
        add(licence);

    }
}
