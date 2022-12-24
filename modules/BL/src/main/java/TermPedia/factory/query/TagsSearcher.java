package TermPedia.factory.query;

import TermPedia.dto.ActionsException;
import TermPedia.queries.instances.IByNameGetSettings;
import TermPedia.queries.instances.IRatedGetSettings;
import TermPedia.queries.instances.tags.RatedTagQueryResult;
import TermPedia.queries.instances.tags.TagQueryResult;

public interface TagsSearcher {
    TagQueryResult getTagsByName(IByNameGetSettings settings) throws ActionsException;
    RatedTagQueryResult getTagsByTerm(IRatedGetSettings settings) throws ActionsException;
}
