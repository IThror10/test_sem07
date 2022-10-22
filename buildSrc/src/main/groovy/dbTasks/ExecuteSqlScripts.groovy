package dbTasks

import groovy.sql.Sql
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction

abstract class ExecuteSqlScripts extends DefaultTask {
    @Input
    List<String> inputFiles
    @Input
    String inputDir
    @Input
    String url, username, password, driver
    @Input
    @Optional
    String dataBase

    protected Sql sql

    @TaskAction
    void execute() {
        sql = Sql.newInstance(
                createUrl(),
                username,
                password,
                driver
        )

        try {
            if (dataBase != null)
                createBuildInfoTable()

            inputFiles.each {String fileName ->
                File source = new File(inputDir + "\\" + fileName)
                if (dataBase == null)
                    execute(source.text)
                else if (!selectExists(fileName)) {
                    execute(source.text)
                    tableAddFile(fileName)
                }
            }
        } finally {
            sql.close()
        }
    }

    abstract String createUrl();
    abstract void createBuildInfoTable() throws Exception;
    abstract boolean selectExists(String fileName) throws Exception;
    abstract void tableAddFile(String fileName) throws Exception
    abstract void execute(String query) throws Exception;
}