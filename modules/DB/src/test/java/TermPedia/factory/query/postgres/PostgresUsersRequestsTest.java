package TermPedia.factory.query.postgres;

import TermPedia.events.EventType;
import TermPedia.factory.command.EventData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostgresUsersRequestsTest {

    @Test
    void addUserQuery() {
        //Arrange
        PostgresUsersRequests requests = new PostgresUsersRequests();
        EventData data = new EventData("{JSON}", EventType.registration.ordinal(), 0);
        String expected = "call data.add_user(0);";

        //Act
        String query = requests.addUserQuery(data);

        //Assert
        assertEquals(expected, query);
    }
}