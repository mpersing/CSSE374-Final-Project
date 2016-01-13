package data.impl;

import java.io.IOException;

import data.api.AddStrategy;

public class UMLAddStrategy extends AddStrategy {

	@Override
	public void add(String[] toAdd) throws IOException {
		dataManager.addClass(toAdd[0]);
		
	}

}
