package data.api;

import java.util.List;

import data.impl.UMLCluster;

public interface IUMLModifierManager {

	public String getStyle(String className);
	public void addStyle(String className, String style);
	public String getSubtext(String className);
	public void setSubtext(String className, String subtext);
	public List<UMLCluster> getClusters();
	public void addCluster(Cluster cluster);
	
	
}
