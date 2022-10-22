package TermPedia.queries.instances.tags;

import TermPedia.dto.Tag;
import TermPedia.dto.Term;
import TermPedia.queries.QueryResult;

import java.util.Vector;

public class TagQueryResult extends QueryResult {
    private final Vector<Tag> tags;
    public TagQueryResult(Vector<Tag> tags) { this.tags = tags; }
    public Vector<Tag> getTags() { return tags; }
}
