package me.kp56.timetables.ui.editor.settings;

import me.kp56.timetables.configuration.Config;

import javax.swing.*;
import java.awt.*;

public class ValueSettingDetail extends JPanel {
    private JSpinner spinner = new JSpinner();
    public ValueSettingDetail(String name) {
        setLayout(new FlowLayout());
        JLabel label = new JLabel(name + ":");
        add(label);
        add(spinner);
    }

    public Integer getValue() {
        return (Integer) spinner.getValue();
    }
    public void setValue(Integer value){spinner.setValue(value);}
}
