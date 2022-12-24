package TermPedia.events.user;

import TermPedia.dto.ActionsException;
import TermPedia.events.BaseEvent;
import TermPedia.events.EventType;
import TermPedia.events.EventStatus;
import TermPedia.events.visitors.EventVisitor;
import org.jetbrains.annotations.NotNull;
import TermPedia.dto.JsonBuilder;

public class RegisterEvent extends BaseEvent {
    private EventStatus status;
    public RegisterEvent(@NotNull String login, @NotNull String password, @NotNull String email)
            throws ActionsException {
        super(0);
        status = null;

        if (login.length() < 5)
            throw new ActionsException("Логин слишком короткий");
        else if (password.length() < 5)
            throw new ActionsException("Пароль слишком короткий");

        this.eventType = EventType.registration;
        this.data = new JsonBuilder(128)
                .addStr("Login", login)
                .addStr("Password", password)
                .addStr("Email", email)
                .getData();
    }

    @Override
    public void acceptVisitor(@NotNull EventVisitor visitor) throws ActionsException {
        visitor.visitRegisterEvent(this);
    }

    @Override
    public EventStatus getResult() { return status; }
    public void setStatus(EventStatus status) { this.status = status; }
}
