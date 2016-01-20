package data.impl;

import java.util.List;
import java.util.Map;

import data.api.IUMLModifierManager;

public class UMLModifierManager implements IUMLModifierManager {

	private List<UMLCluster> clusters;
	private Map<String, String> stylings;
	private Map<String, String> subtexts;

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
	public String getStyle(String className) {
		// TODO Auto-generated method stub
		return null;
	}

}
