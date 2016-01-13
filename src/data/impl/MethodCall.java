package data.impl;

public class MethodCall {
	
	private String classToCall;
	private String methodToCall;
	
	public MethodCall(String c, String m) {
		this.classToCall = c;
		this.methodToCall = m;
	}
	
	public String getClassToCall() {
		return this.classToCall;
	}
	
	public String getMethodToCall() {
		return this.methodToCall;
	}

}
