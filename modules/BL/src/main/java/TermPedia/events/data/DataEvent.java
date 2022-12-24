package TermPedia.events.data;

import TermPedia.dto.ActionsException;
import TermPedia.events.BaseEvent;
import TermPedia.events.EventStatus;
import TermPedia.events.visitors.EventVisitor;

public abstract class DataEvent extends BaseEvent {
    private EventStatus status;
    protected DataEvent(Integer uid) {
        super(uid);
        this.status = null;
    }

    @Override
    public EventStatus getResult() { return this.status; }
    public void setResult(EventStatus status) { this.status = status; }
}
