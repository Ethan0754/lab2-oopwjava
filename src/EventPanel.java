import javax.swing.*;
import java.awt.*;

public class EventPanel extends JPanel {
    private Event event;
    private JButton completeButton;

    public EventPanel(Event event) {
        this.event = event;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 14)); // Set font for better readability
        g.setColor(Color.BLACK); // Set text color

        if (event != null) {
            g.drawString("Event: " + event.getName(), 10, 20);
            g.drawString("Date: " + event.getDateTime().toString(), 10, 40);
        }
    }
}

