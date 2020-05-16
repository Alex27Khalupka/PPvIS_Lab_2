package lab2.deletePanels;

import lab2.Controller;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ArrivalTimeDelPanel {
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JPanel activPanel = new JPanel(new GridLayout(3, 2, 2, 2));
    private final JLabel lowerTimeLabel = new JLabel("    Lower limit: ");
    private final JLabel upperTimeLabel = new JLabel("    Upper limit: ");
    private final JTextField lowerTimeField = new JTextField();
    private final JTextField upperTimeField = new JTextField();
    private final JButton backRemovalPanelButton = new JButton("<-- Back");
    private final JButton deleteButtonButton = new JButton("Delete");


    public JComponent makeUI() {

        for (JComponent r : Arrays.asList(lowerTimeLabel, lowerTimeField, upperTimeLabel, upperTimeField,
                backRemovalPanelButton, deleteButtonButton)) {
            activPanel.add(r);
        }
        mainPanel.add(activPanel, BorderLayout.CENTER);

        deleteButtonButton.addActionListener(actionEvent -> {
            try {
                Date lowerTime = new SimpleDateFormat("HH:mm").parse(lowerTimeField.getText());
                Date upperTime = new SimpleDateFormat("HH:mm").parse(upperTimeField.getText());

                int countDeletedRows = Controller.arrivalTimeDelete(lowerTime, upperTime);

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
        f.getContentPane().add(new ArrivalTimeDelPanel().makeUI());
        f.setSize(400, 150);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
