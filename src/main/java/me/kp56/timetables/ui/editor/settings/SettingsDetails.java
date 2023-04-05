package me.kp56.timetables.ui.editor.settings;

import me.kp56.timetables.configuration.Config;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SettingsDetails extends JPanel {
    CardLayout layout = new CardLayout();
    private HashMap<String, ValueSettingDetail> valueSettings = new HashMap<>();
    private HashMap<String, BooleanSettingDetail> booleanSettings = new HashMap<>();

    public SettingsDetails() {
        setLayout(layout);
        for (String name : Config.getInstance().getValueSettings().keySet()) {
            ValueSettingDetail detail = new ValueSettingDetail(name);
            valueSettings.put(name, detail);
            add(name, detail);
        }
        for (String name : Config.getInstance().getBooleanSettings().keySet()) {
            BooleanSettingDetail detail = new BooleanSettingDetail(name);
            booleanSettings.put(name, detail);
            add(name, detail);
        }
        add("none", new Label("Setting not selected"));
        refresh();
    }

    public void show(String id) {
        if (id == null) {
            layout.show(this, "none");
        } else {
            layout.show(this, id);
        }

    }

    public void apply() {
        for (Map.Entry<String, ValueSettingDetail> setting: valueSettings.entrySet()) {
            Config.getInstance().set(setting.getKey(), setting.getValue().getValue());
        }
        for (Map.Entry<String, BooleanSettingDetail> setting: booleanSettings.entrySet()) {
            Config.getInstance().set(setting.getKey(), setting.getValue().getValue());
        }
    }

    public void refresh() {
        for (Map.Entry<String, ValueSettingDetail> setting: valueSettings.entrySet()) {
            setting.getValue().setValue(Config.getInstance().getInteger(setting.getKey()));
        }
        for (Map.Entry<String, BooleanSettingDetail> setting: booleanSettings.entrySet()) {
            setting.getValue().setValue(Config.getInstance().getBoolean(setting.getKey()));
        }
    }
}
