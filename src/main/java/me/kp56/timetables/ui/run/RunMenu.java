package me.kp56.timetables.ui.run;

import javax.swing.*;
import java.io.File;

public class RunMenu extends JMenu {
    public RunMenu() {
        setText("Run");
        JMenuItem run = new JMenuItem("Run");
        run.addActionListener(actionEvent -> new PreRunSettings());

        add(run);

        JMenuItem combine = new JMenuItem("Combine");
        combine.addActionListener(actionEvent -> {
            File runsDir = new File("runs");
            if (!runsDir.exists()) {
                JOptionPane.showMessageDialog(new JFrame(), "No timetables have been generated. Use the run option first.",
                        "Use the run option first", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (runsDir.listFiles().length < 2) {
                JOptionPane.showMessageDialog(new JFrame(), "Combiner is used for combining 2 different classes. Generate timetables for 2 classes first.",
                        "Generate timetables for 2 classes first", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (runsDir.listFiles().length > 2) {
                new ClassSelection();
                return;
            }

            new CombinerSettings(runsDir.listFiles()[0].getName(), runsDir.listFiles()[1].getName());
        });

        add(combine);
    }
}
