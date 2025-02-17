import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class EventListPanel extends JPanel {
    private ArrayList<Event> events;
    private JPanel controlPanel;
    private JPanel displayPanel;
    private JComboBox sortDropDown;
    private JCheckBox filterDisplay;
    private JButton addEventButton;

    public EventListPanel() {
        this.setPreferredSize(new Dimension(1920,1080));
        this.setBackground(Color.DARK_GRAY);
        controlPanel = new JPanel();
        displayPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(250,250));
        controlPanel.setBackground(Color.BLUE);
        displayPanel.setPreferredSize(new Dimension(500,500));
        displayPanel.setBackground(Color.BLACK);
        this.add(controlPanel);
        this.add(displayPanel);

        addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Add Event button clicked!");
        });
        controlPanel.add(addEventButton);

        EventPanel test123 = new EventPanel(new Deadline("test123", LocalDateTime.now()));
        test123.setPreferredSize(new Dimension(250,250));
        displayPanel.add(test123);
    }

    public void addEvents(ArrayList<Event> events) {

    }

    public void add(Deadline testDeadline) {

    }

    public void add(Meeting meeting) {
    }


}
