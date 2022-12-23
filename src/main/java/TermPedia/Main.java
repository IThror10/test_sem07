package TermPedia;

import TermPedia.events.user.*;
import TermPedia.factory.ConstProvider;
import TermPedia.factory.EnvProvider;
import TermPedia.factory.command.SyncCommandFactory;
import TermPedia.factory.command.postgres.PostgresCommandConnection;
import TermPedia.factory.command.postgres.PostgresCommandFactory;
import TermPedia.factory.query.IUpdater;
import TermPedia.factory.query.SyncQueryFactory;
import TermPedia.factory.query.postgres.PostgresQueryConnection;
import TermPedia.factory.query.postgres.PostgresQueryFactory;
import TermPedia.handlers.EventHandler;
import TermPedia.handlers.QueryHandler;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static User user = null;
    private static Scanner reader = new Scanner(System.in);
    private static QueryHandler queryHandler = new QueryHandler();
    private static EventHandler eventHandler = new EventHandler();

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {

            System.out.println("Set Command DB Type in Args");
            return;
        } else
            initFactories(args[0]);

        Thread sync = new Thread() {
            public void run() {
                try {
                    IUpdater updater = SyncQueryFactory.instance().createUpdater();
                    updater.setSynchronizer(SyncCommandFactory.instance().createSynchronizer());
                    while (true) {
                        Thread.sleep(5000);
                        try {
                            updater.update();
                        } catch (Exception err) {

                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };

        sync.start();
        JFrame frame = new ApplicationFrame(1100, 700);
    }

    private static void initFactories(String commandDB) throws Exception {
        PostgresCommandFactory.completeRegistration();
        PostgresCommandFactory.setConnectionEstablisher(new PostgresCommandConnection());

        PostgresQueryFactory.completeRegistration();
        PostgresQueryFactory.setConnectionEstablisher(new PostgresQueryConnection());

        SyncCommandFactory.setProvider(new ConstProvider(commandDB));
        SyncQueryFactory.setProvider(new EnvProvider());
    }
}
