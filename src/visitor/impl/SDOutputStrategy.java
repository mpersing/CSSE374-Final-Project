package visitor.impl;

import visitor.api.IOutputStrategy;

public class SDOutputStrategy implements IOutputStrategy{
	
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

}
