package pattern.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pattern.api.IPatternFinder;
import data.api.IClass;
import data.api.IField;
import data.api.IMethod;
import data.api.IUMLModifierManager;

public class AdapterPatternFinder implements IPatternFinder {

	Set<String> whiteList;
	
	private static final String style = "style=filled, fillcolor=red,";
	private static final String adapterSub = "\\<\\<Adapter\\>\\>";
	private static final String targetSub = "\\<\\<Target\\>\\>";
	private static final String adapteeSub = "\\<\\<Adaptee\\>\\>";
	
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
			
			if (	fs.size() == 1 &&
					impls.length == 1 &&
					assoc.size() == 1){
				
				IField f = fs.get(0);
				if (!f.isPrivate() && !f.isProtected()) continue;
				
				String fType = f.getType();
				String implType = impls[0];
				Iterator<String> assocIter  = assoc.iterator();;
				String assocType = assocIter.next();
				
				if (!fType.contains(c.getName())){
					
					Collection<IMethod> ms = c.getMethods();
					Iterator<IMethod> msIter = ms.iterator();
					IMethod m;
					while (msIter.hasNext()){
						m = msIter.next();
						String name = m.getName();
						if (name.equals("<init>")){
							String[] args = m.getArguments();
							if (args.length == 1){
								String argType = args[0];
								if (fType.contains(argType)){
									this.styleClasses(mm, c.getName(), implType, assocType);
								}
							}
						}
					}
				}
			}
		}
	}

	private void styleClasses(IUMLModifierManager mm, String adapterClass, String targetClass, String adapteeSub){
		mm.addStyle(adapterClass, AdapterPatternFinder.style);
		mm.setSubtext(adapterClass, AdapterPatternFinder.adapterSub);
		
		mm.addStyle(targetClass, AdapterPatternFinder.style);
		mm.setSubtext(targetClass, AdapterPatternFinder.targetSub);
		
		mm.addStyle(adapteeSub, AdapterPatternFinder.style);
		mm.setSubtext(adapteeSub, AdapterPatternFinder.adapteeSub);
	}
	
}
