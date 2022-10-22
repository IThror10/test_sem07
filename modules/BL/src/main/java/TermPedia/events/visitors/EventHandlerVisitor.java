package TermPedia.events.visitors;

import TermPedia.dto.ActionsException;
import TermPedia.events.data.AddTermEvent;
import TermPedia.events.data.DataEvent;
import TermPedia.events.EventStatus;
import TermPedia.events.user.*;
import TermPedia.factory.command.*;
import TermPedia.factory.query.QueryFactory;
import TermPedia.factory.query.TermsSearcher;
import TermPedia.handlers.QueryHandler;

public class EventHandlerVisitor implements EventVisitor {
    @Override
    public void visitDataEvent(DataEvent event) throws ActionsException {
        EventHandler handler = CommandFactory.instance().createEventHandler();
        EventStatus status = handler.accept(event);
        event.setResult(status);
    }

    @Override
    public void visitRegisterEvent(RegisterEvent event) throws ActionsException {
        ReqAuthHandler handler = CommandFactory.instance().createReqAuthHandler();
        EventStatus status = handler.register(event);
        event.setStatus(status);

    }

    @Override
    public void visitAuthorizeEvent(AuthorizeEvent event) throws ActionsException {
        ReqAuthHandler handler = CommandFactory.instance().createReqAuthHandler();
        User user = handler.authorize(event);
        event.setUser(user);
    }

    @Override
    public void visitAddTermEvent(AddTermEvent event) throws ActionsException {
        TermsSearcher searcher = QueryFactory.instance().createTermSearcher();
        if (searcher.termExists(event.term))
            throw new ActionsException("Term is Already Exists");

        EventHandler handler = CommandFactory.instance().createEventHandler();
        EventStatus status = handler.accept(event);
        event.setResult(status);
    }
}
