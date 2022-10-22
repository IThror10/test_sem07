package TermPedia.factory.query;

import TermPedia.dto.ActionsException;
import TermPedia.factory.EnvProvider;
import TermPedia.factory.query.postgres.PostgresQueryFactory;
import TermPedia.queries.instances.authors.FindAuthorByNameQuery;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class StatementAuthorsSearcherTest {
    StatementAuthorsSearcherTest() throws Exception {
        PostgresQueryFactory.completeRegistration();
        PostgresQueryFactory.setConnectionEstablisher(new TestPostgresQueryConnection());
        PostgresQueryFactory.setProvider(new EnvProvider());
    }
    @Test
    void getAuthorByName() throws Exception {
        AuthorsSearcher searcher = QueryFactory.instance().createAuthorSearcher();

        Vector<String> authors1 = searcher.getAuthorByName(
                new FindAuthorByNameQuery("london", 2, 0)).getAuthors();

        Vector<String> authors2 = searcher.getAuthorByName(new FindAuthorByNameQuery(
                "d", 3, 0)).getAuthors();

        Vector<String> authors3 = searcher.getAuthorByName(new FindAuthorByNameQuery(
                "d", 1, 0)).getAuthors();

        assertAll(
                () -> assertEquals("London J.", authors1.get(0)),
                () -> assertEquals(1, authors1.size()),
                () -> assertEquals("ABCD", authors2.get(0)),
                () -> assertEquals("DEF", authors2.get(1)),
                () -> assertEquals("London J.", authors2.get(2)),
                () -> assertEquals(3, authors2.size()),
                () -> assertEquals("ABCD", authors3.get(0)),
                () -> assertEquals(1, authors3.size()),
                () -> assertThrows(ActionsException.class, () -> searcher.getAuthorByName(new FindAuthorByNameQuery(
                        "London", 0, 10))),
                () -> assertThrows(ActionsException.class, () -> searcher.getAuthorByName(new FindAuthorByNameQuery(
                                "London", 10, -10)))
        );
    }
}