package data.api;

import visitor.api.ITraverser;

public interface IDataManager extends ITraverser{
	
	public String output(StringBuffer sb);
	
}
