package TermPedia.factory.query.postgres;

import TermPedia.dto.ActionsException;
import TermPedia.dto.Term;
import TermPedia.factory.command.EventData;
import TermPedia.factory.query.common.AssertByNameGetSettings;
import TermPedia.factory.query.common.TermsRequests;
import TermPedia.queries.instances.IByNameGetSettings;

public class PostgresTermsRequests extends AssertByNameGetSettings implements TermsRequests {
    private final StringBuilder builder;
    public PostgresTermsRequests() {
        builder = new StringBuilder(256);
    }

    @Override
    public String getTermsByNameQuery(IByNameGetSettings settings) throws ActionsException {
        assertCorrect(settings);

        builder.setLength(0);
        builder.append("SELECT terms.name, terms.description FROM data.terms WHERE lower(name) = lower('");
        builder.append(settings.getName());
        builder.append("') or plainto_tsquery('");
        builder.append(settings.getName());
        builder.append("') @@ vector ORDER BY name LIMIT ");
        builder.append(settings.getSearchAmount());
        builder.append(" OFFSET ");
        builder.append(settings.getSkipAmount());
        return builder.toString();
    }

    @Override
    public String termsExists(Term term) {
        builder.setLength(0);
        builder.append("SELECT EXISTS (SELECT name FROM data.terms WHERE name = '");
        builder.append(term.name);
        builder.append("');");
        return builder.toString();
    }

    @Override
    public String newTermQuery(EventData data) {
        builder.setLength(0);
        builder.append("call data.add_term('");
        builder.append(data.json);
        builder.append("');");
        return builder.toString();
    }

    @Override
    public String addLitToTermQuery(EventData data) {
        builder.setLength(0);
        builder.append("call data.add_lit_term('");
        builder.append(data.json);
        builder.append("');");
        return builder.toString();
    }

    @Override
    public String rateLitTermQuery(EventData data) {
        builder.setLength(0);
        builder.append("call data.rate_lit_term('");
        builder.append(data.json);
        builder.append("', ");
        builder.append(data.uid);
        builder.append(");");
        return builder.toString();
    }
}
