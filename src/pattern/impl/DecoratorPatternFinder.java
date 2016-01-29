package pattern.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import data.api.IClass;
import data.api.IUMLModifierManager;
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
	public void find(Map<String, IClass> classMap, IUMLModifierManager mm) {
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
			if(!compName.equals("")) {
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
