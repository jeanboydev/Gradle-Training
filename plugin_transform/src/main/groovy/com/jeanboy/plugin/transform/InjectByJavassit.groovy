package com.jeanboy.plugin.transform

import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import org.gradle.api.Project

class InjectByJavassit {
    private static final ClassPool classPool = ClassPool.getDefault()

    static void injectToast(String path, Project project) {
        classPool.appendClassPath(path) //加入当前路径
        // project.android.bootClasspath 加入android.jar，不然找不到android相关的所有类
        classPool.appendClassPath(project.android.bootClasspath[0].toString())
        // 引入android.os.Bundle包，因为onCreate方法参数有Bundle
        classPool.importPackage("android.os.Bundle")

        File dir = new File(path)
        if (dir.isDirectory()) {
            // 遍历文件夹
            dir.eachFileRecurse { File file ->
                String filePath = file.absolutePath
                println("--------filepath: $filePath")

                if (file.name == "MainActivity.class") {
                    CtClass ctClass = classPool.getCtClass("com.jeanboy.app.gradletraining.MainActivity")
                    println("ctClass: $ctClass")

                    if (ctClass.isFrozen()) { // 解冻
                        ctClass.defrost()
                    }

                    CtMethod ctMethod = ctClass.getDeclaredMethod("onCreate")
                    println("ctMethod: $ctMethod")

                    String toast = """android.widget.Toast.makeText(this,"我是被插入的Toast代码~!!",android.widget.Toast.LENGTH_SHORT).show(); """
                    ctMethod.insertAfter(toast) // 方法尾部插入
                    ctClass.writeFile(path)
                    ctClass.detach() // 释放
                }
            }
        }
    }
}