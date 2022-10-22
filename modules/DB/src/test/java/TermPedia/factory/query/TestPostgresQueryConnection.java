package TermPedia.factory.query;

import TermPedia.factory.query.common.IQueryConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestPostgresQueryConnection implements IQueryConnection {
    @Override
    public Connection establishReaderConnection() throws Exception {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/TestTermPediaQuery",
                "query_test_reader",
                "read_only"
        );
    }

    @Override
    public Connection establishUpdaterConnection() throws Exception {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/TestTermPediaQuery",
                "query_test_synchronizer",
                "synch"
        );
    }
}
