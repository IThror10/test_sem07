package TermPedia.factory.query;

import TermPedia.dto.ActionsException;
import TermPedia.factory.adapters.PostgresAdapter;
import TermPedia.factory.query.postgres.PostgresLitTypesRequests;
import TermPedia.queries.instances.types.FindLitTypesByNameQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StatementLitTypesSearcherTest {
    @Mock
    Connection connection;
    @Mock
    Statement statement;
    @Mock
    ResultSet resultSet;

    StatementLitTypesSearcherTest() throws Exception {
        connection = Mockito.mock(Connection.class);
        statement = Mockito.mock(Statement.class);
        resultSet = Mockito.mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.getResultSet()).thenReturn(resultSet);
        when(statement.execute(any(String.class))).thenReturn(true);
    }
    @Test
    void getLitTypesByName() throws Exception {
        //Mock
        when(resultSet.next()).thenReturn(true).thenReturn(false)
                .thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getString(any(String.class))).thenReturn("book");

        //Arrange
        StatementLitTypesSearcher searcher = new StatementLitTypesSearcher(
                new PostgresAdapter(connection),
                new PostgresLitTypesRequests()
        );
        FindLitTypesByNameQuery searchBook = new FindLitTypesByNameQuery("book", 2, 0);
        FindLitTypesByNameQuery searchAll = new FindLitTypesByNameQuery("o", 4, 0);
        FindLitTypesByNameQuery wrongLimits = new FindLitTypesByNameQuery("d", -1, 3);

        //Act
        Vector<String> londonRes = searcher.getLitTypesByName(searchBook).getLitTypes();
        Vector<String> allRes = searcher.getLitTypesByName(searchAll).getLitTypes();
        Executable wrongLimitsException = () -> searcher.getLitTypesByName(wrongLimits);

        //Assert
        assertEquals(1, londonRes.size());
        assertEquals(2, allRes.size());
        assertEquals("book", allRes.get(0));
        assertThrows(ActionsException.class, wrongLimitsException);
    }
}