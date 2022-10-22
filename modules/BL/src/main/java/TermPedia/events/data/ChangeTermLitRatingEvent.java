package TermPedia.events.data;

import TermPedia.events.EventType;
import org.jetbrains.annotations.NotNull;
import TermPedia.dto.Book;
import TermPedia.dto.JsonBuilder;

public class ChangeTermLitRatingEvent extends DataEvent {
    public ChangeTermLitRatingEvent(@NotNull String term, @NotNull Book book, int mark, Integer uid) {
        super(uid);
        this.eventType = EventType.change_term_lit_rating;

        this.data = new JsonBuilder(128)
                .addStr("Term", term)
                .addBook(book)
                .addInt("Mark", mark)
                .getData();
    }
}
