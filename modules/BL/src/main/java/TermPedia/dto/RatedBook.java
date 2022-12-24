package TermPedia.dto;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

public class RatedBook extends Book {
    public final boolean isRated;
    public double rating;
    public int ratesAmount;
    public int userRating;

    public RatedBook(@NotNull String name, @NotNull String type, int year, @NotNull Vector<String> authors,
                     double rating, int ratesAmount, int userRating) throws ActionsException {
        super(name, type, year, authors);
        this.rating = rating;
        this.ratesAmount = ratesAmount;

        this.isRated = true;
        this.userRating = userRating;
    }

    public RatedBook(@NotNull String name, @NotNull String type, int year, @NotNull Vector<String> authors,
                    double rating, int ratesAmount) throws ActionsException {
        super(name, type, year, authors);
        this.rating = rating;
        this.ratesAmount = ratesAmount;

        this.isRated = false;
        this.userRating = 0;
    }

    public void setUserRating(int rating) {
        this.userRating = rating;
    }
    public String toString() {
        if (!isRated)
            return super.toString() + "; Rating: " + rating + "; RatesAmount: " + ratesAmount;
        else
            return super.toString() + "; Rating: " + rating + "; RatesAmount: " + ratesAmount + "; UserRating :" + userRating;
    }
}