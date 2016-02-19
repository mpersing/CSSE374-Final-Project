package visitor.impl;

import java.util.Set;

import data.api.IUMLModifier;

public class GUIUMLOutputStrategy extends UMLOutputStrategy {
	
	private IUMLModifier umlModMan;
	
	public void output(StringBuffer sb) {
		for(OutputVisitor v : this.visitors) {
			v.setClassWhitelist(umlModMan.getWhitelist());
		}
		super.output(sb);
	}

	public void addOutputVisitor(OutputVisitor v, IUMLModifier umlModMan, Set<String> whitelist) {
		this.umlModMan = umlModMan;
		v.setClassWhitelist(umlModMan.getWhitelist());
		v.setIUMLModifierManager(umlModMan);
		this.visitors.add(v);
	}
}
