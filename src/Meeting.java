import java.time.Duration;
import java.time.LocalDateTime;

public class Meeting extends Event implements Completable {
    private String location;
    private boolean complete;
    private boolean filterShowComplete = true; //handles whether it is filtered out or not
    private boolean filterShowMeeting = true; //handles whether it is filtered out or not
    private LocalDateTime endDateTime;



    Meeting(String name, LocalDateTime start, LocalDateTime end, String location) {
        super(name, start); //gets the name and start time from the event
        //meeting specifc variables
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

    public boolean isFilterShowComplete() {
        return filterShowComplete;
    }

    public void setFilterShowComplete(boolean filterShowComplete) {
        this.filterShowComplete = filterShowComplete;
    }

    public boolean isFilterShowMeeting() {
        return filterShowMeeting;
    }

    public void setFilterShowMeeting(boolean filterShowMeeting) {
        this.filterShowMeeting = filterShowMeeting;
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
