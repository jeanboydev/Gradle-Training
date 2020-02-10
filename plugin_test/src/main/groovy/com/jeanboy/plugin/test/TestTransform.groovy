package com.jeanboy.plugin.test

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Status
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.io.FileUtils


class TestTransform extends Transform {

    @Override
    String getName() {
        return "TestTransform"
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
        // 管理输出路径
        def outputProvider = transformInvocation.outputProvider
        // 当前是否是增量编译
        def isIncremental = transformInvocation.isIncremental()
        if (!isIncremental) {
            outputProvider.deleteAll()
        }
        transformInvocation.inputs.each { input ->
            processDirectory(input.directoryInputs, outputProvider, isIncremental)
            processJar(input.jarInputs, outputProvider, isIncremental)
        }
    }

    void processDirectory(DirectoryInput input, TransformOutputProvider outputProvider, boolean isIncremental) {
        def dest = outputProvider.getContentLocation(input.name,
                input.contentTypes, input.scopes, Format.DIRECTORY)
        if (isIncremental) {
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

        if (isIncremental) {
            def status = input.getStatus()
            switch (status) {
                case Status.NOTCHANGED:
                    break
                case Status.ADDED:
                case Status.CHANGED:
                    // transformJar(jarInput.getFile(), dest, status)
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