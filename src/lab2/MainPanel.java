package lab2;

import lab2.deletePanels.DeletePanel;
import lab2.searchPanels.SearchPanel;
import org.xml.sax.SAXException;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class MainPanel {
    private final JLabel pageCountLabel = new JLabel();
    private final JButton toFindButton = new JButton("Search");
    private final JButton toDeleteButton = new JButton("Delete");
    private final JButton toSaveButton = new JButton("Save");
    private final JLabel countActiveRowsLabel = new JLabel();
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private int virtualPage;
    private int rowCount;
    private int defaultItemsPerPage = 10;
    private int maxPageIndex;
    private int currentPageIndex = 1;

    private String[] columnNames;
    private DefaultTableModel model;
    private List<Train> trainsList;
    private TableRowSorter<TableModel> sorter;
    private JTable mainTable;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    public MainPanel() {
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


    public JComponent makeUI() throws ParseException {
        mainTable.setFillsViewportHeight(true);
        mainTable.setRowSorter(sorter);
        for
        (int i = 0; i < TableInfo.getTrainsListArray().size(); i++) {
            model.addRow(TableInfo.getTrainsListArray().get(i));
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

        mainTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    boolean badValue = true;
                    int column = mainTable.columnAtPoint(e.getPoint());
                    int row = mainTable.rowAtPoint(e.getPoint());
                    Object value = mainTable.getValueAt(row, column);

                    while (badValue) {
                        if (value instanceof Double) {
                            JOptionPane.showMessageDialog(mainTable, "Immutable column!", "Error!", JOptionPane.ERROR_MESSAGE);
                            break;
                        }
                        if (column == 5) {
                            JOptionPane.showMessageDialog(mainTable, "Immutable column!", "Error!", JOptionPane.ERROR_MESSAGE);
                            break;
                        }
                        String object = JOptionPane.showInputDialog(mainTable, "Input value", "Input", JOptionPane.INFORMATION_MESSAGE);
                        if (object.equals("")){
                            JOptionPane.showMessageDialog(mainTable, "Error", "Error!", JOptionPane.ERROR_MESSAGE);
                            object = JOptionPane.showInputDialog(mainTable, "Input value", "Input", JOptionPane.INFORMATION_MESSAGE);

                        }
                        if (value instanceof Integer) {
                            try {
                                value = Integer.parseInt(object);
                                badValue = false;
                                mainTable.setValueAt(value, row, column);
                                Controller.setValue(row, column, value);
                            } catch (Exception e1) {
                                JOptionPane.showMessageDialog(mainTable, "Error", "Error!", JOptionPane.ERROR_MESSAGE);

                            }
                        } else{
                            try {
                                value = Integer.parseInt(object);
                                JOptionPane.showMessageDialog(mainTable, "Error", "Error!", JOptionPane.ERROR_MESSAGE);
                                value = String.valueOf(value);

                            } catch (Exception e1) {
                                value = object;
                                badValue = false;
                                if (column == 3 || column == 4){
                                    try{
                                        Date date = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse((String) value);
                                        mainTable.setValueAt(formatter.format(date), row, column);
                                        Controller.setValue(row, column, value);
                                    } catch (ParseException ex) {
                                        badValue = true;
                                        JOptionPane.showMessageDialog(mainTable, "Wrong time format", "Error!", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                                else {
                                    mainTable.setValueAt(value, row, column);
                                    try {
                                        Controller.setValue(row, column, value);
                                    } catch (ParseException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        JPanel panelInfoPages = new JPanel(new GridLayout(1, 0, 5, 12));

        if (defaultItemsPerPage <= rowCount) {
            countActiveRowsLabel.setText(String.format("Activ rows: %d", defaultItemsPerPage));
        }else{
            countActiveRowsLabel.setText(String.format("Activ rows: %d", rowCount));
        }

        JLabel rowCountLabel = new JLabel(String.format("All rows: %d", rowCount));
        JTextField countPagesfield = new JTextField(maxPageIndex);

        for (JComponent r : Arrays.asList(countActiveRowsLabel, rowCountLabel, countPagesfield, toSaveButton, toFindButton, toDeleteButton)) {
            panelInfoPages.add(r);
        }

        countPagesfield.addActionListener(actionEvent -> {

            if (rowCount < Integer.parseInt(countPagesfield.getText())) {
                JOptionPane.showMessageDialog(mainPanel, "Error", "Error!", JOptionPane.ERROR_MESSAGE);
                initFilterAndButton();
            } else {

                defaultItemsPerPage = Integer.parseInt(countPagesfield.getText());
                rowCount = model.getRowCount();
                virtualPage = rowCount % defaultItemsPerPage == 0 ? 0 : 1;
                maxPageIndex = rowCount / defaultItemsPerPage + virtualPage;
                initFilterAndButton();

                countPagesfield.setText("");
                pageCountLabel.setText(String.format("/ %d", maxPageIndex));
                countActiveRowsLabel.setText(String.format("Activ rows: %d", defaultItemsPerPage));
            }
        });

        toSaveButton.addActionListener(actionEvent -> {
            try {
                TableInfo.setTrainsList(Controller.saveInfo());
                Frame mainFrame[] = JFrame.getFrames();
                for (Frame frame : mainFrame) {
                    frame.setVisible(false);
                }
                createAndShowGUI();
                initFilterAndButton();
            } catch (ParserConfigurationException | ParseException | TransformerException | IOException | SAXException e) {
                e.printStackTrace();
            }
        });

        toFindButton.addActionListener(actionEvent -> {
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

        toDeleteButton.addActionListener(actionEvent -> {
            Frame mainFrame[] = JFrame.getFrames();
            for (Frame frame : mainFrame) {
                frame.setVisible(false);
            }
            DeletePanel.createAndShowGUI();
        });

        mainPanel.add(box, BorderLayout.NORTH);
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

    public static void createAndShowGUI() throws ParseException {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.getContentPane().add(new MainPanel().makeUI());
        f.setSize(1000, 350);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
