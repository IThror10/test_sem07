package TermPedia.factory.query;

import TermPedia.dto.RatedTag;
import TermPedia.dto.TagBook;
import TermPedia.factory.EnvProvider;
import TermPedia.factory.query.postgres.PostgresQueryFactory;
import TermPedia.queries.books.SearchBookByAuthorQuery;
import TermPedia.queries.books.SearchBookByLikeNameQuery;
import TermPedia.queries.books.SearchBookByLikeTermQuery;
import TermPedia.queries.instances.authors.FindAuthorByNameQuery;
import TermPedia.queries.instances.tags.FindTagByNameQuery;
import TermPedia.queries.instances.tags.FindTagByTermNameQuery;
import TermPedia.queries.instances.terms.FindTermByNameQuery;
import TermPedia.queries.instances.types.FindLitTypesByNameQuery;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


class StatementUpdaterTest {
    StatementUpdaterTest() throws Exception {
        PostgresQueryFactory.completeRegistration();
        PostgresQueryFactory.setConnectionEstablisher(new TestPostgresQueryConnection());
        PostgresQueryFactory.setProvider(new EnvProvider());
    }

    @Test
    void update() throws Exception {
        IUpdater updater = SyncQueryFactory.instance().createUpdater();
        updater.setSynchronizer(new MockSyncronizer());
        boolean updated = updater.update();

        TermsSearcher termsSearcher = SyncQueryFactory.instance().createTermSearcher();
        TagsSearcher tagsSearcher = SyncQueryFactory.instance().createTagSearcher();
        AuthorsSearcher authorsSearcher = SyncQueryFactory.instance().createAuthorSearcher();
        LitTypesSearcher litTypesSearcher = SyncQueryFactory.instance().createLitTypesSearcher();
        BookSearcher bookSearcher = SyncQueryFactory.instance().createBookSearcher();


        RatedTag tag = tagsSearcher.getTagsByTerm(new FindTagByTermNameQuery(
                "NewTerm", 20, 0, null, false)).getTags().get(0);
        assertAll(
                () -> assertEquals(true, updated),
                () -> assertEquals("NewTerm", termsSearcher.getTermsByName(new FindTermByNameQuery(
                        "newTerm", 20, 0)).getTerms().get(0).name),
                () -> assertEquals("NewTag", tagsSearcher.getTagsByName(new FindTagByNameQuery(
                        "newTag", 20, 0)).getTags().get(0).name),
                () -> assertEquals("qwerty", authorsSearcher.getAuthorByName(new FindAuthorByNameQuery(
                        "qwe", 20, 0)).getAuthors().get(0)),
                () -> assertEquals("NewType", litTypesSearcher.getLitTypesByName(new FindLitTypesByNameQuery(
                        "newType", 20, 0)).getLitTypes().get(0)),
                () -> assertEquals("NewTag", tag.name),
                () -> assertEquals(5, tag.rating),
                () -> assertEquals(1, tag.ratesAmount)
        );

        TagBook book = bookSearcher.searchByBookName(new SearchBookByLikeNameQuery(
                "zz", true, 0, 20, 0)).getBooks().get(0);
        assertAll(
                () -> assertEquals("Zzz", bookSearcher.searchByBookName(new SearchBookByLikeNameQuery(
                    "zz", true, 0, 20, 0)).getBooks().get(0).name),
                () -> assertEquals("Zzz", bookSearcher.searchByTermName(new SearchBookByLikeTermQuery(
                    "newTerm", true, false, 0, null, 20, 0))
                            .getBooks().get(0).name),
                () -> assertEquals("Zzz", bookSearcher.searchByAuthorName(new SearchBookByAuthorQuery(
                        "QWERTY", 20, 0)).getBooks().get(0).name)
        );
    }
}