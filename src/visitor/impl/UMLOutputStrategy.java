package visitor.impl;

import java.util.ArrayList;
import java.util.Set;

import visitor.api.IOutputStrategy;
import data.api.Cluster;
import data.api.IDataManager;
import data.api.IUMLModifier;

public class UMLOutputStrategy implements IOutputStrategy{
	
	private ArrayList<OutputVisitor> visitors;
	private IDataManager dm;
	
	public UMLOutputStrategy() {
		this.visitors = new ArrayList<OutputVisitor>();
	}
	
	public void output(StringBuffer sb){
		// Start the digraph
		this.preVisit(sb);
		
		// Add all the classes
		for(OutputVisitor v : this.visitors){
			v.setStringBuffer(sb);
			this.dm.accept(v);
		}
		
		// Add all relations
		for(OutputVisitor v : this.visitors){
			v.postVisit(this.dm);
		}
		
		// Add all of the clusters
		IUMLModifier modMan = dm.getUMLModifierManager();
		for (Cluster cluster : modMan.getClusters()){
			sb.append(cluster.toString());
		}
		sb.append("\n");
		
		this.postVisit(sb);
	}
	
	public void addOutputVisitor(OutputVisitor v, IUMLModifier umlModMan, Set<String> whitelist) {
		v.setClassWhitelist(whitelist);
		v.setIUMLModifierManager(umlModMan);
		this.visitors.add(v);
	}
	
	public void setDataManager(IDataManager dm) {
		this.dm = dm;
	}
	
	public void preVisit(StringBuffer sb){
		sb.append("digraph g {\n");
	}
	
	public void postVisit(StringBuffer sb){
		sb.append("}\n");
	}


}
