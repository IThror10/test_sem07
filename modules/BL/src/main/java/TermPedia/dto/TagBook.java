package TermPedia.dto;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

public class TagBook extends Book {
    public final double rating;

    public TagBook(@NotNull String name, @NotNull String type, int year, @NotNull Vector<String> authors,
                     double rating) {
        super(name, type, year, authors);
        this.rating = rating;
    }

    @Override
    public String toString() {
        if (rating == 0)
            return super.toString();
        else
            return super.toString() + "; Rating: " + rating;
    }
}
