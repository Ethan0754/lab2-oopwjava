import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Collections;

public class EventListPanel extends JPanel {
    private ArrayList<Event> events;
    private JPanel controlPanel;
    private JPanel displayPanel;
    private JComboBox<String> sortDropDown;
    private String[] sorts = {"Name", "ReverseName", "dateTime"};
    private JCheckBox filterDisplay;
    private JButton addEventButton;

    public EventListPanel() {
        events = new ArrayList<>();

        //this configurations
        this.setPreferredSize(new Dimension(1920,1080));
        this.setBackground(Color.DARK_GRAY);

        //display panel and control panel config
        controlPanel = new JPanel();
        displayPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(1920,80));
        controlPanel.setBackground(Color.DARK_GRAY);
        displayPanel.setPreferredSize(new Dimension(1920,1000));
        displayPanel.setBackground(Color.BLACK);


        this.add(controlPanel);
        this.add(displayPanel);

        //eventbutton config
        addEventButton = new JButton("Add Event");

        addEventButton.setBackground(Color.WHITE);
        addEventButton.setForeground(Color.RED);

        addEventButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addEventButton.setOpaque(true);
        addEventButton.setFocusPainted(false);
        controlPanel.add(addEventButton);

        addEventButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addEventButton.setBackground(Color.GRAY); // Darker blue
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                addEventButton.setBackground(Color.WHITE); // Original blue
            }
        });
        addEventButton.addActionListener(e -> {
            new AddEventModal(this);


            revalidate();
            repaint();

        });


        //dropdown config
        sortDropDown = new JComboBox<>(sorts);
        sortDropDown.setBackground(Color.WHITE);
        sortDropDown.setForeground(Color.RED);

        sortDropDown.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        sortDropDown.setOpaque(true);

        controlPanel.add(sortDropDown);

        sortDropDown.addActionListener(e -> {
            switch (sortDropDown.getSelectedIndex()) {
                case 0:
                    Collections.sort(events);
                    updateDisplay();
            }
        });



    }

    public void addEvents(Event event) {
        this.events.add(event);
        updateDisplay();
    }

    public void updateDisplay() {
        displayPanel.removeAll();
        for (Event event : events) {

            displayPanel.add(new EventPanel(event)).setPreferredSize(new Dimension(350, 140));
        }
        revalidate();
        repaint();
    }



}

