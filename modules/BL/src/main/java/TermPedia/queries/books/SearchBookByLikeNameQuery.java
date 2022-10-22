package TermPedia.queries.books;

import TermPedia.dto.ActionsException;
import TermPedia.queries.IQuery;
import TermPedia.queries.visitors.QueryVisitor;
import org.jetbrains.annotations.NotNull;

public class SearchBookByLikeNameQuery extends BaseSearchBookByBookNameQuery implements IQuery {
    private TagBookQueryResult result;
    public SearchBookByLikeNameQuery(@NotNull String bookName, boolean orderByRating, double minRating,
                                     int searchAmount, int skipAmount) {
        super(bookName, orderByRating, minRating, searchAmount, skipAmount);
        this.result = null;
    }

    @Override
    public void acceptVisitor(QueryVisitor visitor) throws ActionsException {
        visitor.visitSearchBookByNameQuery(this);
    }

    @Override
    public TagBookQueryResult getResult() {
        return result;
    }

    public void setResult(TagBookQueryResult result) { this.result = result; }
}
