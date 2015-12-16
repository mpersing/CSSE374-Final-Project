package data.impl;

import java.io.IOException;
import java.util.ArrayList;

import com.sun.xml.internal.ws.org.objectweb.asm.ClassReader;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassVisitor;

import asm.ClassDeclarationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;
import data.api.IClass;
import data.api.IDataManager;
import jdk.internal.org.objectweb.asm.Opcodes;
import visitor.api.ITraverser;
import visitor.impl.OutputVisitor;

public class DataManager implements IDataManager, ITraverser {

	private ArrayList<IClass> classes;
	
	public DataManager(){
		this.classes = new ArrayList<IClass>();
	}
	
	public void add(String toAdd) throws IOException{
		IClass newClass = new Class();
		classes.add(newClass);
		ClassReader reader = new ClassReader(toAdd);
		
		ClassDeclarationVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5);
		declVisitor.setClass(newClass);
		
		ClassFieldVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor);
		fieldVisitor.setClass(newClass);
		
		ClassMethodVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);
		methodVisitor.setClass(newClass);
		
		reader.accept((ClassVisitor)methodVisitor, ClassReader.EXPAND_FRAMES);
	}
	
	@Override
	public String output(StringBuffer sb) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void accept(OutputVisitor v) {
		v.visit(this);
		for (IClass c: this.classes){
			c.accept(v);
		}
	}

}
