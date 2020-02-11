package com.jeanboy.plugin.buildsrc

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.io.FileUtils

class TestTransform2 extends Transform {

    @Override
    String getName() {
        return "TestTransform2"
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
        // 当前是否是增量编译
        def isIncremental = transformInvocation.isIncremental()
        if (!isIncremental) { // 不是增量编译，清空目录
            outputProvider.deleteAll()
        }
        transformInvocation.inputs.each { input ->
            println("--transform--directoryInputs--------${input.directoryInputs.size()}")
            println("--transform--jarInputs--------${input.jarInputs.size()}")
            input.directoryInputs.each { directoryInput ->
                processDirectory(directoryInput, outputProvider, isIncremental)
            }
            input.jarInputs.each { jarInput ->
                processJar(jarInput, outputProvider, isIncremental)
            }
        }
        println("=====transform===================end============")
    }

    void processDirectory(DirectoryInput input, TransformOutputProvider outputProvider, boolean isIncremental) {
        def dest = outputProvider.getContentLocation(input.name,
                input.contentTypes, input.scopes, Format.DIRECTORY)

        if (isIncremental) { // 增量编译
            def inputPath = input.getFile().getAbsolutePath()
            def outputPath = dest.getAbsolutePath()
            input.changedFiles.each { changeFile ->
                def status = changeFile.value
                def inputFile = changeFile.key
                def destPath = inputFile.getAbsolutePath().replace(inputPath, outputPath)
                def destFinal = new File(destPath)
                switch (status) {
                    case Status.NOTCHANGED:
                        break
                    case Status.ADDED:
                    case Status.CHANGED:
//                        transformSingleFile(inputFile, destFinal, inputPath);
                        break
                    case Status.REMOVED:
                        if (dest.exists()) {
                            FileUtils.forceDelete(dest)
                        }
                        break
                }
            }
        } else {
            transformDir(directoryInput.getFile(), dest)
        }

        // 将修改过的字节码 copy 到 dest
        FileUtils.copyDirectory(input.getFile(), dest)
    }

    void processJar(JarInput input, TransformOutputProvider outputProvider, boolean isIncremental) {
        def dest = outputProvider.getContentLocation(input.name,
                input.contentTypes, input.scopes, Format.JAR)

        if (isIncremental) { // 增量编译
            def status = input.getStatus()
            switch (status) {
                case Status.NOTCHANGED:
                    break
                case Status.ADDED:
                case Status.CHANGED:
                    // 注入代码
                    InjectByJavassit.injectToast(project, input.file)
                    break
                case Status.REMOVED:
                    if (dest.exists()) {
                        FileUtils.forceDelete(dest)
                    }
                    break
            }
        } else {
            // transformJar(jarInput.getFile(), dest, status)
        }

        // 将修改过的字节码 copy 到 dest
        FileUtils.copyFile(input.getFile(), dest)
    }
}