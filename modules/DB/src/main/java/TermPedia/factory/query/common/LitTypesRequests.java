package TermPedia.factory.query.common;

import TermPedia.dto.ActionsException;
import TermPedia.queries.instances.IByNameGetSettings;

public interface LitTypesRequests {
    String getLitTypesByNameQuery(IByNameGetSettings settings) throws ActionsException;
}
