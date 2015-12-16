package visitor.impl;

import data.api.IClass;
import data.api.IDataManager;
import data.api.IField;
import data.api.IInterface;
import data.api.IMethod;

public abstract class OutputVisitor {
	
	protected StringBuffer sb;
	
	public void setStringBuffer(StringBuffer sb){
		this.sb = sb;
	}
	
	public void visit(IClass c){};
	public void visit(IDataManager d){};
	public void visit(IField f){};
	public void visit(IInterface i){};
	public void visit(IMethod m){};
	

}
