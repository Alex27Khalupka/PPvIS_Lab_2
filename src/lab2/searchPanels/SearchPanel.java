package lab2.searchPanels;

import lab2.MainPanel;
import lab2.TableInfo;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

public class SearchPanel {

    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JPanel buttonPanel = new JPanel(new FlowLayout());

    private final JButton trainNumberSearchButton = new JButton("Train number search");
    private final JButton departureDateSearchButton = new JButton("Departure date search");
    private final JButton departureTimeSearchButton = new JButton("Departure time search");
    private final JButton arrivalTimeSearchButton = new JButton("Arrival time search");
    private final JButton departureStationSearchButton = new JButton("Departure station search");
    private final JButton arrivalStationSearchButton = new JButton("Arrival station search");
    private final JButton tripTimeSearchButton = new JButton("Trip time search");

    private final JButton backMainPanelButton = new JButton("<-- Back");

    public JComponent makeUI() throws ParseException{
        buttonPanel.add(trainNumberSearchButton);
        buttonPanel.add(departureDateSearchButton);
        buttonPanel.add(departureTimeSearchButton);
        buttonPanel.add(arrivalTimeSearchButton);
        buttonPanel.add(departureStationSearchButton);
        buttonPanel.add(arrivalStationSearchButton);
        buttonPanel.add(tripTimeSearchButton);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(backMainPanelButton, BorderLayout.SOUTH);

        departureDateSearchButton.addActionListener(actionEvent -> {
            Frame mainFrame[] = JFrame.getFrames();
            for (Frame frame : mainFrame) {
                frame.setVisible(false);
            }
            try {
                DepartureDateSearchPanel.createAndShowGUI(TableInfo.getTrainsListArray());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        trainNumberSearchButton.addActionListener(actionEvent -> {
            Frame mainFrame[] = JFrame.getFrames();
            for (Frame frame : mainFrame) {
                frame.setVisible(false);
            }
            try {
                TrainNumberSearchPanel.createAndShowGUI(TableInfo.getTrainsListArray());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        departureTimeSearchButton.addActionListener(actionEvent -> {
            Frame mainFrame[] = JFrame.getFrames();
            for (Frame frame : mainFrame) {
                frame.setVisible(false);
            }
            try {
                DepartureTimeSearchPanel.createAndShowGUI(TableInfo.getTrainsListArray());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        arrivalTimeSearchButton.addActionListener(actionEvent -> {
            Frame mainFrame[] = JFrame.getFrames();
            for (Frame frame : mainFrame) {
                frame.setVisible(false);
            }
            try {
                ArrivalTimeSearchPanel.createAndShowGUI(TableInfo.getTrainsListArray());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        departureStationSearchButton.addActionListener(actionEvent -> {
            Frame mainFrame[] = JFrame.getFrames();
            for (Frame frame : mainFrame) {
                frame.setVisible(false);
            }
            try {
                DepartureStationSearchPanel.createAndShowGUI(TableInfo.getTrainsListArray());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        arrivalStationSearchButton.addActionListener(actionEvent -> {
            Frame mainFrame[] = JFrame.getFrames();
            for (Frame frame : mainFrame) {
                frame.setVisible(false);
            }
            try {
                ArrivalStationSearchPanel.createAndShowGUI(TableInfo.getTrainsListArray());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        tripTimeSearchButton.addActionListener(actionEvent -> {
            Frame mainFrame[] = JFrame.getFrames();
            for (Frame frame : mainFrame) {
                frame.setVisible(false);
            }
            try {
                TripTimeSearchPanel.createAndShowGUI(TableInfo.getTrainsListArray());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });




        backMainPanelButton.addActionListener(actionEvent -> {
            Frame mainFrame[] = JFrame.getFrames();
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

    public static void createAndShowGUI() throws ParseException{
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.getContentPane().add(new SearchPanel().makeUI());
        f.setSize(1200, 150);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}