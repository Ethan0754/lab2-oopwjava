import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.time.Duration;

public class EventPanel extends JPanel {
    private Event event;
    private JButton completeButton;

    public EventPanel(Event event) {
        this.event = event;
        this.setLayout(new BorderLayout(0, 0));
        completeButton = new JButton("Complete");
        if (event instanceof Completable) {
            add(completeButton, BorderLayout.EAST);
        }
        completeButton.setBackground(Color.GRAY);
        completeButton.setForeground(Color.GREEN);

        // Make it non-opaque for a flat look
        completeButton.setOpaque(true);
        completeButton.setFocusPainted(false);
        completeButton.addActionListener(e-> {
            if (event instanceof Meeting meeting) {
                meeting.complete();
            }
            else if (event instanceof Deadline deadline) {
                deadline.complete();
            }
            revalidate();
            repaint();
        });
        completeButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                completeButton.setBackground(Color.GRAY); // Set to Gray
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                completeButton.setBackground(Color.DARK_GRAY); // Original White
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 14)); // Set font for better readability
        g.setColor(Color.BLACK); // Set text color

        if (event instanceof Meeting meeting) {
            g.drawString("Event: " + meeting.getName(), 10, 20);
            g.drawString("Start Date: " + meeting.getDateTime().toString(), 10, 40);
            g.drawString("End Date: " + meeting.getEndDateTime().toString(), 10, 60);
            Duration duration = meeting.getDuration();
            long days = duration.toDays();
            long hours = duration.toHoursPart();
            long minutes = duration.toMinutesPart();
            long seconds = duration.toSecondsPart();

            String formattedDurationp1 = String.format("%d days, %d hours",
                    days, hours);
            String formattedDurationp2 = String.format("%d minutes, %d seconds",minutes, seconds);

            g.drawString("Duration: " + formattedDurationp1, 10, 80);
            g.drawString("Duration: " + formattedDurationp2, 10, 100);
            g.drawString("Location: " + meeting.getLocation(), 10, 120);
            g.drawString("Completed?: " + meeting.isComplete(), 10, 140);

        }
        if (event instanceof Deadline deadline) {
            g.drawString("Event: " + deadline.getName(), 10, 20);
            g.drawString("Due Date: " + deadline.getDateTime().toString(), 10, 40);
            g.drawString("Completed?: " + deadline.isComplete(), 10, 60);
        }
    }
}

