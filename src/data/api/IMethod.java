package data.api;

import visitor.api.ITraverser;

public interface IMethod extends IElement, ITraverser {

	public void setArguments(String[] args);
	public void setName(String n);
	public void setReturnType(String r);
}
