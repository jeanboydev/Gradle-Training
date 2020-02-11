package com.jeanboy.plugin.test.lifecycle

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes


class LifecycleClassVisitor extends ClassVisitor {

    private String className

    LifecycleClassVisitor(ClassWriter classWriter) {
        super(Opcodes.ASM6, classWriter)
    }

    /**
     * 该方法是当扫描类时第一个调用的方法，主要用于类声明使用
     *
     * @param version 类版本
     * @param access 修饰符
     * @param name 类名
     * @param signature 泛型信息
     * @param superName 继承的父类名
     * @param interfaces 实现的接口
     */
    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
        println("----LifecycleClassVisitor-------visit()----")
        this.className = name
    }

    /**
     * 该方法是当扫描器扫描到类的方法时进行调用
     *
     * @param access 修饰符
     * @param name 方法名
     * @param desc 方法签名
     * @param signature 泛型信息
     * @param exceptions 抛出的异常
     * @return
     */
    @Override
    MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        println("----LifecycleClassVisitor-------visitMethod()----")
        def methodVisitor = super.visitMethod(access, name, desc, signature, exceptions)

        if (name.startsWith("on")) {
            return new LifecycleMethodVisitor(methodVisitor, className, name)
        }

        return methodVisitor
    }
}