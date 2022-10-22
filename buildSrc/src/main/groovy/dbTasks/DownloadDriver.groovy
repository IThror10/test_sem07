package dbTasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class DownloadDriver extends DefaultTask {
    @Input
    def buildDrivers

    @Input
    String driverName

    @TaskAction
    def execute() {
        URLClassLoader loader = GroovyObject.class.classLoader as URLClassLoader
        buildDrivers.each {
            File file -> loader.addURL(file.toURI().toURL())
        }
        loader.loadClass(driverName)
    }
}