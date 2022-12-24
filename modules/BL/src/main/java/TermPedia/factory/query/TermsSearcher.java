package TermPedia.factory.query;

import TermPedia.dto.ActionsException;
import TermPedia.dto.Term;
import TermPedia.queries.instances.IByNameGetSettings;
import TermPedia.queries.instances.terms.TermQueryResult;
public interface TermsSearcher {
    TermQueryResult getTermsByName(IByNameGetSettings settings) throws ActionsException;
    boolean termExists(Term term) throws ActionsException;
}
