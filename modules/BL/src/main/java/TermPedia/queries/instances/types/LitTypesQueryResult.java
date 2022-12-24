package TermPedia.queries.instances.types;

import TermPedia.queries.QueryResult;

import java.util.Vector;

public class LitTypesQueryResult extends QueryResult {
    private final Vector<String> litTypes;
    public LitTypesQueryResult(Vector<String> litTypes) { this.litTypes = litTypes; }
    public Vector<String> getLitTypes() { return litTypes; }
}
