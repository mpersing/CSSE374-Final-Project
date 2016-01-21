package data.api;

import java.util.HashSet;
import java.util.Set;

public abstract class Cluster {

	protected Set<String> classNames;
	protected String name;
	protected String style;
	
	public Cluster(){
		classNames = new HashSet<String>();
		name = "";
		style = "";
	}
	
	public Set<String> getClasses() {
		return classNames;
	}

	public void addClass(String className) {
		this.classNames.add(className);
	}

	public void setClasses(Set<String> classNames) {
		this.classNames = classNames;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStyle() {
		return this.style;
	}

	public void addStyle(String style) {
		this.style += " " + style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
	public abstract String toString();
	
}
