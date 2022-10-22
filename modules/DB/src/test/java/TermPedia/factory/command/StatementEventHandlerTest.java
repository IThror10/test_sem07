package TermPedia.factory.command;


import TermPedia.dto.ActionsException;
import TermPedia.events.data.AddTermEvent;
import TermPedia.factory.EnvProvider;
import TermPedia.factory.command.mssql.MsSqlCommandFactory;
import TermPedia.factory.command.postgres.PostgresCommandFactory;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class StatementEventHandlerTest {
    StatementEventHandlerTest() throws Exception {
        PostgresCommandFactory.completeRegistration();
        PostgresCommandFactory.setConnectionEstablisher(new TestPostgresCommandConnection());

        MsSqlCommandFactory.completeRegistration();
        MsSqlCommandFactory.setConnectionEstablisher(new TestMsSqlCommandConnection());

        SyncCommandFactory.setProvider(new EnvProvider());
    }

    @Test
    void accept() throws Exception {
        AddTermEvent event1 = new AddTermEvent("OOP", "Object Oriented", 0);
        AddTermEvent event2 = new AddTermEvent("Other", "Throw something", -12);

        EventHandler handler = CommandFactory.instance().createEventHandler();
        assertAll(
                () -> assertEquals(true, handler.accept(event1).getStatus()),
                () -> assertThrows(ActionsException.class, () -> handler.accept(event2))
        );
    }
}