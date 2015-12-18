package runners;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import visitor.impl.ClassOutputVisitor;
import visitor.impl.ExtendOutputVisitor;
import visitor.impl.ImplementOutputVisitor;
import data.api.IDataManager;
import data.impl.DataManager;

public class CommandLineRunner {
	
	public static void runApplication(String command, String arg) {

		// Run the application if support is available
		try {
			System.out.format("Launching %s ...%n", command);
			Runtime.getRuntime().exec("cmd /c start " + command + " " + arg);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		IDataManager data = new DataManager();
		data.addOutputVisitor(new ClassOutputVisitor());
		data.addOutputVisitor(new ExtendOutputVisitor());
		data.addOutputVisitor(new ImplementOutputVisitor());
		
		for(String s : args) {
			data.add(s);
		}
		
		StringBuffer sb = new StringBuffer();
		data.output(sb);
		
		System.out.println(sb.toString());
		
		BufferedWriter bwr = new BufferedWriter(new FileWriter(new File("test.gv")));
        
        //write contents of StringBuffer to a file
        bwr.write(sb.toString().replace('/', '_'));
       
        //flush the stream
        bwr.flush();
       
        //close the stream
        bwr.close();
        
        // runs the dot
        CommandLineRunner.runApplication("dot", "-Tpng test.gv -o graph1.png");
        
        System.out.println("done, I gues?");
	}
}
