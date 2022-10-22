package TermPedia.events.user;

import TermPedia.dto.ActionsException;
import TermPedia.events.BaseEvent;
import TermPedia.events.EventType;
import TermPedia.events.visitors.EventVisitor;
import org.jetbrains.annotations.NotNull;
import TermPedia.dto.JsonBuilder;

public class AuthorizeEvent extends BaseEvent {
    private User user;
    public AuthorizeEvent(@NotNull String login, @NotNull String password) {
        super(0);
        this.user = null;
        this.eventType = EventType.authorization;

        this.data = new JsonBuilder(128)
                .addStr("Login", login)
                .addStr("Password", password)
                .getData();
    }

    @Override
    public void acceptVisitor(EventVisitor visitor) throws ActionsException {
        visitor.visitAuthorizeEvent(this);
    }

    @Override
    public User getResult() { return user; }
    public void setUser(User user) { this.user = user; }
}