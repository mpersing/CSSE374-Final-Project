package asm;

import com.sun.org.apache.bcel.internal.generic.Type;

import data.api.IClass;
import data.api.IMethod;
import data.impl.MethodCall;
import jdk.internal.org.objectweb.asm.MethodVisitor;

public class InternalMethodVisitor extends MethodVisitor {

	private IClass newClass;
	private IMethod newMethod;
	
	public InternalMethodVisitor(int arg0, MethodVisitor arg1) {
		super(arg0, arg1);
	}
	
	public void setClass(IClass c) {
		this.newClass = c;
	}
	
	public void setMethod(IMethod m) {
		this.newMethod = m;
	}
	
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		this.newMethod.addMethodCall(new MethodCall(owner.replace("/",  "."), name, Type.getArgumentTypes(desc), Type.getReturnType(desc)));
	}
	
	public void visitTypeInsn(int opcode, String type) {
		this.newClass.addUses(type.replace("/", "."));
	}
	

}
