package me.kp56.timetables.ui.run;

import me.kp56.timetables.ui.ScreenSize;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ClassSelection extends JFrame {
    public ClassSelection() {
        super("Select classes");

        setName("Class selection");

        setSize(ScreenSize.getInstance().half());
        setLocationRelativeTo(null);
        Container contentPane = getContentPane();

        JPanel leftComponent = new JPanel();
        leftComponent.setLayout(new BoxLayout(leftComponent, BoxLayout.PAGE_AXIS));

        JList<String> list = new JList<>();
        DefaultListModel<String> listModel = new DefaultListModel<>();

        File runsDir = new File("runs");
        for (File f : runsDir.listFiles()) {
            listModel.addElement(f.getName());
        }

        list.setModel(listModel);

        leftComponent.add(new JScrollPane(list));
        leftComponent.setPreferredSize(new Dimension(ScreenSize.getInstance().getWidth() / 5, ScreenSize.getInstance().getHeight()));

        contentPane.add(leftComponent);

        setVisible(true);
    }
}
