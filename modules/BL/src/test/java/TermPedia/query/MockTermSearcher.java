package TermPedia.query;

import TermPedia.dto.Term;
import TermPedia.factory.query.TermsSearcher;
import TermPedia.queries.instances.IByNameGetSettings;
import TermPedia.queries.instances.terms.TermQueryResult;

import java.util.Objects;
import java.util.Vector;

public class MockTermSearcher implements TermsSearcher {
    @Override
    public TermQueryResult getTermsByName(IByNameGetSettings settings) {
        Vector<Term> terms = new Vector<>();
        terms.add(new Term("OOP", "PPO"));
        return new TermQueryResult(terms);
    }

    @Override
    public boolean termExists(Term term) {
        if (Objects.equals(term.name, "OOP"))
            return true;
        return false;
    }
}
