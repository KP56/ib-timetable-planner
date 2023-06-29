package me.kp56.timetables.ui.run;

import me.kp56.timetables.students.Student;
import me.kp56.timetables.timetable.Timetable;
import me.kp56.timetables.timetable.Subject;
import me.kp56.timetables.timetable.fix.FixerCache;
import me.kp56.timetables.ui.ScreenSize;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwoClassTeachers extends JFrame {
    public TwoClassTeachers(String class1, String class2, Timetable reference) {
        super("Provide names of teachers");

        setName("Combine timetables");

        setSize(ScreenSize.getInstance().half());
        setLocationRelativeTo(null);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        String[] columnNames = {"Subjects", class1, class2};
        Object[][] data = new Object[Subject.values().length][3];

        Map<Subject, String> map1 = new HashMap<>();
        Map<Subject, String> map2 = new HashMap<>();

        if (Files.exists(Path.of("teachers_" + class1 + ".teachers"))) {
            try {
                FileInputStream fis = new FileInputStream("teachers_" + class1 + ".teachers");
                ObjectInputStream ois = new ObjectInputStream(fis);
                map1 = (Map<Subject, String>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        if (Files.exists(Path.of("teachers_" + class2 + ".teachers"))) {
            try {
                FileInputStream fis = new FileInputStream("teachers_" + class2 + ".teachers");
                ObjectInputStream ois = new ObjectInputStream(fis);
                map2 = (Map<Subject, String>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        for (int i = 0; i < Subject.values().length; i++) {
            data[i][0] = Subject.values()[i];
            data[i][1] = data[i][2] = "";
            if (map1.containsKey(data[i][0])) data[i][1] = map1.get(data[i][0]);
            if (map2.containsKey(data[i][0])) data[i][2] = map2.get(data[i][0]);
        }

        JTable table = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        table.setModel(tableModel);
        add(table);

        JScrollPane js = new JScrollPane(table);
        js.setVisible(true);
        add(js);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(actionEvent -> {
            if (table.getCellEditor() != null) {
                table.getCellEditor().stopCellEditing();
            }

            Map<Subject, String> teachers1 = new HashMap<>();
            Map<Subject, String> teachers2 = new HashMap<>();
            for (int i = 0; i < Subject.values().length; i++) {
                teachers1.put(Subject.values()[i], (String) table.getModel().getValueAt(i, 1));
                teachers2.put(Subject.values()[i], (String) table.getModel().getValueAt(i, 2));
            }

            try {
                FileOutputStream fos = new FileOutputStream("teachers_" + class1 + ".teachers");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(teachers1);

                FileOutputStream fos2 = new FileOutputStream("teachers_" + class2 + ".teachers");
                ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
                oos2.writeObject(teachers2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Map<String, List<Subject>> reverseMap = new HashMap<>();
            for (Map.Entry<Subject, String> entry : teachers2.entrySet()) {
                if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                    for (String teacher : entry.getValue().split(",")) {
                        if (!teacher.isEmpty()) {
                            if (!reverseMap.containsKey(teacher)) {
                                reverseMap.put(teacher, new ArrayList<>(List.of(entry.getKey())));
                            } else {
                                reverseMap.get(teacher).add(entry.getKey());
                            }
                        }
                    }
                }
            }

            //@Wasymir's idea, treating each teacher as a student who has the subjects they teach
            for (Map.Entry<String, List<Subject>> teacherEntry : reverseMap.entrySet()) {
                Student.addStudent(new Student(teacherEntry.getKey(), teacherEntry.getValue(), true));
            }

            Map<Subject, List<String>> referenceTeachers = new HashMap<>();
            for (Map.Entry<Subject, String> entry : teachers1.entrySet()) {
                if (!entry.getValue().isEmpty()) {
                    referenceTeachers.put(entry.getKey(), List.of(entry.getValue().split(",")));
                }
            }

            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            try {
                new RunDialog(class2, reference, referenceTeachers);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        add(submitButton);

        setVisible(true);
        //pack();
    }

    private static void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation)
            throws IOException {
        Files.walk(Paths.get(sourceDirectoryLocation))
                .forEach(source -> {
                    Path destination = Paths.get(destinationDirectoryLocation, source.toString()
                            .substring(sourceDirectoryLocation.length()));
                    try {
                        Files.copy(source, destination);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private static void deleteDirectory(File f) {
        for (File f2 : f.listFiles()) {
            f2.delete();
        }
        f.delete();
    }
}
