package TermPedia.factory.command;


import TermPedia.dto.ActionsException;
import TermPedia.events.EventStatus;
import TermPedia.events.data.DataEvent;
import TermPedia.factory.adapters.ISearchAdapter;
import TermPedia.factory.command.common.IEventHandlerRequests;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class StatementEventHandler implements EventHandler {
    private final ISearchAdapter searcher;
    private final IEventHandlerRequests builder;
    private final static Logger logger;
    static { logger = Logger.getLogger("CommandDB"); }

    public StatementEventHandler(@NotNull ISearchAdapter searcher, @NotNull IEventHandlerRequests builder) {
        this.searcher = searcher;
        this.builder = builder;
    }

    @Override
    public EventStatus accept(@NotNull DataEvent event) throws ActionsException {
        if (event.uid == null)
            throw new ActionsException("You need to be logged in");

        String query = builder.acceptEventQuery(event);
        try {
            if (searcher.execute(query)) {
                searcher.next();
                return new EventStatus(searcher.getBoolean("accept_event"));
            }
            return new EventStatus(false);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }
}
