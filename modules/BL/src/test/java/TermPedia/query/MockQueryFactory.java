package TermPedia.query;

import TermPedia.dto.ActionsException;
import TermPedia.factory.query.*;

public class MockQueryFactory extends QueryFactory {
    @Override
    public TermsSearcher createTermSearcher() throws ActionsException {
        return new MockTermSearcher();
    }

    @Override
    public TagsSearcher createTagSearcher() throws ActionsException {
        return new MockTagSearcher();
    }

    @Override
    public AuthorsSearcher createAuthorSearcher() throws ActionsException {
        return new MockAuthorSearcher();
    }

    @Override
    public LitTypesSearcher createLitTypesSearcher() throws ActionsException {
        return new MockLitTypesSearcher();
    }

    @Override
    public BookSearcher createBookSearcher() throws ActionsException {
        return new MockBookSearcher();
    }

    public static void completeRegistration() {
        QueryFactory.register("Mock", new MockQueryFactory());
    }
}
