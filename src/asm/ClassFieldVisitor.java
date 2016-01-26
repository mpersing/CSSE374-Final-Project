package asm;

import java.util.ArrayList;
import java.util.List;

import data.api.IField;
import data.impl.Field;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.signature.SignatureReader;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

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
			String type = Type.getType(desc).getClassName();
			field.setType(type);
			newClass.addAssoc(type);
		} else {
			List<String> insideGeneric = new ArrayList<String>();
			new SignatureReader(signature).accept(new SignatureVisitor(Opcodes.ASM5) {
				@Override
				public void visitClassType(String type) {
					type = type.replace('/', '.');
					insideGeneric.add(type);
				}
			});
			String sigResult = "";
			if(insideGeneric.size() >= 2) {
				sigResult = insideGeneric.get(1);
				newClass.addAssoc(insideGeneric.get(1));
				for(int i = 2 ; i < insideGeneric.size() ; ++i) {
					sigResult += "," + insideGeneric.get(i);
					newClass.addAssoc(insideGeneric.get(i));
				}
			}
			field.setType(Type.getType(desc).getClassName() + "\\<" + sigResult + "\\>");
			// signature magic
		}
		field.setName(name);
		newClass.addField(field);
		return toDecorate;
	}

}
