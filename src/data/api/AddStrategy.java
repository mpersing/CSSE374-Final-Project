package data.api;

import java.io.IOException;

public abstract class AddStrategy {
	protected IDataManager dataManager; 
	
	public void add(String[] toAdd) throws IOException	{};
	
	public void setDataManager(IDataManager dataManager){
		this.dataManager = dataManager;
	}
}