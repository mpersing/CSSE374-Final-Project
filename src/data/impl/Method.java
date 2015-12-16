package data.impl;

import visitor.api.ITraverser;
import visitor.impl.OutputVisitor;
import data.api.IMethod;

public class Method extends Element implements IMethod, ITraverser {

	private String[] arguments;
	private String name;
	private String returnType;
	
	@Override
	public void setArguments(String[] args) {
		this.arguments = args;
	}

	@Override
	public void setName(String n) {
		this.name = n;
	}

	@Override
	public void setReturnType(String r) {
		this.returnType = r;
	}

	@Override
	public void accept(OutputVisitor v) {
		v.visit(this);
	}

}
