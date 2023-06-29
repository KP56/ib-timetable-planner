package me.kp56.timetables.ui.run;

import me.kp56.timetables.timetable.Timetable;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class RunMenu extends JMenu {
    public RunMenu() {
        setText("Run");
        JMenuItem run = new JMenuItem("Run");
        run.addActionListener(actionEvent -> new PreRunSettings(null, null));

        add(run);

        JMenuItem combine = new JMenuItem("Run with reference");
        combine.addActionListener(actionEvent -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileFilter(new FileNameExtensionFilter("Timetable files", "timetable"));
            int returnVal = jFileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File f = jFileChooser.getSelectedFile();
                try {
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                    Timetable timetable = (Timetable) ois.readObject();
                    timetable.source = f.toString();
                    new PreRunSettings(timetable.clazz, timetable);
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        add(combine);
    }
}
