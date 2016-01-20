package pattern.impl;

import java.util.Map;

import data.api.IClass;
import data.api.IField;
import data.api.IMethod;
import data.api.IUMLModifierManager;
import pattern.api.IPatternFinder;

public class SingletonPatternFinder implements IPatternFinder {

	@Override
	public void find(Map<String, IClass> classMap, IUMLModifierManager mm) {
		String cName;
		boolean foundField;
		boolean foundMethod;
		for(IClass c : classMap.values()) {
			foundField = false;
			foundMethod = false;
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
			if(foundMethod && foundField) {
				mm.setSubtext(cName, "\\<\\<Singleton\\>\\>");
				mm.addStyle(cName, "color=blue");
			}
		}
	}

}
