package dbTasks


class CreateMsSqlDB extends CreateDB {
    @Override
    boolean db_exists(String dbName) throws Exception {
        String query = String.format("SELECT DB_ID('%s')", DBName)
        return !sql.rows(query).toString().contains("[[:null]]");
    }

    @Override
    void create_db(String dbName) throws Exception {
        sql.execute(String.format("Create DataBase \"%s\";", DBName))
    }
}
