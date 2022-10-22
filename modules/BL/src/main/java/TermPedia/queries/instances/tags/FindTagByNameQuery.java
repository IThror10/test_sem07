package TermPedia.queries.instances.tags;

import TermPedia.dto.ActionsException;
import TermPedia.queries.IQuery;
import TermPedia.queries.instances.IByNameGetSettings;
import TermPedia.queries.visitors.QueryVisitor;
import org.jetbrains.annotations.NotNull;

public class FindTagByNameQuery implements IQuery, IByNameGetSettings {
    private TagQueryResult result;
    private final int searchAmount;
    private final int skipAmount;
    private final String name;

    public FindTagByNameQuery(@NotNull String tagName, int searchAmount, int skipAmount) {
        this.name = tagName;
        this.searchAmount = searchAmount;
        this.skipAmount = skipAmount;
        this.result = null;
    }

    public void setResult(TagQueryResult result) {
        this.result = result;
    }
    @Override
    public void acceptVisitor(QueryVisitor visitor) throws ActionsException {
        visitor.visitFindTagByNameQuery(this);
    }

    @Override
    public TagQueryResult getResult() {
        return result;
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
}
