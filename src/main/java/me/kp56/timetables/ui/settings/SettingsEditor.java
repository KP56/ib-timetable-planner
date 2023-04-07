package me.kp56.timetables.ui.settings;

import me.kp56.timetables.ui.ScreenSize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsEditor extends JFrame {
    JSplitPane splitPane = new JSplitPane();
    SettingsList settingsList;
    SettingsDetails settingsDetails;
    public SettingsEditor() {
        settingsList = new SettingsList(this);
        settingsDetails = new SettingsDetails();
        setTitle("Settings");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(ScreenSize.getInstance().half());
        setLocationRelativeTo(null);
        Container contentPane = getContentPane();
        JPanel leftComponent = new JPanel();
        leftComponent.setLayout(new BoxLayout(leftComponent, BoxLayout.PAGE_AXIS));
        leftComponent.add(new JScrollPane(settingsList));
        leftComponent.setPreferredSize(new Dimension(ScreenSize.getInstance().getWidth() / 5, ScreenSize.getInstance().getHeight()));
        JButton applyButton = new JButton("Save");
        applyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                settingsDetails.apply();
                dispose();
                JOptionPane.showMessageDialog(null, "Settings has been updated", "Saved settings", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        leftComponent.add(applyButton);
        splitPane.setLeftComponent(leftComponent);
        splitPane.setRightComponent(settingsDetails);
        contentPane.add(splitPane);

    }

}
