package asm;

import data.api.IClass;
import jdk.internal.org.objectweb.asm.ClassVisitor;

public abstract class ClassInformationVisitor extends ClassVisitor {
	
	protected IClass newClass;

	public ClassInformationVisitor(int arg0) {
		super(arg0);
	}
	
	public ClassInformationVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}
	
	public void setClass(IClass c) {
		this.newClass = c;
	}

}
