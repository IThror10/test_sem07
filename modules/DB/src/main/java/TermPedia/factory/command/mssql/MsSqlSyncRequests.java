package TermPedia.factory.command.mssql;

import TermPedia.factory.command.common.ISyncRequests;

public class MsSqlSyncRequests implements ISyncRequests {
    @Override
    public String syncNewRowsQuery() {
        return "SELECT * FROM sync.TransactionOutbox WHERE completed = 0 ORDER BY datetime, UID, eventType";
    }

    @Override
    public String syncDeleteCompletedQuery() {
        return "DELETE FROM sync.TransactionOutbox WHERE completed = 1";
    }
}
