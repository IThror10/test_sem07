package TermPedia;

import TermPedia.factory.BaseProvider;
import org.jetbrains.annotations.NotNull;

public class MockProvider implements BaseProvider {
    private String value;
    public MockProvider(String value) { this.value = value; }

    @Override
    public String getKey(@NotNull String key) throws NullPointerException {
        return value;
    }
}
