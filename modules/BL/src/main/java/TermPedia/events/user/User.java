package TermPedia.events.user;

import TermPedia.events.EventResult;
import org.jetbrains.annotations.NotNull;

public class User extends EventResult {
    public final String login;
    public final int UID;

    public User(@NotNull String login, int uid) {
        this.login = login;
        this.UID = uid;
    }

    @Override
    public String toString() { return login + UID; }
}
