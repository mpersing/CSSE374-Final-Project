package asm;

import data.api.IClass;
import jdk.internal.org.objectweb.asm.MethodVisitor;

public class InternalMethodVisitor extends MethodVisitor {

	private IClass newClass;
	
	public InternalMethodVisitor(int arg0, MethodVisitor arg1) {
		super(arg0, arg1);
	}
	
	public void setClass(IClass c) {
		this.newClass = c;
	}
	
	public void visitTypeInsn(int opcode, String type) {
		this.newClass.addUses(type.replace("/", "."));
	}
	

}
