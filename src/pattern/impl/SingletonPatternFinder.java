package pattern.impl;

import java.util.Map;

import data.api.IClass;
import data.api.IField;
import data.api.IMethod;
import data.api.IUMLModifier;
import data.impl.UMLModifier;
import pattern.api.IPatternFinder;

public class SingletonPatternFinder implements IPatternFinder {

	@Override
	public void find(Map<String, IClass> classMap, IUMLModifier mm) {
		String cName;
		boolean foundField;
		boolean foundMethod;
		boolean privateConstructors;
		for(IClass c : classMap.values()) {
			foundField = false;
			foundMethod = false;
			privateConstructors = true;
			cName = c.getName();
			for(IMethod m : c.getMethods()) {
				if(m.getReturnType().equals(cName) && m.isStatic() && m.isPublic()) {
					foundField = true;
					break;
				}
			}
			for(IField f : c.getFields()) {
				if(f.getType().equals(cName) && f.isPrivate() && f.isStatic()) {
					foundMethod = true;
					break;
				}
			}
			for(IMethod m : c.getMethods()) {
				if(m.getName().contains("<init>")) {
					privateConstructors |= m.isPrivate();
				}
			}
			if(foundMethod && foundField && privateConstructors) {
				IUMLModifier mods = new UMLModifier();
				mm.addUMLModifier(mods);
				mods.setDisplayName(c.getName());
				mods.setSubtext(cName, "\\<\\<Singleton\\>\\>");
				mods.addStyle(cName, "color=blue");
			}
		}
	}

	@Override
	public String getName() {
		return "Singleton";
	}

}
