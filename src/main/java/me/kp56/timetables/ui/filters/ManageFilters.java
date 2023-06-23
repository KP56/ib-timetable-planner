package me.kp56.timetables.ui.filters;

import me.kp56.timetables.filters.Filter;
import me.kp56.timetables.filters.Period;
import me.kp56.timetables.ui.ScreenSize;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ManageFilters extends JFrame {
    private JPanel filterDetails = new JPanel();
    private DefaultListModel<Filter> filterModel = new DefaultListModel<>();
    private JPanel studentNotSelectedWrapper = new JPanel();
    private int selectedRow = -1;
    private int selectedColumn = -1;
    private List<Period> selectedPeriods;

    public ManageFilters() {
        super("Manage filters");

        JSplitPane splitPane = new JSplitPane();

        JPanel filterSelector = new JPanel();

        filterSelector.setLayout(new BoxLayout(filterSelector, BoxLayout.PAGE_AXIS));

        JList<Filter> filters = new JList<>();
        filters.setModel(filterModel);
        filters.setVisibleRowCount(-1);
        filters.addListSelectionListener(event -> {
            if (filters.getSelectedValue() != null) {
                selected(filters.getSelectedValue());
            }
        });

        for (Filter f : Filter.getFilters()) {
            filterModel.addElement(f);
        }

        filterSelector.add(new JScrollPane(filters));

        JButton addStudentButton = new JButton("ADD");
        addStudentButton.setAlignmentX(CENTER_ALIGNMENT);
        addStudentButton.addActionListener(actionEvent -> {
            String name = JOptionPane.showInputDialog(null, "Enter the name of the person related to the filter:", "Person's name");
            if (name.isEmpty()) {
                name = "Bezimienny";
            }

            Filter filter = new Filter(name, new ArrayList<>(List.of()));
            refresh();
        });
        filterSelector.add(addStudentButton);

        splitPane.setLeftComponent(filterSelector);
        splitPane.setRightComponent(filterDetails);

        setTitle("Add & Edit Filters");
        setSize(ScreenSize.getInstance().half());
        getContentPane().add(splitPane);

        filters.setPreferredSize(new Dimension(getWidth() / 5, getHeight()));

        JLabel studentNotSelectedLabel = new JLabel("Filter not selected.");
        studentNotSelectedLabel.setAlignmentX(CENTER_ALIGNMENT);
        studentNotSelectedWrapper.setLayout(new BoxLayout(studentNotSelectedWrapper, BoxLayout.PAGE_AXIS));
        studentNotSelectedWrapper.add(studentNotSelectedLabel);
        filterDetails.add("none", studentNotSelectedWrapper);

        setVisible(true);
    }

    private void refresh() {
        filterModel.removeAllElements();
        for (Filter f : Filter.getFilters()) {
            filterModel.addElement(f);
        }

        setVisible(true);
    }

    private void selected(Filter filter) {
        selectedPeriods = new ArrayList<>(filter.periods());

        filterDetails.removeAll();
        filterDetails.setLayout(new CardLayout());
        JPanel filterEditor = new JPanel();
        GroupLayout filterEditorLayout = new GroupLayout(filterEditor);
        filterEditorLayout.setAutoCreateGaps(true);
        filterEditorLayout.setAutoCreateContainerGaps(true);
        filterEditor.setLayout(filterEditorLayout);

        JTextField personNameField = new JTextField();
        personNameField.setPreferredSize(new Dimension(ScreenSize.getInstance().getWidth() / 16, ScreenSize.getInstance().getHeight() / 40));
        personNameField.setText(filter.person());

        JLabel nameLabel = new JLabel("Person's Name:");
        JLabel timetableLabel = new JLabel("Period Selection:");

        JButton applyButton = new JButton("Apply");
        JButton deleteButton = new JButton("Delete");

        deleteButton.addActionListener(listener -> {
            Filter.deleteFilter(filter.person());
            filterDetails.removeAll();
            filterDetails.add("none", studentNotSelectedWrapper);

            refresh();
            repaint();
        });

        applyButton.addActionListener(listener -> {
            Filter.deleteFilter(filter.person());
            Filter editedFilter = new Filter(personNameField.getText(), selectedPeriods);

            refresh();
        });

        JScrollPane tableScrollPane = new JScrollPane(generateTable());

        filterEditorLayout.setHorizontalGroup(
                filterEditorLayout.createSequentialGroup()
                        .addGroup(
                                filterEditorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(nameLabel)
                                        .addComponent(personNameField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        )
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(
                                filterEditorLayout.createParallelGroup()
                                        .addComponent(timetableLabel)
                                        .addComponent(tableScrollPane)
                                        .addGroup(
                                                filterEditorLayout.createSequentialGroup()
                                                        .addComponent(deleteButton)
                                                        .addComponent(applyButton)
                                        )
                        )
        );
        filterEditorLayout.setVerticalGroup(
                filterEditorLayout.createSequentialGroup()
                        .addGroup(
                                filterEditorLayout.createParallelGroup()
                                        .addComponent(nameLabel)
                                        .addComponent(timetableLabel)
                        )
                        .addGroup(
                                filterEditorLayout.createParallelGroup()
                                        .addComponent(personNameField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tableScrollPane)
                        )
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(
                                filterEditorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(deleteButton)
                                        .addComponent(applyButton)
                        )
        );
        filterDetails.add("filter", filterEditor);

        setVisible(true);
    }

    private JTable generateTable() {
        String[][] data = new String[11][6];
        for (int i = 1; i <= 11; i++) {
            data[i - 1][0] = String.valueOf(i);
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, new String[]{"", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Disable editing for days of the week and specific lessons
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setCellSelectionEnabled(true);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected,
                        hasFocus, row, column);
                if (selectedPeriods.stream().anyMatch(filter -> filter.isWithin(column - 1, row))) {
                    rendererComponent.setBackground(Color.RED);
                } else {
                    rendererComponent.setBackground(table.getBackground());
                }
                return rendererComponent;
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int clickedRow = table.rowAtPoint(e.getPoint());
                int clickedColumn = table.columnAtPoint(e.getPoint());
                if (clickedColumn == 0) return;

                for (Period period : selectedPeriods) {
                    if (period.isWithin(clickedColumn - 1, clickedRow)) {
                        selectedPeriods.remove(period);
                        tableModel.fireTableRowsUpdated(0, 11);
                        selectedRow = -1;
                        selectedColumn = -1;
                        return;
                    }
                }

                if (selectedRow == -1 && selectedColumn == -1) {
                    // First selection
                    selectedRow = clickedRow;
                    selectedColumn = clickedColumn;
                } else {
                    // Second selection

                    int startRow = selectedRow;
                    int endRow = clickedRow;
                    int startColumn = selectedColumn;
                    int endColumn = clickedColumn;

                    if (isGreaterOrEqual(selectedColumn, selectedRow, clickedColumn, clickedRow)) {
                        startRow = clickedRow;
                        startColumn = clickedColumn;
                        endRow = selectedRow;
                        endColumn = selectedColumn;
                    }

                    Period newPeriod = new Period(startColumn - 1, startRow, endColumn - 1, endRow);
                    for (Period period : selectedPeriods) {
                        if (period.intersect(newPeriod)) {
                            selectedRow = -1;
                            selectedColumn = -1;
                            return;
                        }
                    }

                    selectedPeriods.add(newPeriod);

                    tableModel.fireTableRowsUpdated(0, 11);

                    selectedRow = -1;
                    selectedColumn = -1;
                }
            }
        });

        return table;
    }

    private boolean isGreaterOrEqual(int day1, int lesson1, int day2, int lesson2) {
        if (day1 > day2) {
            return true;
        } else if (day1 < day2) {
            return false;
        } else {
            if (lesson1 >= lesson2) {
                return true;
            } else {
                return false;
            }
        }
    }
}
