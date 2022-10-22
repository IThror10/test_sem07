package TermPedia.query;

import TermPedia.factory.query.LitTypesSearcher;
import TermPedia.queries.instances.IByNameGetSettings;
import TermPedia.queries.instances.types.LitTypesQueryResult;

import java.util.Vector;

public class MockLitTypesSearcher implements LitTypesSearcher {
    @Override
    public LitTypesQueryResult getLitTypesByName(IByNameGetSettings settings) {
        Vector<String> types = new Vector<>();
        types.add("Article");
        return new LitTypesQueryResult(types);
    }
}
