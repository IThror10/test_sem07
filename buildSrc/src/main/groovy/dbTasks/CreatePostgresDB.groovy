package dbTasks

import groovy.sql.Sql

class CreatePostgresDB extends CreateDB {
    @Override
    boolean db_exists(String dbName) throws Exception {
        String query = String.format(
                "SELECT EXISTS (SELECT datname FROM pg_catalog.pg_database WHERE datname = '%s');", DBName)
        return !sql.rows(query).toString().contains("false");
    }

    @Override
    void create_db(String dbName) throws Exception {
        sql.execute(String.format("Create DataBase \"%s\";", DBName))
    }
}