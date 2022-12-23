package TermPedia.queries.books;

import org.jetbrains.annotations.NotNull;

public abstract class BaseSearchBookByAuthorNameQuery extends BaseSearchQuery {
    private final String authorName;
    public BaseSearchBookByAuthorNameQuery(@NotNull String authorName, int searchAmount, int skipAmount) {
        super(searchAmount, skipAmount);
        this.authorName = authorName;
    }

    public String getName() { return authorName; }
}
