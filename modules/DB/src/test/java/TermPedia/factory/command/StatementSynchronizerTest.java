package TermPedia.factory.command;

import TermPedia.factory.adapters.PostgresAdapter;
import TermPedia.factory.command.postgres.PostgresSyncRequests;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StatementSynchronizerTest {
    @Mock
    Connection connection;
    @Mock
    Statement statement;
    @Mock
    ResultSet resultSet;

    StatementSynchronizerTest() throws Exception {
        connection = Mockito.mock(Connection.class);
        statement = Mockito.mock(Statement.class);
        resultSet = Mockito.mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.getResultSet()).thenReturn(resultSet);
    }

    @Test
    void hasNewRows() throws Exception {
        //Mock
        when(statement.execute(any(String.class))).thenReturn(true).thenReturn(false);

        //Arrange
        StatementSynchronizer synchronizer = new StatementSynchronizer(
                new PostgresAdapter(connection),
                new PostgresSyncRequests()
        );

        //Act
        boolean result1 = synchronizer.hasNewRows();
        boolean result2 = synchronizer.hasNewRows();

        //Assert
        assertTrue(result1);
        assertFalse(result2);
    }

    @Test
    void getEventData() throws Exception {
        //Mock
        when(statement.execute(any(String.class))).thenReturn(true);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        when(resultSet.getString("eventdata")).thenReturn("{JSON}");
        when(resultSet.getInt("eventtype")).thenReturn(2);
        when(resultSet.getInt("uid")).thenReturn(0);

        //Arrange
        StatementSynchronizer synchronizer = new StatementSynchronizer(
                new PostgresAdapter(connection),
                new PostgresSyncRequests()
        );

        //Act
        synchronizer.hasNewRows();
        EventData data = synchronizer.getEventData();
        EventData empty = synchronizer.getEventData();

        //Assert
        assertEquals("{JSON}", data.json);
        assertEquals(2, data.type.ordinal());
        assertEquals(0, data.uid);
        assertEquals(null, empty);
    }
}