package data.impl;

import data.api.Cluster;

public class UMLCluster extends Cluster {

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		// Start with name
		sb.append("        ");
		sb.append("subgraph \"cluster");
		sb.append(this.name);
		sb.append("\" {\n");
		
		// Add style
		sb.append("                label=\"");
		sb.append(this.name);
		sb.append("\"\n");
		
		// Add classes in cluster
		for (String className : classNames){
			sb.append("                \"");
			sb.append(className);
			sb.append("\";\n");
		}
		
		// Close the subgraph
		sb.append("        }\n");
		
		return sb.toString();
	}
}