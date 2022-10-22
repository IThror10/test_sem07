package TermPedia;

import TermPedia.command.MockCommandFactory;
import TermPedia.dto.ActionsException;
import TermPedia.events.data.AddTagToTermEvent;
import TermPedia.events.data.AddTermEvent;
import TermPedia.events.user.AuthorizeEvent;
import TermPedia.events.user.RegisterEvent;
import TermPedia.events.user.User;
import TermPedia.events.visitors.EventHandlerVisitor;
import TermPedia.factory.command.CommandFactory;
import TermPedia.query.MockQueryFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventHandlerVisitorTest {
    EventHandlerVisitorTest() {
        MockCommandFactory.completeRegistration();
        MockQueryFactory.completeRegistration();
        CommandFactory.setProvider(new MockProvider("Mock"));
        MockQueryFactory.setProvider(new MockProvider("Mock"));
    }
    @Test
    void visitDataEvent() throws Exception {
        AddTermEvent event = new AddTermEvent("OOP", "Object Oriented", null);
        EventHandlerVisitor visitor = new EventHandlerVisitor();
        visitor.visitDataEvent(event);
        assertEquals(false, event.getResult().getStatus());

        AddTagToTermEvent event1 = new AddTagToTermEvent("OOP", "PPO", 1);
        visitor.visitDataEvent(event1);
        assertEquals(true, event1.getResult().getStatus());
    }

    @Test
    void visitRegisterEvent() throws Exception {
        RegisterEvent event1 = new RegisterEvent("admin", "password", "email");
        RegisterEvent event2 = new RegisterEvent("login", "password", "email");

        EventHandlerVisitor visitor = new EventHandlerVisitor();
        assertThrows(ActionsException.class, () -> visitor.visitRegisterEvent(event1));

        visitor.visitRegisterEvent(event2);
        assertEquals(true, event2.getResult().getStatus());
    }

    @Test
    void visitAuthorizeEvent() throws Exception {
        AuthorizeEvent event1 = new AuthorizeEvent("login", "password");
        AuthorizeEvent event2 = new AuthorizeEvent("unknown", "login");

        EventHandlerVisitor visitor = new EventHandlerVisitor();
        assertThrows(ActionsException.class, () -> visitor.visitAuthorizeEvent(event2));

        visitor.visitAuthorizeEvent(event1);
        assertEquals(new User("admin", 0).toString(), event1.getResult().toString());
    }

    @Test
    void visitAddTermEvent() throws Exception {
        AddTermEvent event1 = new AddTermEvent("OOP", "Object Oriented", 0);
        AddTermEvent event2 = new AddTermEvent("Triangle", "Geometry Figure", 0);

        EventHandlerVisitor visitor = new EventHandlerVisitor();
        assertThrows(ActionsException.class, () -> visitor.visitAddTermEvent(event1));

        visitor.visitAddTermEvent(event2);
        assertTrue(event2.getResult().getStatus());
    }
}