package com.jeanboy.plugin.buildsrc

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class MyTestPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println("这是 MyTestPlugin")
        // 获取 Android 扩展
        def appExtension = project.extensions.getByType(AppExtension)
        // 注册 Transform
        appExtension.registerTransform(new TestTransform(project))
    }
}