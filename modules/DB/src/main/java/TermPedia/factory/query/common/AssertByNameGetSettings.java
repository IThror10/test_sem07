package TermPedia.factory.query.common;

import TermPedia.dto.ActionsException;
import TermPedia.queries.instances.IByNameGetSettings;

public abstract class AssertByNameGetSettings {
    public void assertCorrect(IByNameGetSettings settings) throws ActionsException {
        if (settings.getSearchAmount() <= 0)
            throw new ActionsException("Search Amount Must Be Positive");
        else if (settings.getSearchAmount() < 0)
            throw new ActionsException("Skip Amount Must not be Negative");
    }
}
