package pattern.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pattern.api.IPatternFinder;
import data.api.Cluster;
import data.api.IClass;
import data.api.IUMLModifier;
import data.impl.UMLCluster;
import data.impl.UMLModifier;

public class PackageClusterPatternFinder implements IPatternFinder {

	@Override
	public void find(Map<String, IClass> classMap, IUMLModifier mm) {
		Set<String> names = classMap.keySet();
		Map<String, Set<String>> clustersData = new HashMap<String, Set<String>>();
		
		// form data
		for (String className : names){
			int i = className.lastIndexOf(".");
			String name = className.substring(0, i);
			if (!clustersData.containsKey(name)){
				clustersData.put(name, new HashSet<String>());
			}
			clustersData.get(name).add(className);
		}
		
		// form clusters
		Set<String> clusterNames = clustersData.keySet();
		
		// for each cluster
		for (String clusterName: clusterNames){
			IUMLModifier mods = new UMLModifier();
			Cluster cluster = new UMLCluster();
			cluster.setName(clusterName);
			
			// for each class in the cluster
			for (String className : clustersData.get(clusterName)){
				cluster.addClass(className);
			}
			
			// Set style
			// No styles right now
			cluster.addStyle("");
			
			// Add the cluster
			mods.addCluster(cluster);
			mods.setDisplayName(clusterName);
			mm.addUMLModifier(mods);
		}
	}

	@Override
	public String getName() {
		return "Package";
	}

}
