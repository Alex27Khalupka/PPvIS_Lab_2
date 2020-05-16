package lab2.deletePanels;

import lab2.Controller;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class DepartureDateDelPanel {
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JPanel activPanel = new JPanel(new GridLayout(2, 2, 2, 2));
    private final JLabel departureDateLabel = new JLabel("    Departure date: ");
    private final JTextField departureDateField = new JTextField();
    private final JButton backRemovalPanelButton = new JButton("<-- Back");
    private final JButton deleteButtonButton = new JButton("Delete");


    public JComponent makeUI() {

        for (JComponent r : Arrays.asList(departureDateLabel, departureDateField,
                backRemovalPanelButton, deleteButtonButton)) {
            activPanel.add(r);
        }
        mainPanel.add(activPanel, BorderLayout.CENTER);

        deleteButtonButton.addActionListener(actionEvent -> {
            try {
                Date departureDate = new SimpleDateFormat("yyyy/MM/dd").parse(departureDateField.getText());

                int countDeletedRows = Controller.departureDateDelete(departureDate);

                if (countDeletedRows!=0){

                    JOptionPane.showMessageDialog(mainPanel, "Count of deleted rows: "+countDeletedRows,
                           "Successfully!",JOptionPane.INFORMATION_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog(mainPanel,"Not found!","Error!",JOptionPane.ERROR_MESSAGE);
                }
                Controller.saveInfo();
            }catch (Exception e){
                JOptionPane.showMessageDialog(mainPanel,"Error","Error!",JOptionPane.ERROR_MESSAGE);
            }

        });

        backRemovalPanelButton.addActionListener(actionEvent -> {
            Frame mainFrame[] = JFrame.getFrames();
            for (Frame frame : mainFrame) {
                frame.setVisible(false);
            }
            DeletePanel.createAndShowGUI();
        });
        return mainPanel;

    }

    public static void createAndShowGUI() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.getContentPane().add(new DepartureDateDelPanel().makeUI());
        f.setSize(400, 150);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}