package TermPedia.queries.books;

import org.jetbrains.annotations.NotNull;

public abstract class BaseSearchBookByNameQuery extends BaseSearchQuery {
    private final String authorName;
    public BaseSearchBookByNameQuery(@NotNull String authorName, int searchAmount, int skipAmount) {
        super(searchAmount, skipAmount);
        this.authorName = authorName;
    }

    public String getName() { return authorName; }
}
