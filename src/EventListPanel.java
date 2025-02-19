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

    private JButton addEventButton;

    //options for sorting in the dropdown
    private String[] sorts = {"Name", "Reverse Name", "Date & Time", "Reverse Date and Time"};
    private JComboBox<String> sortDropDown;
    //options for filtering with the checkboxes
    private String[] filters = {"Remove Completed", "Only Deadlines", "Only Meetings"};
    private JCheckBox[] filterDisplay;



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

        //adding main two panels to master panel
        this.add(controlPanel);
        this.add(displayPanel);

        //eventbutton config
        addEventButton = new JButton("Add Event");

        addEventButton.setBackground(Color.WHITE);
        addEventButton.setForeground(Color.RED);

        addEventButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); //change the border of the button
        addEventButton.setOpaque(true);
        addEventButton.setFocusPainted(false);
        controlPanel.add(addEventButton); //adding eventbutton to control panel

        //this mouselistener changes the color when you are hovering over it
        addEventButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addEventButton.setBackground(Color.GRAY); // Darker blue
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                addEventButton.setBackground(Color.WHITE); // Original blue
            }
        });

        //this is the actual event listener that creates the modal
        addEventButton.addActionListener(e -> {
            new AddEventModal(this);//pass in this to give info to the modal

            revalidate();
            repaint();

        });



        //dropdown config
        sortDropDown = new JComboBox<>(sorts);
        sortDropDown.setBackground(Color.WHITE);
        sortDropDown.setForeground(Color.RED);

        sortDropDown.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); //changes border of sortdropdown
        sortDropDown.setOpaque(true);

        controlPanel.add(sortDropDown);

        sortDropDown.addActionListener(e -> {
            switch (sortDropDown.getSelectedIndex()) {
                case 0: //sort by name
                    Collections.sort(events, Comparator.comparing(Event::getName));
                    updateDisplay();
                    break;
                case 1: //sort by reverse name
                    Collections.sort(events, Comparator.comparing(Event::getName).reversed());
                    updateDisplay();
                    break;
                case 2: //sort by datetime
                    Collections.sort(events);
                    updateDisplay();
                    break;
                case 3: //sort by reverse datetime
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
                    //yes, I know this if else if chain looks like the devil made it, im sorry...
                    JCheckBox source = (JCheckBox) e.getSource(); // gets which checkbox pressed
                    //main option - remove completed
                    if (Objects.equals(source.getText(), "Remove Completed")) {
                        for (int i = 0; i < events.size(); i++) {
                            //if the event is a deadline:
                            if (events.get(i) instanceof Deadline) {
                                Deadline deadline = (Deadline) events.get(i);
                                //this controls if the button was checked or unchecked
                                if (source.isSelected()) {
                                    if (deadline.isComplete()) { //if the deadline is complete, then set var so it doesn't show up
                                        deadline.setFilterShowComplete(false);
                                    }
                                } else
                                    deadline.setFilterShowComplete(true); //if the checkbox was unchecked, the set the showcomplete filter to true

                                //this is just doing the same as above but with meeting
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
                    //main option - only deadlines
                    else if (Objects.equals(source.getText(), "Only Deadlines")) {
                        for (int i = 0; i < events.size(); i++) {
                            //Only deadlines means we are looking for instances of meeting only
                            if (events.get(i) instanceof Meeting) {
                                Meeting meeting = (Meeting) events.get(i);
                                //if checkbox was selected then remove this meeting from the filter
                                if (source.isSelected()) {
                                    meeting.setFilterShowComplete(false);
                                }
                                else { //if checkbox was unselected add this meeting back
                                    meeting.setFilterShowComplete(true);
                                }
                            }

                        }
                    }
                    //main option - only meetings
                    else if (Objects.equals(source.getText(), "Only Meetings")) {
                        for (int i = 0; i < events.size(); i++) {
                            //same as above but opposite - only looking for instance of deadline
                            if (events.get(i) instanceof Deadline) {
                                Deadline deadline = (Deadline) events.get(i);
                                //if checkbox was checked then remove from filter
                                if (source.isSelected()) {
                                    deadline.setFilterShowComplete(false);
                                }
                                else { //if checkbox was unchecked then add back to filter
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
        displayPanel.removeAll(); //remove all the items in the display
        //loop through all of the events
        for (Event event : events) {
            if (event instanceof Deadline) {
                Deadline deadline = (Deadline) event;
                //checks the filters for each deadline
                if (deadline.isFilterShowComplete() && deadline.isFilterShowDeadline()) {
                    displayPanel.add(new EventPanel(event)).setPreferredSize(new Dimension(350, 140));
                }
            }
            else {
                Meeting meeting = (Meeting) event;
                //checks the filters for each meeting
                if (meeting.isFilterShowMeeting() && meeting.isFilterShowComplete()) {
                    displayPanel.add(new EventPanel(event)).setPreferredSize(new Dimension(350, 140));
                }
            }

        }
        revalidate();
        repaint();
    }



}

