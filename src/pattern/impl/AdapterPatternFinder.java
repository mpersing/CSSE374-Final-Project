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
import data.api.IUMLModifier;
import data.impl.UMLModifier;

public class AdapterPatternFinder implements IPatternFinder {

	private static final String style = "style=filled, fillcolor=red,";
	private static final String adapterSub = "\\<\\<Adapter\\>\\>";
	private static final String targetSub = "\\<\\<Target\\>\\>";
	private static final String adapteeSub = "\\<\\<Adaptee\\>\\>";
	
	public AdapterPatternFinder(){
	}
	
	@Override
	public void find(Map<String, IClass> classMap, IUMLModifier mm) {
		for (String key : classMap.keySet()){
			IClass c = classMap.get(key);
			List<IField> fs = c.getFields();
			String[] impls = c.getImplements();
			Set<String> assoc = c.getAssoc();
			assoc.addAll(c.getUses());
			
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

	private void styleClasses(IUMLModifier mm, String adapterClass, String targetClass, String adapteeClass){
		if(targetClass.equals(adapteeClass)) {
			return;
		}
		IUMLModifier mods = new UMLModifier();
		mods.setDisplayName(adapteeClass);
		mods.addStyle(adapterClass, AdapterPatternFinder.style);
		mods.setSubtext(adapterClass, AdapterPatternFinder.adapterSub);
		
		mods.addStyle(targetClass, AdapterPatternFinder.style);
		mods.setSubtext(targetClass, AdapterPatternFinder.targetSub);
		
		mods.addStyle(adapteeClass, AdapterPatternFinder.style);
		mods.setSubtext(adapteeClass, AdapterPatternFinder.adapteeSub);
		mm.addUMLModifier(mods);
	}

	@Override
	public String getName() {
		return "Adapter";
	}
	
}
