package TermPedia;

import TermPedia.dto.*;
import TermPedia.factory.query.QueryFactory;
import TermPedia.queries.books.SearchBookByAuthorQuery;
import TermPedia.queries.books.SearchBookByLikeNameQuery;
import TermPedia.queries.books.SearchBookByTagQuery;
import TermPedia.queries.books.SearchBookByLikeTermQuery;
import TermPedia.queries.instances.authors.FindAuthorByNameQuery;
import TermPedia.queries.instances.tags.FindTagByNameQuery;
import TermPedia.queries.instances.tags.FindTagByTermNameQuery;
import TermPedia.queries.instances.terms.FindTermByNameQuery;
import TermPedia.queries.instances.types.FindLitTypesByNameQuery;
import TermPedia.queries.visitors.QueryHandlerVisitor;
import TermPedia.query.MockQueryFactory;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class QueryHandlerVisitorTest {
    QueryHandlerVisitorTest() {
        MockQueryFactory.completeRegistration();
        QueryFactory.setProvider(new MockProvider("Mock"));
    }

    @Test
    void visitFindTermQuery() throws Exception {
        FindTermByNameQuery query = new FindTermByNameQuery("OOP", 20, 0);
        QueryHandlerVisitor visitor = new QueryHandlerVisitor();
        visitor.visitFindTermQuery(query);

        Vector<Term> terms = query.getResult().getTerms();
        assertAll(
                () -> assertEquals(new Term("OOP", "PPO").toString(), terms.get(0).toString()),
                () -> assertEquals(1, terms.size())
        );
    }

    @Test
    void visitFindTagByNameQuery() throws Exception {
        FindTagByNameQuery query = new FindTagByNameQuery("IT", 20, 0);
        QueryHandlerVisitor visitor = new QueryHandlerVisitor();
        visitor.visitFindTagByNameQuery(query);

        Vector<Tag> tags = query.getResult().getTags();
        assertAll(
                () -> assertEquals(new Tag("IT").toString(), tags.get(0).toString()),
                () -> assertEquals(1, tags.size())
        );
    }

    @Test
    void visitFindTagByTermNameQuery() throws Exception {
        FindTagByTermNameQuery query = new FindTagByTermNameQuery("OOP", 20, 0, null, false);
        QueryHandlerVisitor visitor = new QueryHandlerVisitor();
        visitor.visitFindTagByTermNameQuery(query);

        Vector<RatedTag> tags = query.getResult().getTags();
        assertAll(
                () -> assertEquals(new RatedTag("IT", 3.0, 5, 5).toString(), tags.get(0).toString()),
                () -> assertEquals(1, tags.size())
        );
    }

    @Test
    void visitFindAuthorByNameQuery() throws Exception {
        FindAuthorByNameQuery query = new FindAuthorByNameQuery("ABC", 20, 0);
        QueryHandlerVisitor visitor = new QueryHandlerVisitor();
        visitor.visitFindAuthorByNameQuery(query);

        Vector<String> authors = query.getResult().getAuthors();
        assertAll(
                () -> assertEquals("ABC", authors.get(0)),
                () -> assertEquals(1, authors.size())
        );
    }

    @Test
    void visitFindLitTypesByNameQuery() throws Exception {
        FindLitTypesByNameQuery query = new FindLitTypesByNameQuery("Article", 20, 0);
        QueryHandlerVisitor visitor = new QueryHandlerVisitor();
        visitor.visitFindLitTypesByNameQuery(query);

        Vector<String> types = query.getResult().getLitTypes();
        assertAll(
                () -> assertEquals("Article", types.get(0)),
                () -> assertEquals(1, types.size())
        );
    }

    @Test
    void visitSearchBookByNameQuery() throws Exception {
        SearchBookByLikeNameQuery query = new SearchBookByLikeNameQuery("GOF", true, 3.0, 5, 5);
        QueryHandlerVisitor visitor = new QueryHandlerVisitor();
        visitor.visitSearchBookByNameQuery(query);

        Vector<String> authors = new Vector<>();
        authors.add("ABC");
        TagBook book = new TagBook("GOF", "Book", 2020, authors, 3.0);

        Vector<TagBook> books = query.getResult().getBooks();
        assertAll(
                () -> assertEquals(book.toString(), books.get(0).toString()),
                () -> assertEquals(1, books.size())
        );
    }

    @Test
    void visitSearchBookByTagQuery() throws Exception {
        SearchBookByTagQuery query = new SearchBookByTagQuery(false, 5, 20, 0);
        QueryHandlerVisitor visitor = new QueryHandlerVisitor();
        visitor.visitSearchBookByTagQuery(query);

        Vector<String> authors = new Vector<>();
        authors.add("ABC");
        TagBook book = new TagBook("GOF", "Book", 2020, authors, 3.0);

        Vector<TagBook> books = query.getResult().getBooks();
        assertAll(
                () -> assertEquals(book.toString(), books.get(0).toString()),
                () -> assertEquals(1, books.size())
        );
    }

    @Test
    void visitSearchBookByAuthorQuery() throws Exception {
        SearchBookByAuthorQuery query = new SearchBookByAuthorQuery("ABC", 20, 0);
        QueryHandlerVisitor visitor = new QueryHandlerVisitor();
        visitor.visitSearchBookByAuthorQuery(query);

        Vector<String> authors = new Vector<>();
        authors.add("ABC");
        Book book = new Book("GOF", "Book", 2020, authors);

        Vector<Book> books = query.getResult().getBooks();
        assertAll(
                () -> assertEquals(book.toString(), books.get(0).toString()),
                () -> assertEquals(1, books.size())
        );
    }

    @Test
    void visitSearchBookByTermQuery() throws Exception {
        SearchBookByLikeTermQuery query = new SearchBookByLikeTermQuery("OOP", true, false, 3.0, null, 20, 0);
        QueryHandlerVisitor visitor = new QueryHandlerVisitor();
        visitor.visitSearchBookByTermQuery(query);

        Vector<String> authors = new Vector<>();
        authors.add("ABC");
        RatedBook book = new RatedBook("GOF", "Book", 2020, authors, 3.0, 5);

        Vector<RatedBook> books = query.getResult().getBooks();
        assertAll(
                () -> assertEquals(book.toString(), books.get(0).toString()),
                () -> assertEquals(1, books.size())
        );
    }
}