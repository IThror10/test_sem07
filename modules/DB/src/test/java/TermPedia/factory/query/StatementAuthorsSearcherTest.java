package TermPedia.factory.query;

import TermPedia.dto.ActionsException;
import TermPedia.factory.adapters.PostgresAdapter;
import TermPedia.factory.query.postgres.PostgresAuthorsRequests;
import TermPedia.queries.instances.authors.FindAuthorByNameQuery;
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

class StatementAuthorsSearcherTest {
    @Mock
    Connection connection;
    @Mock
    Statement statement;
    @Mock
    ResultSet resultSet;

    StatementAuthorsSearcherTest() throws Exception {
        connection = Mockito.mock(Connection.class);
        statement = Mockito.mock(Statement.class);
        resultSet = Mockito.mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.getResultSet()).thenReturn(resultSet);
    }
    @Test
    void getAuthorByName() throws Exception {
        //Mock
        when(statement.execute(any(String.class))).thenReturn(true);
        when(resultSet.next()).thenReturn(true).thenReturn(false).thenReturn(true)
                .thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getString(any(String.class))).thenReturn("ABCD");

        //Arrange
        StatementAuthorsSearcher searcher = new StatementAuthorsSearcher(
                new PostgresAdapter(connection),
                new PostgresAuthorsRequests()
        );

        FindAuthorByNameQuery searchLondon = new FindAuthorByNameQuery("london", 2, 0);
        FindAuthorByNameQuery searchAll = new FindAuthorByNameQuery("d", 4, 0);
        FindAuthorByNameQuery wrongLimits = new FindAuthorByNameQuery("d", -1, 3);

        //Act
        Vector<String> londonRes = searcher.getAuthorByName(searchLondon).getAuthors();
        Vector<String> allRes = searcher.getAuthorByName(searchAll).getAuthors();
        Executable wrongLimitsException = () -> searcher.getAuthorByName(wrongLimits);

        //Assert
        assertEquals(1, londonRes.size());
        assertEquals(3, allRes.size());
        assertEquals("ABCD", allRes.get(0));
        assertThrows(ActionsException.class, wrongLimitsException);
    }
}