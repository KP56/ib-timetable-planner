package me.kp56.timetables.ui.run;

import me.kp56.timetables.timetable.Timetable;
import me.kp56.timetables.ui.ScreenSize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class PreRunSettings extends JFrame {
    public PreRunSettings(String referenceClass, Timetable reference) {
        super("Provide class name");

        setName("Provide class name");
        setSize(new Dimension(ScreenSize.getInstance().getWidth() / 8, ScreenSize.getInstance().getHeight() / 8));
        setLocationRelativeTo(null);
        Container contentPane = getContentPane();

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());
        add(northPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        add(southPanel, BorderLayout.SOUTH);

        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        northPanel.add(new JLabel("Provide class name:"));
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(ScreenSize.getInstance().getWidth() / 16, ScreenSize.getInstance().getHeight() / 32));
        centerPanel.add(field);
        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(actionEvent -> {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            if (referenceClass == null) {
                new SingleClassTeachers(field.getText());
            } else {
                new TwoClassTeachers(referenceClass, field.getText(), reference);
            }
        });
        southPanel.add(selectButton);
        setVisible(true);

        pack();
    }
}
