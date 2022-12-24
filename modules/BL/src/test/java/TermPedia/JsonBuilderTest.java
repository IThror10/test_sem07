package TermPedia;

import TermPedia.dto.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonBuilderTest {
    @Test
    void addInt() {
        JsonBuilder builder1 = new JsonBuilder();
        JsonBuilder builder2 = new JsonBuilder();

        String singleIntRes = builder1.addInt("AAA", 1).getData();
        String twoIntsRes = builder2.addInt("AAA", 1).addInt("BBB", -2).getData();

        assertEquals("{\"AAA\" : 1}", singleIntRes);
        assertEquals("{\"AAA\" : 1, \"BBB\" : -2}", twoIntsRes);
    }

    @Test
    void addStr() throws Exception {
        JsonBuilder builder1 = new JsonBuilder();
        JsonBuilder builder2 = new JsonBuilder();

        String singleStringRes = builder1.addStr("AAA", "a'a|a").getData();
        String multiStringsRes = builder2.addStr("AAA", "aaa").addStr("BBB", "bbb").addStr("CCC", null).getData();

        assertEquals("{\"AAA\" : \"a'a|a\"}", singleStringRes);
        assertEquals("{\"AAA\" : \"aaa\", \"BBB\" : \"bbb\", \"CCC\" : null}", multiStringsRes);
        assertThrows(ActionsException.class, () -> builder1.addStr("DDD", "d\"dd"));
        assertThrows(ActionsException.class, () -> builder1.addStr("EEE", "e\\ee"));
    }

    @Test
    void addBook() throws Exception {
        JsonBuilder builder1 = new JsonBuilder();
        JsonBuilder builder2 = new JsonBuilder();
        JsonBuilder builder3 = new JsonBuilder();

        String meta = "\"Name\" : \"Kamnem po golove\", \"Type\" : \"Album\", \"Year\" : 1996";
        String add1 = "\"Authors\" : [%s]".formatted("\"Mihail G.\"");
        String add2 = "\"Authors\" : [%s]".formatted("\"Mihail G.\", \"Andrew K.\"");
        String add3 = "\"Authors\" : []";

        String[] singleAuthor = {"Mihail G."}, twoAuthors = {"Mihail G.", "Andrew K."},
                noAuthors = {}, wrongAuthors = {"DD\"DD"};
        Book singleAuthorAlbum = TestObjectsFactory.createAlbum(singleAuthor);
        Book twoAuthorsAlbum = TestObjectsFactory.createAlbum(twoAuthors);
        Book noAuthorsAlbum = TestObjectsFactory.createAlbum(noAuthors);
        Book wrongAuthorsAlbum = TestObjectsFactory.createAlbum(wrongAuthors);


        String noAuthorsRes = builder1.addBook(noAuthorsAlbum).getData();
        String singleAuthorRes = builder2.addBook(singleAuthorAlbum).getData();
        String twoAuthorsRes = builder3.addBook(twoAuthorsAlbum).getData();


        assertEquals("{\"Book\" : {%s, %s}}".formatted(meta, add1), singleAuthorRes);
        assertEquals("{\"Book\" : {%s, %s}}".formatted(meta, add2), twoAuthorsRes);
        assertEquals("{\"Book\" : {%s, %s}}".formatted(meta, add3), noAuthorsRes);
        assertThrows(ActionsException.class, () -> builder1.addBook(wrongAuthorsAlbum));
    }
}