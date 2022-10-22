package TermPedia.factory.command;

import TermPedia.factory.command.common.ICommandConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestPostgresCommandConnection implements ICommandConnection {
    @Override
    public Connection establishEventHandlerConnection() throws Exception {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/TestTermPediaCommand",
                "command_test_authorized",
                "write_only"
        );
    }

    @Override
    public Connection establishReqAuthHandlerConnection() throws Exception {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/TestTermPediaCommand",
                "command_test_access",
                "read_only"
        );
    }

    @Override
    public Connection establishSyncConnection() throws Exception {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/TestTermPediaCommand",
                "command_test_synchronizer",
                "sync"
        );
    }
}
