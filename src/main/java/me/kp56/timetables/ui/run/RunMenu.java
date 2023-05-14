package me.kp56.timetables.ui.run;

import javax.swing.*;

public class RunMenu extends JMenu {
    public RunMenu() {
        setText("Run");
        JMenuItem run = new JMenuItem("Run");
        run.addActionListener(actionEvent -> new PreRunSettings());

        add(run);
    }
}
