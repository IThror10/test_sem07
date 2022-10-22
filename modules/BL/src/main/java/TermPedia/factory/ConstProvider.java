package TermPedia.factory;

import org.jetbrains.annotations.NotNull;

public class ConstProvider implements BaseProvider{
    private String value;
    public ConstProvider(String value) {
        this.value = value;
    }

    @Override
    public String getKey(@NotNull String key) throws NullPointerException {
        return value;
    }
}
