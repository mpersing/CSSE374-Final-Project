package data.api;

import visitor.api.ITraverser;

public interface IClass extends IElement, ITraverser {
	
	public void addMethod(IMethod m);
	public void addField(IField f);
	public void setExtends(String e);
	public void setImplements(String[] i);
	public boolean isInterface();
	
}
