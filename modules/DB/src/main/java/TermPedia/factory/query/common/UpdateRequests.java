package TermPedia.factory.query.common;

import TermPedia.factory.command.EventData;
import org.jetbrains.annotations.NotNull;

public interface UpdateRequests {
    String newUserQuery(EventData data);
    String newTermQuery(EventData data);
    String newLitTermPareQuery(EventData data);
    String newTagTermPareQuery(EventData data);
    String newRateTermLitQuery(EventData data);
    String newRateTermTagQuery(EventData data);
}
