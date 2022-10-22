package TermPedia.command;

import TermPedia.dto.ActionsException;
import TermPedia.events.EventStatus;
import TermPedia.events.user.AuthorizeEvent;
import TermPedia.events.user.RegisterEvent;
import TermPedia.events.user.User;
import TermPedia.factory.command.ReqAuthHandler;
import org.jetbrains.annotations.NotNull;

public class MockReqAuthHandler implements ReqAuthHandler {
    @Override
    public EventStatus register(@NotNull RegisterEvent event) throws ActionsException {
        if (event.getData().contains("\"Login\" : \"admin\""))
            throw new ActionsException("Login already exists");
        return new EventStatus(true);
    }

    @Override
    public User authorize(@NotNull AuthorizeEvent event) throws ActionsException {
        if (event.getData().contains("\"Login\" : \"login\"") &&
                event.getData().contains("\"Password\" : \"password\""))
            return new User("admin", 0);
        throw new ActionsException("Wrong login/password");
    }
}
