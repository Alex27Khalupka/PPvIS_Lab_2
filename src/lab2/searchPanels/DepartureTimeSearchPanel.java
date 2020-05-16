package lab2.searchPanels;

import lab2.Controller;
import lab2.TableInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DepartureTimeSearchPanel {

    private final JLabel pageCountLabel = new JLabel();
    private final JLabel countActivRowsLabel = new JLabel();
    private final JPanel searchPanel = new JPanel(new GridLayout(2, 2, 2, 2));
    private final JPanel searchPrevNextPanel = new JPanel(new GridLayout(2, 1, 2, 2));
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JLabel lowerLimit = new JLabel("    Lower limit: ");
    private final JTextField textLowerLimit = new JTextField();
    private final JLabel upperLimit = new JLabel("    Upper limit: ");
    private final JTextField textUpperLimit = new JTextField();
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
    //private java.util.List<GroupOfStudents> groupOfStudents;
    private TableRowSorter<TableModel> sorter;
    private JTable mainTable;

    public DepartureTimeSearchPanel() {

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


    public JComponent makeUI(java.util.List<Object[]> trainList) throws ParseException {
        mainTable.setFillsViewportHeight(true);
        mainTable.setRowSorter(sorter);
        for (int i = 0; i < trainList.size(); i++) {
            model.addRow(trainList.get(i));

        }
        for (JComponent r : Arrays.asList(lowerLimit, textLowerLimit, upperLimit, textUpperLimit)) {
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
            countActivRowsLabel.setText(String.format("Activ rows: %d", defaultItemsPerPage));
        } else {
            countActivRowsLabel.setText(String.format("Activ rows: %d", rowCount));
        }
        JLabel rowCountLabel = new JLabel(String.format("All rows: %d", rowCount));
        JTextField countPagesfield = new JTextField(maxPageIndex);

        for (JComponent r : Arrays.asList(countActivRowsLabel, rowCountLabel, countPagesfield, backSearchPanelButton, searchButton, updateTableButton)) {
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
                Date lowerTime = new SimpleDateFormat("HH:mm").parse(textLowerLimit.getText());
                Date upperTime = new SimpleDateFormat("HH:mm").parse(textUpperLimit.getText());
                List<Object[]> foundTrainList = Controller.departureTimeSearch(lowerTime, upperTime);
                Frame mainFrame[] = JFrame.getFrames();
                for (Frame frame : mainFrame) {
                    frame.setVisible(false);
                }
                createAndShowGUI(foundTrainList);

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

                countActivRowsLabel.setText(String.format("Activ rows: %d", defaultItemsPerPage));

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
        f.getContentPane().add(new DepartureTimeSearchPanel().makeUI(trainList));
        f.setSize(1000, 400);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }


}