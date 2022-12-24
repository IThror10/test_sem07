package dbTasks

class ExecuteMsSqlScripts extends ExecuteSqlScripts{
    @Override
    String createUrl() {
        return dataBase == null ? url : url + ";Database=" + dataBase
    }

    @Override
    void createBuildInfoTable() throws Exception {
        String query = "SELECT name FROM sysobjects where name = 'build_info' and xtype = 'U'";
        String res = sql.rows(query).toString()
        if (res == "[]")
            sql.execute("CREATE TABLE build_info(stage varchar(50));")
    }

    @Override
    boolean selectExists(String fileName) throws Exception {
        String query = String.format("SELECT stage FROM dbo.build_info where stage = '%s';", fileName)
        String res = sql.rows(query).toString()
        return !(res == "[]")
    }

    @Override
    void tableAddFile(String fileName) throws Exception {
        String query = String.format("INSERT INTO dbo.build_info values ('%s');", fileName)
        sql.execute(query)
    }

    @Override
    void execute(String query) throws Exception {
        String[] arr = query.split("Go");
        arr.each { String it ->
            sql.execute(it)
        }
    }
}
