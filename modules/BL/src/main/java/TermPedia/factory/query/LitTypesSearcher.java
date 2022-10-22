package TermPedia.factory.query;

import TermPedia.dto.ActionsException;
import TermPedia.queries.instances.IByNameGetSettings;
import TermPedia.queries.instances.types.LitTypesQueryResult;

public interface LitTypesSearcher {
    LitTypesQueryResult getLitTypesByName(IByNameGetSettings settings) throws ActionsException;
}
