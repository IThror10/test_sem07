package TermPedia.queries.books;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

public abstract class BaseSearchBookByBookNameQuery extends BaseSearchQuery {
    private String bookName;
    private boolean orderByRating;
    private double minRating;
    private Vector<String> tags;
    public BaseSearchBookByBookNameQuery(@NotNull String bookName, boolean orderByRating, double minRating,
                                         int searchAmount, int skipAmount) {
        super(searchAmount, skipAmount);
        this.bookName = bookName;
        this.orderByRating = orderByRating;
        this.minRating = minRating;
        this.tags = null;
    }

    public void setTags(Vector<String> tags) { this.tags = tags; }
    public String getBookName() { return bookName; }
    public boolean isOrderByRating() { return orderByRating; }
    public double getMinRating() { return minRating; }
    public Vector<String> getTags() { return tags; }

}
