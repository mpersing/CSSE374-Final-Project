package pattern.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import data.api.IClass;
import data.api.IField;
import data.api.IUMLModifierManager;
import pattern.api.IPatternFinder;

public class CompositePatternFinder implements IPatternFinder {
	
	enum SET_TYPE {
		PUBLIC,
		PRIVATE
	}

	private static final String style = "style=filled, fillcolor=yellow,";
	private static final String componentSub = "\\<\\<Component\\>\\>";
	private static final String compositeSub = "\\<\\<Composite\\>\\>";
	private static final String leafSub = "\\<\\<Leaf\\>\\>";

	@Override
	public void find(Map<String, IClass> classMap, IUMLModifierManager mm) {
		for(IClass c : classMap.values()) {
			if(mm.getSubtext(c.getName()).contains("Comp")) { // avoid composite + component duplicates
				continue;
			}
			Set<String> multiFields = findMultiFields(c);
			boolean foundMatch = searchForMatch(classMap, multiFields, c.getName(), mm);
			if(foundMatch) {
				mm.setSubtext(c.getName(), compositeSub);
				mm.addStyle(c.getName(), style);
				findKids(classMap, c, mm);
			}
		}
		markLeaves(classMap, mm);
	}
	
	

	private void markLeaves(Map<String, IClass> classMap, IUMLModifierManager mm) {
		for(IClass c: classMap.values()) {
			String sub = mm.getSubtext(c.getName());
			if(sub.contains("Composite") || sub.contains("Component")) {
				continue;
			}
			if(hasLeafOrComponentParent(classMap, c.getName(), mm)) {
				mm.addStyle(c.getName(), style);
				mm.setSubtext(c.getName(), leafSub);
			}
		}
	}

	private boolean hasLeafOrComponentParent(Map<String, IClass> classMap, String clas, IUMLModifierManager mm) {
		String sub = mm.getSubtext(clas);
		if(sub.contains("Component")||sub.contains("Leaf")) {
			return true;
		}
		IClass c = classMap.get(clas);
		if(c == null) {
			return false;
		}
		boolean found = false;
		for(String cl : c.getImplements()) {
			found |= hasLeafOrComponentParent(classMap, cl, mm);
		}
		found |= hasLeafOrComponentParent(classMap, c.getExtends(), mm);
		return found;
	}



	private void findKids(Map<String, IClass> classMap, IClass cl, IUMLModifierManager mm) {
		String name = cl.getName();
		for(IClass c : classMap.values()) {
			boolean found = false;
			found |= c.getExtends().equals(name);
			String[] impl = c.getImplements();
			for(int i = 0 ; i < impl.length ; ++i) {
				found |= impl[i].equals(name);
			}
			if(found) {
				if(mm.getSubtext(c.getName()).contains("Comp")) { // avoid composite + component duplicates
					continue;
				}
				mm.setSubtext(c.getName(), compositeSub);
				mm.addStyle(c.getName(), style);
				findKids(classMap, c, mm);
			}
		}
	}

	private boolean searchForMatch(Map<String, IClass> classMap, Set<String> multiFields, String clas, IUMLModifierManager mm) {
		IClass c = classMap.get(clas);
		if(c == null) {
			return false;
		}
		if(multiFields.contains(c.getName())) {
			if(!mm.getSubtext(c.getName()).contains("Comp")) { // avoid composite + component duplicates
				mm.setSubtext(clas, componentSub);
				mm.addStyle(clas, style);
			}
			return true;
		}
		boolean found = false;
		for(String cl : c.getImplements()) {
			found |= searchForMatch(classMap, multiFields, cl, mm);
		}
		found |= searchForMatch(classMap, multiFields, c.getExtends(), mm);
		return found;
	}

	private Set<String> findMultiFields(IClass c) {
		Set<String> multiFields = new HashSet<String>();
		Set<String> singleFields = new HashSet<String>();
		for(IField f : c.getFields()) {
			String type = f.getType();
			if(type.contains(">")) {
				type = type.substring((type.contains(",") ? type.lastIndexOf(',') : type.lastIndexOf('<')) + 1, type.lastIndexOf('>') - 1);
				multiFields.add(type);
			} else if(type.contains("[]")) {
				type = type.replace("[]", "");
				multiFields.add(type);
			} else if(singleFields.contains(type)) {
				multiFields.add(type);
			} else {
				singleFields.add(type);
			}
		}		
		return multiFields;
	}

}
