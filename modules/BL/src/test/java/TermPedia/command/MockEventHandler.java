package TermPedia.command;

import TermPedia.dto.ActionsException;
import TermPedia.events.EventStatus;
import TermPedia.events.data.DataEvent;
import TermPedia.factory.command.EventHandler;
import org.jetbrains.annotations.NotNull;

public class MockEventHandler implements EventHandler {
    @Override
    public EventStatus accept(@NotNull DataEvent event) throws ActionsException {
        return new EventStatus(event.uid != null);
    }
}
