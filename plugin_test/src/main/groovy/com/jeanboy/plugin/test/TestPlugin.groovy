package com.jeanboy.plugin.test

import org.gradle.api.Plugin
import org.gradle.api.Project

class TestPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        println("这是 TestPlugin")
    }
}