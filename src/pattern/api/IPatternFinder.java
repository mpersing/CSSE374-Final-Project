package pattern.api;

import java.util.Map;

import data.api.IClass;
import data.api.IUMLModifierManager;

public interface IPatternFinder {
	
	public void find(Map<String, IClass> classMap, IUMLModifierManager mm);

}
