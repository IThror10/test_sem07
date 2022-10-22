package TermPedia.factory.query.postgres;

import TermPedia.dto.ActionsException;
import TermPedia.factory.query.common.AssertByNameGetSettings;
import TermPedia.factory.query.common.AuthorsRequests;
import TermPedia.queries.instances.IByNameGetSettings;

public class PostgresAuthorsRequests extends AssertByNameGetSettings implements AuthorsRequests {
    private final StringBuilder builder;
    public PostgresAuthorsRequests() {
        builder = new StringBuilder(128);
    }

    @Override
    public String getAuthorsByNameQuery(IByNameGetSettings settings) throws ActionsException {
        assertCorrect(settings);

        builder.setLength(0);
        builder.append("SELECT name FROM data.authors WHERE lower(name) like lower('%");
        builder.append(settings.getName());
        builder.append("%') ORDER BY name LIMIT ");
        builder.append(settings.getSearchAmount());
        builder.append(" OFFSET ");
        builder.append(settings.getSkipAmount());
        return builder.toString();
    }
}
