package TermPedia.factory.adapters;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PostgresAdapter implements ISyncAdapter, ISearchAdapter{
    protected final Connection connection;
    protected ResultSet resultSet;
    private boolean updatable;

    public PostgresAdapter(Connection connection) {
        this.connection = connection;
        this.resultSet = null;
        this.updatable = false;
    }

    public PostgresAdapter(Connection connection, boolean updatable) {
        this.connection = connection;
        this.resultSet = null;
        this.updatable = updatable;
    }
    @Override
    public boolean execute(String query) throws Exception {
        Statement statement;
        if (updatable)
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        else
            statement = connection.createStatement();

        boolean result = statement.execute(query);
        resultSet = statement.getResultSet();
        return result;
    }

    @Override
    public boolean next() throws Exception {
        return resultSet.next();
    }

    @Override
    public int getInt(String key) throws Exception {
        return resultSet.getInt(key);
    }

    @Override
    public String getString(String key) throws Exception {
        return resultSet.getString(key);
    }

    @Override
    public boolean getBoolean(String key) throws Exception {
        return resultSet.getBoolean(key);
    }

    @Override
    public double getDouble(String key) throws Exception {
        return resultSet.getDouble(key);
    }

    @Override
    public void updateBoolean(String key, boolean value) throws Exception {
        resultSet.updateBoolean(key, value);
        resultSet.updateRow();
    }
}
