package TermPedia.factory.query;

import TermPedia.dto.ActionsException;
import TermPedia.dto.Book;
import TermPedia.dto.RatedBook;
import TermPedia.dto.TagBook;
import TermPedia.factory.adapters.PostgresAdapter;
import TermPedia.factory.query.postgres.PostgresBookRequests;
import TermPedia.queries.books.*;
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

class StatementBookSearcherTest {
    @Mock
    Connection connection;
    @Mock
    Statement statement;
    @Mock
    ResultSet resultSet;

    StatementBookSearcherTest() throws Exception {
        connection = Mockito.mock(Connection.class);
        statement = Mockito.mock(Statement.class);
        resultSet = Mockito.mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.getResultSet()).thenReturn(resultSet);

        when(statement.execute(any(String.class))).thenReturn(true);
        when(resultSet.getString("name")).thenReturn("Go4");
        when(resultSet.getString("type")).thenReturn("book");
        when(resultSet.getInt("year")).thenReturn(2020);
        when(resultSet.getString("authors")).thenReturn("[]");
        when(resultSet.getDouble("rating")).thenReturn(4.0);
        when(resultSet.getInt("rates_amount")).thenReturn(5);
        when(resultSet.getInt("user_rating")).thenReturn(4);
    }
    @Test
    void searchByBookName() throws Exception {
        //Mock
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false)
                .thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);

        //Arrange
        StatementBookSearcher searcher = new StatementBookSearcher(
                new PostgresAdapter(connection),
                new PostgresBookRequests()
        );

        SearchBookByLikeNameQuery query = new SearchBookByLikeNameQuery
                ("any", false, 3, 3, 5);
        SearchBookByLikeNameQuery exception = new SearchBookByLikeNameQuery
                ("any", false, 3, -3, 0);

        //Act
        Vector<TagBook> books = searcher.searchByBookName(query).getBooks();
        Executable throwing = () -> searcher.searchByBookName(exception);

        //Assert
        assertEquals(2, books.size());
        assertEquals("Go4", books.get(0).name);
        assertEquals(4, books.get(0).rating);
        assertThrows(ActionsException.class, throwing);
    }

    @Test
    void searchByTagName() throws Exception {
        //Mock
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false)
                .thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);

        //Arrange
        StatementBookSearcher searcher = new StatementBookSearcher(
                new PostgresAdapter(connection),
                new PostgresBookRequests()
        );

        SearchBookByTagQuery query = new SearchBookByTagQuery(false, 3.0, 5, 3);
        SearchBookByTagQuery exception = new SearchBookByTagQuery(false, 3.0, -2, 4);

        //Act
        Vector<TagBook> books = searcher.searchByTagName(query).getBooks();
        Executable throwing = () -> searcher.searchByTagName(exception);

        //Assert
        assertEquals(2, books.size());
        assertEquals("Go4", books.get(0).name);
        assertEquals(4, books.get(0).rating);
        assertThrows(ActionsException.class, throwing);
    }

    @Test
    void searchByAuthorName() throws Exception {
        //Mock
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

        //Arrange
        StatementBookSearcher searcher = new StatementBookSearcher(
                new PostgresAdapter(connection),
                new PostgresBookRequests()
        );

        SearchBookByAuthorQuery query = new SearchBookByAuthorQuery("any", 3, 0);
        SearchBookByAuthorQuery exception = new SearchBookByAuthorQuery("any", -3, 0);

        //Act
        Vector<Book> books = searcher.searchByAuthorName(query).getBooks();
        Executable throwing = () -> searcher.searchByAuthorName(exception);

        //Assert
        assertEquals(2, books.size());
        assertEquals("Go4", books.get(0).name);
        assertThrows(ActionsException.class, throwing);
    }

    @Test
    void searchByTermName() throws Exception {
        //Mock
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false).thenReturn(true)
                .thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);

        //Arrange
        StatementBookSearcher searcher = new StatementBookSearcher(
                new PostgresAdapter(connection),
                new PostgresBookRequests()
        );

        SearchBookByTermQuery query = new SearchBookByTermQuery
                ("any", false, false, 3.0, 0, 3, 5);
        SearchBookByTermQuery exception = new SearchBookByTermQuery
                ("any", false, false, 3.0, 0, -3, 5);

        //Act
        Vector<RatedBook> books = searcher.searchByTermName(query).getBooks();
        Executable throwing = () -> searcher.searchByTermName(exception);

        //Assert
        assertEquals(2, books.size());
        assertEquals("Go4", books.get(0).name);
        assertEquals(5, books.get(0).ratesAmount);
        assertThrows(ActionsException.class, throwing);
    }

    @Test
    void searchBooksConnectedToTerm() throws Exception {
        //Mock
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false)
                .thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);

        //Arrange
        StatementBookSearcher searcher = new StatementBookSearcher(
                new PostgresAdapter(connection),
                new PostgresBookRequests()
        );

        SearchBookByTermQuery query = new SearchBookByTermQuery
                ("any", false, false, 3.0, 0, 3, 5);
        SearchBookByTermQuery exception = new SearchBookByTermQuery
                ("any", false, false, 3.0, 0, -3, 5);

        //Act
        Vector<RatedBook> books = searcher.searchConnectedBooks(query).getBooks();
        Executable throwing = () -> searcher.searchConnectedBooks(exception);

        //Assert
        assertEquals(2, books.size());
        assertEquals("Go4", books.get(0).name);
        assertEquals(4, books.get(0).userRating);
        assertThrows(ActionsException.class, throwing);
    }
}