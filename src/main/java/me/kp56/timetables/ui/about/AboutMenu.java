package me.kp56.timetables.ui.about;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutMenu extends JMenu {
    public AboutMenu() {
        setText("About");
//        JMenuItem creators = new JMenuItem("Creators");
//        JMenuItem help = new JMenuItem("Help");
        JMenuItem licence = new JMenuItem("License");

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switch (actionEvent.getActionCommand()) {
                    case "License":
                        new License().setVisible(true);
                        break;
                }
            }
        };

//        creators.addActionListener(actionListener);
//        help.addActionListener(actionListener);
        licence.addActionListener(actionListener);

//        add(help);
//        add(creators);
        add(licence);

    }
}
