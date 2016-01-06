package visitor.impl;

import visitor.api.IOutputStrategy;

public class OutputStrategy implements IOutputStrategy{
	
	public void preVisit(StringBuffer sb){
		sb.append("digraph g {\n");
	}
	
	public void postVisit(StringBuffer sb){
		sb.append("}\n");
	}

}
