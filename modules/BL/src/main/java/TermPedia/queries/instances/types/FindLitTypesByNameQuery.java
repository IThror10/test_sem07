package TermPedia.queries.instances.types;

import TermPedia.dto.ActionsException;
import TermPedia.queries.IQuery;
import TermPedia.queries.instances.IByNameGetSettings;
import TermPedia.queries.visitors.QueryVisitor;
import org.jetbrains.annotations.NotNull;

public class FindLitTypesByNameQuery implements IQuery, IByNameGetSettings {
    private LitTypesQueryResult result;
    private final int searchAmount;
    private final int skipAmount;
    private final String name;

    public FindLitTypesByNameQuery(@NotNull String litTypeName, int searchAmount, int skipAmount) {
        this.result = null;
        this.searchAmount = searchAmount;
        this.skipAmount = skipAmount;
        this.name = litTypeName;
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
        visitor.visitFindLitTypesByNameQuery(this);
    }

    @Override
    public LitTypesQueryResult getResult() {
        return result;
    }

    public void setResult(LitTypesQueryResult result) {
        this.result = result;
    }
}
