package com.jeanboy.plugin.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by jeanboy on 2020/02/05 19:51.
 */
class LifecycleMethodVisitor extends MethodVisitor {

    private String className;
    private String methodName;

    public LifecycleMethodVisitor(MethodVisitor methodVisitor, String className, String methodName) {
        super(Opcodes.ASM6, methodVisitor);
        this.className = className;
        this.methodName = methodName;
    }

    /**
     * 表示 ASM 开始扫描这个方法
     */
    @Override
    public void visitCode() {
        super.visitCode();
        System.out.println("LifecycleMethodVisitor visitCode------");

        mv.visitLdcInsn(className);
        mv.visitLdcInsn("-------" + methodName + "-------");
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(Opcodes.POP);
    }

    /**
     * 访问本地变量类型指令，操作码可以是 LOAD、STORE、RET 中的一种
     *
     * @param opcode
     * @param operand
     */
    @Override
    public void visitIntInsn(int opcode, int operand) {
        super.visitIntInsn(opcode, operand);
    }

    /**
     * 域操作指令，用来加载或者存储对象的 Field
     *
     * @param opcode
     * @param owner
     * @param name
     * @param desc
     */
    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        super.visitFieldInsn(opcode, owner, name, desc);
    }

    /**
     * 方法操作指令
     *
     * @param opcode
     * @param owner
     * @param name
     * @param desc
     * @param itf
     */
    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }

    @Override
    public void visitInsn(int opcode) {
        super.visitInsn(opcode);
        System.out.println("LifecycleMethodVisitor visitInsn------");
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        System.out.println("LifecycleMethodVisitor visitEnd------");
    }
}
