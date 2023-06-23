package me.kp56.timetables.ui.filters;

import javax.swing.*;

public class FilterMenu extends JMenu {
    public FilterMenu() {
        setText("Filter");
        JMenuItem filter = new JMenuItem("Filter");

        filter.addActionListener(actionEvent -> new ManageFilters());

        add(filter);
    }
}
