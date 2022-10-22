package TermPedia.dto;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

public class Book {
    public final String name;
    public final String type;
    public final int year;
    public final Vector<String> authors;
    public Book(@NotNull String name, @NotNull String type, int year, @NotNull Vector<String> authors) {
        this.name = name;
        this.type = type;
        this.year = year;
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "Name : " + name + "; Type: " + type + ", Year : " + year + ", Authors : " + authors;
    }
}
