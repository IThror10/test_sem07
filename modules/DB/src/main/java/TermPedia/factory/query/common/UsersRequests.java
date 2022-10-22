package TermPedia.factory.query.common;

import TermPedia.dto.ActionsException;
import TermPedia.factory.command.EventData;

public interface UsersRequests {
    String addUserQuery(EventData data) throws ActionsException;
}
