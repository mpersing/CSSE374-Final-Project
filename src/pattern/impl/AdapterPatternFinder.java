package pattern.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pattern.api.IPatternFinder;
import data.api.IClass;
import data.api.IField;
import data.api.IUMLModifierManager;

public class AdapterPatternFinder implements IPatternFinder {

	Set<String> whiteList;
	
	String style = "style=filled, fillcolor=red,";
	String adapter = "\\<\\<Adapter\\>\\>";
	String target = "\\<\\<Target\\>\\>";
	String adaptee = "\\<\\<Adaptee\\>\\>";
	
	public AdapterPatternFinder(Set<String> whiteList){
		this.whiteList = whiteList;
	}
	
	@Override
	public void find(Map<String, IClass> classMap, IUMLModifierManager mm) {
		for (String key : classMap.keySet()){
			IClass c = classMap.get(key);
			List<IField> fs = c.getFields();
			String[] impls = c.getImplements();
			Set<String> assoc = c.getAssoc();
			assoc.addAll(c.getUses());
			assoc.retainAll(whiteList);
			if (fs.size() == 1 && impls.length == 1 && assoc.size() == 1){
				IField f = fs.get(0);
				String fType = f.getType();
				String implType = impls[0];
				Iterator<String> assocIter  = assoc.iterator();;
				String assocType = assocIter.next();
				if (fType.contains(assocType)){
					mm.addStyle(c.getName(), style);
					mm.setSubtext(c.getName(), adapter);
					
					mm.addStyle(implType, style);
					mm.setSubtext(implType, target);
					
					mm.addStyle(assocType, style);
					mm.setSubtext(assocType, adaptee);
				}
			}
		}
		
	}

}
