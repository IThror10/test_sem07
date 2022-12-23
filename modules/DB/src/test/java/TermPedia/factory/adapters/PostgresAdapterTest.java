package TermPedia.factory.adapters;

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

class PostgresAdapterTest {
    @Mock
    Connection connection;
    @Mock
    Statement statement;
    @Mock
    ResultSet resultSet;

    PostgresAdapterTest() throws Exception {
        connection = Mockito.mock(Connection.class);
        statement = Mockito.mock(Statement.class);
        resultSet = Mockito.mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.getResultSet()).thenReturn(resultSet);
    }
    @Test
    void execute() throws Exception {
        //Mock
        when(statement.execute(any(String.class))).thenReturn(true);

        //Arrange
        PostgresAdapter adapter = new PostgresAdapter(connection);

        //Act
        boolean res = adapter.execute("SomeString");

        //Assert
        assertTrue(res);
    }

    @Test
    void next() throws Exception {
        //Mock
        when(statement.execute(any(String.class))).thenReturn(true);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        //Arrange
        PostgresAdapter adapter = new PostgresAdapter(connection);

        //Act
        boolean status = adapter.execute("Some String");

        boolean res1 = adapter.next();
        boolean res2 = adapter.next();

        //Assert
        assertAll(
                () -> assertTrue(status),
                () -> assertTrue(res1),
                () -> assertFalse(res2)
        );
    }

    @Test
    void getInt() throws Exception {
        //Mock
        when(statement.execute(any(String.class))).thenReturn(true);
        when(resultSet.getInt(any(String.class))).thenReturn(123).thenReturn(245)
                .thenThrow(SQLException.class);

        //Arrange
        PostgresAdapter adapter = new PostgresAdapter(connection);

        //Act
        adapter.execute("key1 : 123;key2 : 245");

        int value1 = adapter.getInt("key1");
        int value2 = adapter.getInt("key2");

        Executable noKey = () -> adapter.getInt("no_key");

        //Assert
        assertAll(
                () -> assertEquals(123, value1),
                () -> assertEquals(245, value2),
                () -> assertThrows(SQLException.class, noKey)
        );
    }

    @Test
    void getString() throws Exception {
        //Mock
        when(statement.execute(any(String.class))).thenReturn(true);
        when(resultSet.getString(any(String.class))).thenReturn("ABC").thenReturn("CDE")
                .thenThrow(SQLException.class);

        //Arrange
        PostgresAdapter adapter = new PostgresAdapter(connection);

        //Act
        adapter.execute("key1 : ABC;key2 : CDE");

        String value1 = adapter.getString("key1");
        String value2 = adapter.getString("key2");
        Executable noKey = () -> adapter.getString("no_key");

        //Assert
        assertAll(
                () -> assertEquals("ABC", value1),
                () -> assertEquals("CDE", value2),
                () -> assertThrows(SQLException.class, noKey)
        );
    }

    @Test
    void getBoolean() throws Exception {
        //Mock
        when(statement.execute(any(String.class))).thenReturn(true);
        when(resultSet.getBoolean(any(String.class))).thenReturn(true).thenReturn(false)
                .thenThrow(SQLException.class);

        //Arrange
        PostgresAdapter adapter = new PostgresAdapter(connection);

        //Act
        adapter.execute("key1 : true;key2 : false");

        boolean value1 = adapter.getBoolean("key1");
        boolean value2 = adapter.getBoolean("key2");
        Executable noKey = () -> adapter.getBoolean("no_key");

        //Assert
        assertAll(
                () -> assertTrue(value1),
                () -> assertFalse(value2),
                () -> assertThrows(SQLException.class, noKey)
        );
    }

    @Test
    void getDouble() throws Exception {
        //Mock
        when(statement.execute(any(String.class))).thenReturn(true);
        when(resultSet.getDouble(any(String.class))).thenReturn(123.2).thenReturn(245.8)
                .thenThrow(SQLException.class);

        //Arrange
        PostgresAdapter adapter = new PostgresAdapter(connection);

        //Act
        adapter.execute("key1 : 123.2;key2 : 245.8");

        double value1 = adapter.getDouble("key1");
        double value2 = adapter.getDouble("key2");
        Executable noKey = () -> adapter.getDouble("no_key");

        //Assert
        assertAll(
                () -> assertEquals(123.2, value1),
                () -> assertEquals(245.8, value2),
                () -> assertThrows(SQLException.class, noKey)
        );
    }
}