package TermPedia.queries.books;

import TermPedia.dto.ActionsException;
import TermPedia.queries.IQuery;
import TermPedia.queries.visitors.QueryVisitor;
import org.jetbrains.annotations.NotNull;

public class SearchBookByAuthorQuery extends BaseSearchBookByNameQuery implements IQuery {
    private BookQueryResult result;
    public SearchBookByAuthorQuery(@NotNull String authorName, int searchAmount, int skipAmount) {
        super(authorName, searchAmount, skipAmount);
        this.result = null;
    }

    @Override
    public void acceptVisitor(QueryVisitor visitor) throws ActionsException {
        visitor.visitSearchBookByAuthorQuery(this);
    }

    @Override
    public BookQueryResult getResult() {
        return result;
    }

    public void setResult(BookQueryResult result) { this.result = result; }
}
