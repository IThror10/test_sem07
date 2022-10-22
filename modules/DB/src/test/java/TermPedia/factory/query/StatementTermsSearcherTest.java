package TermPedia.factory.query;

import TermPedia.dto.ActionsException;
import TermPedia.dto.Term;
import TermPedia.factory.EnvProvider;
import TermPedia.factory.query.postgres.PostgresQueryFactory;
import TermPedia.queries.instances.terms.FindTermByNameQuery;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class StatementTermsSearcherTest {
    StatementTermsSearcherTest() throws Exception {
        PostgresQueryFactory.completeRegistration();
        PostgresQueryFactory.setConnectionEstablisher(new TestPostgresQueryConnection());
        PostgresQueryFactory.setProvider(new EnvProvider());
    }
    @Test
    void getTermsByName() throws Exception {
        TermsSearcher searcher = QueryFactory.instance().createTermSearcher();

        Vector<Term> terms1 = searcher.getTermsByName(new FindTermByNameQuery(
                "pattern", 2, 0)).getTerms();

        Vector<Term> terms2 = searcher.getTermsByName(new FindTermByNameQuery(
                "objects", 3, 0)).getTerms();

        Vector<Term> terms3 = searcher.getTermsByName(new FindTermByNameQuery(
                "objects", 1, 0)).getTerms();

        assertAll(
                () -> assertEquals("Singleton", terms1.get(0).name),
                () -> assertEquals("Single object pattern", terms1.get(0).description),
                () -> assertEquals(1, terms1.size()),

                () -> assertEquals("OOP", terms2.get(0).name),
                () -> assertEquals("Object Oriented Programming", terms2.get(0).description),
                () -> assertEquals("Singleton", terms2.get(1).name),
                () -> assertEquals("Single object pattern", terms2.get(1).description),
                () -> assertEquals(2, terms2.size()),

                () -> assertEquals("OOP", terms3.get(0).name),
                () -> assertEquals("Object Oriented Programming", terms3.get(0).description),
                () -> assertEquals(1, terms3.size()),

                () -> assertThrows(ActionsException.class, () -> searcher.getTermsByName(new FindTermByNameQuery(
                        "object", 0, 0))),
                () -> assertThrows(ActionsException.class, () -> searcher.getTermsByName(new FindTermByNameQuery(
                        "object", 5, -10)))
        );
    }

    @Test
    void termExistsTest() throws Exception {
        TermsSearcher searcher = QueryFactory.instance().createTermSearcher();

        Term term1 = new Term("Singleton", null);
        Term term2 = new Term("NewTermWasNotAdded", null);

        assertAll(
                () -> assertTrue(searcher.termExists(term1)),
                () -> assertFalse(searcher.termExists(term2))
        );
    }
}