package TermPedia.command;

import TermPedia.dto.ActionsException;
import TermPedia.factory.command.CommandFactory;
import TermPedia.factory.command.EventHandler;
import TermPedia.factory.command.ReqAuthHandler;

public class MockCommandFactory extends CommandFactory {
    @Override
    public EventHandler createEventHandler() throws ActionsException {
        return new MockEventHandler();
    }

    @Override
    public ReqAuthHandler createReqAuthHandler() throws ActionsException {
        return new MockReqAuthHandler();
    }

    public static void completeRegistration() {
        CommandFactory.register("Mock", new MockCommandFactory());
    }
}
