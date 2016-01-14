package data.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import asm.ClassDeclarationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;
import data.api.AddStrategy;
import data.api.IClass;
import data.api.IDataManager;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import visitor.api.IOutputStrategy;
import visitor.impl.OutputVisitor;

public class DataManager implements IDataManager {

	private Map<String,IClass> classes;
	private IOutputStrategy outStrat;
	private AddStrategy addStrat;
	
	public DataManager(){
		this.classes = new HashMap<String,IClass>();
	}
	
	public void addClass(String toAdd) throws IOException{
		IClass newClass = new Class();
		if(this.classes.containsKey(toAdd)) {
			return;
		}	
		classes.put(toAdd, newClass);
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
	public void output(StringBuffer sb) {
		this.outStrat.output(sb);
	}

	@Override
	public void accept(OutputVisitor v) {
		v.visit(this);
		for (IClass c: this.classes.values()){
			c.accept(v);
		}
	}
	
	public Collection<IClass> getClasses() {
		return this.classes.values();
	}
	
	public void setOutputStrategy(IOutputStrategy outStrat){
		this.outStrat = outStrat;
	}
	
	public void setAddStrategy(AddStrategy addStrat){
		this.addStrat = addStrat;
		addStrat.setDataManager(this);
	}

	@Override
	public void add(String[] toAdd) throws IOException {
		this.addStrat.add(toAdd);
	}

	@Override
	public IClass getClass(String s) {
		return this.classes.get(s);
	}

}
