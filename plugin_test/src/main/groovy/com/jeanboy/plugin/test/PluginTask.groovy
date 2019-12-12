package com.jeanboy.plugin.test

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class PluginTask extends DefaultTask {
    PluginTask() {
    }

    @TaskAction
    void doAction() {
        // 获取到参数
        String param1 = project.extensions.pluginExtension.param1
        String param2 = project.extensions.pluginExtension.param2

        println("param1: ${param1}")
        println("param2: ${param2}")
    }
}