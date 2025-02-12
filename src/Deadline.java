import java.time.LocalDateTime;

public class Deadline extends Event implements Completable {
    private boolean complete;
    private String name;
    private LocalDateTime dateTime;

    Deadline(String name, LocalDateTime dateTime) {
        super(name, dateTime);
    }

    @Override
    public void complete() {
        complete = true;
    }

    @Override
    public boolean isComplete() {
        return complete;
    }
}
