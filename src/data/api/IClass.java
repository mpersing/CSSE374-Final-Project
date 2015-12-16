package data.api;

public interface IClass extends IElement {
	
	public void addMethod(IMethod m);
	public void addField(IField f);
	public void setExtends(String e);
	public void addImplements(String i);
	
}
