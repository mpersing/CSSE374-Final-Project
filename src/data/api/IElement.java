package data.api;

import visitor.api.ITraverser;

public interface IElement extends ITraverser {
	
	public void setName(String n);
	public String getName();
	public void setAccess(int a);
	public boolean isPrivate();
	public boolean isProtected();
	public boolean isPublic();
	public boolean isStatic();
}
