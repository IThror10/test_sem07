package TermPedia.factory.command.postgres;

import TermPedia.events.user.AuthorizeEvent;
import TermPedia.events.user.RegisterEvent;
import TermPedia.factory.command.common.IReqAuthHandlerRequests;

public class PostgresReqAuthHandlerRequests implements IReqAuthHandlerRequests {
    public StringBuilder builder;
    public PostgresReqAuthHandlerRequests() {
        builder = new StringBuilder(128);
    }

    @Override
    public String registerEventQuery(RegisterEvent event) {
        builder.setLength(0);
        builder.append("SELECT * FROM app.register_user('");
        builder.append(event.getData());
        builder.append("', '");
        builder.append(event.dateTime);
        builder.append("', ");
        builder.append(event.getEventType().ordinal());
        builder.append(");");
        return builder.toString();
    }

    @Override
    public String authorizeEventQuery(AuthorizeEvent event) {
        builder.setLength(0);
        builder.append("SELECT * FROM app.authorize_user('");
        builder.append(event.getData());
        builder.append("');");
        return builder.toString();
    }
}
