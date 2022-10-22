package TermPedia.query;

import TermPedia.factory.query.AuthorsSearcher;
import TermPedia.queries.instances.IByNameGetSettings;
import TermPedia.queries.instances.authors.AuthorQueryResult;

import java.util.Vector;

public class MockAuthorSearcher implements AuthorsSearcher {
    @Override
    public AuthorQueryResult getAuthorByName(IByNameGetSettings settings) {
        Vector<String> authors = new Vector<>();
        authors.add("ABC");
        return new AuthorQueryResult(authors);
    }
}
