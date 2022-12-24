package TermPedia.factory.query.common;

import TermPedia.dto.ActionsException;
import TermPedia.queries.books.BaseSearchQuery;
import TermPedia.queries.instances.IByNameGetSettings;

public abstract class AssertBaseSearchQuery {
    public void assertCorrect(BaseSearchQuery settings) throws ActionsException {
        if (settings.getYearStart() > settings.getYearEnd())
            throw new ActionsException("Wrong Year Limits");
        else if (settings.getSearchAmount() <= 0)
            throw new ActionsException("Select Amount Must Be Positive");
        else if (settings.getSkipAmount() < 0)
            throw new ActionsException("Skip Amount Must Be Positive");
    }
}
