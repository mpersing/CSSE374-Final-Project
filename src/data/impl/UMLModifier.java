package data.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import data.api.Cluster;
import data.api.IUMLModifier;

public class UMLModifier implements IUMLModifier {

	private List<UMLCluster> clusters;
	private Map<String, String> stylings;
	private Map<String, String> subtexts;
	private String displayName;
	private boolean enabled;

	public UMLModifier(){
		clusters = new ArrayList<UMLCluster>();
		stylings = new HashMap<String, String>();
		subtexts = new HashMap<String, String>();
		this.enabled = true;
		
	}

	@Override
	public String getStyle(String className) {
		String result = "";
		if(!enabled) {
			return result;
		}
		
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
		if(!enabled) {
			return result;
		}
		
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
		if(this.enabled) {
			return this.clusters;
		} else {
			return null;
		}
	}

	@Override
	public void addCluster(Cluster cluster) {
		this.clusters.add((UMLCluster) cluster);
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean getEnabled() {
		return this.enabled;
	}

	@Override
	public String getDisplayName() {
		return this.displayName;
	}

	@Override
	public Set<String> getWhitelist() {
		Set<String> whitelist = new HashSet<String>();
		if(this.enabled) {
			whitelist.addAll(this.stylings.keySet());
			whitelist.addAll(this.subtexts.keySet());
			for(Cluster c : this.clusters) {
				whitelist.addAll(c.getClasses());
			}
		}
		return whitelist;
	}

	@Override
	public List<IUMLModifier> getList() {
		return null;
	}

	@Override
	public void setDisplayName(String newName) {
		this.displayName = newName;
	}

	@Override
	public void addUMLModifier(IUMLModifier toAdd) {
		throw new UnsupportedOperationException();
	}
}
