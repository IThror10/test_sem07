package TermPedia.factory.query.postgres;

import TermPedia.queries.instances.IByNameGetSettings;
import TermPedia.queries.instances.authors.FindAuthorByNameQuery;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostgresAuthorsRequestsTest {

    @Test
    void getAuthorsByNameQuery() throws Exception {
        //Arrange
        PostgresAuthorsRequests requests = new PostgresAuthorsRequests();
        IByNameGetSettings settings = new FindAuthorByNameQuery("NAME", 5, 5);

        String expected = "SELECT name FROM data.authors WHERE lower(name) like lower('%NAME%') " +
                "ORDER BY name LIMIT 5 OFFSET 5";

        //Act
        String query = requests.getAuthorsByNameQuery(settings);

        //Assert
        assertEquals(expected, query);
    }
}