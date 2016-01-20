package visitor.impl;

import java.util.ArrayList;
import java.util.Set;

import data.api.IDataManager;
import data.api.IUMLModifierManager;
import visitor.api.IOutputStrategy;

public class UMLOutputStrategy implements IOutputStrategy{
	
	private ArrayList<OutputVisitor> visitors;
	private IDataManager dm;
	
	public UMLOutputStrategy() {
		this.visitors = new ArrayList<OutputVisitor>();
	}
	
	public void output(StringBuffer sb){
		this.preVisit(sb);
		
		for(OutputVisitor v : this.visitors){
			v.setStringBuffer(sb);
			this.dm.accept(v);
		}
		
		for(OutputVisitor v : this.visitors){
			v.postVisit(this.dm);
		}
		
		this.postVisit(sb);
	}
	
	public void addOutputVisitor(OutputVisitor v, IUMLModifierManager umlModMan, Set<String> whitelist) {
		this.visitors.add(v);
	}
	
	public void setDataManager(IDataManager dm) {
		this.dm = dm;
	}
	
	public void preVisit(StringBuffer sb){
		sb.append("digraph g {\n");
	}
	
	public void postVisit(StringBuffer sb){
		sb.append(" subgraph cluster_0 { \"data.api.IClass\"; \"data.api.IField\";\"java.util.Set\"}");
		sb.append("}\n");
	}


}
