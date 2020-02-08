package com.jeanboy.plugin.asm;


import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Created by jeanboy on 2020/02/07 10:50.
 */
public class LifecycleMethodAdapter extends AdviceAdapter {

    private String methodName;

    protected LifecycleMethodAdapter(MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM6, mv, access, name, desc);
        this.methodName = name;
    }

    @Override
    public void visitCode() {
        super.visitCode();
        System.out.println("LifecycleMethodAdapter visitCode------");
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        System.out.println("LifecycleMethodAdapter onMethodEnter------");
        mv.visitLdcInsn("jeanboy");
        mv.visitLdcInsn("-------" + methodName + "-------");
        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(POP);
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        System.out.println("LifecycleMethodAdapter onMethodExit------");
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        System.out.println("LifecycleMethodAdapter visitEnd------");
    }
}
