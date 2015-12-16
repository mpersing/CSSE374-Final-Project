package data.impl;

import data.api.IField;

public class Field extends data.impl.Element implements IField {

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

}
