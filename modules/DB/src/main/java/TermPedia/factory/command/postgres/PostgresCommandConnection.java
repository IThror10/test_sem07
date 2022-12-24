package TermPedia.factory.command.postgres;

import TermPedia.factory.command.common.ICommandConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgresCommandConnection implements ICommandConnection {
    @Override
    public Connection establishEventHandlerConnection() throws Exception {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/TermPediaCommand",
                "command_authorized",
                "write_only"
        );
    }

    @Override
    public Connection establishReqAuthHandlerConnection() throws Exception {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/TermPediaCommand",
                "command_access",
                "read_only"
        );
    }

    @Override
    public Connection establishSyncConnection() throws Exception {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/TermPediaCommand",
                "command_synchronizer",
                "sync"
        );
    }
}
