package TermPedia.factory.query.common;

import TermPedia.dto.ActionsException;
import TermPedia.queries.instances.IByNameGetSettings;

public interface AuthorsRequests {
    String getAuthorsByNameQuery(IByNameGetSettings settings) throws ActionsException;
}
