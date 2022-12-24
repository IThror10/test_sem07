package TermPedia.queries.books;

import TermPedia.dto.ActionsException;
import TermPedia.queries.IQuery;
import TermPedia.queries.visitors.QueryVisitor;

public class SearchBookByTagQuery extends BaseSearchBookByTagQuery implements IQuery {
    private TagBookQueryResult result;
    public SearchBookByTagQuery(boolean orderByRating, double minRating, int searchAmount, int skipAmount) {
        super(orderByRating, minRating, searchAmount, skipAmount);
        this.result = null;
    }

    @Override
    public void acceptVisitor(QueryVisitor visitor) throws ActionsException {
        visitor.visitSearchBookByTagQuery(this);
    }

    @Override
    public TagBookQueryResult getResult() {
        return result;
    }

    public void setResult(TagBookQueryResult result) {
        this.result = result;
    }
}
