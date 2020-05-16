package lab2.deletePanels;

import lab2.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

public class DeletePanel {

    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JPanel buttonPanel = new JPanel(new FlowLayout());
    private final JButton trainNumberDelButton = new JButton("Delete by train number");
    private final JButton departureDateDelButton = new JButton("Delete by departure date");
    private final JButton departureTimeDelButton = new JButton("Delete by departure time");
    private final JButton arrivalTimeDelButton = new JButton("Delete by arrival time");
    private final JButton departureStationDelButton = new JButton("Delete by departure station");
    private final JButton arrivalStationDelButton = new JButton("Delete by arrival station");
    private final JButton tripTimeDelButton = new JButton("Delete by trip time");

    private final JButton backMainPanelButton = new JButton("<-- Back");

    public JComponent makeUI() {
        buttonPanel.add(trainNumberDelButton);
        buttonPanel.add(departureDateDelButton);
        buttonPanel.add(departureTimeDelButton);
        buttonPanel.add(arrivalTimeDelButton);
        buttonPanel.add(departureStationDelButton);
        buttonPanel.add(arrivalStationDelButton);
        buttonPanel.add(tripTimeDelButton);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(backMainPanelButton, BorderLayout.SOUTH);

        trainNumberDelButton.addActionListener(actionEvent -> {
            Frame[] mainFrame = JFrame.getFrames();
            for (Frame frame : mainFrame) {
                frame.setVisible(false);
            }
            TrainNumberDelPanel.createAndShowGUI();
        });

        departureDateDelButton.addActionListener(actionEvent -> {
            Frame[] mainFrame = JFrame.getFrames();
            for (Frame frame : mainFrame) {
                frame.setVisible(false);
            }

            DepartureDateDelPanel.createAndShowGUI();
        });

        departureTimeDelButton.addActionListener(actionEvent -> {
            Frame[] mainFrame = JFrame.getFrames();
            for (Frame frame : mainFrame) {
                frame.setVisible(false);
            }

            DepartureTimeDelPanel.createAndShowGUI();
        });

        arrivalTimeDelButton.addActionListener(actionEvent -> {
            Frame[] mainFrame = JFrame.getFrames();
            for (Frame frame : mainFrame) {
                frame.setVisible(false);
            }
            ArrivalTimeDelPanel.createAndShowGUI();
        });

        departureStationDelButton.addActionListener(actionEvent -> {
            Frame[] mainFrame = JFrame.getFrames();
            for (Frame frame : mainFrame) {
                frame.setVisible(false);
            }
            DepartureStationDelPanel.createAndShowGUI();
        });

        arrivalStationDelButton.addActionListener(actionEvent -> {
            Frame[] mainFrame = JFrame.getFrames();
            for (Frame frame : mainFrame) {
                frame.setVisible(false);
            }
            ArrivalStationDelPanel.createAndShowGUI();
        });

        tripTimeDelButton.addActionListener(actionEvent -> {
            Frame[] mainFrame = JFrame.getFrames();
            for (Frame frame : mainFrame) {
                frame.setVisible(false);
            }
            TripTimeDelPanel.createAndShowGUI();
        });


        backMainPanelButton.addActionListener(actionEvent -> {
            Frame[] mainFrame = JFrame.getFrames();
            for (Frame frame : mainFrame) {
                frame.setVisible(false);
            }
            try {
                MainPanel.createAndShowGUI();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });


        return mainPanel;

    }

    public static void createAndShowGUI() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.getContentPane().add(new DeletePanel().makeUI());
        f.setSize(1300, 150);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}