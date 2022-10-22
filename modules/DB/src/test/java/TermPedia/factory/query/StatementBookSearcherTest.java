package TermPedia.factory.query;

import TermPedia.dto.Book;
import TermPedia.dto.RatedBook;
import TermPedia.dto.TagBook;
import TermPedia.factory.EnvProvider;
import TermPedia.factory.query.postgres.PostgresQueryFactory;
import TermPedia.queries.books.*;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class StatementBookSearcherTest {
    StatementBookSearcherTest() throws Exception {
        PostgresQueryFactory.completeRegistration();
        PostgresQueryFactory.setConnectionEstablisher(new TestPostgresQueryConnection());
        PostgresQueryFactory.setProvider(new EnvProvider());
    }
    @Test
    void searchByBookName() throws Exception {
        BookSearcher searcher = QueryFactory.instance().createBookSearcher();

        Vector<String> authors1 = new Vector<>();
        authors1.add("ABCD");
        authors1.add("DEF");
        Vector<String> authors2 = new Vector<>();
        authors2.add("London J.");

        Vector<String> tags1 = new Vector<>();
        tags1.add("IT");
        Vector<String> tags2 = new Vector<>();
        tags2.add("IT");
        tags2.add("Pattern");

        // Search Without tags
        TagBook go4no = new TagBook("Go4", "book", 2010, authors1, 0);
        TagBook go4 = new TagBook("Go4", "book", 2010, authors1, 3.2);

        SearchBookByLikeNameQuery query1 = new SearchBookByLikeNameQuery("go", true, 0, 3, 0);
        SearchBookByLikeNameQuery query2 = new SearchBookByLikeNameQuery("g", true, 0, 3, 0);
        SearchBookByLikeNameQuery query3 = new SearchBookByLikeNameQuery("g", true, 3, 1, 0);

        Vector<TagBook> res1 = searcher.searchByBookName(query1).getBooks();
        Vector<TagBook> res2 = searcher.searchByBookName(query2).getBooks();
        Vector<TagBook> res3 = searcher.searchByBookName(query3).getBooks();

        assertAll(
                () -> assertEquals(1, res1.size()),
                () -> assertEquals(go4no.toString(), res1.get(0).toString()),
                () -> assertEquals(2, res2.size()),
                () -> assertEquals(1, res3.size())
        );


        // Search with tags
        query2.setTags(tags1);
        query3.setTags(tags2);

        Vector<TagBook> res11 = searcher.searchByBookName(query2).getBooks();
        Vector<TagBook> res12 = searcher.searchByBookName(query3).getBooks();

        assertAll(
                () -> assertEquals(2, res11.size()),
                () -> assertEquals(go4.toString(), res11.get(0).toString()),
                () -> assertEquals(true, res11.get(0).rating >= res11.get(1).rating),
                () -> assertEquals(1, res12.size())
        );
    }

    @Test
    void searchByTagName() throws Exception {
        BookSearcher searcher = QueryFactory.instance().createBookSearcher();

        Vector<String> authors1 = new Vector<>();
        authors1.add("ABCD");
        authors1.add("DEF");
        Vector<String> authors2 = new Vector<>();
        authors2.add("London J.");

        Vector<String> tags1 = new Vector<>();
        tags1.add("Anti Pattern");
        Vector<String> tags2 = new Vector<>();
        tags2.add("IT");
        tags2.add("Pattern");

        // Search Without tags
        TagBook noRatesGo4 = new TagBook("Go4", "book", 2010, authors1, 0);
        TagBook go4 = new TagBook("Go4", "book", 2010, authors1, 3);
        TagBook go4bet = new TagBook("Go4", "book", 2010, authors1, 3.2);

        SearchBookByTagQuery query1 = new SearchBookByTagQuery(true, 0, 2, 0);
        SearchBookByTagQuery query2 = new SearchBookByTagQuery(true, 3, 1, 0);

        Vector<TagBook> res1 = searcher.searchByTagName(query1).getBooks();
        Vector<TagBook> res2 = searcher.searchByTagName(query2).getBooks();

        assertAll(
                () -> assertEquals(2, res1.size()),
                () -> assertEquals(noRatesGo4.toString(), res1.get(0).toString()),
                () -> assertEquals(1, res2.size())
        );

        // Search with tags
        query1.setTags(tags1);
        query2.setTags(tags2);

        Vector<TagBook> res11 = searcher.searchByTagName(query1).getBooks();
        Vector<TagBook> res12 = searcher.searchByTagName(query2).getBooks();

        SearchBookByTagQuery query3 = new SearchBookByTagQuery(true, 0, 3, 0);
        Vector<TagBook> res13 = searcher.searchByTagName(query3).getBooks();

        assertAll(
                () -> assertEquals(1, res11.size()),
                () -> assertEquals(go4.toString(), res11.get(0).toString()),
                () -> assertEquals(1, res12.size()),
                () -> assertEquals(go4bet.toString(), res12.get(0).toString()),
                () -> assertEquals(2, res13.size())
        );
    }

    @Test
    void searchByAuthorName() throws Exception {
        BookSearcher searcher = QueryFactory.instance().createBookSearcher();

        Vector<String> authors1 = new Vector<>();
        authors1.add("ABCD");
        authors1.add("DEF");

        Vector<String> authors2 = new Vector<>();
        authors2.add("London J.");

        // Search Without tags
        Book go4 = new Book("Go4", "book", 2010, authors1);
        Book wf = new Book("White Fang", "Novel", 1906, authors2);

        SearchBookByAuthorQuery query1 = new SearchBookByAuthorQuery("Lon", 3, 0);
        SearchBookByAuthorQuery query2 = new SearchBookByAuthorQuery("d", 3, 0);
        SearchBookByAuthorQuery query3 = new SearchBookByAuthorQuery("d", 1, 1);

        Vector<Book> res1 = searcher.searchByAuthorName(query1).getBooks();
        Vector<Book> res2 = searcher.searchByAuthorName(query2).getBooks();
        Vector<Book> res3 = searcher.searchByAuthorName(query3).getBooks();

        assertAll(
                // WF
                () -> assertEquals(1, res1.size()),
                () -> assertEquals(wf.toString(), res1.get(0).toString()),
                // Both
                () -> assertEquals(2, res2.size()),
                () -> assertEquals(go4.toString(), res2.get(0).toString()),
                () -> assertEquals(wf.toString(), res2.get(1).toString()),
                // WF
                () -> assertEquals(1, res3.size()),
                () -> assertEquals(wf.toString(), res3.get(0).toString())
        );
    }

    @Test
    void searchByTermName() throws Exception {
        BookSearcher searcher = QueryFactory.instance().createBookSearcher();

        SearchBookByLikeTermQuery query1 = new SearchBookByLikeTermQuery(
                "objects", true, false, 0, null, 3, 0);
        SearchBookByLikeTermQuery query2 = new SearchBookByLikeTermQuery(
                "object", false, true, 0, null, 3, 0);
        SearchBookByLikeTermQuery query3 = new SearchBookByLikeTermQuery(
                "object", true, false, 3, null, 3, 0);

        Vector<RatedBook> res1 = searcher.searchByTermName(query1).getBooks();
        Vector<RatedBook> res2 = searcher.searchByTermName(query2).getBooks();
        Vector<RatedBook> res3 = searcher.searchByTermName(query3).getBooks();

        assertAll(
                () -> assertEquals(2, res1.size()),
                () -> assertTrue(res1.get(0).rating > res1.get(1).rating)
        );

        assertAll(
                () -> assertEquals(2, res2.size()),
                () -> assertTrue(res2.get(0).ratesAmount < res2.get(1).ratesAmount),
                () -> assertEquals(1, res3.size())
        );
    }

    @Test
    void searchBooksConnectedToTerm() throws Exception {
        BookSearcher searcher = QueryFactory.instance().createBookSearcher();

        SearchBookByTermQuery query1 = new SearchBookByTermQuery(
                "OOP", true, false, 0, null, 3, 0);
        SearchBookByTermQuery query2 = new SearchBookByTermQuery(
                "OOP", false, true, 0, null, 3, 0);
        SearchBookByTermQuery query3 = new SearchBookByTermQuery(
                "OOP", true, false, 3, null, 3, 0);

        Vector<RatedBook> res1 = searcher.searchConnectedBooks(query1).getBooks();
        Vector<RatedBook> res2 = searcher.searchConnectedBooks(query2).getBooks();
        Vector<RatedBook> res3 = searcher.searchConnectedBooks(query3).getBooks();

        assertAll(
                () -> assertEquals(2, res1.size()),
                () -> assertTrue(res1.get(0).rating > res1.get(1).rating)
        );

        assertAll(
                () -> assertEquals(2, res2.size()),
                () -> assertTrue(res2.get(0).ratesAmount < res2.get(1).ratesAmount),
                () -> assertEquals(1, res3.size())
        );
    }
}