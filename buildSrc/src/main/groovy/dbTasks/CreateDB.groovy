package dbTasks

import groovy.sql.Sql
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class CreateDB extends DefaultTask {
    @Input
    String DBName

    @Input
    String url, username, password, driver

    protected Sql sql;

    @TaskAction
    def execute() {
        sql = Sql.newInstance(url, username, password, driver)
        try {
            if (db_exists(DBName) == false)
                create_db(DBName)
        } finally {
            sql.close()
        }
    }

    abstract boolean db_exists(String dbName) throws Exception;
    abstract void create_db(String dbNames) throws Exception;
}