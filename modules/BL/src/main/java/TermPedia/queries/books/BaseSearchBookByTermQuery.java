package TermPedia.queries.books;

import org.jetbrains.annotations.NotNull;

public abstract class BaseSearchBookByTermQuery extends BaseSearchQuery {
    private final boolean orderByRating;
    private final boolean recentlyAdded;
    private final double minRating;
    private final Integer uid;
    private final String termName;

    public BaseSearchBookByTermQuery(@NotNull String termName, boolean orderByRating, boolean recentlyAdded,
                                     double minRating, Integer uid, int searchAmount, int skipAmount) {
        super(searchAmount, skipAmount);
        this.termName = termName;
        this.orderByRating = orderByRating;
        this.recentlyAdded = recentlyAdded;
        this.minRating = minRating;
        this.uid = uid;
    }

    public boolean isOrderByRating() {
        return orderByRating;
    }

    public boolean isRecentlyAdded() {
        return recentlyAdded;
    }

    public double getMinRating() {
        return minRating;
    }

    public Integer getUid() {
        return uid;
    }

    public String getTermName() {
        return termName;
    }
}
