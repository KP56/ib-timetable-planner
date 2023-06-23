package me.kp56.timetables.ui.editor;

import me.kp56.timetables.timetable.Subject;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class SubjectSelector extends JPanel {
    private Map<Subject, JCheckBox> buttons;
    private JPanel column1;
    private JPanel column2;

    public SubjectSelector() {
        buttons = new HashMap<>();
        column1 = new JPanel();
        column2 = new JPanel();
        column1.setLayout(new BoxLayout(column1, BoxLayout.PAGE_AXIS));
        column2.setLayout(new BoxLayout(column2, BoxLayout.PAGE_AXIS));
        this.setLayout(new GridLayout(1, 2));
        this.add(column1);
        this.add(column2);

        for (Subject subject : Subject.values()) {
            JCheckBox button = new JCheckBox(subject.toString());
            button.setVisible(true);
            buttons.put(subject, button);
            if (subject.toString().contains("2")) {
                column2.add(button);
            } else {
                column1.add(button);
            }
        }
    }


    //get list of selected items
    public Set<Subject> getSubjects() {
        HashSet<Subject> selected = new HashSet<>();
        for (Map.Entry<Subject, JCheckBox> entry : buttons.entrySet()) {
            if (entry.getValue().isSelected()) {
                selected.add(entry.getKey());
            }
        }
        return selected;
    }

    public void setSubjects(Set<Subject> subjects) {
        for (Map.Entry<Subject, JCheckBox> entry : buttons.entrySet()) {
            entry.getValue().setSelected(subjects.contains(entry.getKey()));
        }
    }
}
