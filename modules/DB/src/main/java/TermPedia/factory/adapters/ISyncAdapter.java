package TermPedia.factory.adapters;

public interface ISyncAdapter {
    boolean execute(String query) throws Exception;
    boolean next() throws Exception;
    int getInt(String key) throws Exception;
    String getString(String key) throws Exception;
    void updateBoolean(String key, boolean value) throws Exception;
}
