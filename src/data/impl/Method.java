package data.impl;

import java.util.ArrayList;
import java.util.List;

import data.api.IMethod;
import visitor.impl.OutputVisitor;

public class Method extends Element implements IMethod {

	private String[] arguments;
	private List<MethodCall> functionCallSeq;
	private String returnType;
	
	public Method() {
		functionCallSeq = new ArrayList<MethodCall>();
	}
	
	@Override
	public void setArguments(String[] args) {
		this.arguments = args;
	}

	@Override
	public void setReturnType(String r) {
		this.returnType = r;
	}
	
	@Override
	public void addMethodCall(MethodCall mc) {
		this.functionCallSeq.add(mc);
	}

	@Override
	public void accept(OutputVisitor v) {
		v.visit(this);
	}

	@Override
	public List<MethodCall> getMethodCalls() {
		return this.functionCallSeq;
	}

	@Override
	public String[] getArguments() {
		return this.arguments;
	}

	@Override
	public String getReturnType() {
		return this.returnType;
	}
	
	public String getKey() {
		String toReturn = this.getName();
		toReturn += "(";
		for(int i = 0 ; i < arguments.length ; ++i) {
			toReturn += arguments[i];
			if(i != arguments.length - 1) {
				toReturn += ",";
			}
		}
		toReturn += ")";
		return toReturn;
	}

}
