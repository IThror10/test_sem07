package TermPedia.factory.command;

import TermPedia.events.EventType;
import TermPedia.factory.EnvProvider;
import TermPedia.factory.command.common.ISynchronizer;
import TermPedia.factory.command.mssql.MsSqlCommandFactory;
import TermPedia.factory.command.postgres.PostgresCommandFactory;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class StatementSynchronizerTest {
    StatementSynchronizerTest() {
        PostgresCommandFactory.completeRegistration();
        PostgresCommandFactory.setConnectionEstablisher(new TestPostgresCommandConnection());

        MsSqlCommandFactory.completeRegistration();
        MsSqlCommandFactory.setConnectionEstablisher(new TestMsSqlCommandConnection());

        SyncCommandFactory.setProvider(new EnvProvider());
    }

    @Test
    void synchronizerClass() throws Exception {
        ISynchronizer sync = SyncCommandFactory.instance().createSynchronizer();

        assertTrue(sync.hasNewRows());
        assertTrue(EventType.registration == sync.getEventData().type);
        sync.updated();
        assertTrue(EventType.new_term == sync.getEventData().type);
        sync.updated();
        assertTrue(EventType.new_tag == sync.getEventData().type);
        sync.updated();
        assertTrue(EventType.new_lit == sync.getEventData().type);
        sync.updated();
        assertTrue(EventType.change_term_tag_rating == sync.getEventData().type);
        sync.updated();
        assertTrue(EventType.change_term_lit_rating == sync.getEventData().type);
        sync.updated();

        sync.freeUsed();
    }
}