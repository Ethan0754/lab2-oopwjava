import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

public class EventListPanel extends JPanel {
    private ArrayList<Event> events;
    private JPanel controlPanel;
    private JPanel displayPanel;
    private JComboBox sortDropDown;
    private JCheckBox filterDisplay;
    private JButton addEventButton;

    public EventListPanel() {
        events = new ArrayList<>();
        this.setPreferredSize(new Dimension(1920,1080));
        this.setBackground(Color.DARK_GRAY);
        controlPanel = new JPanel();
        displayPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(1920,80));
        controlPanel.setBackground(Color.DARK_GRAY);
        displayPanel.setPreferredSize(new Dimension(1920,1000));
        displayPanel.setBackground(Color.BLACK);
        this.add(controlPanel);
        this.add(displayPanel);

        addEventButton = new JButton("Add Event");
        addEventButton.setBackground(Color.WHITE);
        addEventButton.setForeground(Color.RED);
        addEventButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Make it non-opaque for a flat look
        addEventButton.setOpaque(true);
        addEventButton.setFocusPainted(false);
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
        controlPanel.add(addEventButton);

    }

    public void addEvents(Event event) {
        this.events.add(event);
        updateDisplay();
    }

    public void updateDisplay() {
        displayPanel.removeAll();
        for (Event event : events) {

            displayPanel.add(new EventPanel(event)).setPreferredSize(new Dimension(300, 100));
        }
        revalidate();
        repaint();
    }



}

