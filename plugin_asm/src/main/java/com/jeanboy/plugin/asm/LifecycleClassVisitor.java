package com.jeanboy.plugin.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by jeanboy on 2020/02/05 19:43.
 */
class LifecycleClassVisitor extends ClassVisitor {

    private String className;

    public LifecycleClassVisitor(ClassWriter classWriter) {
        super(Opcodes.ASM6, classWriter);
    }

    /**
     * 该方法是当扫描类时第一个调用的方法，主要用于类声明使用
     *
     * @param version    类版本
     * @param access     修饰符
     * @param name       类名
     * @param signature  泛型信息
     * @param superName  继承的父类名
     * @param interfaces 实现的接口
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
    }

    /**
     * 该方法是当扫描器扫描到类注解声明时进行调用
     *
     * @param desc    注解类型
     * @param visible 注解是否在 JVM 中可见
     * @return
     */
    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return super.visitAnnotation(desc, visible);
    }

    /**
     * 该方法是当扫描器扫描到类中字段时进行调用
     *
     * @param access    修饰符
     * @param name      字段名
     * @param desc      字段类型
     * @param signature 泛型描述
     * @param value     默认值
     * @return
     */
    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        return super.visitField(access, name, desc, signature, value);
    }

    /**
     * 该方法是当扫描器扫描到类的方法时进行调用
     *
     * @param access     修饰符
     * @param name       方法名
     * @param desc       方法签名
     * @param signature  泛型信息
     * @param exceptions 抛出的异常
     * @return
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println("LifecycleClassVisitor visitMethod name-------" + name);
        // MethodVisitor 是一个抽象类，当 ASM 的 ClassReader 读取到 Method 时就转入 MethodVisitor 接口处理。
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);

        // AdviceAdapter 是 MethodVisitor 的子类，使用 AdviceAdapter 可以更方便的修改方法的字节码。

        if (name.startsWith("on")) {
            return new LifecycleMethodVisitor(methodVisitor, className, name);
        }
        return methodVisitor;
    }

    /**
     * 该方法是当扫描器完成类扫描时才会调用，如果想在类中追加某些方法
     */
    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}
