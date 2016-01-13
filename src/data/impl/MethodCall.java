package data.impl;

import com.sun.org.apache.bcel.internal.generic.Type;

public class MethodCall {
	
	private String classToCall;
	private String methodToCall;
	private Type[] argTypes;
	
	public MethodCall(String c, String m, Type[] types) {
		this.classToCall = c;
		this.methodToCall = m;
		this.argTypes = types;
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
	
	// TODO: Figure out if this thing works
	public String getKey() {
		String toReturn = this.methodToCall;
		toReturn += "(";
		for(Type t : argTypes) {
			toReturn += t.toString();
		}
		return toReturn;
	}
}
