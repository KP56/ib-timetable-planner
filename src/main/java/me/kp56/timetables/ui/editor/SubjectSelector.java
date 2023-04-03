package me.kp56.timetables.ui.editor;

import me.kp56.timetables.timetable.Timetable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.*;
import java.util.List;

// TODO scan Config for disabled subjects
public class SubjectSelector extends JPanel {
    private Map<Timetable.Subject, JCheckBox> buttons;

    public SubjectSelector() {
        buttons = new HashMap();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        for (Timetable.Subject subject : Timetable.Subject.values()) {
            JCheckBox button = new JCheckBox(subject.toString().replace('_' , ' '));
            this.add(button);
            button.setVisible(true);
            buttons.put(subject, button);
        }
    }


//    get list of selected items
    public Set<Timetable.Subject> getSubjects() {
        HashSet<Timetable.Subject> selected = new HashSet<>();
        for (Map.Entry<Timetable.Subject, JCheckBox> entry : buttons.entrySet()) {
            if (entry.getValue().isSelected()) {
                selected.add(entry.getKey());
            }
        }
        return selected;
    }

    public void setSubjects(Set<Timetable.Subject> subjects) {
        for (Map.Entry<Timetable.Subject, JCheckBox> entry : buttons.entrySet()) {
            entry.getValue().setSelected(subjects.contains(entry.getKey()));
        }
    }
}
