package TermPedia.factory.command;

import TermPedia.dto.ActionsException;
import TermPedia.events.EventStatus;
import TermPedia.events.user.*;
import TermPedia.factory.adapters.ISearchAdapter;
import TermPedia.factory.command.common.IReqAuthHandlerRequests;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class StatementReqAuthHandler implements ReqAuthHandler {
    private final ISearchAdapter searcher;
    private final IReqAuthHandlerRequests builder;
    private final static Logger logger;
    static { logger = Logger.getLogger("CommandDB"); }
    public StatementReqAuthHandler(@NotNull ISearchAdapter searcher, @NotNull IReqAuthHandlerRequests builder) {
        this.searcher = searcher;
        this.builder = builder;
    }


    @Override
    public User authorize(@NotNull AuthorizeEvent event) throws ActionsException {
        String query = builder.authorizeEventQuery(event);
        try {
            if (searcher.execute(query) && searcher.next()) {
                int uid = searcher.getInt("uid");
                return new User(searcher.getString("Login"), uid);
            }
            throw new ActionsException("Wrong Login/Password");

        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }


    @Override
    public EventStatus register(@NotNull RegisterEvent event) throws ActionsException {
        String query = builder.registerEventQuery(event);
        try {
            if (searcher.execute(query)) {
                searcher.next();
                switch (searcher.getInt("register_user")) {
                    case -1:
                        throw new ActionsException("This Login is Already Used");

                    case -2:
                        throw new ActionsException("This Email is Already Used");

                    case -3:
                        throw new ActionsException("Wrong Email address");
                }
                return new EventStatus(true);
            }
            return new EventStatus(false);
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }
}
