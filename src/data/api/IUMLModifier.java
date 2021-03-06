package data.api;

import java.util.List;
import java.util.Set;

import data.impl.UMLCluster;

public interface IUMLModifier {

	public void setEnabled(boolean enabled);
	public boolean getEnabled();
	
	public String getDisplayName();
	public void setDisplayName(String newName);
	
	public Set<String> getWhitelist();
	public List<IUMLModifier> getList();
	public void addUMLModifier(IUMLModifier toAdd);
	
	public String getStyle(String className);
	public void addStyle(String className, String style);
	public String getSubtext(String className);
	public void setSubtext(String className, String subtext);
	public String getArrowColor(String source, String dest, String type);
	public void setArrowColor(String source, String dest, String type, String newColor);
	public List<UMLCluster> getClusters();
	public void addCluster(Cluster cluster);
	
	
}
