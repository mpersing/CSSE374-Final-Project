package data.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;

import pattern.api.IPatternFinder;
import visitor.api.IOutputStrategy;
import visitor.api.ITraverser;

public interface IDataManager extends ITraverser{
	
	public void add(String[] toAdd) throws IOException;
	public void addClass(String toAdd, InputStream is) throws IOException;
	public void addClass(String toAdd) throws IOException;
	public void output(StringBuffer sb);
	public void findAllPatterns();
	public void runPhase(String patternName);
	
	public Collection<IClass> getClasses();
	public void setOutputStrategy(IOutputStrategy outputStrategy);
	public void setAddStrategy(AddStrategy addStrat);
	public IClass getClass(String s);
	public Set<String> getWhitelist();
	public IUMLModifier getUMLModifierManager();
	public void addPatternFinder(IPatternFinder pf);
	public void addPatternFinderPhase(String patternName, IPatternFinder pf);
	
}
