package TermPedia.queries.books;

import TermPedia.queries.IQuery;
import org.jetbrains.annotations.NotNull;

public abstract class BaseSearchQuery {
    private String litType;
    private int searchAmount;
    private int skipAmount;
    private int yearStart;
    private int yearEnd;

    public BaseSearchQuery(int searchAmount, int skipAmount) {
        this.litType = null;
        this.searchAmount = searchAmount;
        this.skipAmount = skipAmount;
        this.yearStart = 0;
        this.yearEnd = 3000;
    }

    public void setYearsLimits(int start, int end) {
        this.yearStart = start;
        this.yearEnd = end;
    }

    public void setLitType(@NotNull String litType) {
        this.litType = litType;
    }

    public int getYearStart() { return yearStart; }
    public int getYearEnd() { return yearEnd; }
    public int getSkipAmount() { return skipAmount; }
    public int getSearchAmount() { return searchAmount; }
    public String getLitType() { return litType; }
}
