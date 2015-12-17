package data.api;

import visitor.api.ITraverser;

public interface IField extends IElement, ITraverser {

	public void setType(String t);
	public String getType();
	
}
