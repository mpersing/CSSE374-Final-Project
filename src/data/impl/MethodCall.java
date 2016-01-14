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
		System.out.println("Calling class: " + this.classToCall + " method: " + this.getKey());
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
	
	public String getKey() {
		String toReturn = this.methodToCall;
		toReturn += "(";
		for(int i = 0 ; i < argTypes.length ; ++i) {
			toReturn += argTypes[i].toString();
			if(i != argTypes.length - 1) {
				toReturn += ",";
			} else {
				toReturn += ")";
			}
		}
		return toReturn;
	}
}
