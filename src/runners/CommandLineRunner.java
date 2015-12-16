package runners;

import java.io.IOException;

import data.api.IDataManager;
import data.impl.DataManager;

public class CommandLineRunner {

	public static void main(String[] args) throws IOException {
		IDataManager data = new DataManager();
		// TODO: add output visitors here
		for(String s : args) {
			data.add(s);
		}
		// TODO: call output		
	}
}
