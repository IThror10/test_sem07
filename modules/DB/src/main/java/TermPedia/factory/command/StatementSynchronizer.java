package TermPedia.factory.command;

import TermPedia.factory.adapters.ISyncAdapter;
import TermPedia.factory.command.common.ISynchronizer;
import TermPedia.factory.command.common.ISyncRequests;
import org.jetbrains.annotations.NotNull;

public class StatementSynchronizer implements ISynchronizer {
    private final ISyncAdapter updater;
    private final ISyncRequests builder;
    private boolean hasRows;
    private String query;
    public StatementSynchronizer(@NotNull ISyncAdapter updater, @NotNull ISyncRequests builder) throws Exception {
        this.updater = updater;
        this.builder = builder;
        this.query = builder.syncNewRowsQuery();
        this.hasRows = false;
    }

    @Override
    public boolean hasNewRows() throws Exception {
        if (updater.execute(query)) {
            hasRows = true;
        } else
            hasRows = false;
        return hasRows;
    }

    @Override
    public EventData getEventData() throws Exception {
        if (hasRows && updater.next())
            return new EventData(
                updater.getString("eventdata"),
                updater.getInt("eventtype"),
                updater.getInt("uid")
            );
        hasRows = false;
        return null;
    }


    @Override
    public void updated() throws Exception {
        updater.updateBoolean("completed", true);
    }

    @Override
    public void freeUsed() throws Exception {
        String query = builder.syncDeleteCompletedQuery();
        updater.execute(query);
    }
}
