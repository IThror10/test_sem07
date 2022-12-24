package TermPedia.factory.query.common;

import TermPedia.dto.ActionsException;
import TermPedia.factory.command.EventData;
import TermPedia.queries.instances.IByNameGetSettings;
import TermPedia.queries.instances.IRatedGetSettings;

public interface TagsRequests {
    String getTagsByNameQuery(IByNameGetSettings settings) throws ActionsException;
    String getTagsByTermNameQuery(IRatedGetSettings settings) throws ActionsException;
    String addTagToTermQuery(EventData data);
    String rateTagTermQuery(EventData data);
}
