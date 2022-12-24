package TermPedia.queries.instances.terms;

import TermPedia.dto.Term;
import TermPedia.queries.QueryResult;

import java.util.Vector;

public class TermQueryResult extends QueryResult {
    private final Vector<Term> terms;
    public TermQueryResult(Vector<Term> terms) { this.terms = terms; }
    public Vector<Term> getTerms() { return terms; }
}
