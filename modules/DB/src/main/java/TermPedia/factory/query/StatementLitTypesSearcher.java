package TermPedia.factory.query;

import TermPedia.dto.ActionsException;
import TermPedia.factory.adapters.ISearchAdapter;
import TermPedia.factory.query.LitTypesSearcher;
import TermPedia.factory.query.common.LitTypesRequests;
import TermPedia.queries.instances.IByNameGetSettings;
import TermPedia.queries.instances.types.LitTypesQueryResult;
import org.jetbrains.annotations.NotNull;

import java.util.Vector;
import java.util.logging.Logger;

public class StatementLitTypesSearcher implements LitTypesSearcher {
    private final ISearchAdapter searcher;
    private final LitTypesRequests builder;
    private final static Logger logger;
    static { logger = Logger.getLogger("QueryDB"); }

    public StatementLitTypesSearcher(@NotNull ISearchAdapter searcher, @NotNull LitTypesRequests builder) {
        this.builder = builder;
        this.searcher = searcher;
    }
    @Override
    public LitTypesQueryResult getLitTypesByName(IByNameGetSettings settings) throws ActionsException {
        try {
            String query = builder.getLitTypesByNameQuery(settings);
            Vector<String> litTypes = new Vector<>(settings.getSearchAmount());

            if (searcher.execute(query))
                while (searcher.next())
                    litTypes.add(searcher.getString("name"));
            return new LitTypesQueryResult(litTypes);
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }
}
