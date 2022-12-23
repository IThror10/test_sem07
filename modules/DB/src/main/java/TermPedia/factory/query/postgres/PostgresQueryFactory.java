package TermPedia.factory.query.postgres;

import TermPedia.dto.ActionsException;
import TermPedia.factory.adapters.PostgresAdapter;
import TermPedia.factory.query.*;
import TermPedia.factory.query.common.IQueryConnection;

import java.sql.Connection;
import java.util.logging.Logger;

public class PostgresQueryFactory extends SyncQueryFactory {
    private static IQueryConnection _establisher;
    private final static Logger logger;
    static {
        logger = Logger.getLogger("QueryDB");
        _establisher = null;
    }

    @Override
    public TermsSearcher createTermSearcher() throws ActionsException {
        try {
            Connection connection = _establisher.establishReaderConnection();
            return new StatementTermsSearcher(
                    new PostgresAdapter(connection),
                    new PostgresTermsRequests()
            );
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    @Override
    public TagsSearcher createTagSearcher() throws ActionsException {
        try {
            Connection connection = _establisher.establishReaderConnection();
            return new StatementTagSearcher(
                    new PostgresAdapter(connection),
                    new PostgresTagsRequests()
            );
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    @Override
    public AuthorsSearcher createAuthorSearcher() throws ActionsException {
        try {
            Connection connection = _establisher.establishReaderConnection();
            return new StatementAuthorsSearcher(
                    new PostgresAdapter(connection),
                    new PostgresAuthorsRequests()
            );
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    @Override
    public LitTypesSearcher createLitTypesSearcher() throws ActionsException {
        try {
            Connection connection = _establisher.establishReaderConnection();
            return new StatementLitTypesSearcher(
                    new PostgresAdapter(connection),
                    new PostgresLitTypesRequests()
            );
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    @Override
    public BookSearcher createBookSearcher() throws ActionsException {
        try {
            Connection connection = _establisher.establishReaderConnection();
            return new StatementBookSearcher(
                    new PostgresAdapter(connection),
                    new PostgresBookRequests()
            );
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    @Override
    public IUpdater createUpdater() throws ActionsException {
        try {
            Connection connection = _establisher.establishUpdaterConnection();
            return new StatementUpdater(
                    new PostgresAdapter(connection),
                    new PostgresUpdateRequests()
            );
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }
    static public void completeRegistration() throws Exception {
        SyncQueryFactory.register("postgres", new PostgresQueryFactory());
    }

    static public void setConnectionEstablisher(IQueryConnection establisher) { _establisher = establisher; }
}
