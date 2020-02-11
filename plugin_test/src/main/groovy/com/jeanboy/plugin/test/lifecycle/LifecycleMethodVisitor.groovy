package com.jeanboy.plugin.test.lifecycle


import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class LifecycleMethodVisitor extends MethodVisitor {

    private String className
    private String methodName

    LifecycleMethodVisitor(MethodVisitor methodVisitor, String className, String methodName) {
        super(Opcodes.ASM6, methodVisitor)
        this.className = className
        this.methodName = methodName
    }

    /**
     * 表示 ASM 开始扫描这个方法
     */
    @Override
    void visitCode() {
        super.visitCode()
        println("----LifecycleMethodVisitor-------visitCode()----")
        // Log.e("MainActivity", "-----------onCreate---------")
        mv.visitLdcInsn(className)
        mv.visitLdcInsn("-------" + methodName + "-------")
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false)
        mv.visitInsn(Opcodes.POP)
    }
}