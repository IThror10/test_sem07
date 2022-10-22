package TermPedia.factory.command.common;

import TermPedia.events.user.AuthorizeEvent;
import TermPedia.events.user.RegisterEvent;

public interface IReqAuthHandlerRequests {
    String registerEventQuery(RegisterEvent event);
    String authorizeEventQuery(AuthorizeEvent event);
}
