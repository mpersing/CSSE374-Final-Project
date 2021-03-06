package visitor.impl;

import java.util.Set;

import data.api.IClass;
import data.api.IDataManager;
import data.api.IField;
import data.api.IMethod;
import data.api.IUMLModifier;

public abstract class OutputVisitor {
	
	protected StringBuffer sb;
	protected Set<String> classWhitelist;
	protected IUMLModifier iumlmod;
	
	public void setStringBuffer(StringBuffer sb){
		this.sb = sb;
	}
	
	public void setClassWhitelist(Set<String> wl) {
		this.classWhitelist = wl;
	}
	
	public void setIUMLModifierManager(IUMLModifier mm) {
		this.iumlmod = mm;
	}
	
	public void visit(IClass c){};
	public void visit(IDataManager d){};
	public void visit(IField f){};
	public void visit(IMethod m){};
	public void midVisit(IClass c){};
	public void postVisit(IClass c){};
	public void postVisit(IDataManager d){};
	

}
