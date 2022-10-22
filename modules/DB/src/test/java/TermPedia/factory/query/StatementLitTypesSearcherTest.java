package TermPedia.factory.query;

import TermPedia.dto.ActionsException;
import TermPedia.factory.EnvProvider;
import TermPedia.factory.query.postgres.PostgresQueryFactory;
import TermPedia.queries.instances.types.FindLitTypesByNameQuery;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class StatementLitTypesSearcherTest {
    StatementLitTypesSearcherTest() throws Exception {
        PostgresQueryFactory.completeRegistration();
        PostgresQueryFactory.setConnectionEstablisher(new TestPostgresQueryConnection());
        PostgresQueryFactory.setProvider(new EnvProvider());
    }
    @Test
    void getLitTypesByName() throws Exception {
        LitTypesSearcher searcher = QueryFactory.instance().createLitTypesSearcher();

        Vector<String> authors1 = searcher.getLitTypesByName(new FindLitTypesByNameQuery(
                "book", 2, 0)).getLitTypes();

        Vector<String> authors3 = searcher.getLitTypesByName(new FindLitTypesByNameQuery(
                "novel", 1, 0)).getLitTypes();

        assertAll(
                () -> assertEquals("book", authors1.get(0)),
                () -> assertEquals(1, authors1.size()),
                () -> assertEquals("Novel", authors3.get(0)),
                () -> assertEquals(1, authors3.size()),
                () -> assertThrows(ActionsException.class, () -> searcher.getLitTypesByName(new FindLitTypesByNameQuery(
                        "book", 0, 0))),
                () -> assertThrows(ActionsException.class, () -> searcher.getLitTypesByName(new FindLitTypesByNameQuery(
                        "book", 5, -10)))
        );
    }
}