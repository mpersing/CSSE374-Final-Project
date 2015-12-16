package data.api;

import visitor.api.ITraverser;

public interface IInterface extends IElement, ITraverser {
	public void addExtends(String e);
	public void addMethod(IMethod m);
	public void setName(String n);
}
