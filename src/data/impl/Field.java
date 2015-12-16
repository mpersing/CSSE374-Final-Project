package data.impl;

import visitor.api.ITraverser;
import visitor.impl.OutputVisitor;
import data.api.IField;

public class Field extends data.impl.Element implements IField, ITraverser {

	private String name;
	private String type;
	
	@Override
	public void setName(String n) {
		this.name = n;
	}

	@Override
	public void setType(String t) {
		this.type = t;
	}

	@Override
	public void accept(OutputVisitor v) {
		v.visit(this);
	}

}
