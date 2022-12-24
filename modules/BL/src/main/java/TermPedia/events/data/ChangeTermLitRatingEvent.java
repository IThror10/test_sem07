package TermPedia.events.data;

import TermPedia.dto.ActionsException;
import TermPedia.events.EventType;
import TermPedia.events.visitors.EventVisitor;
import org.jetbrains.annotations.NotNull;
import TermPedia.dto.Book;
import TermPedia.dto.JsonBuilder;

public class ChangeTermLitRatingEvent extends DataEvent {
    public ChangeTermLitRatingEvent(@NotNull String term, @NotNull Book book, int mark, Integer uid)
            throws ActionsException {
        super(uid);
        this.eventType = EventType.change_term_lit_rating;

        if (mark < 0 || mark > 5)
            throw new ActionsException("Wrong mark");

        this.data = new JsonBuilder(128)
                .addStr("Term", term)
                .addBook(book)
                .addInt("Mark", mark)
                .getData();
    }

    @Override
    public void acceptVisitor(@NotNull EventVisitor visitor) throws ActionsException {
        visitor.visitChangeTermLitRatingEvent(this);
    }
}
