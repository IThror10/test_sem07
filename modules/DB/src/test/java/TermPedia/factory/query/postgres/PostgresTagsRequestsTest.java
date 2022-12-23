package TermPedia.factory.query.postgres;

import TermPedia.events.EventType;
import TermPedia.factory.command.EventData;
import TermPedia.queries.instances.tags.FindTagByNameQuery;
import TermPedia.queries.instances.tags.FindTagByTermNameQuery;
import jdk.jfr.Event;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostgresTagsRequestsTest {

    @Test
    void getTagsByNameQuery() throws Exception {
        //Arrange
        PostgresTagsRequests requests = new PostgresTagsRequests();
        FindTagByNameQuery settings = new FindTagByNameQuery("NAME", 5, 10);
        String expected = "SELECT name FROM data.tags WHERE lower(name) = lower('NAME') or plainto_tsquery('NAME') " +
                "@@ vector ORDER BY name LIMIT 5 OFFSET 10";

        //Act
        String query = requests.getTagsByNameQuery(settings);

        //Assert
        assertEquals(expected, query);
    }
    @Test
    void getTagsByTermNameQuery() throws Exception {
        //Arrange
        PostgresTagsRequests requests = new PostgresTagsRequests();

        FindTagByTermNameQuery recentlySettings = new FindTagByTermNameQuery
                ("NAME", 5, 12, 12, true);
        String recentlyExpected = "SELECT tt.tag, tt.rating, tt.rates_amount, CASE WHEN ttr.rating IS NULL THEN 0 " + "" +
                "ELSE ttr.rating END as user_rating FROM (SELECT term, tag, rating, rates_amount FROM " +
                "data.terms_tags where term = 'NAME') as tt LEFT JOIN data.term_tag_rates ttr on " + "" +
                "tt.term = ttr.term and tt.tag = ttr.tag and ttr.uid = 12 ORDER BY rates_amount, tt.rating DESC " +
                "LIMIT 5 OFFSET 12";

        FindTagByTermNameQuery bestSettings = new FindTagByTermNameQuery
                ("NAME", 5, 12, 12, false);
        String bestExpected = "SELECT tt.tag, tt.rating, tt.rates_amount, CASE WHEN ttr.rating IS NULL THEN 0 " + "" +
                "ELSE ttr.rating END as user_rating FROM (SELECT term, tag, rating, rates_amount FROM " +
                "data.terms_tags where term = 'NAME') as tt LEFT JOIN data.term_tag_rates ttr on " + "" +
                "tt.term = ttr.term and tt.tag = ttr.tag and ttr.uid = 12 ORDER BY tt.rating DESC, rates_amount DESC " +
                "LIMIT 5 OFFSET 12";

        //Act
        String recentlyQuery = requests.getTagsByTermNameQuery(recentlySettings);
        String bestQuery = requests.getTagsByTermNameQuery(bestSettings);

        //Assert
        assertEquals(recentlyExpected, recentlyQuery);
        assertEquals(bestExpected, bestQuery);
    }

    @Test
    void addTagToTermQuery() {
        //Arrange
        PostgresTagsRequests requests = new PostgresTagsRequests();
        EventData data = new EventData("{JSON}", EventType.new_term.ordinal(), 0);
        String expected = "call data.add_tag_term('{JSON}');";

        //Act
        String query = requests.addTagToTermQuery(data);

        //Assert
        assertEquals(expected, query);
    }

    @Test
    void rateTagTermQuery() {
        //Arrange
        PostgresTagsRequests requests = new PostgresTagsRequests();
        EventData data = new EventData("{JSON}", EventType.change_term_tag_rating.ordinal(), 0);
        String expected = "call data.rate_tag_term('{JSON}', 0);";

        //Act
        String query = requests.rateTagTermQuery(data);

        //Assert
        assertEquals(expected, query);
    }
}