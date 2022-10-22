package TermPedia.factory.query;

import TermPedia.dto.ActionsException;
import TermPedia.dto.RatedTag;
import TermPedia.dto.Tag;
import TermPedia.factory.EnvProvider;
import TermPedia.factory.query.postgres.PostgresQueryFactory;
import TermPedia.queries.instances.tags.FindTagByNameQuery;
import TermPedia.queries.instances.tags.FindTagByTermNameQuery;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class StatementTagSearcherTest {
    StatementTagSearcherTest() throws Exception {
        PostgresQueryFactory.completeRegistration();
        PostgresQueryFactory.setConnectionEstablisher(new TestPostgresQueryConnection());
        PostgresQueryFactory.setProvider(new EnvProvider());
    }
    @Test
    void getTagsByName() throws Exception {
        TagsSearcher searcher = QueryFactory.instance().createTagSearcher();

        Vector<Tag> tags1 = searcher.getTagsByName(new FindTagByNameQuery(
                "medicine", 2, 0)).getTags();

        Vector<Tag> tags2 = searcher.getTagsByName(new FindTagByNameQuery(
                "pattern", 3, 0)).getTags();

        Vector<Tag> tags3 = searcher.getTagsByName(new FindTagByNameQuery(
                "pattern", 1, 0)).getTags();;

        assertAll(
                () -> assertEquals("Medicine", tags1.get(0).name),
                () -> assertEquals(1, tags1.size()),

                () -> assertEquals("Anti Pattern", tags2.get(0).name),
                () -> assertEquals("Pattern", tags2.get(1).name),
                () -> assertEquals(2, tags2.size()),

                () -> assertEquals("Anti Pattern", tags3.get(0).name),
                () -> assertEquals(1, tags3.size()),

                () -> assertThrows(ActionsException.class, () -> searcher.getTagsByName(new FindTagByNameQuery(
                        "medicine", 0, 0)).getTags())
        );
    }

    @Test
    void getTagsByTerm() throws Exception {
        TagsSearcher searcher = QueryFactory.instance().createTagSearcher();

        Vector<RatedTag> tags1 = searcher.getTagsByTerm(new FindTagByTermNameQuery(
                "OOP", 4, 0, 2, false)).getTags();

        Vector<RatedTag> tags2 = searcher.getTagsByTerm(new FindTagByTermNameQuery(
                "Singleton", 20, 0, null, false)).getTags();

        Vector<RatedTag> tags3 = searcher.getTagsByTerm(new FindTagByTermNameQuery(
                "single", 1, 0, null, false)).getTags();

        assertAll(
                () -> assertEquals(new RatedTag("IT", 4.0, 2, 3).toString(),
                        tags1.get(0).toString()),
                () -> assertEquals(new RatedTag("Pattern", 4.0, 1, 0).toString(),
                        tags1.get(1).toString()),
                () -> assertEquals(new RatedTag("Medicine", 1.0, 1, 1).toString(),
                        tags1.get(2).toString()),
                () -> assertEquals(3, tags1.size()),

                () -> assertEquals(new RatedTag("Anti Pattern", 5.0, 1, 0).toString(),
                        tags2.get(0).toString()),
                () -> assertEquals(new RatedTag("Pattern", 4.0, 1, 0).toString(),
                        tags2.get(1).toString()),
                () -> assertEquals(2, tags2.size()),

                () -> assertEquals(0, tags3.size()),

                () -> assertThrows(ActionsException.class, () -> searcher.getTagsByTerm(new FindTagByTermNameQuery(
                        "oop", 0, 0, 2, false)))
        );
    }
}