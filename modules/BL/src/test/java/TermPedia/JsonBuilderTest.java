package TermPedia;

import TermPedia.dto.*;
import org.junit.jupiter.api.Test;

import java.awt.image.Kernel;
import java.util.Vector;
import static org.junit.jupiter.api.Assertions.*;

class JsonBuilderTest {
    @Test
    void addInt() {
        JsonBuilder builder1 = new JsonBuilder();
        JsonBuilder builder2 = new JsonBuilder();
        assertAll(
                () -> assertEquals("{\"AAA\" : 1}",
                        builder1.addInt("AAA", 1).getData()),
                () -> assertEquals("{\"AAA\" : 1, \"BBB\" : -2}",
                        builder2.addInt("AAA", 1).addInt("BBB", -2).getData())
        );
    }

    @Test
    void addStr() {
        JsonBuilder builder1 = new JsonBuilder();
        JsonBuilder builder2 = new JsonBuilder();

        assertAll(
                () -> assertEquals("{\"AAA\" : \"a''a|a\"}",
                        builder1.addStr("AAA", "a\"\"a\\a").getData()),
                () -> assertEquals("{\"AAA\" : \"aaa\", \"BBB\" : \"bbb\", \"CCC\" : null}",
                        builder2.addStr("AAA", "aaa").addStr("BBB", "bbb").addStr("CCC", null).getData())
        );
    }

    @Test
    void addBook() {
        JsonBuilder builder1 = new JsonBuilder();
        JsonBuilder builder2 = new JsonBuilder();
        JsonBuilder builder3 = new JsonBuilder();

        Vector<String> authors1 = new Vector<>(5);
        authors1.add("Mihail G.");
        Book book1 = new Book("Kamnem po golove", "Album", 1996, authors1);

        Vector<String> authors2 = new Vector<>(authors1);
        authors2.add("Andrew K.");
        Book book2 = new Book("Kamnem po golove", "Album", 1996, authors2);

        Vector<String> authors3 = new Vector<>();
        Book book3 = new Book("Kamnem po golove", "Album", 1996, authors3);

        String meta = "\"Name\" : \"Kamnem po golove\", \"Type\" : \"Album\", \"Year\" : 1996";
        String add1 = "\"Authors\" : [%s]".formatted("\"Mihail G.\"");
        String add2 = "\"Authors\" : [%s]".formatted("\"Mihail G.\", \"Andrew K.\"");
        String add3 = "\"Authors\" : []";

        assertAll(
                () -> assertEquals("{\"Book\" : {%s, %s}}".formatted(meta, add1),
                        builder1.addBook(book1).getData()),
                () -> assertEquals("{\"Book\" : {%s, %s}}".formatted(meta, add2),
                        builder2.addBook(book2).getData()),
                () -> assertEquals("{\"Book\" : {%s, %s}}".formatted(meta, add3),
                        builder3.addBook(book3).getData())
        );
    }
}