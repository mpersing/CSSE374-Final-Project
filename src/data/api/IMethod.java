package data.api;

import java.util.List;

import data.impl.MethodCall;
import visitor.api.ITraverser;

public interface IMethod extends IElement, ITraverser {

	public void setArguments(String[] args);
	public String[] getArguments();
	public void setReturnType(String r);
	public String getReturnType();
	public void addMethodCall(MethodCall mc);
	public List<MethodCall> getMethodCalls();
	
}
