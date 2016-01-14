package data.impl;

import java.io.IOException;
import java.util.List;

import data.api.AddStrategy;
import data.api.IClass;
import data.api.IMethod;

public class SDAddStrategy extends AddStrategy {

	@Override
	public void add(String[] toAdd) throws IOException {
		this.addRecursively(toAdd[0], toAdd[1], Integer.parseInt(toAdd[2]));
	}
	
	private void addRecursively(String classToCall, String methodToCall, int callDepth) throws IOException {
		if(callDepth == -1) {
			return;
		}
		dataManager.addClass(classToCall);
		IClass newClass = dataManager.getClass(classToCall);
		IMethod calledMethod = newClass.getMethod(methodToCall);
		List<MethodCall> mcList = calledMethod.getMethodCalls();
		for(MethodCall m : mcList) {
			this.addRecursively(m.getClassToCall(), m.getKey(), callDepth - 1);
		}
		
	}

}