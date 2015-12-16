package data.api;

public interface IElement {
	public void setAccess(int a);
	public boolean isPublic();
	public boolean isPrivate();
	public boolean isStatic();
}
