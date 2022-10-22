package TermPedia.query;

import TermPedia.dto.ActionsException;
import TermPedia.dto.Book;
import TermPedia.dto.RatedBook;
import TermPedia.dto.TagBook;
import TermPedia.factory.query.BookSearcher;
import TermPedia.queries.books.*;

import java.util.Vector;

public class MockBookSearcher implements BookSearcher {
    @Override
    public TagBookQueryResult searchByBookName(BaseSearchBookByBookNameQuery query) throws ActionsException {
        Vector<TagBook> books = new Vector<>();
        Vector<String> authors = new Vector<>();
        authors.add("ABC");
        books.add(new TagBook("GOF", "Book", 2020, authors, 3.0));
        return new TagBookQueryResult(books);
    }

    @Override
    public TagBookQueryResult searchByTagName(BaseSearchBookByTagQuery query) throws ActionsException {
        Vector<TagBook> books = new Vector<>();
        Vector<String> authors = new Vector<>();
        authors.add("ABC");
        books.add(new TagBook("GOF", "Book", 2020, authors, 3.0));
        return new TagBookQueryResult(books);
    }

    @Override
    public BookQueryResult searchByAuthorName(BaseSearchBookByNameQuery query) throws ActionsException {
        Vector<Book> books = new Vector<>();
        Vector<String> authors = new Vector<>();
        authors.add("ABC");
        books.add(new Book("GOF", "Book", 2020, authors));
        return new BookQueryResult(books);
    }

    @Override
    public RatedBookQueryResult searchByTermName(BaseSearchBookByTermQuery query) throws ActionsException {
        Vector<RatedBook> books = new Vector<>();
        Vector<String> authors = new Vector<>();
        authors.add("ABC");
        books.add(new RatedBook("GOF", "Book", 2020, authors, 3.0, 5));
        return new RatedBookQueryResult(books);
    }

    @Override
    public RatedBookQueryResult searchConnectedBooks(BaseSearchBookByTermQuery query) throws ActionsException {
        Vector<RatedBook> books = new Vector<>();
        Vector<String> authors = new Vector<>();
        authors.add("ABC");
        books.add(new RatedBook("GOF", "Book", 2020, authors, 3.0, 5));
        return new RatedBookQueryResult(books);
    }
}
