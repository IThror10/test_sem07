package TermPedia.factory.query;

import TermPedia.dto.ActionsException;
import TermPedia.queries.instances.authors.AuthorQueryResult;
import TermPedia.queries.instances.IByNameGetSettings;

public interface AuthorsSearcher {
    AuthorQueryResult getAuthorByName(IByNameGetSettings settings) throws ActionsException;
}
