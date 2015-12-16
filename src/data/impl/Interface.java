package data.impl;

import java.util.ArrayList;
import java.util.List;

import data.api.IInterface;
import data.api.IMethod;

public class Interface extends data.impl.Element implements IInterface {

	private String name;
	private List<String> extendList;
	private List<IMethod> methodList;
	
	public Interface() {
		this.name = null;
		this.extendList = new ArrayList<String>();
		this.methodList = new ArrayList<IMethod>();
	}
	
	@Override
	public void addExtends(String e) {
		this.extendList.add(e);
	}

	@Override
	public void addMethod(IMethod m) {
		this.methodList.add(m);
	}

	@Override
	public void setName(String n) {
		this.name = n;
	}

}
