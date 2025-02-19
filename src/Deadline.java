import java.time.LocalDateTime;

public class Deadline extends Event implements Completable {
    private boolean complete;
    private boolean filterShowComplete = true; //handles whether it is filtered out or not
    private boolean filterShowDeadline = true;


    Deadline(String name, LocalDateTime dateTime) {
        super(name, dateTime); //deadline only has event variables - no extras

    }

    @Override
    public void complete() {
        complete = true;
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    public boolean isFilterShowComplete() {
        return filterShowComplete;
    }

    public void setFilterShowComplete(boolean filterShowComplete) {
        this.filterShowComplete = filterShowComplete;
    }

    public boolean isFilterShowDeadline() {
        return filterShowDeadline;
    }

    public void setFilterShowDeadline(boolean filterShowDeadline) {
        this.filterShowDeadline = filterShowDeadline;
    }
}
