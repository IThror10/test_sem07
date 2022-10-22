package TermPedia.factory.query;

import TermPedia.dto.ActionsException;
import TermPedia.factory.adapters.ISearchAdapter;
import TermPedia.factory.query.AuthorsSearcher;
import TermPedia.factory.query.common.AuthorsRequests;
import TermPedia.queries.instances.IByNameGetSettings;
import TermPedia.queries.instances.authors.AuthorQueryResult;
import org.jetbrains.annotations.NotNull;

import java.util.Vector;
import java.util.logging.Logger;

public class StatementAuthorsSearcher implements AuthorsSearcher {
    private final ISearchAdapter searcher;
    private final AuthorsRequests builder;
    private final static Logger logger;
    static { logger = Logger.getLogger("QueryDB"); }

    public StatementAuthorsSearcher(@NotNull ISearchAdapter searcher, @NotNull AuthorsRequests builder) {
        this.builder = builder;
        this.searcher = searcher;
    }
    @Override
    public AuthorQueryResult getAuthorByName(IByNameGetSettings settings) throws ActionsException {
        try {
            String query = builder.getAuthorsByNameQuery(settings);
            Vector<String> authors = new Vector<>(settings.getSearchAmount());
            if (searcher.execute(query))
                while (searcher.next())
                    authors.add(searcher.getString("name"));
            return new AuthorQueryResult(authors);
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }
}
