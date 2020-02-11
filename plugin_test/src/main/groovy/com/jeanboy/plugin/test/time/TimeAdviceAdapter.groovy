package com.jeanboy.plugin.test.time

import com.jeanboy.plugin.annotation.TimeMark
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter

class TimeAdviceAdapter extends AdviceAdapter {

    private String className
    private String methodName
    private boolean isMatched = false

    protected TimeAdviceAdapter(String className, MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM6, mv, access, name, desc)
        this.className = className
        this.methodName = name
    }

    @Override
    AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (Type.getDescriptor(TimeMark.class) == desc) {
            isMatched = true
        }
        return super.visitAnnotation(desc, visible)
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter()
        if (isMatched) {
            // Log.e("jeanboy", "-------method() start-----------");
            mv.visitLdcInsn(className)
            mv.visitLdcInsn("-------" + methodName + "() start-----------")
            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            mv.visitInsn(POP)

            // TimeHelper.markStart("methodName", System.nanoTime());
            mv.visitLdcInsn(methodName)
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false)
            mv.visitMethodInsn(INVOKESTATIC, "com/jeanboy/plugin/annotation/TimeHelper", "markStart", "(Ljava/lang/String;J)V", false)
        }
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode)
        if (isMatched) {
            // TimeHelper.markEnd("methodName", System.nanoTime());
            mv.visitLdcInsn(methodName)
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false)
            mv.visitMethodInsn(INVOKESTATIC, "com/jeanboy/plugin/annotation/TimeHelper", "markEnd", "(Ljava/lang/String;J)V", false)

            // Log.e("jeanboy", TimeHelper.getCostTime("methodName"));
            mv.visitLdcInsn(className)
            mv.visitLdcInsn(methodName)
            mv.visitMethodInsn(INVOKESTATIC, "com/jeanboy/plugin/annotation/TimeHelper", "getCostTime", "(Ljava/lang/String;)Ljava/lang/String;", false)
            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false)
            mv.visitInsn(POP)

            // Log.e("jeanboy", "-------method() end-----------");
            mv.visitLdcInsn(className)
            mv.visitLdcInsn("-------" + methodName + "() end-----------");
            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            mv.visitInsn(POP)
        }
    }
}