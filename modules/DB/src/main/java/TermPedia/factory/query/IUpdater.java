package TermPedia.factory.query;

import TermPedia.factory.command.common.ISynchronizer;

public interface IUpdater {
    boolean update() throws Exception;
    void setSynchronizer(ISynchronizer synchronizer);
}
