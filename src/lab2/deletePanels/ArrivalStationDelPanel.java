package lab2.deletePanels;

import lab2.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class ArrivalStationDelPanel {
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JPanel activPanel = new JPanel(new GridLayout(2, 2, 2, 2));
    private final JLabel stationNameLabel = new JLabel("    Station name: ");
    private final JTextField stationNameField = new JTextField();
    private final JButton backRemovalPanelButton = new JButton("<-- Back");
    private final JButton deleteButtonButton = new JButton("Delete");


    public JComponent makeUI() {

        for (JComponent r : Arrays.asList(stationNameLabel, stationNameField,
                backRemovalPanelButton, deleteButtonButton)) {
            activPanel.add(r);
        }
        mainPanel.add(activPanel, BorderLayout.CENTER);

        deleteButtonButton.addActionListener(actionEvent -> {
            try {
                String stationName = stationNameField.getText();
                if (!stationName.equals("")){
                    int countDeletedRows = Controller.arrivalStationDelete(stationName);

                    if (countDeletedRows!=0){

                        JOptionPane.showMessageDialog(mainPanel, "Count of deleted rows: "+countDeletedRows,
                                "Successfully!",JOptionPane.INFORMATION_MESSAGE);
                    }else {
                        JOptionPane.showMessageDialog(mainPanel,"Not found!","Error!",JOptionPane.ERROR_MESSAGE);
                    }
                    Controller.saveInfo();
                }else{
                    JOptionPane.showMessageDialog(mainPanel,"Fill in all the fields!","Error!",JOptionPane.ERROR_MESSAGE);
                }
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
        f.getContentPane().add(new ArrivalStationDelPanel().makeUI());
        f.setSize(400, 150);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
