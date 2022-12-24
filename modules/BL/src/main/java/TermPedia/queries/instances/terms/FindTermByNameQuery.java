package TermPedia.queries.instances.terms;

import TermPedia.dto.ActionsException;
import TermPedia.queries.IQuery;
import TermPedia.queries.instances.IByNameGetSettings;
import TermPedia.queries.visitors.QueryVisitor;
import org.jetbrains.annotations.NotNull;

public class FindTermByNameQuery implements IQuery, IByNameGetSettings {
    private TermQueryResult result;
    private final int searchAmount;
    private final int skipAmount;
    private final String name;
    public FindTermByNameQuery(@NotNull String termName, int searchAmount, int skipAmount) {
        this.result = null;
        this.name = termName;
        this.searchAmount = searchAmount;
        this.skipAmount = skipAmount;
    }
    public void setResult(TermQueryResult result) {
        this.result = result;
    }

    @Override
    public void acceptVisitor(QueryVisitor visitor) throws ActionsException {
        visitor.visitFindTermQuery(this);
    }

    @Override
    public TermQueryResult getResult() {
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
