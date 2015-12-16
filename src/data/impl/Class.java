package data.impl;

import java.util.ArrayList;
import java.util.List;

import data.api.IClass;
import data.api.IField;
import data.api.IMethod;

public class Class extends Element implements IClass {
	
	List<IMethod> methodList;
	List<IField> fieldList;
	String extendsClass;
	List<String> implementList;
	
	public Class() {
		methodList = new ArrayList<IMethod>();
		fieldList = new ArrayList<IField>();
		implementList = new ArrayList<String>();
		extendsClass = null;
	}
	
	@Override
	public void addMethod(IMethod m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addField(IField f) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setExtends(String e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addImplements(String i) {
		// TODO Auto-generated method stub

	}

}
