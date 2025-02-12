import java.time.Duration;
import java.time.LocalDateTime;

public class Meeting extends Event implements Completable {
    private String location;
    private boolean complete;
    private LocalDateTime endDateTime;



    Meeting(String name, LocalDateTime start, LocalDateTime end, String location) {
        super(name, start);
        this.endDateTime = end;
        this.location = location;
    }

    @Override
    public void complete() {
        complete = true;
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public String getLocation() {
        return location;
    }

    public Duration getDuration() {
        return Duration.between(getDateTime(), endDateTime);
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
