package TermPedia.factory;

import org.jetbrains.annotations.NotNull;

public interface BaseProvider {
    String getKey(@NotNull String key) throws NullPointerException;
}
