package TermPedia.factory.command.postgres;

import TermPedia.dto.ActionsException;
import TermPedia.factory.adapters.PostgresSearchAdapter;
import TermPedia.factory.adapters.PostgresSyncAdapter;
import TermPedia.factory.command.*;
import TermPedia.factory.command.common.*;

import java.sql.Connection;
import java.util.logging.Logger;

public class PostgresCommandFactory extends SyncCommandFactory {
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
                    new PostgresSearchAdapter(connection),
                    new PostgresEventHandlerRequests()
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
                    new PostgresSearchAdapter(connection),
                    new PostgresReqAuthHandlerRequests()
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
                    new PostgresSyncAdapter(connection),
                    new PostgresSyncRequests()
            );
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    static public void completeRegistration() {
        SyncCommandFactory.register("postgres", new PostgresCommandFactory());
    }

    static public void setConnectionEstablisher(ICommandConnection establisher) { _establisher = establisher; }
}
