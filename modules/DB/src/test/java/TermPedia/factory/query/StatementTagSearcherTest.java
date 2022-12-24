package TermPedia.factory.query;

import TermPedia.dto.ActionsException;
import TermPedia.dto.RatedTag;
import TermPedia.dto.Tag;
import TermPedia.factory.adapters.PostgresAdapter;
import TermPedia.factory.query.postgres.PostgresTagsRequests;
import TermPedia.queries.instances.tags.FindTagByNameQuery;
import TermPedia.queries.instances.tags.FindTagByTermNameQuery;
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

class StatementTagSearcherTest {
    @Mock
    Connection connection;
    @Mock
    Statement statement;
    @Mock
    ResultSet resultSet;

    StatementTagSearcherTest() throws Exception {
        connection = Mockito.mock(Connection.class);
        statement = Mockito.mock(Statement.class);
        resultSet = Mockito.mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.getResultSet()).thenReturn(resultSet);
        when(statement.execute(any(String.class))).thenReturn(true);
    }
    @Test
    void getTagsByName() throws Exception {
        //Mock
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getString("name")).thenReturn("Pattern");

        //Arrange
        StatementTagSearcher searcher = new StatementTagSearcher(
            new PostgresAdapter(connection),
            new PostgresTagsRequests()
        );

        FindTagByNameQuery searchPattern = new FindTagByNameQuery("pattern", 3, 0);
        FindTagByNameQuery searchException = new FindTagByNameQuery("pattern", -3, 5);

        //Act
        Vector<Tag> patternRes = searcher.getTagsByName(searchPattern).getTags();
        Executable throwsException = () -> searcher.getTagsByName(searchException);

        //Assert
        assertEquals(2, patternRes.size());
        assertEquals(new Tag("Pattern").toString(), patternRes.get(1).toString());
        assertThrows(ActionsException.class, throwsException);
    }

    @Test
    void getTagsByTerm() throws Exception {
        //Mock
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getString("tag")).thenReturn("Pattern");
        when(resultSet.getDouble("rating")).thenReturn(3.0);;
        when(resultSet.getInt("rates_amount")).thenReturn(5);
        when(resultSet.getInt("user_rating")).thenReturn(4);

        //Arrange
        StatementTagSearcher searcher = new StatementTagSearcher(
                new PostgresAdapter(connection),
                new PostgresTagsRequests()
        );

        FindTagByTermNameQuery searchPattern = new FindTagByTermNameQuery
                ("pattern", 3, 0, 0, false);
        FindTagByTermNameQuery searchException = new FindTagByTermNameQuery
                ("pattern", -3, 5, 0, false);

        //Act
        Vector<RatedTag> patternRes = searcher.getTagsByTerm(searchPattern).getTags();
        Executable throwsException = () -> searcher.getTagsByTerm(searchException);

        //Assert
        assertEquals(2, patternRes.size());
        assertEquals(new RatedTag("Pattern", 3.0, 5, 4).toString(),
                patternRes.get(1).toString());
        assertThrows(ActionsException.class, throwsException);
    }
}