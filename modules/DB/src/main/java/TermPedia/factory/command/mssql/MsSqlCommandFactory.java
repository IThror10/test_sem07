package TermPedia.factory.command.mssql;

import TermPedia.dto.ActionsException;
import TermPedia.factory.adapters.MsSqlSearchAdapter;
import TermPedia.factory.adapters.MsSqlSyncAdapter;
import TermPedia.factory.command.*;
import TermPedia.factory.command.common.ICommandConnection;
import TermPedia.factory.command.common.ISynchronizer;

import java.sql.Connection;
import java.util.logging.Logger;

public class MsSqlCommandFactory extends SyncCommandFactory {
    private final static Logger logger;
    private static ICommandConnection _establisher;
    static {
        logger = Logger.getLogger("CommandDB");
        _establisher = null;
    }

    @Override
    public EventHandler createEventHandler() throws ActionsException {
        try {
            Connection connection = _establisher.establishEventHandlerConnection();
            return new StatementEventHandler(
                    new MsSqlSearchAdapter(connection),
                    new MsSqlEventHandlerRequests()
            );
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    @Override
    public ReqAuthHandler createReqAuthHandler() throws ActionsException {
        try {
            Connection connection = _establisher.establishReqAuthHandlerConnection();
            return new StatementReqAuthHandler(
                    new MsSqlSearchAdapter(connection),
                    new MsSqlReqAuthHandlerRequests()
            );
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    @Override
    public ISynchronizer createSynchronizer() throws ActionsException {
        try {
            Connection connection = _establisher.establishSyncConnection();
            return new StatementSynchronizer(
                    new MsSqlSyncAdapter(connection),
                    new MsSqlSyncRequests()
            );
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    static public void completeRegistration() {
        SyncCommandFactory.register("mssql", new MsSqlCommandFactory());
    }

    static public void setConnectionEstablisher(ICommandConnection establisher) { _establisher = establisher; }
}
