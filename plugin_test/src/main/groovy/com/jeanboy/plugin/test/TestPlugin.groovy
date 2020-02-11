package com.jeanboy.plugin.test

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class TestPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println("这是 TestPlugin")

        // 获取 Android 扩展
        def appExtension = project.extensions.getByType(AppExtension)
        // 注册 Transform
        appExtension.registerTransform(new TestTransformWithAsm(project))
    }
}