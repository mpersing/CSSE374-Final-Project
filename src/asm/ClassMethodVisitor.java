package asm;

import data.api.IMethod;
import data.impl.Method;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Type;

public class ClassMethodVisitor extends ClassInformationVisitor {

	public ClassMethodVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature,
			String[] exceptions) {
		MethodVisitor toDecorateLaterThisQuarter = super.visitMethod(access, name, desc, signature
				, exceptions);
		IMethod method = new Method();
		
		Type[] argTypes = Type.getArgumentTypes(desc);
		String[] classNames = new String[argTypes.length];
		for(int i = 0 ; i < argTypes.length ; ++i) {
			classNames[i] = argTypes[i].getClassName();
		}
		method.setArguments(classNames);
		method.setReturnType(Type.getReturnType(desc).getClassName());
		method.setName(name);
		method.setAccess(access);
		
		return toDecorateLaterThisQuarter;
	}



}
