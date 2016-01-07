package asm;

public class ClassDeclarationVisitor extends ClassInformationVisitor {
	
	public ClassDeclarationVisitor(int arg0) {
		super(arg0);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, 
			String[] interfaces) {
		this.newClass.setName(name.replace('/', '.'));
		this.newClass.setAccess(access);
		if(superName != null) {
			this.newClass.setExtends(superName.replace('/', '.'));
		} else {
			this.newClass.setExtends(superName);
		}
		for(int i = 0 ; i < interfaces.length ; ++i) {
			interfaces[i] = interfaces[i].replace('/', '.');
		}
		this.newClass.setImplements(interfaces);
		super.visit(version, access, name, signature, superName, interfaces);
	}

}
