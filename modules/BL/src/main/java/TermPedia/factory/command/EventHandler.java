package TermPedia.factory.command;

import TermPedia.dto.ActionsException;
import TermPedia.events.data.DataEvent;
import TermPedia.events.EventStatus;
import org.jetbrains.annotations.NotNull;

public interface EventHandler {
    EventStatus accept(@NotNull DataEvent event) throws ActionsException;
}
