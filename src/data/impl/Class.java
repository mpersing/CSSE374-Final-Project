package data.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

import data.api.IClass;
import data.api.IField;
import data.api.IMethod;
import visitor.impl.OutputVisitor;

public class Class extends Element implements IClass {
	
	private Map<String,IMethod> methodMap;
	private List<IField> fieldList;
	private Set<String> usesSet;
	private Set<String> assocSet;
	private String extendsClass;
	private String[] implementArray;
	private static Set<String> primativeSet;
	
	public Class() {
		this.methodMap = new HashMap<String,IMethod>();
		this.fieldList = new ArrayList<IField>();
		this.usesSet = new HashSet<String>();
		this.assocSet = new HashSet<String>();
		this.implementArray = null;
		this.setExtendsClass(null);
		if(primativeSet == null) {
			primativeSet = new HashSet<String>();
			primativeSet.add("int");
			primativeSet.add("java.lang.String");
			primativeSet.add("void");
		}
	}
	
	@Override
	public boolean isInterface() {
		return (this.access & Opcodes.ACC_INTERFACE) != 0;
	}
	
	@Override
	public boolean isAbstract() {
		return (this.access & Opcodes.ACC_ABSTRACT) != 0;
	}
	
	@Override
	public void addMethod(IMethod m) {
		String key = m.getKey();
		this.methodMap.put(key, m);
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
		for (IMethod m : this.methodMap.values()){
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
	public Collection<IMethod> getMethods() {
		return this.methodMap.values();
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
	
	private String removeBrackets(String s) {
		return s.replaceAll("[\\[\\]]", "");
	}

	@Override
	public void addUses(String u) {
		usesSet.add(removeBrackets(u));
	}

	@Override
	public void addAssoc(String a) {
		assocSet.add(removeBrackets(a));
	}

	@Override
	public Set<String> getUses() {
		Set<String> toReturn = new HashSet<String>(usesSet);
		toReturn.removeAll(assocSet);
		toReturn.removeAll(primativeSet);
		return toReturn;
	}

	@Override
	public Set<String> getAssoc() {
		Set<String> toReturn = new HashSet<String>(assocSet);
		toReturn.removeAll(primativeSet);
		return toReturn;
	}

	@Override
	public IMethod getMethod(String m) {
		return this.methodMap.get(m);
	}

}
