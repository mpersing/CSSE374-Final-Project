package runners;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import data.api.IDataManager;
import data.api.IUMLModifierManager;
import data.impl.DataManager;
import data.impl.UMLAddStrategy;
import visitor.impl.AssocOutputVisitor;
import visitor.impl.ClassOutputVisitor;
import visitor.impl.ExtendOutputVisitor;
import visitor.impl.ImplementOutputVisitor;
import visitor.impl.UMLOutputStrategy;
import visitor.impl.UsesOutputVisitor;

public class UMLCommandLineRunner extends CommandLineRunner {
	public static void main(String[] args) throws IOException {
		IDataManager data = new DataManager();
		IUMLModifierManager modMan = data.getUMLModifierManagers();
		Set<String> whiteList = data.getWhitelist();
		
		data.setAddStrategy(new UMLAddStrategy());
		
		UMLOutputStrategy outStrat = new UMLOutputStrategy();
		outStrat.setDataManager(data);
		
		
		outStrat.addOutputVisitor(new ClassOutputVisitor(), modMan, whiteList);
		outStrat.addOutputVisitor(new ExtendOutputVisitor(), modMan, whiteList);
		outStrat.addOutputVisitor(new ImplementOutputVisitor(), modMan, whiteList);
		outStrat.addOutputVisitor(new AssocOutputVisitor(), modMan, whiteList);
		outStrat.addOutputVisitor(new UsesOutputVisitor(), modMan, whiteList);
		
		data.setOutputStrategy(outStrat);
		
		for(String s : args) {
			data.add(new String[]{s});
		}
		
		StringBuffer sb = new StringBuffer();
		data.output(sb);
		
		System.out.println(sb.toString());
		
		BufferedWriter bwr = new BufferedWriter(new FileWriter(new File("milestone4Automatic.gv")));
        
        //write contents of StringBuffer to a file
        bwr.write(sb.toString());
       
        //flush the stream
        bwr.flush();
       
        //close the stream
        bwr.close();
        
        // runs the dot
        CommandLineRunner.runApplication("dot", "-Tpng milestone4Automatic.gv -o milestone4Automatic.png");
	}
}
