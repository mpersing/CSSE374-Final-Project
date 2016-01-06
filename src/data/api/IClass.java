package data.api;

import java.util.List;
import java.util.Set;

import visitor.api.ITraverser;

public interface IClass extends IElement, ITraverser {
	
	public void addMethod(IMethod m);
	public List<IMethod> getMethods();
	public void addField(IField f);
	public List<IField> getFields();
	public void setExtends(String e);
	public String getExtends();
	public void setImplements(String[] i);
	public String[] getImplements();
	public boolean isInterface();
	public boolean isAbstract();
	public void addUses(String u);
	public void addAssoc(String a);
	public Set<String> getUses();
	public Set<String> getAssoc();
	
}
