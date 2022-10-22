package TermPedia.events.data;

import TermPedia.events.EventType;
import org.jetbrains.annotations.NotNull;
import TermPedia.dto.Book;
import TermPedia.dto.JsonBuilder;

public class AddLitToTermEvent extends DataEvent {
    public AddLitToTermEvent(@NotNull String term, @NotNull Book book, Integer uid) {
        super(uid);
        this.eventType = EventType.new_lit;

        this.data = new JsonBuilder(128)
                .addStr("Term", term)
                .addBook(book)
                .getData();
    }
}
