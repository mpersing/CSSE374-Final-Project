package asm;

import data.api.IMethod;
import data.impl.Method;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;

public class ClassMethodVisitor extends ClassInformationVisitor {

	public ClassMethodVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature,
			String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature
				, exceptions);
		InternalMethodVisitor imv = new InternalMethodVisitor(Opcodes.ASM5, toDecorate);
		IMethod method = new Method();
		imv.setClass(this.newClass);
		imv.setMethod(method);
		
		Type[] argTypes = Type.getArgumentTypes(desc);
		String[] classNames = new String[argTypes.length];
		for(int i = 0 ; i < argTypes.length ; ++i) {
			String cName = argTypes[i].getClassName();
			classNames[i] = cName;
			this.newClass.addUses(cName);
			
		}
		method.setArguments(classNames);
		String returnType = Type.getReturnType(desc).getClassName();
		method.setReturnType(returnType);
		this.newClass.addUses(returnType);
		
		method.setName(name);
		method.setAccess(access);
		
		this.newClass.addMethod(method);
		
		return imv;
	}



}
