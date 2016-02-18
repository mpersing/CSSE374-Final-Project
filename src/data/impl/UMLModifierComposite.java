package data.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import data.api.Cluster;
import data.api.IUMLModifier;

public class UMLModifierComposite implements IUMLModifier {

	private List<IUMLModifier> list;
	private String displayName;
	private boolean enabled = false;
	
	public UMLModifierComposite() {
		this.list = new ArrayList<IUMLModifier>();
	}
	
	@Override
	public String getStyle(String className) {
		String result = "";
		for(IUMLModifier m : list) {
			result += m.getStyle(className);
		}
		return result;
	}

	@Override
	public void addStyle(String className, String style) {
		for(IUMLModifier m : list) {
			m.addStyle(className, style);
		}
	}

	@Override
	public String getSubtext(String className) {
		String result = "";
		for(IUMLModifier m : list) {
			result += m.getSubtext(className);
		}
		return result;
	}

	@Override
	public void setSubtext(String className, String subtext) {
		for(IUMLModifier m : list) {
			m.setSubtext(className, subtext);
		}
	}

	@Override
	public List<UMLCluster> getClusters() {
		List<UMLCluster> toReturn = new ArrayList<UMLCluster>();
		for(IUMLModifier m : list) {
			toReturn.addAll(m.getClusters());
		}
		return toReturn;
	}

	@Override
	public void addCluster(Cluster cluster) {
		for(IUMLModifier m : list) {
			m.addCluster(cluster);
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		for(IUMLModifier m : list) {
			m.setEnabled(enabled);
		}
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
		Set<String> wl = new HashSet<String>();
		for(IUMLModifier m : list) {
			wl.addAll(m.getWhitelist());
		}
		return wl;
	}

	@Override
	public List<IUMLModifier> getList() {
		return this.list;
	}

	@Override
	public void setDisplayName(String newName) {
		this.displayName = newName;
	}

}
