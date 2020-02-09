package com.jeanboy.plugin.buildsrc

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyTestPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println("这是 MyTestPlugin")
    }
}