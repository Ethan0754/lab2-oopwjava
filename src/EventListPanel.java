import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class EventListPanel extends JPanel {
    private ArrayList<Event> events;
    private JPanel controlPanel;
    private JPanel displayPanel;
    private JComboBox<String> sortDropDown;
    private String[] sorts = {"Name", "Reverse Name", "Date & Time", "Reverse Date and Time"};
    private String[] filters = {"Remove Completed", "Only Deadlines", "Only Meetings"};
    private JCheckBox[] filterDisplay;
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
                case 0: //name
                    Collections.sort(events, Comparator.comparing(Event::getName));
                    updateDisplay();
                    break;
                case 1: //reverse name
                    Collections.sort(events, Comparator.comparing(Event::getName).reversed());
                    updateDisplay();
                    break;
                case 2: //datetime
                    Collections.sort(events);
                    updateDisplay();
                    break;
                case 3: //reverse datetime
                    Collections.sort(events, Collections.reverseOrder());
                    updateDisplay();
                    break;

            }
        });



        //checkbox configurations
        filterDisplay = new JCheckBox[filters.length];

        int i = 0;
        for (JCheckBox box : filterDisplay) {
            box = new JCheckBox(filters[i++]); // Assign a new checkbox with text from filters
            controlPanel.add(box);
            box.setBackground(Color.WHITE);
            box.setForeground(Color.RED);
            box.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            box.setOpaque(true);

            // Add an ActionListener using an anonymous class
            box.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JCheckBox source = (JCheckBox) e.getSource();
                    if (Objects.equals(source.getText(), "Remove Completed")) {
                        for (int i = 0; i < events.size(); i++) {
                            if (events.get(i) instanceof Deadline) {
                                Deadline deadline = (Deadline) events.get(i);
                                if (source.isSelected()) {
                                    if (deadline.isComplete()) {
                                        deadline.setFilterShowComplete(false);
                                    }
                                } else
                                    deadline.setFilterShowComplete(true);
                            } else if (events.get(i) instanceof Meeting) {
                                Meeting meeting = (Meeting) events.get(i);
                                if (source.isSelected()) {
                                    if (meeting.isComplete()) {
                                        meeting.setFilterShowComplete(false);
                                    }
                                } else
                                    meeting.setFilterShowComplete(true);
                            }
                        }
                    }
                    else if (Objects.equals(source.getText(), "Only Deadlines")) {
                        for (int i = 0; i < events.size(); i++) {
                            if (events.get(i) instanceof Meeting) {
                                Meeting meeting = (Meeting) events.get(i);
                                if (source.isSelected()) {
                                    meeting.setFilterShowComplete(false);
                                }
                                else {
                                    meeting.setFilterShowComplete(true);
                                }
                            }

                        }
                    }
                    else if (Objects.equals(source.getText(), "Only Meetings")) {
                        for (int i = 0; i < events.size(); i++) {
                            if (events.get(i) instanceof Deadline) {
                                Deadline deadline = (Deadline) events.get(i);
                                if (source.isSelected()) {
                                    deadline.setFilterShowComplete(false);
                                }
                                else {
                                    deadline.setFilterShowComplete(true);
                                }
                            }
                        }
                    }

                    updateDisplay();
                }
            });
        }



    }

    public void addEvents(Event event) {
        this.events.add(event);
        updateDisplay();
    }

    public void updateDisplay() {
        displayPanel.removeAll();
        for (Event event : events) {
            if (event instanceof Deadline) {
                Deadline deadline = (Deadline) event;
                if (deadline.isFilterShowComplete() && deadline.isFilterShowDeadline()) {
                    displayPanel.add(new EventPanel(event)).setPreferredSize(new Dimension(350, 140));
                }
            }
            else {
                Meeting meeting = (Meeting) event;
                if (meeting.isFilterShowMeeting() && meeting.isFilterShowComplete()) {
                    displayPanel.add(new EventPanel(event)).setPreferredSize(new Dimension(350, 140));
                }
            }

        }
        revalidate();
        repaint();
    }



}

