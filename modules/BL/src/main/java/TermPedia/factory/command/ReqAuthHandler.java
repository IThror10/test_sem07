package TermPedia.factory.command;

import TermPedia.dto.ActionsException;
import TermPedia.events.EventStatus;
import TermPedia.events.user.User;
import TermPedia.events.user.AuthorizeEvent;
import TermPedia.events.user.RegisterEvent;
import org.jetbrains.annotations.NotNull;

public interface ReqAuthHandler {
    EventStatus register(@NotNull RegisterEvent event) throws ActionsException;
    User authorize(@NotNull AuthorizeEvent event) throws ActionsException;
}
