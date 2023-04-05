package me.kp56.timetables.ui.editor.settings;

import me.kp56.timetables.configuration.Config;

import javax.swing.*;
import java.awt.*;

public class BooleanSettingDetail extends JPanel {
    private JCheckBox checkBox = new JCheckBox();
    public BooleanSettingDetail(String name) {
        setLayout(new FlowLayout());
        JLabel label = new JLabel(name + ":");
        add(label);
        add(checkBox);
    }

    public Boolean getValue() {
        return checkBox.isEnabled();
    }
    public void setValue(Boolean value){ checkBox.setSelected(value); }
}
