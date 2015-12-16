package data.api;

public interface IClass {
	
	public void addMethod(IMethod m);
	public void addField(IField f);
	public void setExtends(String e);
	public void addImplements(String i);
	public void setName(String name);
	public void setAccess(int a);
	public boolean isPublic();
	public boolean isPrivate();
	public boolean isStatic();

}
