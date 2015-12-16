package asm;

public class ClassDeclarationVisitor extends ClassInformationVisitor {
	
	public ClassDeclarationVisitor(int arg0) {
		super(arg0);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, 
			String[] interfaces) {
		this.newClass.setName(name);
		this.newClass.setAccess(access);
		this.newClass.setExtends(superName);
		this.newClass.setImplements(interfaces);
		super.visit(version, access, name, signature, superName, interfaces);
	}

}
