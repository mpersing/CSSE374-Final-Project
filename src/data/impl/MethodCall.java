package data.impl;

import com.sun.org.apache.bcel.internal.generic.Type;

public class MethodCall {
	
	private String classToCall;
	private String methodToCall;
	private Type[] argTypes;
	private Type returnType;
	
	public MethodCall(String c, String m, Type[] types, Type r) {
		this.classToCall = c;
		this.methodToCall = m;
		this.argTypes = types;
		this.returnType = r;
	}
	
	public String getClassToCall() {
		return this.classToCall;
	}
	
	public String getMethodToCall() {
		return this.methodToCall;
	}
	
	public Type[] getArgTypes() {
		return this.argTypes;
	}
	
	public String getReturnType() {
		return this.returnType.toString();
	}
	
	public String getKey() {
		String toReturn = this.methodToCall;
		toReturn += "(";
		for(int i = 0 ; i < argTypes.length ; ++i) {
			toReturn += argTypes[i].toString();
			if(i != argTypes.length - 1) {
				toReturn += ",";
			}
		}
		toReturn += ")";
		return toReturn;
	}
}
