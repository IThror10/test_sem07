package TermPedia.factory.adapters;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MsSqlSyncAdapter implements ISyncAdapter {
    protected final Connection connection;
    protected ResultSet resultSet;

    public MsSqlSyncAdapter(Connection connection) {
        this.connection = connection;
        this.resultSet = null;
    }

    @Override
    public boolean execute(String query) throws Exception {
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
        statement.execute(query);
        resultSet = statement.getResultSet();
        return true;
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
    public void updateBoolean(String key, boolean value) throws Exception {
        resultSet.updateInt(key, value ? 1 : 0);
        resultSet.updateRow();
    }
}
