package TermPedia.events;

import TermPedia.dto.ActionsException;
import TermPedia.events.visitors.EventVisitor;

import java.time.LocalDateTime;

public abstract class BaseEvent {
    public final LocalDateTime dateTime;
    public final Integer uid;
    protected String data;
    protected EventType eventType;

    protected BaseEvent(Integer uid) {
        this.uid = uid;
        this.dateTime = LocalDateTime.now();
    }
    public String getData() { return this.data; }
    public EventType getEventType() { return this.eventType; }

    public abstract void acceptVisitor(EventVisitor visitor) throws ActionsException;
    public abstract EventResult getResult();
}
