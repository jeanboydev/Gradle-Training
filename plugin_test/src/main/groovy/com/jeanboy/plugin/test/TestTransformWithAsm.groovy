package com.jeanboy.plugin.test

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

class TestTransformWithAsm extends Transform {

    private Project project

    TestTransformWithAsm(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return "TestTransformWithAsm"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return true
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        println("=====transform===================start============")
        // 管理输出路径
        def outputProvider = transformInvocation.outputProvider
        // 便利 input
        transformInvocation.inputs.each { input ->
            println("--transform--directoryInputs--------${input.directoryInputs.size()}")
            println("--transform--jarInputs--------${input.jarInputs.size()}")
            input.directoryInputs.each { directoryInput ->
                processDirectory(directoryInput, outputProvider)
            }
            input.jarInputs.each { jarInput ->
                processJar(jarInput, outputProvider)
            }
        }
        println("=====transform===================end============")
    }

    void processDirectory(DirectoryInput input, TransformOutputProvider outputProvider) {
        println("=====processDirectory=============start==============")
        def dest = outputProvider.getContentLocation(input.name,
                input.contentTypes, input.scopes, Format.DIRECTORY)
        println("--processDirectory--input--${input.file.name}")
        println("--processDirectory--dest--${dest.absolutePath}")

        // 注入代码
        InjectByAsm.injectLog(input)

        // 将修改过的字节码 copy 到 dest
        FileUtils.copyDirectory(input.getFile(), dest)
        println("=====processDirectory=============end==============")
    }

    void processJar(JarInput input, TransformOutputProvider outputProvider) {
//        println("=====processJar==================start=============")
        def dest = outputProvider.getContentLocation(input.name,
                input.contentTypes, input.scopes, Format.JAR)
//        println("--processJar--input---${input.file.name}--${input.file.absolutePath}")
//        println("--processJar--dest-----${dest.absolutePath}")


        // 将修改过的字节码 copy 到 dest
        FileUtils.copyFile(input.getFile(), dest)
//        println("=====processJar==================end=============")
    }
}