package TermPedia.factory.query;

import TermPedia.dto.ActionsException;
import TermPedia.dto.RatedTag;
import TermPedia.dto.Tag;
import TermPedia.factory.adapters.ISearchAdapter;
import TermPedia.factory.query.TagsSearcher;
import TermPedia.factory.query.common.TagsRequests;
import TermPedia.queries.instances.IByNameGetSettings;
import TermPedia.queries.instances.IRatedGetSettings;
import TermPedia.queries.instances.tags.RatedTagQueryResult;
import TermPedia.queries.instances.tags.TagQueryResult;
import org.jetbrains.annotations.NotNull;

import java.util.Vector;
import java.util.logging.Logger;

public class StatementTagSearcher implements TagsSearcher {
    private final ISearchAdapter searcher;
    private final TagsRequests builder;
    private final static Logger logger;
    static { logger = Logger.getLogger("QueryDB"); }

    public StatementTagSearcher(@NotNull ISearchAdapter searcher, @NotNull TagsRequests builder) {
        this.builder = builder;
        this.searcher = searcher;
    }

    @Override
    public TagQueryResult getTagsByName(IByNameGetSettings settings) throws ActionsException {
        try {
            String query = builder.getTagsByNameQuery(settings);
            Vector<Tag> tags = new Vector<>(settings.getSearchAmount());
            if (searcher.execute(query))
                while (searcher.next())
                    tags.add(new Tag(searcher.getString("name")));
            return new TagQueryResult(tags);
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    @Override
    public RatedTagQueryResult getTagsByTerm(IRatedGetSettings settings) throws ActionsException {
        String query = builder.getTagsByTermNameQuery(settings);
        try {
            Vector<RatedTag> tags = new Vector<>(settings.getSearchAmount());
            if (searcher.execute(query))
                while (searcher.next())
                    tags.add(new RatedTag(
                        searcher.getString("tag"),
                        searcher.getDouble("rating"),
                        searcher.getInt("rates_amount"),
                        searcher.getInt("user_rating")
                    ));
            return new RatedTagQueryResult(tags);
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }
}
