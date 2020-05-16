package lab2.searchPanels;

import lab2.Controller;
import lab2.TableInfo;
import lab2.Train;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

public class TripTimeSearchPanel {
    private final JLabel pageCountLabel = new JLabel();
    private final JLabel countActiveRowsLabel = new JLabel();
    private final JPanel searchPanel = new JPanel(new GridLayout(1, 2, 2, 2));
    private final JPanel searchPrevNextPanel = new JPanel(new GridLayout(2, 1, 2, 2));
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JLabel stationNameLabel = new JLabel("    Station name: ");
    private final JTextField stationNameField = new JTextField();
    private final JButton backSearchPanelButton = new JButton("<-- Back");
    private final JButton searchButton = new JButton("Search");
    private final JButton updateTableButton = new JButton("Update");
    private int virtualPage;
    private int rowCount;
    private int defaultItemsPerPage = 10;
    private int maxPageIndex;
    private int currentPageIndex = 1;

    private String[] columnNames;
    private DefaultTableModel model;
    private java.util.List<Train> trainsList;
    private TableRowSorter<TableModel> sorter;
    private JTable mainTable;

    public TripTimeSearchPanel() {
        columnNames = Controller.generateTableHeader();
        model = new DefaultTableModel(null, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                return (column == 0) ? Integer.class : Object.class;
            }

            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        sorter = new TableRowSorter<TableModel>(model);
        mainTable = new JTable(model);
    }


    private final JButton first = new JButton(new AbstractAction("|<") {
        @Override
        public void actionPerformed(ActionEvent e) {
            currentPageIndex = 1;
            initFilterAndButton();
        }
    });
    private final JButton prev = new JButton(new AbstractAction("<") {
        @Override
        public void actionPerformed(ActionEvent e) {
            currentPageIndex -= 1;
            initFilterAndButton();
        }
    });
    private final JButton next = new JButton(new AbstractAction(">") {
        @Override
        public void actionPerformed(ActionEvent e) {
            currentPageIndex += 1;
            initFilterAndButton();
        }
    });
    private final JButton last = new JButton(new AbstractAction(">|") {
        @Override
        public void actionPerformed(ActionEvent e) {
            currentPageIndex = maxPageIndex;
            initFilterAndButton();
        }
    });

    private final JTextField field = new JTextField(2);


    public JComponent makeUI(java.util.List<Object[]> studentList) throws ParseException{
        mainTable.setFillsViewportHeight(true);
        mainTable.setRowSorter(sorter);
        for (int i = 0; i < studentList.size(); i++) {
            model.addRow(studentList.get(i));

        }
        for (JComponent r : Arrays.asList(stationNameLabel, stationNameField)) {
            searchPanel.add(r);
        }

        JPanel po = new JPanel();
        po.add(field);
        po.add(pageCountLabel);
        JPanel box = new JPanel(new GridLayout(1, 4, 2, 2));
        for (JComponent r : Arrays.asList(first, prev, po, next, last)) {
            box.add(r);
        }

        rowCount = model.getRowCount();
        virtualPage = rowCount % defaultItemsPerPage == 0 ? 0 : 1;
        maxPageIndex = rowCount / defaultItemsPerPage + virtualPage;
        initFilterAndButton();

        JPanel panelInfoPages = new JPanel(new GridLayout(1, 0, 5, 12));

        if (defaultItemsPerPage <= rowCount) {
            countActiveRowsLabel.setText(String.format("Activ rows: %d", defaultItemsPerPage));
        } else {
            countActiveRowsLabel.setText(String.format("Activ rows: %d", rowCount));
        }
        JLabel rowCountLabel = new JLabel(String.format("All rows: %d", rowCount));
        JTextField countPagesfield = new JTextField(maxPageIndex);

        for (JComponent r : Arrays.asList(countActiveRowsLabel, rowCountLabel, countPagesfield, backSearchPanelButton, searchButton, updateTableButton)) {
            panelInfoPages.add(r);
        }



        pageCountLabel.setText(String.format("/ %d", maxPageIndex));
        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        field.getInputMap(JComponent.WHEN_FOCUSED).put(enter, "Enter");


        field.getActionMap().put("Enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int v = Integer.parseInt(field.getText());
                    if (v > 0 && v <= maxPageIndex) {
                        currentPageIndex = v;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                initFilterAndButton();
            }
        });

        backSearchPanelButton.addActionListener(actionEvent -> {
            Frame mainFrame[] = JFrame.getFrames();
            for (Frame frame : mainFrame) {
                frame.setVisible(false);
            }
            try {
                SearchPanel.createAndShowGUI();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        searchButton.addActionListener(actionEvent -> {
            try {
                String stationName = stationNameField.getText();
                if (!stationName.equals("")) {
                    List<Object[]> foundStudentList = Controller.tripTimeSearch(stationName);
                    Frame mainFrame[] = JFrame.getFrames();
                    for (Frame frame : mainFrame) {
                        frame.setVisible(false);
                    }
                    createAndShowGUI(foundStudentList);
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Fill in all the fields!", "Error!", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(mainPanel, "Error", "Error!", JOptionPane.ERROR_MESSAGE);
            }

        });

        updateTableButton.addActionListener(actionEvent -> {
            Frame mainFrame[] = JFrame.getFrames();
            for (Frame frame : mainFrame) {
                frame.setVisible(false);
            }
            try {
                createAndShowGUI(TableInfo.getTrainsListArray());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        countPagesfield.addActionListener(actionEvent -> {

            if (rowCount < Integer.valueOf(countPagesfield.getText())) {
                JOptionPane.showMessageDialog(mainPanel, "Error", "Error!", JOptionPane.ERROR_MESSAGE);
                initFilterAndButton();
            } else {

                defaultItemsPerPage = Integer.valueOf(countPagesfield.getText());
                rowCount = model.getRowCount();
                virtualPage = rowCount % defaultItemsPerPage == 0 ? 0 : 1;
                maxPageIndex = rowCount / defaultItemsPerPage + virtualPage;
                initFilterAndButton();

                countPagesfield.setText("");
                pageCountLabel.setText(String.format("/ %d", maxPageIndex));

                countActiveRowsLabel.setText(String.format("Activ rows: %d", defaultItemsPerPage));

            }
        });

        searchPrevNextPanel.add(searchPanel);
        searchPrevNextPanel.add(box);
        mainPanel.add(searchPrevNextPanel, BorderLayout.NORTH);
        mainPanel.add(panelInfoPages, BorderLayout.SOUTH);
        mainPanel.add(new JScrollPane(mainTable));

        return mainPanel;
    }

    private void initFilterAndButton() {
        sorter.setRowFilter(new RowFilter<>() {
            @Override
            public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
                int ti = currentPageIndex - 1;
                int ei = entry.getIdentifier();
                return ti * defaultItemsPerPage <= ei && ei < ti * defaultItemsPerPage + defaultItemsPerPage;
            }
        });
        first.setEnabled(currentPageIndex > 1);
        prev.setEnabled(currentPageIndex > 1);
        next.setEnabled(currentPageIndex < maxPageIndex);
        last.setEnabled(currentPageIndex < maxPageIndex);
        field.setText(Integer.toString(currentPageIndex));
    }

    public static void createAndShowGUI(List<Object[]> trainList) throws ParseException {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.getContentPane().add(new TripTimeSearchPanel().makeUI(trainList));
        f.setSize(1000, 400);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}

