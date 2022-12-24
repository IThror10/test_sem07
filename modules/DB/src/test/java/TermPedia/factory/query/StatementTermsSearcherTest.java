package TermPedia.factory.query;

import TermPedia.dto.ActionsException;
import TermPedia.dto.Term;
import TermPedia.factory.adapters.PostgresAdapter;
import TermPedia.factory.query.postgres.PostgresTermsRequests;
import TermPedia.queries.instances.terms.FindTermByNameQuery;
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

class StatementTermsSearcherTest {
    @Mock
    Connection connection;
    @Mock
    Statement statement;
    @Mock
    ResultSet resultSet;

    StatementTermsSearcherTest() throws Exception {
        connection = Mockito.mock(Connection.class);
        statement = Mockito.mock(Statement.class);
        resultSet = Mockito.mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.getResultSet()).thenReturn(resultSet);
        when(statement.execute(any(String.class))).thenReturn(true);
    }
    @Test
    void getTermsByName() throws Exception {
        //Mock
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getString("name")).thenReturn("abc");
        when(resultSet.getString("description")).thenReturn("alpha betta gamma");

        //Arrange
        StatementTermsSearcher searcher = new StatementTermsSearcher(
                new PostgresAdapter(connection),
                new PostgresTermsRequests()
        );

        FindTermByNameQuery searchBC = new FindTermByNameQuery
                ("bc", 3, 0);
        FindTermByNameQuery searchException = new FindTermByNameQuery
                ("abc", -3, 0);

        //Act
        Vector<Term> terms = searcher.getTermsByName(searchBC).getTerms();
        Executable throwsException = () -> searcher.getTermsByName(searchException);

        //Assert
        assertEquals(2, terms.size());
        assertEquals(new Term("abc", "alpha betta gamma").toString(),
                terms.get(0).toString());
        assertThrows(ActionsException.class, throwsException);
    }

    @Test
    void termExistsTest() throws Exception {
        //Mock
        when(resultSet.getBoolean(any(String.class))).thenReturn(true).thenReturn(false);
        //Arrange
        StatementTermsSearcher searcher = new StatementTermsSearcher(
                new PostgresAdapter(connection),
                new PostgresTermsRequests()
        );

        Term termABC = new Term("abc", "description long");
        Term termBC = new Term("bc", "other description");

        //Act
        boolean abcRes = searcher.termExists(termABC);
        boolean bcRes = searcher.termExists(termBC);

        //Assert
        assertTrue(abcRes);
        assertFalse(bcRes);
    }
}