package TermPedia.handlers;

import TermPedia.dto.ActionsException;
import TermPedia.events.BaseEvent;
import TermPedia.events.visitors.EventHandlerVisitor;

public class EventHandler {
    public void handle(BaseEvent event) throws ActionsException {
        EventHandlerVisitor visitor = new EventHandlerVisitor();
        event.acceptVisitor(visitor);
    }
}
