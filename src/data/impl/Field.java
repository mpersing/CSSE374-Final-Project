package data.impl;

import visitor.impl.OutputVisitor;
import data.api.IField;

public class Field extends Element implements IField {

	private String type;

	@Override
	public void setType(String t) {
		this.type = t;
	}

	@Override
	public void accept(OutputVisitor v) {
		v.visit(this);
	}

	@Override
	public String getType() {
		return this.type;
	}

}
