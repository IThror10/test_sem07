package TermPedia.factory.command.common;

import java.sql.Connection;

public interface ICommandConnection {
    Connection establishEventHandlerConnection() throws Exception;

    Connection establishReqAuthHandlerConnection() throws Exception;

    Connection establishSyncConnection() throws Exception;
}
