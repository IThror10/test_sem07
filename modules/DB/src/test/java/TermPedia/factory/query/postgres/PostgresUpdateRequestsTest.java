package TermPedia.factory.query.postgres;

import TermPedia.factory.command.EventData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostgresUpdateRequestsTest {
    @Test
    void newUserQuery() {
        //Arrange
        PostgresUpdateRequests requests = new PostgresUpdateRequests();
        EventData data = new EventData("{JSON}", 0, 0);
        String expected = new PostgresUsersRequests().addUserQuery(data);

        //Act
        String query = requests.newUserQuery(data);

        //Assert
        assertEquals(expected, query);
    }

    @Test
    void newTermQuery() {
        //Arrange
        PostgresUpdateRequests requests = new PostgresUpdateRequests();
        EventData data = new EventData("{JSON}", 0, 0);
        String expected = new PostgresTermsRequests().newTermQuery(data);

        //Act
        String query = requests.newTermQuery(data);

        //Assert
        assertEquals(expected, query);
    }

    @Test
    void newLitTermPareQuery() {
        //Arrange
        PostgresUpdateRequests requests = new PostgresUpdateRequests();
        EventData data = new EventData("{JSON}", 0, 0);
        String expected = new PostgresTermsRequests().addLitToTermQuery(data);

        //Act
        String query = requests.newLitTermPareQuery(data);

        //Assert
        assertEquals(expected, query);
    }

    @Test
    void newTagTermPareQuery() {
        //Arrange
        PostgresUpdateRequests requests = new PostgresUpdateRequests();
        EventData data = new EventData("{JSON}", 0, 0);
        String expected = new PostgresTagsRequests().addTagToTermQuery(data);

        //Act
        String query = requests.newTagTermPareQuery(data);

        //Assert
        assertEquals(expected, query);
    }

    @Test
    void newRateTermLitQuery() {
        //Arrange
        PostgresUpdateRequests requests = new PostgresUpdateRequests();
        EventData data = new EventData("{JSON}", 0, 0);
        String expected = new PostgresTermsRequests().rateLitTermQuery(data);

        //Act
        String query = requests.newRateTermLitQuery(data);

        //Assert
        assertEquals(expected, query);
    }

    @Test
    void newRateTermTagQuery() {
        //Arrange
        PostgresUpdateRequests requests = new PostgresUpdateRequests();
        EventData data = new EventData("{JSON}", 0, 0);
        String expected = new PostgresTagsRequests().rateTagTermQuery(data);

        //Act
        String query = requests.newRateTermTagQuery(data);

        //Assert
        assertEquals(expected, query);
    }
}
