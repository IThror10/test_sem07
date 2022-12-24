package TermPedia.queries.instances.tags;

import TermPedia.dto.ActionsException;
import TermPedia.queries.IQuery;
import TermPedia.queries.instances.IRatedGetSettings;
import TermPedia.queries.visitors.QueryVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FindTagByTermNameQuery implements IQuery, IRatedGetSettings {
    private RatedTagQueryResult result;
    private final int searchAmount;
    private final int skipAmount;
    private final String term;
    private final Integer uid;
    private final boolean searchNew;

    public FindTagByTermNameQuery(@NotNull String termName, int searchAmount, int skipAmount,
                                  @Nullable Integer uid, boolean searchNew) {
        this.result = null;
        this.searchAmount = searchAmount;
        this.skipAmount = skipAmount;
        this.term = termName;
        this.uid = uid;
        this.searchNew = searchNew;
    }
    public void setResult(RatedTagQueryResult result) {
        this.result = result;
    }
    @Override
    public void acceptVisitor(QueryVisitor visitor) throws ActionsException {
        visitor.visitFindTagByTermNameQuery(this);
    }

    @Override
    public RatedTagQueryResult getResult() {
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
    public boolean searchNew() {
        return searchNew;
    }

    @Override
    public Integer getUid() {
        return uid;
    }

    @Override
    public String getName() {
        return term;
    }
}
