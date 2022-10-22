package TermPedia.factory.query.postgres;

import TermPedia.dto.ActionsException;
import TermPedia.factory.command.EventData;
import TermPedia.factory.query.common.AssertByNameGetSettings;
import TermPedia.factory.query.common.TagsRequests;
import TermPedia.queries.instances.IByNameGetSettings;
import TermPedia.queries.instances.IRatedGetSettings;


public class PostgresTagsRequests extends AssertByNameGetSettings implements TagsRequests {
    private final StringBuilder builder;
    public PostgresTagsRequests() {
        builder = new StringBuilder(128);
    }

    @Override
    public String getTagsByNameQuery(IByNameGetSettings settings) throws ActionsException {
        assertCorrect(settings);

        builder.setLength(0);
        builder.append("SELECT name FROM data.tags WHERE lower(name) = lower('");
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
    public String getTagsByTermNameQuery(IRatedGetSettings settings) throws ActionsException {
        assertCorrect(settings);

        builder.setLength(0);

        builder.append("SELECT tt.tag, tt.rating, tt.rates_amount, CASE WHEN ttr.rating IS NULL THEN 0 ELSE ttr.rating" +
                " END as user_rating FROM (SELECT term, tag, rating, rates_amount FROM data.terms_tags where term = '");
        builder.append(settings.getName());
        builder.append("') as tt LEFT JOIN data.term_tag_rates ttr on tt.term = ttr.term and tt.tag = ttr.tag and ttr.uid = ");
        builder.append(settings.getUid());
        if (settings.searchNew())
            builder.append(" ORDER BY rates_amount, tt.rating DESC ");
        else
            builder.append(" ORDER BY tt.rating DESC, rates_amount DESC ");
        builder.append("LIMIT ");
        builder.append(settings.getSearchAmount());
        builder.append(" OFFSET ");
        builder.append(settings.getSkipAmount());
        return builder.toString();
    }

    @Override
    public String addTagToTermQuery(EventData data) {
        builder.setLength(0);
        builder.append("call data.add_tag_term('");
        builder.append(data.json);
        builder.append("');");
        return builder.toString();
    }

    @Override
    public String rateTagTermQuery(EventData data) {
        builder.setLength(0);
        builder.append("call data.rate_tag_term('");
        builder.append(data.json);
        builder.append("', ");
        builder.append(data.uid);
        builder.append(");");
        return builder.toString();
    }
}
