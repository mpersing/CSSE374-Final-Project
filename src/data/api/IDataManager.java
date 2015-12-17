package data.api;

import java.io.IOException;

import visitor.api.ITraverser;
import visitor.impl.OutputVisitor;

public interface IDataManager extends ITraverser{
	
	public void add(String toAdd) throws IOException;
	public void addOutputVisitor(OutputVisitor v);
	public void output(StringBuffer sb);
	
}
