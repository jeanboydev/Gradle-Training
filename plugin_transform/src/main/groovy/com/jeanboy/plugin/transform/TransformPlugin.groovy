package com.jeanboy.plugin.transform

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class TransformPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        // 获取 Android 扩展
        def android = project.extensions.getByType(AppExtension)
        android.registerTransform(new TestTransform(project))

        // 这里只是随便定义一个Task而已，和Transform无关
        project.task('JustTask') {
            doLast {
                println('InjectTransform task')
            }
        }
    }
}