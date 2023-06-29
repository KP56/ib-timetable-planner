package me.kp56.timetables.ui.run;

import me.kp56.timetables.students.Student;
import me.kp56.timetables.timetable.Subject;
import me.kp56.timetables.ui.ScreenSize;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleClassTeachers extends JFrame {
    public SingleClassTeachers(String className) {
        super("Provide names of teachers");

        setName("Specify class teachers");

        setSize(ScreenSize.getInstance().half());
        setLocationRelativeTo(null);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        String[] columnNames = {"Subjects", className};
        Object[][] data = new Object[Subject.values().length][2];

        Map<Subject, String> map = new HashMap<>();

        if (Files.exists(Path.of("teachers_" + className + ".teachers"))) {
            try {
                FileInputStream fis = new FileInputStream("teachers_" + className + ".teachers");
                ObjectInputStream ois = new ObjectInputStream(fis);
                map = (Map<Subject, String>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        for (int i = 0; i < Subject.values().length; i++) {
            data[i][0] = Subject.values()[i];
            data[i][1] = "";
            if (map.containsKey(data[i][0])) data[i][1] = map.get(data[i][0]);
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

            Map<Subject, String> teachers = new HashMap<>();
            for (int i = 0; i < Subject.values().length; i++) {
                teachers.put(Subject.values()[i], (String) table.getModel().getValueAt(i, 1));
            }

            try {
                FileOutputStream fos = new FileOutputStream("teachers_" + className + ".teachers");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(teachers);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Map<String, List<Subject>> reverseMap = new HashMap<>();
            for (Map.Entry<Subject, String> entry : teachers.entrySet()) {
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

            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            try {
                new RunDialog(className, null, null);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        add(submitButton);

        setVisible(true);
    }
}
