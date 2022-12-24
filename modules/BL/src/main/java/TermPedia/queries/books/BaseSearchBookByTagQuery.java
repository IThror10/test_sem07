package TermPedia.queries.books;

import java.util.Vector;

public abstract class BaseSearchBookByTagQuery extends BaseSearchQuery {
    private boolean orderByRating;
    private double minRating;
    private Vector<String> tags;

    public BaseSearchBookByTagQuery(boolean orderByRating, double minRating,
                                    int searchAmount, int skipAmount) {
        super(searchAmount, skipAmount);
        this.orderByRating = orderByRating;
        this.minRating = minRating;
        this.tags = null;
    }

    public void setTags(Vector<String> tags) { this.tags = tags; }
    public boolean isOrderByRating() { return orderByRating; }
    public double getMinRating() { return minRating; }
    public Vector<String> getTags() { return tags; }
}
