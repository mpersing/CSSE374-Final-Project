package pattern.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import data.api.IClass;
import data.api.IField;
import data.api.IMethod;
import data.api.IUMLModifier;
import pattern.api.IPatternFinder;

public class DecoratorPatternFinder implements IPatternFinder {
	
	private void chainDetect(List<String> decors, Map<String, IClass> classMap, String toFind) {
		for(IClass c2 : classMap.values()) {
			if(c2.getExtends().equals(toFind)) {
				decors.add(c2.getName());
				this.chainDetect(decors, classMap, c2.getName());
			}
		}
	}

	@Override
	public void find(Map<String, IClass> classMap, IUMLModifier mm) {
		for(IClass c : classMap.values()) {
			String[] implement = c.getImplements();
			Set<String> assoc = c.getAssoc();
			String compName = "";
			for(int i = 0 ; i < implement.length ; ++i) {
				if(assoc.contains(implement[i])) {
					compName = implement[i];
					break;
				}
			}
			if(assoc.contains(c.getExtends())) {
				compName = c.getExtends();
			}
			for(IField f : c.getFields()) {
				if(f.getType().equals(c.getName())) {
					System.out.println(f.getType());
					compName = c.getName();
				}
			}
			if(!compName.equals("")) {
				boolean allConstructors = true;
				for(IMethod m : c.getMethods()) {
					boolean matchedArg = true;
					if(m.getName().contains("init")) {
						matchedArg = false;
						for(String s : m.getArguments()) {
							matchedArg |= s.equals(compName);
						}
					}
					allConstructors &= matchedArg;
				}
				if(allConstructors) {
					List<String> decors = new ArrayList<String>();
					decors.add(c.getName());
					this.chainDetect(decors, classMap, c.getName());
					mm.setSubtext(compName, "\\<\\<component\\>\\>");
					mm.addStyle(compName, "style=filled, fillcolor=green,");
					for(String s : decors) {
						mm.setSubtext(s, "\\<\\<decorator\\>\\>");
						mm.addStyle(s, "style=filled, fillcolor=green,");
					}
				}
			}
		}
	}

	@Override
	public String getName() {
		return "Decorator";
	}

}
