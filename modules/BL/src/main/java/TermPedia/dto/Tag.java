package TermPedia.dto;

import org.jetbrains.annotations.NotNull;

public class Tag {
    final public String name;
    public Tag(@NotNull String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Name : " + name;
    }
}
