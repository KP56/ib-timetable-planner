package me.kp56.timetables.ui.settings;

import me.kp56.timetables.configuration.Config;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SettingsList extends JList<String> {
    private SettingsEditor root;
    public SettingsList(SettingsEditor root) {
        this.root = root;
        setVisibleRowCount(-1);
        addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                root.settingsDetails.show(getSelectedValue());
            }
        });


        DefaultListModel<String> listModel = new DefaultListModel<>();
        setModel(listModel);
        for (String name: Config.getInstance().getValueSettings().keySet()) {
            listModel.addElement(name);
        }
        for (String name: Config.getInstance().getBooleanSettings().keySet()) {
            listModel.addElement(name);
        }



    }
}
