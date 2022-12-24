package TermPedia.factory.command;

import TermPedia.dto.ActionsException;
import TermPedia.events.user.AuthorizeEvent;
import TermPedia.events.user.RegisterEvent;
import TermPedia.events.user.User;
import TermPedia.factory.adapters.PostgresAdapter;
import TermPedia.factory.command.postgres.PostgresReqAuthHandlerRequests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StatementReqAuthHandlerTest {
    @Mock
    Connection connection;
    @Mock
    Statement statement;
    @Mock
    ResultSet resultSet;

    StatementReqAuthHandlerTest() throws Exception {
        connection = Mockito.mock(Connection.class);
        statement = Mockito.mock(Statement.class);
        resultSet = Mockito.mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.getResultSet()).thenReturn(resultSet);
    }
    @Test
    void register() throws Exception {
        //Mock
        when(statement.execute(any(String.class))).thenReturn(true);
        when(resultSet.getInt(any(String.class))).thenReturn(0).thenReturn(-1)
                .thenReturn(-2).thenReturn(-3);
        //Arrange
        StatementReqAuthHandler handler = new StatementReqAuthHandler(
                new PostgresAdapter(connection),
                new PostgresReqAuthHandlerRequests()
        );

        RegisterEvent userRepeat = new RegisterEvent("login", "password", "newEmail");
        RegisterEvent emailRepeat = new RegisterEvent("newLogin", "password", "email@ru");
        RegisterEvent wrongEmail = new RegisterEvent("newLogin", "password", "email");
        RegisterEvent successRegister = new RegisterEvent("newLogin", "password", "newEmail");

        //Act
        boolean status = handler.register(successRegister).getStatus();
        Executable userExists = () -> handler.register(userRepeat);
        Executable emailExists = () -> handler.register(emailRepeat);
        Executable emailFormatError = () -> handler.register(wrongEmail);

        //Assert
        assertTrue(status);
        assertThrows(ActionsException.class, userExists, "This Login is Already Used");
        assertThrows(ActionsException.class, emailExists, "This Email is Already Used");
        assertThrows(ActionsException.class, emailFormatError, "Wrong Email address");
    }

    @Test
    void authorize() throws Exception {
        //Mock
        when(statement.execute(any(String.class))).thenReturn(true);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString(any(String.class))).thenReturn("login").thenThrow(SQLException.class);
        when(resultSet.getInt(any(String.class))).thenReturn(1);

        //Arrange
        StatementReqAuthHandler handler = new StatementReqAuthHandler(
                new PostgresAdapter(connection),
                new PostgresReqAuthHandlerRequests()
        );

        AuthorizeEvent exception = new AuthorizeEvent("admin", "password");
        AuthorizeEvent logging = new AuthorizeEvent("some login", "password");

        //Act
        User user = handler.authorize(logging);
        Executable noUser = () -> handler.authorize(exception);

        //Assert
        assertEquals("login", user.login);
        assertEquals(1, user.UID);
        assertThrows(ActionsException.class, noUser);
    }
}