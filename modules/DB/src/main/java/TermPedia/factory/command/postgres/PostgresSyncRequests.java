package TermPedia.factory.command.postgres;

import TermPedia.factory.command.common.ISyncRequests;

public class PostgresSyncRequests implements ISyncRequests {
    @Override
    public String syncNewRowsQuery() {
        return "SELECT * FROM sync.transactionoutbox WHERE completed = false ORDER BY datetime, uid;";
    }

    @Override
    public String syncDeleteCompletedQuery() {
        return "DELETE FROM sync.TransactionOutbox WHERE completed = true;";
    }


}
