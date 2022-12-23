package TermPedia.events.data;

import TermPedia.dto.ActionsException;
import TermPedia.dto.Term;
import TermPedia.events.EventType;
import TermPedia.events.visitors.EventVisitor;
import org.jetbrains.annotations.NotNull;
import TermPedia.dto.JsonBuilder;

public class AddTermEvent extends DataEvent {
    public final Term term;
    public AddTermEvent(@NotNull String term, @NotNull String description, Integer uid) throws ActionsException {
        super(uid);
        this.eventType = EventType.new_term;

        if (term.length() < 2)
            throw new ActionsException("Term name is too short");
        else if (description.length() < 10)
            throw new ActionsException("Description is too short");
        else {
            this.term = new Term(term, description);
            this.data = new JsonBuilder(128)
                    .addStr("Term", term)
                    .addStr("Description", description)
                    .getData();
        }
    }

    @Override
    public void acceptVisitor(@NotNull EventVisitor visitor) throws ActionsException {
        visitor.visitAddTermEvent(this);
    }
}
