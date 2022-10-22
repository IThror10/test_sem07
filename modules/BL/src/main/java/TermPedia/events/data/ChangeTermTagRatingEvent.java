package TermPedia.events.data;

import TermPedia.events.EventType;
import org.jetbrains.annotations.NotNull;
import TermPedia.dto.JsonBuilder;

public class ChangeTermTagRatingEvent extends DataEvent {
    public ChangeTermTagRatingEvent(@NotNull String term, @NotNull String tag, int mark, Integer uid) {
        super(uid);

        this.eventType = EventType.change_term_tag_rating;
        this.data = new JsonBuilder(128)
                .addStr("Term", term)
                .addStr("Tag", tag)
                .addInt("Mark", mark)
                .getData();
    }
}
