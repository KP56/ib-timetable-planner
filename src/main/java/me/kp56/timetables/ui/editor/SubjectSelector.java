package me.kp56.timetables.ui.editor;

import me.kp56.timetables.timetable.Subject;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class SubjectSelector extends JPanel {
    private Map<Subject, JCheckBox> buttons;
    private JPanel column1;

    public SubjectSelector() {
        buttons = new HashMap<>();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        for (Subject subject : Subject.values()) {
            JCheckBox button = new JCheckBox(subject.toString());
            this.add(button);
            button.setVisible(true);
            buttons.put(subject, button);
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
