package com.jeanboy.plugin.asm;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.android.utils.FileUtils;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;


/**
 * Created by jeanboy on 2020/02/05 17:27.
 */
public class MyTransform extends Transform {

    @Override
    public String getName() {
        return "MyTransform";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS; // classes 和 resources
    }

    /**
     * 指Transform要操作内容的范围，官方文档Scope有7种类型：
     * XTERNAL_LIBRARIES        只有外部库
     * PROJECT                  只有项目内容
     * PROJECT_LOCAL_DEPS       只有项目的本地依赖(本地jar)
     * PROVIDED_ONLY            只提供本地或远程依赖项
     * SUB_PROJECTS             只有子项目。
     * SUB_PROJECTS_LOCAL_DEPS  只有子项目的本地依赖项(本地jar)。
     * TESTED_CODE              由当前变量(包括依赖项)测试的代码
     * SCOPE_FULL_PROJECT       整个项目
     */
    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);
        System.out.println("---asm------transform----开始---");

        Collection<TransformInput> inputs = transformInvocation.getInputs();
        System.out.println("---asm------transform----inputs---" + inputs.size());
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();
        boolean incremental = transformInvocation.isIncremental();

        for (TransformInput input : inputs) {
            for (DirectoryInput directoryInput : input.getDirectoryInputs()) {
                processDirectoryInput(directoryInput, outputProvider);
            }

            for (JarInput jarInput : input.getJarInputs()) {
                processJarInput(jarInput, outputProvider);
            }
        }
        System.out.println("---asm------transform----结束---");
    }

    private interface Callback {
        void onFind(File file);
    }

    private void eachFileRecurse(File dir, Callback callback) {
        if (dir == null) return;
        if (!dir.exists()) return;
        if (!dir.isDirectory()) {
            callback.onFind(dir);
            return;
        }
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    eachFileRecurse(file, callback);
                } else {
                    callback.onFind(file);
                }
            }
        }
    }

    private void processDirectoryInput(DirectoryInput directoryInput, TransformOutputProvider outputProvider) {
        System.out.println("---asm------processDirectoryInput-------");

        // 处理字节码
        eachFileRecurse(directoryInput.getFile(), new Callback() {
            @Override
            public void onFind(File file) {
                try {
                    String fileName = file.getName();
                    System.out.println("---asm--file----transform----fileName---" + fileName);
                    System.out.println("---asm--file----transform----start---");
                    if (fileName.endsWith("Activity.class")) {
                        System.out.println("---asm--file----filename-------" + fileName);
                        // 这个类会将 .class 文件读入到 ClassReader 中的字节数组中，
                        // 它的 accept 方法接受一个 ClassVisitor 实现类，并按照顺序调用 ClassVisitor 中的方法
                        ClassReader classReader = new ClassReader(new FileInputStream(file.getPath()));
                        // ClassWriter 是一个 ClassVisitor 的子类，是和 ClassReader 对应的类，
                        // ClassReader 是将 .class 文件读入到一个字节数组中，ClassWriter 是将修改后的类的字节码内容以字节数组的形式输出
                        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
                        // 访问 class 文件相应的内容，解析到某一个结构就会通知到 ClassVisitor 的相应方法
                        ClassVisitor classVisitor = new LifecycleClassVisitor(classWriter);
                        // 依次调用 ClassVisitor 接口的各个方法
                        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
                        byte[] code = classWriter.toByteArray();
                        FileOutputStream outputStream = new FileOutputStream(file.getPath());
                        outputStream.write(code);
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            System.out.println("---asm--file----transform----end---");
            File dest = outputProvider.getContentLocation(directoryInput.getName(),
                    directoryInput.getContentTypes(), directoryInput.getScopes(), Format.DIRECTORY);
            System.out.println("---asm--file----transform---dest----" + dest.getAbsolutePath());
            FileUtils.copyDirectory(directoryInput.getFile(), dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void processJarInput(JarInput jarInput, TransformOutputProvider outputProvider) {
        File dest = outputProvider.getContentLocation(jarInput.getFile().getAbsolutePath(),
                jarInput.getContentTypes(), jarInput.getScopes(), Format.JAR);
        System.out.println("---asm--jar----transform---dest----" + dest.getAbsolutePath());

        // 处理字节码

        try {
            FileUtils.copyFile(jarInput.getFile(), dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
