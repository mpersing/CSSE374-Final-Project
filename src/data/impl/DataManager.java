package data.impl;

import java.util.ArrayList;

import visitor.api.ITraverser;
import visitor.impl.OutputVisitor;
import data.api.IClass;
import data.api.IDataManager;

public class DataManager implements IDataManager, ITraverser {

	private ArrayList<IClass> classes;
	
	public DataManager(){
		this.classes = new ArrayList<IClass>();
	}
	
	public void addClass(IClass c){
		this.classes.add(c);
	}
	
	@Override
	public String output(StringBuffer sb) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void accept(OutputVisitor v) {
		v.visit(this);
		for (IClass c: this.classes){
			c.accept(v);
		}
	}

}
