package data.impl;

import data.api.IElement;
import jdk.internal.org.objectweb.asm.Opcodes;

public abstract class Element implements IElement {

	int access;
	
	@Override
	public void setAccess(int a) {
		this.access = a;
	}

	@Override
	public boolean isPublic() {
		return (this.access & Opcodes.ACC_PUBLIC) != 0;
	}

	@Override
	public boolean isPrivate() {
		return (this.access & Opcodes.ACC_PRIVATE) != 0;
	}

	@Override
	public boolean isStatic() {
		return (this.access & Opcodes.ACC_STATIC) != 0;
	}

}
