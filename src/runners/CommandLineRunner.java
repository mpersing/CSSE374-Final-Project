package runners;

import java.io.IOException;

import visitor.impl.ClassOutputVisitor;
import visitor.impl.ExtendOutputVisitor;
import visitor.impl.ImplementOutputVisitor;
import data.api.IDataManager;
import data.impl.DataManager;

public class CommandLineRunner {

	public static void main(String[] args) throws IOException {
		IDataManager data = new DataManager();
		// TODO: add output visitors here
		data.addOutputVisitor(new ClassOutputVisitor());
		data.addOutputVisitor(new ExtendOutputVisitor());
		data.addOutputVisitor(new ImplementOutputVisitor());
		
		for(String s : args) {
			data.add(s);
		}
		
		StringBuffer sb = new StringBuffer();
		// TODO: call output		
		data.output(sb);
		
		System.out.println(sb.toString());
	}
}
