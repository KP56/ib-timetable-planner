package me.kp56.timetables.ui.settings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsMenu extends JMenu {
    public SettingsMenu(){
        setText("Settings");
        JMenuItem settings = new JMenuItem("Settings");
        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new SettingsEditor().setVisible(true);
            }
        });

        add(settings);

    }
}
