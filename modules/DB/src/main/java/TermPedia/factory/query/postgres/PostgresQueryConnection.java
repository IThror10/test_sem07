package TermPedia.factory.query.postgres;

import TermPedia.factory.query.common.IQueryConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgresQueryConnection implements IQueryConnection {
    @Override
    public Connection establishReaderConnection() throws Exception {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/TermPediaQuery",
                "query_reader",
                "read_only"
        );
    }

    @Override
    public Connection establishUpdaterConnection() throws Exception {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/TermPediaQuery",
                "query_synchronizer",
                "synch"
        );
    }
}
