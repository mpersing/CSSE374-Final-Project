package data.impl;

import java.io.IOException;

import data.api.AddStrategy;

public class SDAddStrategy extends AddStrategy {

	@Override
	public void add(String[] toAdd) throws IOException {
		dataManager.addClass(toAdd[0]);
		// findMethod();
		// recurseOnThatMethod();
	}

}