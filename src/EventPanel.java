import javax.swing.*;
import java.awt.*;

public class EventPanel extends JPanel {
    private Event event;
    private JButton completeButton;

    public EventPanel(Event event) {
        this.event = event;
        completeButton = new JButton("Complete");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 14)); // Set font for better readability
        g.setColor(Color.BLACK); // Set text color
        add(completeButton);
        if (event instanceof Meeting meeting) {
            g.drawString("Event: " + meeting.getName(), 10, 20);
            g.drawString("Start Date: " + meeting.getDateTime().toString(), 10, 40);
            g.drawString("End Date: " + meeting.getEndDateTime().toString(), 10, 60);
            g.drawString("Location: " + meeting.getLocation(), 10, 80);
        }
        if (event instanceof Deadline deadline) {
            g.drawString("Event: " + deadline.getName(), 10, 20);
            g.drawString("Due Date: " + deadline.getDateTime().toString(), 10, 40);
        }
    }
}

