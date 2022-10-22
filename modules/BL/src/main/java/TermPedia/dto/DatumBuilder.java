package TermPedia.dto;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DatumBuilder {
    String getData();
    DatumBuilder addInt(@NotNull String key, int value);
    DatumBuilder addStr(@NotNull String key, @Nullable String value);
    DatumBuilder addBook(@NotNull Book book);

}
