package TermPedia.factory.command.mssql;

import TermPedia.factory.command.common.ICommandConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class MsSqlCommandConnection implements ICommandConnection {
    @Override
    public Connection establishEventHandlerConnection() throws Exception {
        return DriverManager.getConnection(
                "jdbc:sqlserver://localhost;encrypt=true;trustServerCertificate=true;Database=TermPediaCommand",
                "sa",
                "mssql"
        );
    }

    @Override
    public Connection establishReqAuthHandlerConnection() throws Exception {
        return DriverManager.getConnection(
                "jdbc:sqlserver://localhost;encrypt=true;trustServerCertificate=true;Database=TermPediaCommand",
                "sa",
                "mssql"
        );
    }

    @Override
    public Connection establishSyncConnection() throws Exception {
        return DriverManager.getConnection(
                "jdbc:sqlserver://localhost;encrypt=true;trustServerCertificate=true;Database=TermPediaCommand",
                "sa",
                "mssql"
        );
    }
}
