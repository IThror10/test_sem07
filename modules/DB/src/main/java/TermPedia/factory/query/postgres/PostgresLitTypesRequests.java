package TermPedia.factory.query.postgres;

import TermPedia.dto.ActionsException;
import TermPedia.factory.query.common.AssertByNameGetSettings;
import TermPedia.factory.query.common.LitTypesRequests;
import TermPedia.queries.instances.IByNameGetSettings;

public class PostgresLitTypesRequests extends AssertByNameGetSettings implements LitTypesRequests {
    private final StringBuilder builder;
    public PostgresLitTypesRequests() {
        builder = new StringBuilder(128);
    }

    @Override
    public String getLitTypesByNameQuery(IByNameGetSettings settings) throws ActionsException {
        assertCorrect(settings);

        builder.setLength(0);
        builder.append("SELECT name FROM data.lit_types WHERE lower(name) = lower('");
        builder.append(settings.getName());
        builder.append("') or plainto_tsquery('");
        builder.append(settings.getName());
        builder.append("') @@ vector ORDER BY name LIMIT ");
        builder.append(settings.getSearchAmount());
        builder.append(" OFFSET ");
        builder.append(settings.getSkipAmount());
        return builder.toString();
    }
}