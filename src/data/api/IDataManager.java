package data.api;

import java.io.IOException;
import java.util.ArrayList;

import visitor.api.IOutputStrategy;
import visitor.api.ITraverser;
import visitor.impl.OutputVisitor;

public interface IDataManager extends ITraverser{
	
	public void add(String[] toAdd) throws IOException;
	public void addClass(String toAdd) throws IOException;
	public void addOutputVisitor(OutputVisitor v);
	public void output(StringBuffer sb);
	
	public ArrayList<IClass> getClasses();
	public void setOutputStrategy(IOutputStrategy outputStrategy);
	public void setAddStrategy(AddStrategy addStrat);
	
}
