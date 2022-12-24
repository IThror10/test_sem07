package TermPedia.factory.command.postgres;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostgresSyncRequestsTest {

    @Test
    void syncNewRowsQuery() {
        //Arrange
        PostgresSyncRequests handler = new PostgresSyncRequests();
        String query = "SELECT * FROM sync.transactionoutbox WHERE completed = false ORDER BY datetime, uid;";

        //Act
        String result = handler.syncNewRowsQuery();

        //Assert
        assertEquals(query, result);
    }

    @Test
    void syncDeleteCompletedQuery() {
        //Arrange
        PostgresSyncRequests handler = new PostgresSyncRequests();
        String query = "DELETE FROM sync.TransactionOutbox WHERE completed = true;";

        //Act
        String result = handler.syncDeleteCompletedQuery();

        //Assert
        assertEquals(query, result);
    }
}