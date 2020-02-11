package com.jeanboy.plugin.test.time


import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class TimeClassVisitor extends ClassVisitor {

    private String className

    TimeClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM6, classVisitor)
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
        this.className = name
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        def methodVisitor = super.visitMethod(access, name, desc, signature, exceptions)
        methodVisitor = new TimeAdviceAdapter(className, methodVisitor, access, name, desc)
        return methodVisitor
    }
}