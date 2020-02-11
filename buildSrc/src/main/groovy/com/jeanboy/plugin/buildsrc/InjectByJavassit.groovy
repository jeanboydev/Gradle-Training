package com.jeanboy.plugin.buildsrc

import javassist.ClassPool
import org.gradle.api.Project

class InjectByJavassit {

    private static final ClassPool classPool = ClassPool.getDefault()

    static void injectToast(Project project, File dir) {
        classPool.appendClassPath(dir.absolutePath)
        //  project.android.bootClasspath 加入android.jar，不然找不到 android 相关的所有类
        classPool.appendClassPath(project.android.bootClasspath[0].toString())
        // classPool.importPackage("android.os.Bundle")

        if (dir.isDirectory()) {
            dir.eachFileRecurse { file ->
                println("--InjectByJavassit----filePath--${file.absolutePath}")
                println("--InjectByJavassit----fileName--${file.name}")
                if (file.name == "MainActivity.class") {
                    println("==InjectByJavassit=======inject start=======")
                    def ctClass = classPool.getCtClass("com.jeanboy.app.gradletraining.MainActivity")
                    println("--InjectByJavassit----ctClass--${ctClass}")
                    // 解冻
                    if (ctClass.isFrozen()) {
                        ctClass.defrost()
                    }

                    def ctMethod = ctClass.getDeclaredMethod("onCreate")
                    println("--InjectByJavassit----ctMethod--${ctMethod}")

                    String toast = """android.widget.Toast.makeText(this,"我是被插入的 Toast 代码~!!",android.widget.Toast.LENGTH_SHORT).show(); """
                    ctMethod.insertAfter(toast) // 方法尾部插入
                    ctClass.writeFile(dir.absolutePath) // 写入文件
                    ctClass.detach() // 释放
                    println("==InjectByJavassit=======inject end=======")
                }
            }
        }
    }
}