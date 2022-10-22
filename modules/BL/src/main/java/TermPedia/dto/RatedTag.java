package TermPedia.dto;

import org.jetbrains.annotations.NotNull;

public class RatedTag extends Tag {
    final public boolean isRated;
    public double rating;
    public int ratesAmount;
    public int userRating;

    public RatedTag(@NotNull String name, double rating, int ratesAmount, int userRating) {
        super(name);
        this.rating = rating;
        this.ratesAmount = ratesAmount;
        this.userRating = userRating;
        this.isRated = true;
    }
    @Override
    public String toString() {
        return name + ";\t Rating : " + String.format("%.2f", rating) + "; \tratesAmount : " + ratesAmount +
                (isRated ? "; \tUser Rating : " + userRating : "");
    }
}
