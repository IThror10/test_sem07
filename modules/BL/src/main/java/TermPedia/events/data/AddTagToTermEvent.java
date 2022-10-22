package TermPedia.events.data;

import TermPedia.events.EventType;
import org.jetbrains.annotations.NotNull;
import TermPedia.dto.JsonBuilder;

public class AddTagToTermEvent extends DataEvent {
    public AddTagToTermEvent(@NotNull String term, @NotNull String tag, Integer uid) {
        super(uid);
        this.eventType = EventType.new_tag;

        this.data = new JsonBuilder(128)
                .addStr("Term", term)
                .addStr("Tag", tag)
                .getData();
    }
}
