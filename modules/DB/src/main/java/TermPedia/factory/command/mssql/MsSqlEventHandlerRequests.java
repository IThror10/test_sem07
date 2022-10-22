package TermPedia.factory.command.mssql;

import TermPedia.events.BaseEvent;
import TermPedia.factory.command.common.IEventHandlerRequests;

import java.time.temporal.ChronoUnit;

public class MsSqlEventHandlerRequests implements IEventHandlerRequests {
    private final StringBuilder builder;
    public MsSqlEventHandlerRequests() {
        builder = new StringBuilder(128);
    }

    @Override
    public String acceptEventQuery(BaseEvent event) {
        builder.setLength(0);
        builder.append("IF ");
        builder.append(event.uid);
        builder.append(" IN (SELECT UID FROM app.Users) BEGIN SELECT 1 as accept_event; INSERT INTO app.Events VALUES (");
        builder.append(event.uid);
        builder.append(", '");
        builder.append(event.dateTime.truncatedTo(ChronoUnit.SECONDS));
        builder.append("', ");
        builder.append(event.getEventType().ordinal());
        builder.append(", '");
        builder.append(event.getData());
        builder.append("'); END ELSE THROW 1234, 'User does not Exists', 1");
        return builder.toString();
    }
}
