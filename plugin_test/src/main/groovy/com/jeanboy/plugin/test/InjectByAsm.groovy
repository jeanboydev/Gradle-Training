package com.jeanboy.plugin.test

import com.android.build.api.transform.DirectoryInput
import com.jeanboy.plugin.test.lifecycle.LifecycleClassVisitor
import com.jeanboy.plugin.test.time.TimeClassVisitor
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

class InjectByAsm {

    static void injectLog(DirectoryInput input) {
        input.file.eachFileRecurse { file ->
            def fileName = file.name
            println("--InjectByAsm----fileName--${fileName}")

            if (fileName.endsWith("Activity.class")) {
                println("--InjectByAsm--matched--fileName--${fileName}")
                // 这个类会将 .class 文件读入到 ClassReader 中的字节数组中，
                // 它的 accept 方法接受一个 ClassVisitor 实现类，并按照顺序调用 ClassVisitor 中的方法
                def classReader = new ClassReader(file.bytes)
                // ClassWriter 是一个 ClassVisitor 的子类，是和 ClassReader 对应的类，
                // ClassReader 是将 .class 文件读入到一个字节数组中，ClassWriter 是将修改后的类的字节码内容以字节数组的形式输出
                def classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                // 访问 class 文件相应的内容，解析到某一个结构就会通知到 ClassVisitor 的相应方法

//                def classVisitor = new LifecycleClassVisitor(classWriter) // 生命周期打印
                def classVisitor = new TimeClassVisitor(classWriter) // 方法耗时统计

                // 依次调用 ClassVisitor 接口的各个方法
                classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
                // 通过文件流写入方式覆盖掉原先的内容，实现class文件的改写
                def outputStream = new FileOutputStream(file.path)
                outputStream.write(classWriter.toByteArray())
                outputStream.close()
            }
        }
    }
}