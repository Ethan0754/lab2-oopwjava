import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class EventPlanner {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Calendar"); //create initial window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        EventListPanel panel = new EventListPanel();//create a EventListPanel panel that adds the panel and handles the logic

        frame.getContentPane().add(panel);
        frame.pack();

        frame.setVisible(true);

    }
    static void addDefaultEvents(EventListPanel events) {
        events.add(new Deadline("Test Deadline", LocalDateTime.now()));
        events.add(new Meeting("Test Meeting", LocalDateTime.now(), LocalDateTime.now(), "Farris Hall"));
    }

}


