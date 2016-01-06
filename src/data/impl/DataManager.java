package data.impl;

import java.io.IOException;
import java.util.ArrayList;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import visitor.api.IOutputStrategy;
import visitor.impl.OutputVisitor;
import asm.ClassDeclarationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;
import data.api.IClass;
import data.api.IDataManager;

public class DataManager implements IDataManager {

	private ArrayList<IClass> classes;
	private ArrayList<OutputVisitor> visitors;
	private IOutputStrategy outStrat;
	
	public DataManager(){
		this.classes = new ArrayList<IClass>();
		this.visitors = new ArrayList<OutputVisitor>();
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
	
	public void addOutputVisitor(OutputVisitor v){
		this.visitors.add(v);
	}
	
	@Override
	public void output(StringBuffer sb) {
		this.outStrat.preVisit(sb);
		
		for(OutputVisitor v : this.visitors){
			v.setStringBuffer(sb);
			this.accept(v);
		}
		
		for(OutputVisitor v : this.visitors){
			v.postVisit(this);
		}
		
		this.outStrat.postVisit(sb);
	}

	@Override
	public void accept(OutputVisitor v) {
		v.visit(this);
		for (IClass c: this.classes){
			c.accept(v);
		}
	}
	
	public ArrayList<IClass> getClasses() {
		return this.classes;
	}
	
	public void setOutputStrategy(IOutputStrategy outStrat){
		this.outStrat = outStrat;
	}

}
