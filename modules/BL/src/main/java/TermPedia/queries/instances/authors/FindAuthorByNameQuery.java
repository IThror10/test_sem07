package TermPedia.queries.instances.authors;

import TermPedia.dto.ActionsException;
import TermPedia.queries.IQuery;
import TermPedia.queries.instances.IByNameGetSettings;
import TermPedia.queries.visitors.QueryVisitor;
import org.jetbrains.annotations.NotNull;

public class FindAuthorByNameQuery implements IQuery, IByNameGetSettings {
    private AuthorQueryResult result;
    private final int searchAmount;
    private final int skipAmount;
    private final String name;

    public FindAuthorByNameQuery(@NotNull String authorName, int searchAmount, int skipAmount) {
        this.result = null;
        this.searchAmount = searchAmount;
        this.skipAmount = skipAmount;
        this.name = authorName;
    }

    @Override
    public int getSearchAmount() {
        return searchAmount;
    }

    @Override
    public int getSkipAmount() {
        return skipAmount;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void acceptVisitor(QueryVisitor visitor) throws ActionsException {
        visitor.visitFindAuthorByNameQuery(this);
    }

    @Override
    public AuthorQueryResult getResult() {
        return result;
    }

    public void setResult(AuthorQueryResult result) {
        this.result = result;
    }
}
