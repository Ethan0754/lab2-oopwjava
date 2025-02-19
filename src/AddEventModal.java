import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AddEventModal extends JDialog {

    private EventListPanel eventListPanel;
    private JButton addEventButton;
    private AddEventModal myself;
    private JPanel infoCollectionPanel; // boxes that appear for data collection
    //which events you can add through the dropdown
    private String[] events = {"Deadline", "Meeting"};
    private JComboBox<String> eventSelector;


    record Attribute(String name, JComponent value) {
    }

    ArrayList<Attribute> attributes;


    public AddEventModal(EventListPanel eventListPanel) {
        myself = this;
        this.eventListPanel = eventListPanel;
        this.setBackground(Color.DARK_GRAY);
        this.setTitle("Add Event");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.add(AddEventPanel());
        this.pack();
        this.setVisible(true);
    }


    private JPanel AddEventPanel() {

        JPanel panel = new JPanel();
        attributes = new ArrayList<>();

        setPreferredSize(new Dimension(500,500));
        setBackground(Color.DARK_GRAY);


        //drop down configurations
        eventSelector = new JComboBox<>(events);
        eventSelector.setBackground(Color.CYAN);
        panel.add(eventSelector);

        //data collection boxes configuration
        infoCollectionPanel = new JPanel();
        infoCollectionPanel.setBackground(Color.LIGHT_GRAY);
        infoCollectionPanel.setPreferredSize(new Dimension(480,400));
        panel.add(infoCollectionPanel);

        //add event button configuration
        addEventButton = new JButton("Add Event");
        addEventButton.setBackground(Color.CYAN);
        panel.add(addEventButton);

        //this is to add a deadline when you first open the modal
        attributes.add(new Attribute("Name", new JTextField(10)));
        attributes.add(new Attribute("Due Date", new JTextField(10)));
        attributes.forEach(attr -> {
            JLabel label = new JLabel(attr.name);
            infoCollectionPanel.add(label);
            infoCollectionPanel.add(attr.value);
        });
        infoCollectionPanel.revalidate();
        infoCollectionPanel.repaint();



        eventSelector.addActionListener(e -> {
            attributes.clear();
            infoCollectionPanel.removeAll();
            switch (eventSelector.getSelectedIndex()) {
                case 0: { //if event selected is deadline
                    attributes.add(new Attribute("Name", new JTextField(10)));
                    attributes.add(new Attribute("Due Date", new JTextField(10)));
                    break;
                }
                case 1: { // if event selected is meeting
                    attributes.add(new Attribute("Name", new JTextField(10)));
                    attributes.add(new Attribute("Start Date", new JTextField(10)));
                    attributes.add(new Attribute("End Date", new JTextField(10)));
                    attributes.add(new Attribute("Location", new JTextField(10)));
                    break;
                }
            }
            //adding the attributes and the labels to to the frame
            attributes.forEach(attr -> {
                JLabel label = new JLabel(attr.name);
                infoCollectionPanel.add(label);
                infoCollectionPanel.add(attr.value);
            });
            infoCollectionPanel.revalidate();
            infoCollectionPanel.repaint();
        });




        addEventButton.addActionListener(e -> {
            Event newEvent = null;
            switch (eventSelector.getSelectedIndex()) {
                case 0: {
                    newEvent = new Deadline(getInput(attributes.get(0).value),
                            LocalDateTime.parse(getInput(attributes.get(1).value)));
                    break;
                }
                case 1: {
                    newEvent = new Meeting(getInput(attributes.get(0).value),
                            LocalDateTime.parse(getInput(attributes.get(1).value)), LocalDateTime.parse(getInput(attributes.get(2).value)), getInput(attributes.get(3).value()));
                }
            }
            eventListPanel.addEvents(newEvent);
            myself.dispose();
        });


        return panel;
    }
    
    private String getInput(JComponent c) {
        if (c instanceof JTextComponent) {
            return ((JTextComponent) c).getText();
        }
        return "";
    }
}

