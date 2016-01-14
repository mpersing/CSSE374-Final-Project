package visitor.impl;

import data.api.IDataManager;
import visitor.api.IOutputStrategy;

public class SDOutputStrategy implements IOutputStrategy{
	
	private IDataManager dm;
	
	private String rootClass;
	private String rootMethod;
	private int rootDepth;
	
	public void output(StringBuffer sb){
		this.preVisit(sb);
		
		
		
		this.postVisit(sb);
	}
	
	public void preVisit(StringBuffer sb){
		sb.append("digraph g {\n");
	}
	
	public void postVisit(StringBuffer sb){
		sb.append("}\n");
	}
	
	public void setRoot(String className, String methodSig, int depth) {
		this.rootClass = className;
		this.rootMethod = methodSig;
		this.rootDepth = depth;
	}
	
	public void setDataManager(IDataManager dm) {
		this.dm = dm;
	}

}
