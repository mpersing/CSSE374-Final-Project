package data.impl;

import java.util.ArrayList;
import java.util.List;

import data.api.IClass;
import data.api.IField;
import data.api.IMethod;

public class Class extends Element implements IClass {
	
	private List<IMethod> methodList;
	private List<IField> fieldList;
	private String extendsClass;
	private List<String> implementList;
	
	public Class() {
		this.methodList = new ArrayList<IMethod>();
		this.fieldList = new ArrayList<IField>();
		this.implementList = new ArrayList<String>();
		this.extendsClass = null;
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
		this.extendsClass = e;
	}

	@Override
	public void addImplements(String i) {
		this.implementList.add(i);
	}

}
