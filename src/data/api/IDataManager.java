package data.api;

import java.io.IOException;

import visitor.api.ITraverser;

public interface IDataManager extends ITraverser{
	
	public void add(String toAdd) throws IOException;
	public String output(StringBuffer sb);
	
}
