package TermPedia.factory.command.postgres;

import TermPedia.events.user.AuthorizeEvent;
import TermPedia.events.user.RegisterEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostgresReqAuthHandlerRequestsTest {

    @Test
    void registerEventQuery() throws Exception {
        //Arrange
        PostgresReqAuthHandlerRequests handler = new PostgresReqAuthHandlerRequests();
        RegisterEvent event = new RegisterEvent("Login", "Password", "email@yandex.com");

        String query = "SELECT * FROM app.register_user('%s', '%s', %d);".formatted(
                event.getData(), event.dateTime.toString(), event.getEventType().ordinal());

        //Act
        String result = handler.registerEventQuery(event);

        //Assert
        assertEquals(query, result);
    }

    @Test
    void authorizeEventQuery() throws Exception {
        //Arrange
        PostgresReqAuthHandlerRequests handler = new PostgresReqAuthHandlerRequests();
        AuthorizeEvent event = new AuthorizeEvent("Login", "Password");

        String query = "SELECT * FROM app.authorize_user('%s');".formatted(
                event.getData()
        );

        //Act
        String result = handler.authorizeEventQuery(event);

        //Assert
        assertEquals(result, query);
    }
}