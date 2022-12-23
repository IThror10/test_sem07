package TermPedia.factory.command;


import TermPedia.dto.ActionsException;
import TermPedia.events.data.AddTermEvent;
import TermPedia.factory.adapters.PostgresAdapter;
import TermPedia.factory.command.postgres.PostgresEventHandlerRequests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StatementEventHandlerTest {
    @Mock
    Connection connection;
    @Mock
    Statement statement;
    @Mock
    ResultSet resultSet;

    StatementEventHandlerTest() throws Exception {
        connection = Mockito.mock(Connection.class);
        statement = Mockito.mock(Statement.class);
        resultSet = Mockito.mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.getResultSet()).thenReturn(resultSet);
    }

    @Test
    void accept() throws Exception {
        //Mock
        when(statement.execute(any(String.class))).thenReturn(true).thenThrow(SQLException.class);
        when(resultSet.getBoolean(any(String.class))).thenReturn(true);

        //Arrange
        StatementEventHandler handler = new StatementEventHandler(
                new PostgresAdapter(connection),
                new PostgresEventHandlerRequests()
        );

        AddTermEvent event1 = new AddTermEvent("OOP", "Object Oriented", 0);
        AddTermEvent event2 = new AddTermEvent("Other", "Throw something", null);

        //Act
        boolean result = handler.accept(event1).getStatus();
        Executable wrongUID = () -> handler.accept(event2);

        //Assert
        assertTrue(result);
        assertThrows(ActionsException.class, wrongUID);
    }
}