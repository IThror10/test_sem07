package TermPedia.factory.command.mssql;

import TermPedia.events.user.AuthorizeEvent;
import TermPedia.events.user.RegisterEvent;
import TermPedia.factory.command.common.IReqAuthHandlerRequests;

import java.time.temporal.ChronoUnit;

public class MsSqlReqAuthHandlerRequests implements IReqAuthHandlerRequests {
    private final StringBuilder builder;
    public MsSqlReqAuthHandlerRequests() {
        builder = new StringBuilder(512);
    }

    @Override
    public String registerEventQuery(RegisterEvent event) {
        builder.setLength(0);
        builder.append("BEGIN DECLARE @in_data VARCHAR(1000); SET @in_data = '");
        builder.append(event.getData());
        builder.append("'; IF JSON_VALUE(@in_data, '$.Login') in (SELECT login FROM app.USERS) SELECT -1 as register_user");
        builder.append("; ELSE IF JSON_VALUE(@in_data, '$.Email') in (SELECT email FROM app.Users) SELECT -2 as register_user");
        builder.append("; ELSE IF NOT JSON_VALUE(@in_data, '$.Email') like '___%@_%._%' SELECT -3 as register_user");
        builder.append("ELSE BEGIN SELECT 0 as register_user; INSERT INTO app.Users VALUES (NEXT VALUE FOR app.user_cnt, '");
        builder.append(event.dateTime.truncatedTo(ChronoUnit.SECONDS));
        builder.append("', 0, JSON_VALUE(@in_data, '$.Login'), JSON_VALUE(@in_data, '$.Password'),");
        builder.append("JSON_VALUE(@in_data, '$.Email')); END END");
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
