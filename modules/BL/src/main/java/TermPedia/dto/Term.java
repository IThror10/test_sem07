package TermPedia.dto;

import org.jetbrains.annotations.NotNull;

public class Term {
    public final String name;
    public final String description;
    public Term(@NotNull String name, @NotNull String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return name + "; Description : " + description;
    }
}
