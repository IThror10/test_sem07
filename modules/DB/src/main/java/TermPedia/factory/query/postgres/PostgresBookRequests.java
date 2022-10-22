package TermPedia.factory.query.postgres;

import TermPedia.dto.ActionsException;
import TermPedia.factory.query.common.AssertBaseSearchQuery;
import TermPedia.factory.query.common.BooksRequests;
import TermPedia.queries.books.*;

import java.util.Vector;

public class PostgresBookRequests extends AssertBaseSearchQuery implements BooksRequests {
    private final StringBuilder builder;
    public PostgresBookRequests() {
        builder = new StringBuilder(256);
    }

    @Override
    public String authorSearchQuery(BaseSearchBookByNameQuery query) throws ActionsException {
        assertCorrect(query);

        builder.setLength(0);
        builder.append("SELECT DISTINCT l.name, l.type, l.year, l.authors FROM (SELECT name FROM data.authors WHERE lower(name) like lower('%");
        builder.append(query.getName());
        builder.append("%')) as a JOIN data.authors_lit al on a.name = al.author JOIN data.lit l on l.lid = al.lid WHERE l.year >= ");
        builder.append(query.getYearStart());
        builder.append(" and l.year <=");
        builder.append(query.getYearEnd());
        if (query.getLitType() != null) {
            builder.append(" and l.type = ");
            builder.append(query.getLitType());
        }
        builder.append(" ORDER BY l.name LIMIT ");
        builder.append(query.getSearchAmount());
        builder.append(" OFFSET ");
        builder.append(query.getSkipAmount());
        builder.append(";");
        return builder.toString();
    }

    @Override
    public String bookSearchQuery(BaseSearchBookByBookNameQuery settings) throws ActionsException {
        assertCorrect(settings);

        Vector<String> tags = settings.getTags();
        boolean with_rating = tags != null && tags.size() > 0;

        builder.setLength(0);
        if (with_rating)
            builder.append("SELECT name, type, year, authors, min(rating) as rating FROM ");
        else
            builder.append("SELECT name, type, year, authors, rating as rating FROM ");
        builder.append("(SELECT LID FROM data.lit WHERE lower(name) like lower('%");
        builder.append(settings.getBookName());
        builder.append("%') or vector @@ plainto_tsquery('");
        builder.append(settings.getBookName());
        builder.append("')) as l JOIN data.book_search bs on l.LID = bs.LID WHERE year >= ");

        builder.append(settings.getYearStart());
        builder.append(" and year <= ");
        builder.append(settings.getYearEnd());

        if (settings.getLitType() != null) {
            builder.append(" and type = ");
            builder.append(settings.getLitType());
        }

        if (!with_rating)
            builder.append(" and tag is null");
        else {
            builder.append("and (");
            for (int i = 0; i < tags.size(); i++) {
                if (i > 0)
                    builder.append(" or ");
                builder.append("tag = '");
                builder.append(tags.get(i));
                builder.append("' and rating >= ");
                builder.append(settings.getMinRating());
            }
            builder.append(") ");

            builder.append(" GROUP BY (name, type, year, authors) HAVING count(*) = ");
            builder.append(tags.size());

            if (settings.isOrderByRating())
                builder.append(" ORDER BY rating DESC ");
        }

        builder.append(" LIMIT ");
        builder.append(settings.getSearchAmount());
        builder.append(" OFFSET ");
        builder.append(settings.getSkipAmount());
        builder.append(";");

        return builder.toString();
    }

    @Override
    public String anySearchQuery(BaseSearchBookByTagQuery settings) throws ActionsException {
        assertCorrect(settings);

        Vector<String> tags = settings.getTags();
        boolean with_rating = tags != null && tags.size() > 0;

        builder.setLength(0);
        if (with_rating)
            builder.append("SELECT name, type, year, authors, min(rating) as rating FROM data.book_search WHERE year >= ");
        else
            builder.append("SELECT name, type, year, authors, rating as rating FROM data.book_search WHERE year >= ");

        builder.append(settings.getYearStart());
        builder.append(" and year <= ");
        builder.append(settings.getYearEnd());

        if (settings.getLitType() != null) {
            builder.append(" and type = ");
            builder.append(settings.getLitType());
        }


        if (!with_rating)
            builder.append(" and tag is null");
        else {
            builder.append("and (");
            for (int i = 0; i < tags.size(); i++) {
                if (i > 0)
                    builder.append(" or ");
                builder.append("tag = '");
                builder.append(tags.get(i));
                builder.append("' and rating >= ");
                builder.append(settings.getMinRating());
            }
            builder.append(") ");

            builder.append(" GROUP BY (name, type, year, authors) HAVING count(*) = ");
            builder.append(tags.size());

            if (settings.isOrderByRating())
                builder.append(" ORDER BY rating DESC ");
        }

        builder.append(" LIMIT ");
        builder.append(settings.getSearchAmount());
        builder.append(" OFFSET ");
        builder.append(settings.getSkipAmount());
        builder.append(";");
        return builder.toString();
    }

    @Override
    public String termSearchQuery(BaseSearchBookByTermQuery settings) throws ActionsException {
        assertCorrect(settings);

        builder.setLength(0);

        builder.append("with terms(name) as (SELECT name FROM data.terms WHERE lower(name) = lower('");
        builder.append(settings.getTermName());
        builder.append("') or vector @@ plainto_tsquery('");
        builder.append(settings.getTermName());
        builder.append("')), term_lit (LID, rating, rates_amount) as" +
                "(SELECT LID, rating, rates_amount FROM (SELECT tl.LID, rating, rates_amount, " +
                "row_number() over (partition by LID order by rating DESC) as rn FROM terms t " +
                "JOIN data.terms_lit tl on tl.term = t.name and tl.rating >= ");
        builder.append(settings.getMinRating());
        builder.append(") as temp WHERE rn = 1) SELECT l.name, l.type, l.year, l.authors, tl.rating, " +
                "tl.rates_amount FROM term_lit tl JOIN data.lit l on l.LID = tl.LID WHERE year >= ");

        builder.append(settings.getYearStart());
        builder.append(" and year <= ");
        builder.append(settings.getYearEnd());

        if (settings.getLitType() != null) {
            builder.append("and type = ");
            builder.append(settings.getLitType());
        }

        if (settings.isOrderByRating() && settings.isRecentlyAdded())
            builder.append(" ORDER BY tl.rates_amount, tl.rating DESC");
        else if (settings.isOrderByRating())
            builder.append(" ORDER BY tl.rating DESC");
        else if (settings.isRecentlyAdded())
            builder.append(" ORDER BY tl.rates_amount");

        builder.append(" LIMIT ");
        builder.append(settings.getSearchAmount());
        builder.append(" OFFSET ");
        builder.append(settings.getSkipAmount());
        builder.append(";");
        return builder.toString();
    }

    @Override
    public String connectedTermsSearchQuery(BaseSearchBookByTermQuery settings) throws ActionsException {
        assertCorrect(settings);

        builder.setLength(0);
        builder.append("SELECT l.name, l.type, l.year, l.authors, d.rating, d.rates_amount, d.user_rating ");
        builder.append("FROM (SELECT tl.LID, tl.term, tl.rating, tl.rates_amount, ");
        builder.append("CASE WHEN tlr.rating IS NULL THEN 0 ELSE tlr.rating END as user_rating ");
        builder.append("FROM data.terms_lit tl LEFT JOIN data.term_lit_rates tlr on ");
        builder.append("tl.term = tlr.term and tl.LID = tlr.LID and tlr.uid = ");
        builder.append(settings.getUid());
        builder.append(" WHERE tl.term = '");
        builder.append(settings.getTermName());
        builder.append("' and tl.rating >= ");
        builder.append(settings.getMinRating());
        builder.append(") as d JOIN data.lit l ON l.LID = d.LID and year >= ");
        builder.append(settings.getYearStart());
        builder.append(" and year <= ");
        builder.append(settings.getYearEnd());

        if (settings.getLitType() != null) {
            builder.append("and type = ");
            builder.append(settings.getLitType());

        }

        if (settings.isOrderByRating() && settings.isRecentlyAdded())
            builder.append(" ORDER BY d.rates_amount, d.rating DESC");
        else if (settings.isOrderByRating())
            builder.append(" ORDER BY d.rating DESC");
        else if (settings.isRecentlyAdded())
            builder.append(" ORDER BY d.rates_amount");

        builder.append(" LIMIT ");
        builder.append(settings.getSearchAmount());
        builder.append(" OFFSET ");
        builder.append(settings.getSkipAmount());
        builder.append(";");
        return builder.toString();
    }
}
