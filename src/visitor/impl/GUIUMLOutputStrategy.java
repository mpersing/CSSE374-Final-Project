package visitor.impl;

import java.util.Set;

import data.api.IUMLModifier;

public class GUIUMLOutputStrategy extends UMLOutputStrategy {

	public void addOutputVisitor(OutputVisitor v, IUMLModifier umlModMan, Set<String> whitelist) {
		v.setClassWhitelist(umlModMan.getWhitelist());
		v.setIUMLModifierManager(umlModMan);
		this.visitors.add(v);
	}
}
