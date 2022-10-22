package TermPedia.queries.books;

import TermPedia.dto.Book;
import TermPedia.dto.TagBook;
import TermPedia.queries.QueryResult;

import java.util.Vector;

public class BookQueryResult extends QueryResult {
    private final Vector<Book> books;
    public BookQueryResult(Vector<Book> books) { this.books = books; }
    public Vector<Book> getBooks() { return books; }
}
