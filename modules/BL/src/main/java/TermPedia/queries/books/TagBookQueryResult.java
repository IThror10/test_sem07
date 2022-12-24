package TermPedia.queries.books;

import TermPedia.dto.TagBook;
import TermPedia.queries.QueryResult;

import java.util.Vector;

public class TagBookQueryResult extends QueryResult {
    private final Vector<TagBook> books;
    public TagBookQueryResult(Vector<TagBook> books) { this.books = books; }
    public Vector<TagBook> getBooks() { return books; }
}
