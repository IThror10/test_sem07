package TermPedia.events;

public class EventStatus extends EventResult {
    private final boolean status;
    public EventStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }
}
