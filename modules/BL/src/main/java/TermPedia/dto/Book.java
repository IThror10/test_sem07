package TermPedia.dto;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

public class Book {
    public final String name;
    public final String type;
    public final int year;
    public final Vector<String> authors;
    public Book(@NotNull String name, @NotNull String type, int year, @NotNull Vector<String> authors)
            throws ActionsException {
        if (name.length() < 2)
            throw new ActionsException("Book name is too short");
        else if (type.length() < 3)
            throw new ActionsException("Wrong Book type");
        else
        {
            this.name = name;
            this.type = type;
            this.year = year;
            this.authors = authors;
        }
    }

    @Override
    public String toString() {
        return "Name : " + name + "; Type: " + type + ", Year : " + year + ", Authors : " + authors;
    }
}
