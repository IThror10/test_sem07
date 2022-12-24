package TermPedia.factory.query.common;

import TermPedia.dto.ActionsException;
import TermPedia.dto.Term;
import TermPedia.factory.command.EventData;
import TermPedia.queries.instances.IByNameGetSettings;

public interface TermsRequests {
    String getTermsByNameQuery(IByNameGetSettings settings) throws ActionsException;
    String termsExists(Term term);
    String newTermQuery(EventData data) throws ActionsException;
    String addLitToTermQuery(EventData data);
    String rateLitTermQuery(EventData data);
}
