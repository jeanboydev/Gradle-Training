package com.jeanboy.plugin.test

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 自定义插件
 */
class TestPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.task("testTask") doLast {
            println("========================")
            println("hello gradle plugin!")
            println("========================")
        }

        // 创建扩展属性
        project.extensions.create("pluginExtension", PluginExtension)
        // 创建 Task
        project.tasks.create("pluginTask", PluginTask)
    }
}