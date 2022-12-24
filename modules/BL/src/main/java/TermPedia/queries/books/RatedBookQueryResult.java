package TermPedia.queries.books;

import TermPedia.dto.RatedBook;
import TermPedia.queries.QueryResult;

import java.util.Vector;

public class RatedBookQueryResult extends QueryResult {
    private final Vector<RatedBook> books;
    public RatedBookQueryResult(Vector<RatedBook> books) { this.books = books; }
    public Vector<RatedBook> getBooks() { return books; }
}
