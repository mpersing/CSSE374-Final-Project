package data.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import data.api.Cluster;
import data.api.IUMLModifier;

public class UMLModifierComposite implements IUMLModifier {

	private List<IUMLModifier> list;
	
	public UMLModifierComposite() {
		this.list = new ArrayList<IUMLModifier>();
	}
	
	@Override
	public String getStyle(String className) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addStyle(String className, String style) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getSubtext(String className) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSubtext(String className, String subtext) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<UMLCluster> getClusters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCluster(Cluster cluster) {
		// TODO Auto-generated method stub

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

	@Override
	public void setDisplayName(String newName) {
		// TODO Auto-generated method stub
		
	}

}
