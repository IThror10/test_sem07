package dbTasks

import groovy.sql.Sql
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction

class ExecutePostgresScripts extends ExecuteSqlScripts {
    @Override
    String createUrl() {
        return dataBase == null ? url : url + dataBase
    }

    void createBuildInfoTable() {
        sql.execute("CREATE TABLE IF NOT EXISTS build_info(stage varchar(50) PRIMARY KEY)")
    }


    boolean selectExists(String fileName) {
        String query = "SELECT EXISTS (SELECT stage FROM build_info where stage = '%s');".formatted(fileName)
        return sql.rows(query).toString().contains("true")
    }

    void tableAddFile(String fileName) {
        String query = "INSERT INTO build_info values ('%s');".formatted(fileName)
        sql.execute(query)
    }

    @Override
    void execute(String query) throws Exception {
        sql.execute(query)
    }
}