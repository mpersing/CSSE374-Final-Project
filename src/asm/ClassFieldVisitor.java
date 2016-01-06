package asm;

import data.api.IField;
import data.impl.Field;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.Type;

public class ClassFieldVisitor extends ClassInformationVisitor {

	public ClassFieldVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}
	
	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, 
			Object value) {// value is null if no default value 
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		IField field = new Field();
		field.setAccess(access);
		if(signature == null)
		{
			field.setType(Type.getType(desc).getClassName());
		} else {
			String sigResult;
			int index = signature.lastIndexOf("<L");
			sigResult = signature.substring(index + 2, signature.length() - 3);
			field.setType(sigResult.replace('/', '.'));
			// signature magic
		}
		field.setName(name);
		newClass.addField(field);
		return toDecorate;
	}

}
