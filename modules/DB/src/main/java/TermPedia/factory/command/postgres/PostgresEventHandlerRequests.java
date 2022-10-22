package TermPedia.factory.command.postgres;

import TermPedia.events.BaseEvent;
import TermPedia.factory.command.common.IEventHandlerRequests;

public class PostgresEventHandlerRequests implements IEventHandlerRequests {
    private final StringBuilder builder;
    public PostgresEventHandlerRequests() {
        builder = new StringBuilder(128);
    }

    @Override
    public String acceptEventQuery(BaseEvent event) {
        builder.setLength(0);
        builder.append("SELECT * FROM app.accept_event(");
        builder.append(event.uid);
        builder.append(", '");
        builder.append(event.dateTime);
        builder.append("', ");
        builder.append(event.getEventType().ordinal());
        builder.append(", '");
        builder.append(event.getData());
        builder.append("');");
        return builder.toString();
    }
}
