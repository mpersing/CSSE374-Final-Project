package data.api;

import visitor.api.ITraverser;

public interface IField extends IElement, ITraverser {

	public void setName(String n);
	public void setType(String t);
	
}
