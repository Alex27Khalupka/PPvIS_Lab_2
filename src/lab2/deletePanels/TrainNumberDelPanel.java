package lab2.deletePanels;

import lab2.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class TrainNumberDelPanel {
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JPanel activPanel = new JPanel(new GridLayout(2, 2, 2, 2));
    private final JLabel trainNumberLabel = new JLabel("    Train number: ");
    private final JTextField trainNumberField = new JTextField();
    private final JButton backRemovalPanelButton = new JButton("<-- Back");
    private final JButton deleteButtonButton = new JButton("Delete");


    public JComponent makeUI() {

        for (JComponent r : Arrays.asList(trainNumberLabel, trainNumberField,
                backRemovalPanelButton, deleteButtonButton)) {
            activPanel.add(r);
        }
        mainPanel.add(activPanel, BorderLayout.CENTER);

        deleteButtonButton.addActionListener(actionEvent -> {
            try {
                int trainNumber = Integer.parseInt(trainNumberField.getText());
                if (trainNumber!=0.0){
                    int countDeletedRows = Controller.trainNumberDelete(trainNumber);

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
        f.getContentPane().add(new TrainNumberDelPanel().makeUI());
        f.setSize(400, 150);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}