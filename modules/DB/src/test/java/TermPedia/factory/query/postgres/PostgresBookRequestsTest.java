package TermPedia.factory.query.postgres;

import TermPedia.queries.books.*;
import TermPedia.queries.instances.IByNameGetSettings;
import TermPedia.queries.instances.authors.FindAuthorByNameQuery;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PostgresBookRequestsTest {

    @Test
    void authorSearchQuery() throws Exception {
        //Arrange
        PostgresBookRequests requests = new PostgresBookRequests();
        SearchBookByAuthorQuery noType = new SearchBookByAuthorQuery("NAME", 10, 5);
        noType.setYearsLimits(2000, 3000);
        SearchBookByAuthorQuery withType = new SearchBookByAuthorQuery("NAME", 10, 5);
        withType.setLitType("TYPE");
        withType.setYearsLimits(2000, 3000);

        String expectedNoType = "SELECT DISTINCT l.name, l.type, l.year, l.authors FROM (SELECT name FROM data.authors " +
                "WHERE lower(name) like lower('%NAME%')) as a JOIN data.authors_lit al on a.name = al.author " +
                "JOIN data.lit l on l.lid = al.lid WHERE l.year >= 2000 and l.year <= 3000 " +
                "ORDER BY l.name LIMIT 10 OFFSET 5";

        String expectedType = "SELECT DISTINCT l.name, l.type, l.year, l.authors FROM (SELECT name FROM data.authors " +
                "WHERE lower(name) like lower('%NAME%')) as a JOIN data.authors_lit al on a.name = al.author " +
                "JOIN data.lit l on l.lid = al.lid WHERE l.year >= 2000 and l.year <= 3000 and l.type = TYPE " +
                "ORDER BY l.name LIMIT 10 OFFSET 5";

        //Act
        String typeQuery = requests.authorSearchQuery(withType);
        String noTypeQuery = requests.authorSearchQuery(noType);

        //Assert
        assertEquals(expectedNoType, noTypeQuery);
        assertEquals(expectedType, typeQuery);
    }

    @Test
    void bookSearchQuery() throws Exception {
        //Arrange
        PostgresBookRequests requests = new PostgresBookRequests();
        Vector<String> tags = new Vector<>();
        tags.add("TAG1");
        tags.add("TAG2");

        String withBestRatingType =  "SELECT name, type, year, authors, min(rating) as rating FROM " +
                "(SELECT LID FROM data.lit WHERE lower(name) like lower('%NAME%') or vector @@ " +
                "plainto_tsquery('NAME')) as l JOIN data.book_search bs on l.LID = bs.LID WHERE " +
                "year >= 0 and year <= 3000 and type = TYPE and (tag = 'TAG1' and rating >= 5.0 or " +
                "tag = 'TAG2' and rating >= 5.0) GROUP BY (name, type, year, authors) HAVING count(*) = 2 " +
                "ORDER BY rating DESC LIMIT 5 OFFSET 5";
        SearchBookByLikeNameQuery ratingSettings = new SearchBookByLikeNameQuery
                ("NAME", true, 5, 5, 5);
        ratingSettings.setLitType("TYPE");
        ratingSettings.setTags(tags);

        String noRating = "SELECT name, type, year, authors, rating as rating FROM " +
                "(SELECT LID FROM data.lit WHERE lower(name) like lower('%NAME%') or vector @@ " +
                "plainto_tsquery('NAME')) as l JOIN data.book_search bs on l.LID = bs.LID WHERE " +
                "year >= 0 and year <= 3000 and tag is null LIMIT 5 OFFSET 5";
        SearchBookByLikeNameQuery noRatingSettings = new SearchBookByLikeNameQuery
                ("NAME", false, 5, 5, 5);

        //Act
        String ratingQuery = requests.bookSearchQuery(ratingSettings);
        String noRatingQuery = requests.bookSearchQuery(noRatingSettings);

        //Assert
        assertEquals(withBestRatingType, ratingQuery);
        assertEquals(noRating, noRatingQuery);
    }

    @Test
    void anySearchQuery() throws Exception {
        //Arrange
        PostgresBookRequests requests = new PostgresBookRequests();
        Vector<String> tags = new Vector<>();
        tags.add("TAG1");
        tags.add("TAG2");

        String withBestRatingType =  "SELECT name, type, year, authors, min(rating) as rating FROM data.book_search " +
                "WHERE year >= 0 and year <= 3000 and type = TYPE and (tag = 'TAG1' and rating >= 5.0 or " +
                "tag = 'TAG2' and rating >= 5.0) GROUP BY (name, type, year, authors) HAVING count(*) = 2 " +
                "ORDER BY rating DESC LIMIT 5 OFFSET 5";
        SearchBookByTagQuery ratingSettings = new SearchBookByTagQuery
                (true, 5, 5, 5);
        ratingSettings.setLitType("TYPE");
        ratingSettings.setTags(tags);

        String noRating = "SELECT name, type, year, authors, rating as rating FROM data.book_search WHERE " +
                "year >= 0 and year <= 3000 and tag is null LIMIT 5 OFFSET 5";
        SearchBookByTagQuery noRatingSettings = new SearchBookByTagQuery
                (false, 5, 5, 5);

        //Act
        String ratingQuery = requests.anySearchQuery(ratingSettings);
        String noRatingQuery = requests.anySearchQuery(noRatingSettings);

        //Assert
        assertEquals(withBestRatingType, ratingQuery);
        assertEquals(noRating, noRatingQuery);
    }

    @Test
    void termSearchQuery() throws Exception {
        //Arrange
        PostgresBookRequests requests = new PostgresBookRequests();

        SearchBookByLikeTermQuery recentlySettings = new SearchBookByLikeTermQuery
                ("NAME", false, true, 3, 0, 5, 5);
        recentlySettings.setLitType("TYPE");
        String recentlyWithTypeExpected = "with terms(name) as (SELECT name FROM data.terms WHERE lower(name) = lower('NAME') " +
                "or vector @@ plainto_tsquery('NAME')), term_lit (LID, rating, rates_amount) as " +
                "(SELECT LID, rating, rates_amount FROM (SELECT tl.LID, rating, rates_amount, " +
                "row_number() over (partition by LID order by rating DESC) as rn FROM terms t " +
                "JOIN data.terms_lit tl on tl.term = t.name and tl.rating >= 3.0) as temp WHERE rn = 1) " +
                "SELECT l.name, l.type, l.year, l.authors, tl.rating, tl.rates_amount FROM " +
                "term_lit tl JOIN data.lit l on l.LID = tl.LID WHERE year >= 0 and year <= 3000 and type = TYPE " +
                "ORDER BY tl.rates_amount LIMIT 5 OFFSET 5";

        SearchBookByLikeTermQuery bestSettings = new SearchBookByLikeTermQuery
                ("NAME", true, false, 3, 0, 5, 5);
        String bestExpected = "with terms(name) as (SELECT name FROM data.terms WHERE lower(name) = lower('NAME') " +
                "or vector @@ plainto_tsquery('NAME')), term_lit (LID, rating, rates_amount) as " +
                "(SELECT LID, rating, rates_amount FROM (SELECT tl.LID, rating, rates_amount, " +
                "row_number() over (partition by LID order by rating DESC) as rn FROM terms t " +
                "JOIN data.terms_lit tl on tl.term = t.name and tl.rating >= 3.0) as temp WHERE rn = 1) " +
                "SELECT l.name, l.type, l.year, l.authors, tl.rating, tl.rates_amount FROM " +
                "term_lit tl JOIN data.lit l on l.LID = tl.LID WHERE year >= 0 and year <= 3000 " +
                "ORDER BY tl.rating DESC LIMIT 5 OFFSET 5";

        SearchBookByLikeTermQuery bothSettings = new SearchBookByLikeTermQuery
                ("NAME", true, true, 3, 0, 5, 5);
        String bothExpected = "with terms(name) as (SELECT name FROM data.terms WHERE lower(name) = lower('NAME') " +
                "or vector @@ plainto_tsquery('NAME')), term_lit (LID, rating, rates_amount) as " +
                "(SELECT LID, rating, rates_amount FROM (SELECT tl.LID, rating, rates_amount, " +
                "row_number() over (partition by LID order by rating DESC) as rn FROM terms t " +
                "JOIN data.terms_lit tl on tl.term = t.name and tl.rating >= 3.0) as temp WHERE rn = 1) " +
                "SELECT l.name, l.type, l.year, l.authors, tl.rating, tl.rates_amount FROM " +
                "term_lit tl JOIN data.lit l on l.LID = tl.LID WHERE year >= 0 and year <= 3000 " +
                "ORDER BY tl.rates_amount, tl.rating DESC LIMIT 5 OFFSET 5";

        //Act
        String recentlyWithTypeQuery = requests.termSearchQuery(recentlySettings);
        String bestQuery = requests.termSearchQuery(bestSettings);
        String bothQuery = requests.termSearchQuery(bothSettings);

        //Assert
        assertEquals(recentlyWithTypeExpected, recentlyWithTypeQuery);
        assertEquals(bestExpected, bestQuery);
        assertEquals(bothExpected, bothQuery);
    }

    @Test
    void connectedTermsSearchQuery() throws Exception {
        //Arrange
        PostgresBookRequests requests = new PostgresBookRequests();

        SearchBookByTermQuery bestSettings = new SearchBookByTermQuery
                ("TERM", true, false, 3, 12, 5, 5);
        bestSettings.setLitType("TYPE");
        String bestWithTypeExpected = "SELECT l.name, l.type, l.year, l.authors, d.rating, d.rates_amount, " +
                "d.user_rating FROM (SELECT tl.LID, tl.term, tl.rating, tl.rates_amount, CASE WHEN tlr.rating " +
                "IS NULL THEN 0 ELSE tlr.rating END as user_rating FROM data.terms_lit tl LEFT JOIN " +
                "data.term_lit_rates tlr on tl.term = tlr.term and tl.LID = tlr.LID and tlr.uid = 12 WHERE " +
                "tl.term = 'TERM' and tl.rating >= 3.0) as d JOIN data.lit l ON l.LID = d.LID and year >= 0 " +
                "and year <= 3000 and type = TYPE ORDER BY d.rating DESC LIMIT 5 OFFSET 5";

        SearchBookByTermQuery recentlySettings = new SearchBookByTermQuery
                ("TERM", false, true, 3, 12, 5, 5);
        String recentlyExpected = "SELECT l.name, l.type, l.year, l.authors, d.rating, d.rates_amount, " +
                "d.user_rating FROM (SELECT tl.LID, tl.term, tl.rating, tl.rates_amount, CASE WHEN tlr.rating " +
                "IS NULL THEN 0 ELSE tlr.rating END as user_rating FROM data.terms_lit tl LEFT JOIN " +
                "data.term_lit_rates tlr on tl.term = tlr.term and tl.LID = tlr.LID and tlr.uid = 12 WHERE " +
                "tl.term = 'TERM' and tl.rating >= 3.0) as d JOIN data.lit l ON l.LID = d.LID and year >= 0 " +
                "and year <= 3000 ORDER BY d.rates_amount LIMIT 5 OFFSET 5";

        SearchBookByTermQuery bothSettings = new SearchBookByTermQuery
                ("TERM", true, true, 3, 12, 5, 5);
        String bothExpected = "SELECT l.name, l.type, l.year, l.authors, d.rating, d.rates_amount, " +
                "d.user_rating FROM (SELECT tl.LID, tl.term, tl.rating, tl.rates_amount, CASE WHEN tlr.rating " +
                "IS NULL THEN 0 ELSE tlr.rating END as user_rating FROM data.terms_lit tl LEFT JOIN " +
                "data.term_lit_rates tlr on tl.term = tlr.term and tl.LID = tlr.LID and tlr.uid = 12 WHERE " +
                "tl.term = 'TERM' and tl.rating >= 3.0) as d JOIN data.lit l ON l.LID = d.LID and year >= 0 " +
                "and year <= 3000 ORDER BY d.rates_amount, d.rating DESC LIMIT 5 OFFSET 5";

        //Act
        String bestWithTypeQuery = requests.connectedTermsSearchQuery(bestSettings);
        String recentlyQuery = requests.connectedTermsSearchQuery(recentlySettings);
        String bothQuery = requests.connectedTermsSearchQuery(bothSettings);

        //Assert
        assertEquals(bestWithTypeExpected, bestWithTypeQuery);
        assertEquals(recentlyExpected, recentlyQuery);
        assertEquals(bothExpected, bothQuery);
    }
}