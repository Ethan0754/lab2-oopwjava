import java.time.LocalDateTime;

abstract class Event implements Comparable<Event> {
    private String name;
    private LocalDateTime dateTime;

    Event(String name, LocalDateTime dateTime){
        this.name = name;
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    //compare to method allows you to call .sort on the arraylist
    public int compareTo(Event e) {
        //if the datetime for the event parameters is before the event parameter of this then 1
        if (e.dateTime.isBefore(dateTime)){
            return 1;
        }
        //is after the event parameter of this then -1
        else if (e.dateTime.isAfter(dateTime)){
            return -1;
        }
        // is the same time then 0
        else {
            return 0;
        }
    }


}