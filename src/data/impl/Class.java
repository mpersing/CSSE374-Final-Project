package data.impl;

import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

import visitor.impl.OutputVisitor;
import data.api.IClass;
import data.api.IField;
import data.api.IMethod;

public class Class extends Element implements IClass {
	
	private List<IMethod> methodList;
	private List<IField> fieldList;
	private String extendsClass;
	private String[] implementArray;
	
	public Class() {
		this.methodList = new ArrayList<IMethod>();
		this.fieldList = new ArrayList<IField>();
		this.implementArray = null;
		this.setExtendsClass(null);
	}
	
	public boolean isInterface() {
		return (this.access & Opcodes.ACC_INTERFACE) != 0;
	}
	
	@Override
	public void addMethod(IMethod m) {
		this.methodList.add(m);
	}

	@Override
	public void addField(IField f) {
		this.fieldList.add(f);
	}

	@Override
	public void setExtends(String e) {
		this.setExtendsClass(e);
	}

	@Override
	public void setImplements(String[] i) {
		this.implementArray = i;
	}

	@Override
	public void accept(OutputVisitor v) {
		v.visit(this);
		for (IField f : this.fieldList){
			f.accept(v);
		}
		v.midVisit(this);
		for (IMethod m : this.methodList){
			m.accept(v);
		}
		v.postVisit(this);
	}

	public String getExtendsClass() {
		return extendsClass;
	}

	public void setExtendsClass(String extendsClass) {
		this.extendsClass = extendsClass;
	}

	@Override
	public List<IMethod> getMethods() {
		return this.methodList;
	}

	@Override
	public List<IField> getFields() {
		return this.fieldList;
	}

	@Override
	public String getExtends() {
		return this.extendsClass;
	}

	@Override
	public String[] getImplements() {
		return this.implementArray;
	}

}
