package TermPedia.factory.query;

import TermPedia.dto.ActionsException;
import TermPedia.factory.BaseProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Hashtable;

public abstract class SyncQueryFactory extends QueryFactory {
    public abstract IUpdater createUpdater() throws ActionsException;
    private static SyncQueryFactory factory = null;

    public static SyncQueryFactory instance() throws NullPointerException {
        if (factory == null) {
            if (_provider == null)
                throw new NullPointerException("Provider Is Not Set");

            factory = table.get(_provider.getKey("QueryFactory"));
            if (factory == null)
                throw new NullPointerException("CommandFactory not found");
        }
        return factory;
    }

    private static Hashtable<String, SyncQueryFactory> table;
    static { table = new Hashtable<>(5); }
    protected static void register(@NotNull String name, @NotNull SyncQueryFactory factory) {
        QueryFactory.register(name, factory);
        table.put(name, factory);
    }

    private static BaseProvider _provider = null;
    public static void setProvider(BaseProvider provider) {
        _provider = provider;
        QueryFactory.setProvider(provider);
    }
}
