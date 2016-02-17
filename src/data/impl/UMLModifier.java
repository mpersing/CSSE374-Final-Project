package data.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import data.api.Cluster;
import data.api.IUMLModifier;

public class UMLModifier implements IUMLModifier {

	private List<UMLCluster> clusters;
	private Map<String, String> stylings;
	private Map<String, String> subtexts;

	public UMLModifier(){
		clusters = new ArrayList<UMLCluster>();
		stylings = new HashMap<String, String>();
		subtexts = new HashMap<String, String>();
	}

	@Override
	public String getStyle(String className) {
		String result = "";
		
		if (stylings.containsKey(className)){
			result = stylings.get(className);
		}
		
		return result;
	}
	
	@Override
	public void addStyle(String className, String style) {
		if (!stylings.containsKey(className)){
			stylings.put(className, style);
		} else {
			String prevStyle = stylings.get(className);
			String newStyle = prevStyle + " " + style;
			stylings.put(className, newStyle);
		}
	}

	@Override
	public String getSubtext(String className) {
		String result = "";
		
		if (subtexts.containsKey(className)){
			result = subtexts.get(className);
		}
		
		return result;
	}

	@Override
	public void setSubtext(String className, String subtext) {
		if (!subtexts.containsKey(className)){
			subtexts.put(className, subtext);
		} else {
			String prevSub = subtexts.get(className);
			String newSub = prevSub + " " + subtext;
			subtexts.put(className, newSub);
		}
	}
	
	@Override
	public List<UMLCluster> getClusters() {
		return this.clusters;
	}

	@Override
	public void addCluster(Cluster cluster) {
		this.clusters.add((UMLCluster) cluster);
	}

	@Override
	public void setEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getWhitelist() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IUMLModifier> getList() {
		// TODO Auto-generated method stub
		return null;
	}
}
