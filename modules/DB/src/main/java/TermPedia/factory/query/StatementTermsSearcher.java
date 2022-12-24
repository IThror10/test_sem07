package TermPedia.factory.query;

import TermPedia.dto.ActionsException;
import TermPedia.dto.Term;
import TermPedia.factory.adapters.ISearchAdapter;
import TermPedia.factory.query.common.TermsRequests;
import TermPedia.queries.instances.IByNameGetSettings;
import TermPedia.queries.instances.terms.TermQueryResult;
import org.jetbrains.annotations.NotNull;

import java.util.Vector;
import java.util.logging.Logger;

public class StatementTermsSearcher implements TermsSearcher {
    private final ISearchAdapter searcher;
    private final TermsRequests builder;
    private final static Logger logger;
    static { logger = Logger.getLogger("QueryDB"); }

    public StatementTermsSearcher(@NotNull ISearchAdapter searcher, @NotNull TermsRequests builder) {
        this.builder = builder;
        this.searcher = searcher;
    }

    @Override
    public TermQueryResult getTermsByName(IByNameGetSettings settings) throws ActionsException {
        try {
            String query = builder.getTermsByNameQuery(settings);
            Vector<Term> terms = new Vector<>(settings.getSearchAmount());
            if (searcher.execute(query))
                while (searcher.next())
                    terms.add(new Term(
                            searcher.getString("name"),
                            searcher.getString("description")
                    ));
            return new TermQueryResult(terms);
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }

    @Override
    public boolean termExists(Term term) throws ActionsException {
        try {
            String query = builder.termsExists(term);
            if (searcher.execute(query)) {
                searcher.next();
                return searcher.getBoolean("exists");
            }
            return false;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        }
    }
}
