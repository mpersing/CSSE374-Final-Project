package runners;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import data.api.IDataManager;
import data.impl.DataManager;
import data.impl.SDAddStrategy;
import visitor.impl.SDOutputStrategy;

public class SDCommandLineRunner extends CommandLineRunner {
	public static void main(String[] args) throws IOException {
		IDataManager data = new DataManager();
		
		data.setAddStrategy(new SDAddStrategy());
		
		SDOutputStrategy outStrat = new SDOutputStrategy();
		outStrat.setDataManager(data);
		if(args.length == 2) {
			outStrat.setRoot(args[0], args[1], 5);
		} else if(args.length == 3) {
			outStrat.setRoot(args[0], args[1], Integer.parseInt(args[2]));
		} else {
			return;
		}
		data.setOutputStrategy(outStrat);
		
		data.add(args);
		
		StringBuffer sb = new StringBuffer();
		data.output(sb);
		
		System.out.println(sb.toString());
		
		BufferedWriter bwr = new BufferedWriter(new FileWriter(new File("milestone3AutomaticSD.sd")));
        
        //write contents of StringBuffer to a file
        bwr.write(sb.toString());
       
        //flush the stream
        bwr.flush();
       
        //close the stream
        bwr.close();
        
        // runs the generator
        CommandLineRunner.runApplication("java", "-jar sdedit-4.01.jar -o milestone3AutomaticSD.png -t png milestone3AutomaticSD.sd");
	}
}
