package TermPedia.factory.adapters;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PostgresSearchAdapter implements ISearchAdapter {
    protected final Connection connection;
    protected ResultSet resultSet;

    public PostgresSearchAdapter(Connection connection) {
        this.connection = connection;
        this.resultSet = null;
    }

    @Override
    public boolean execute(String sql) throws Exception {
        Statement statement = connection.createStatement();
        boolean result = statement.execute(sql);
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
}
