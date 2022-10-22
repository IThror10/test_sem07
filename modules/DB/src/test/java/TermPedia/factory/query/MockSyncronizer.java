package TermPedia.factory.query;

import TermPedia.events.EventType;
import TermPedia.factory.command.EventData;
import TermPedia.factory.command.common.ISynchronizer;

import java.util.Vector;

public class MockSyncronizer implements ISynchronizer {
    private Vector<EventData> events;
    private int cur;

    public MockSyncronizer() {
        this.events = new Vector<>();
        events.add(new EventData("{}", EventType.registration.ordinal(), 0));
        events.add(new EventData("{\"Term\" : \"NewTerm\", \"Description\" : \"Some text\"}",
                EventType.new_term.ordinal(), 0));
        events.add(new EventData("{\"Term\" : \"NewTerm\", \"Tag\" : \"NewTag\"}",
                EventType.new_tag.ordinal(), 0));
        events.add(new EventData("{\"Term\" : \"NewTerm\", \"Book\" : {\"Name\" : \"Zzz\", \"Type\" : \"NewType\"," +
                "\"Year\" : 2000, \"Authors\" : [\"qwerty\"]}}", EventType.new_lit.ordinal(), 0));
        events.add(new EventData("{\"Term\" : \"NewTerm\", \"Tag\" : \"NewTag\", \"Mark\" : 5}",
                EventType.change_term_tag_rating.ordinal(), 0));
        events.add(new EventData("{\"Term\" : \"NewTerm\", \"Book\" : {\"Name\" : \"Zzz\", \"Type\" : \"NewType\", \"Year\" : 2000, \"Authors\" : [\"qwerty\"]}," +
                "\"Mark\" : 3, \"Comment\" : \"Some text\"}", EventType.change_term_lit_rating.ordinal(), 0));
    }

    @Override
    public boolean hasNewRows() {
        cur = 0;
        return events.size() > 0;
    }

    @Override
    public EventData getEventData() {
        if (events.size() <= cur)
            return null;
        EventData data = events.get(cur);
        cur++;
        return data;
    }

    @Override
    public void updated() {

    }

    @Override
    public void freeUsed() {

    }
}
