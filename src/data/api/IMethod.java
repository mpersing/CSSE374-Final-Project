package data.api;

import visitor.api.ITraverser;

public interface IMethod extends IElement, ITraverser {

	public void setArguments(String[] args);
	public String[] getArguments();
	public void setReturnType(String r);
	public String getReturnType();
}
