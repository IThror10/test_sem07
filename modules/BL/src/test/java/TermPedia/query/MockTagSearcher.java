package TermPedia.query;

import TermPedia.dto.RatedTag;
import TermPedia.dto.Tag;
import TermPedia.factory.query.TagsSearcher;
import TermPedia.queries.instances.IByNameGetSettings;
import TermPedia.queries.instances.IRatedGetSettings;
import TermPedia.queries.instances.tags.RatedTagQueryResult;
import TermPedia.queries.instances.tags.TagQueryResult;

import java.util.Vector;

public class MockTagSearcher implements TagsSearcher {
    @Override
    public TagQueryResult getTagsByName(IByNameGetSettings settings) {
        Vector<Tag> tags = new Vector<>();
        tags.add(new Tag("IT"));
        return new TagQueryResult(tags);
    }

    @Override
    public RatedTagQueryResult getTagsByTerm(IRatedGetSettings settings) {
        Vector<RatedTag> tags = new Vector<>();
        tags.add(new RatedTag("IT", 3.0, 5, 5));
        return new RatedTagQueryResult(tags);
    }
}
