package me.kp56.timetables.ui.run;

import me.kp56.timetables.timetable.Runner;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RunMenu extends JMenu {
    public RunMenu() {
        setText("Run");
        JMenuItem run = new JMenuItem("Run");
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    new RunDialog();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        add(run);
    }
}
