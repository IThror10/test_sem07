package TermPedia.factory.query.postgres;

import TermPedia.dto.Term;
import TermPedia.events.EventType;
import TermPedia.factory.command.EventData;
import TermPedia.queries.instances.terms.FindTermByNameQuery;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostgresTermsRequestsTest {

    @Test
    void getTermsByNameQuery() throws Exception {
        //Arrange
        PostgresTermsRequests requests = new PostgresTermsRequests();
        FindTermByNameQuery settings = new FindTermByNameQuery("NAME", 8, 2);
        String expected = "SELECT terms.name, terms.description FROM data.terms WHERE lower(name) = lower('NAME') or " +
                "plainto_tsquery('NAME') @@ vector ORDER BY name LIMIT 8 OFFSET 2";

        //Act
        String query = requests.getTermsByNameQuery(settings);

        //Assert
        assertEquals(expected, query);
    }

    @Test
    void termsExists() {
        //Arrange
        PostgresTermsRequests requests = new PostgresTermsRequests();
        Term term = new Term("NAME", "description");
        String expected = "SELECT EXISTS (SELECT name FROM data.terms WHERE name = 'NAME');";

        //Act
        String query = requests.termsExists(term);

        //Assert
        assertEquals(expected, query);
    }
    @Test
    void newTermQuery() {
        //Arrange
        PostgresTermsRequests requests = new PostgresTermsRequests();
        EventData data = new EventData("{JSON}", EventType.new_term.ordinal(), 0);
        String expected = "call data.add_term('{JSON}');";

        //Act
        String query = requests.newTermQuery(data);

        //Assert
        assertEquals(expected, query);
    }

    @Test
    void addLitToTermQuery() {
        //Arrange
        PostgresTermsRequests requests = new PostgresTermsRequests();
        EventData data = new EventData("{JSON}", EventType.new_lit.ordinal(), 0);
        String expected = "call data.add_lit_term('{JSON}');";

        //Act
        String query = requests.addLitToTermQuery(data);

        //Assert
        assertEquals(expected, query);
    }

    @Test
    void rateLitTermQuery() {
        //Arrange
        PostgresTermsRequests requests = new PostgresTermsRequests();
        EventData data = new EventData("{JSON}", EventType.change_term_lit_rating.ordinal(), 0);
        String expected = "call data.rate_lit_term('{JSON}', 0);";

        //Act
        String query = requests.rateLitTermQuery(data);

        //Assert
        assertEquals(expected, query);
    }
}