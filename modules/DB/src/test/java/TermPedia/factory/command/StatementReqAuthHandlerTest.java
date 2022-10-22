package TermPedia.factory.command;

import TermPedia.dto.ActionsException;
import TermPedia.events.user.AuthorizeEvent;
import TermPedia.events.user.RegisterEvent;
import TermPedia.factory.EnvProvider;
import TermPedia.factory.command.mssql.MsSqlCommandFactory;
import TermPedia.factory.command.postgres.PostgresCommandFactory;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class StatementReqAuthHandlerTest {
    StatementReqAuthHandlerTest() throws Exception {
        PostgresCommandFactory.completeRegistration();
        PostgresCommandFactory.setConnectionEstablisher(new TestPostgresCommandConnection());

        MsSqlCommandFactory.completeRegistration();
        MsSqlCommandFactory.setConnectionEstablisher(new TestMsSqlCommandConnection());

        SyncCommandFactory.setProvider(new EnvProvider());
    }

    @Test
    void register() throws Exception {
        String login = "NewLogin";
        String email = "NewEmail" + "@yandex.com";

        RegisterEvent event1 = new RegisterEvent(login, "newPassword", email);
        RegisterEvent event2 = new RegisterEvent(login, "oldPassword", email);
        RegisterEvent event3 = new RegisterEvent(login + "Name", "oldPassword", email);
        RegisterEvent event4 = new RegisterEvent(login + "Name", "oldPassword", "wrong@");

        ReqAuthHandler handler = CommandFactory.instance().createReqAuthHandler();
        assertAll(
                () -> assertEquals(true, handler.register(event1).getStatus()),
                () -> assertThrows(ActionsException.class, () -> handler.register(event2), "This Login is Already Used"),
                () -> assertThrows(ActionsException.class, () -> handler.register(event3), "This Email is Already Used"),
                () -> assertThrows(ActionsException.class, () -> handler.register(event4), "Wrong Email address")
        );
    }

    @Test
    void authorize() throws Exception {
        ReqAuthHandler handler = SyncCommandFactory.instance().createReqAuthHandler();;

        AuthorizeEvent event1 = new AuthorizeEvent("admin", "password");
        AuthorizeEvent event2 = new AuthorizeEvent("wrong", "password");

        assertAll(
                () -> assertEquals(0, handler.authorize(event1).UID),
                () -> assertEquals("admin", handler.authorize(event1).login),
                () -> assertThrows(ActionsException.class, () -> handler.authorize(event2), "Wrong Login/Password")
        );
    }
}