package data.api;

import visitor.api.ITraverser;

public interface IElement extends ITraverser {
	public void setAccess(int a);
	public boolean isPublic();
	public boolean isPrivate();
	public boolean isStatic();
}
