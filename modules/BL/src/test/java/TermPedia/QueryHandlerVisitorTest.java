package TermPedia;

import TermPedia.TestObjectsFactory;
import TermPedia.dto.*;
import TermPedia.factory.ConstProvider;
import TermPedia.factory.command.CommandFactory;
import TermPedia.factory.query.*;
import TermPedia.queries.books.*;
import TermPedia.queries.instances.IByNameGetSettings;
import TermPedia.queries.instances.IRatedGetSettings;
import TermPedia.queries.instances.authors.FindAuthorByNameQuery;
import TermPedia.queries.instances.tags.FindTagByNameQuery;
import TermPedia.queries.instances.tags.FindTagByTermNameQuery;
import TermPedia.queries.instances.terms.FindTermByNameQuery;
import TermPedia.queries.instances.types.FindLitTypesByNameQuery;
import TermPedia.queries.visitors.QueryHandlerVisitor;
import org.junit.jupiter.api.Test;

import java.util.Vector;
import org.mockito.*;
import org.mockito.MockedStatic;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QueryHandlerVisitorTest {
    @Mock
    private QueryFactory mockFactory;

    MockedStatic<QueryFactory> staticMocked;
    QueryHandlerVisitorTest() {
        try {
            staticMocked = Mockito.mockStatic(QueryFactory.class);
            staticMocked.when(QueryFactory::instance).thenReturn(Mockito.mock(QueryFactory.class));
        } catch (Exception e) {}
        mockFactory = QueryFactory.instance();
    }
    @Test
    void visitFindTermQuery() throws Exception {
        //Mocks
        TermsSearcher searcher = Mockito.mock(TermsSearcher.class);
        doReturn(searcher).when(mockFactory).createTermSearcher();
        when(searcher.getTermsByName(any(IByNameGetSettings.class))).thenReturn(TestObjectsFactory.termsVector());

        //Arrange
        QueryHandlerVisitor visitor = new QueryHandlerVisitor();
        FindTermByNameQuery query = new FindTermByNameQuery("OOP", 20, 0);

        //Act
        visitor.visitFindTermQuery(query);
        Vector<Term> terms = query.getResult().getTerms();

        //Assert
        assertAll(
                () -> assertEquals(1, terms.size()),
                () -> assertEquals(new Term("OOP", "PPO").toString(), terms.get(0).toString())
        );
    }

    @Test
    void visitFindTagByNameQuery() throws Exception {
        //Mocks
        TagsSearcher searcher = Mockito.mock(TagsSearcher.class);
        doReturn(searcher).when(mockFactory).createTagSearcher();
        when(searcher.getTagsByName(any(IByNameGetSettings.class))).thenReturn(TestObjectsFactory.tagsVector());

        //Arrange
        QueryHandlerVisitor visitor = new QueryHandlerVisitor();
        FindTagByNameQuery query = new FindTagByNameQuery("IT", 20, 0);

        //Act
        visitor.visitFindTagByNameQuery(query);
        Vector<Tag> tags = query.getResult().getTags();

        //Assert
        assertAll(
                () -> assertEquals(1, tags.size()),
                () -> assertEquals(new Tag("IT").toString(), tags.get(0).toString())
        );
    }

    @Test
    void visitFindTagByTermNameQuery() throws Exception {
        //Mock
        TagsSearcher searcher = Mockito.mock(TagsSearcher.class);
        doReturn(searcher).when(mockFactory).createTagSearcher();
        when(searcher.getTagsByTerm(any(IRatedGetSettings.class))).thenReturn(TestObjectsFactory.ratedTagsVector());

        //Arrange
        QueryHandlerVisitor visitor = new QueryHandlerVisitor();
        FindTagByTermNameQuery query = new FindTagByTermNameQuery("OOP", 20, 0, null, false);

        //Act
        visitor.visitFindTagByTermNameQuery(query);
        Vector<RatedTag> tags = query.getResult().getTags();

        //Assert
        assertAll(
                () -> assertEquals(1, tags.size()),
                () -> assertEquals(new RatedTag("IT", 3.0, 5, 5).toString(), tags.get(0).toString())
        );
    }

    @Test
    void visitFindAuthorByNameQuery() throws Exception {
        //Mock
        AuthorsSearcher searcher = Mockito.mock(AuthorsSearcher.class);
        doReturn(searcher).when(mockFactory).createAuthorSearcher();
        when(searcher.getAuthorByName(any(IByNameGetSettings.class))).thenReturn(TestObjectsFactory.authorsVector());

        //Arrange
        QueryHandlerVisitor visitor = new QueryHandlerVisitor();
        FindAuthorByNameQuery query = new FindAuthorByNameQuery("ABC", 20, 0);

        //Act
        visitor.visitFindAuthorByNameQuery(query);
        Vector<String> authors = query.getResult().getAuthors();

        //Assert
        assertAll(
                () -> assertEquals(1, authors.size()),
                () -> assertEquals("ABC", authors.get(0))
        );
    }

    @Test
    void visitFindLitTypesByNameQuery() throws Exception {
        //Mock
        LitTypesSearcher searcher = Mockito.mock(LitTypesSearcher.class);
        doReturn(searcher).when(mockFactory).createLitTypesSearcher();
        when(searcher.getLitTypesByName(any(IByNameGetSettings.class))).thenReturn(TestObjectsFactory.litTypesVector());

        //Arrange
        FindLitTypesByNameQuery query = new FindLitTypesByNameQuery("Article", 20, 0);
        QueryHandlerVisitor visitor = new QueryHandlerVisitor();

        //Act
        visitor.visitFindLitTypesByNameQuery(query);
        Vector<String> types = query.getResult().getLitTypes();

        //Assert
        assertAll(
                () -> assertEquals("Article", types.get(0)),
                () -> assertEquals(1, types.size())
        );
    }

    @Test
    void visitSearchBookByNameQuery() throws Exception {
        //Mock
        BookSearcher searcher = Mockito.mock(BookSearcher.class);
        doReturn(searcher).when(mockFactory).createBookSearcher();
        when(searcher.searchByBookName(any(BaseSearchBookByBookNameQuery.class)))
                .thenReturn(TestObjectsFactory.tagBooks());

        //Arrange
        QueryHandlerVisitor visitor = new QueryHandlerVisitor();
        SearchBookByLikeNameQuery query = new SearchBookByLikeNameQuery("GOF", true, 3.0, 5, 5);

        //Act
        visitor.visitSearchBookByNameQuery(query);
        Vector<TagBook> books = query.getResult().getBooks();

        //Assert
        assertAll(
                () -> assertEquals(TestObjectsFactory.tagBook(null).toString(), books.get(0).toString()),
                () -> assertEquals(1, books.size())
        );
    }

    @Test
    void visitSearchBookByTagQuery() throws Exception {
        //Mock
        BookSearcher searcher = Mockito.mock(BookSearcher.class);
        doReturn(searcher).when(mockFactory).createBookSearcher();
        when(searcher.searchByTagName(any(BaseSearchBookByTagQuery.class)))
                .thenReturn(TestObjectsFactory.tagBooks());

        //Arrange
        QueryHandlerVisitor visitor = new QueryHandlerVisitor();
        SearchBookByTagQuery query = new SearchBookByTagQuery(false, 5, 20, 0);

        //Act
        visitor.visitSearchBookByTagQuery(query);
        Vector<TagBook> books = query.getResult().getBooks();

        //Assert
        assertAll(
                () -> assertEquals(1, books.size()),
                () -> assertEquals(TestObjectsFactory.tagBook(null).toString(), books.get(0).toString())
        );
    }

    @Test
    void visitSearchBookByAuthorQuery() throws Exception {
        //Mock
        BookSearcher searcher = Mockito.mock(BookSearcher.class);
        doReturn(searcher).when(mockFactory).createBookSearcher();
        when(searcher.searchByAuthorName(any(BaseSearchBookByAuthorNameQuery.class)))
                .thenReturn(TestObjectsFactory.commonBooks());

        //Arrange
        SearchBookByAuthorQuery query = new SearchBookByAuthorQuery("ABC", 20, 0);
        QueryHandlerVisitor visitor = new QueryHandlerVisitor();

        //Act
        visitor.visitSearchBookByAuthorQuery(query);
        Vector<Book> books = query.getResult().getBooks();

        //Assert
        assertAll(
                () -> assertEquals(1, books.size()),
                () -> assertEquals(TestObjectsFactory.commonBook(null).toString(), books.get(0).toString())
        );
    }

    @Test
    void visitSearchBookByTermQuery() throws Exception {
        //Mock
        BookSearcher searcher = Mockito.mock(BookSearcher.class);
        doReturn(searcher).when(mockFactory).createBookSearcher();
        when(searcher.searchByTermName(any(BaseSearchBookByTermQuery.class)))
                .thenReturn(TestObjectsFactory.ratedBooks());

        //Arrange
        SearchBookByLikeTermQuery query = new SearchBookByLikeTermQuery("OOP", true, false, 3.0, null, 20, 0);
        QueryHandlerVisitor visitor = new QueryHandlerVisitor();

        //Act
        visitor.visitSearchBookByTermQuery(query);
        Vector<RatedBook> books = query.getResult().getBooks();

        //Assert
        assertAll(
                () -> assertEquals(1, books.size()),
                () -> assertEquals(TestObjectsFactory.ratedBook(null).toString(), books.get(0).toString())
        );
    }
}