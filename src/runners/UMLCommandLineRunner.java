package runners;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import data.api.IDataManager;
import data.api.IUMLModifier;
import data.impl.DataManager;
import data.impl.UMLAddStrategy;
import pattern.impl.AdapterPatternFinder;
import pattern.impl.CompositePatternFinder;
import pattern.impl.DecoratorPatternFinder;
import pattern.impl.PackageClusterPatternFinder;
import pattern.impl.SingletonPatternFinder;
import visitor.impl.AssocOutputVisitor;
import visitor.impl.ClassOutputVisitor;
import visitor.impl.ExtendOutputVisitor;
import visitor.impl.ImplementOutputVisitor;
import visitor.impl.UMLOutputStrategy;
import visitor.impl.UsesOutputVisitor;

public class UMLCommandLineRunner extends CommandLineRunner {
	public static void main(String[] args) throws IOException {
		IDataManager data = new DataManager();
		IUMLModifier modMan = data.getUMLModifierManager();
		Set<String> whiteList = data.getWhitelist();
		
		// Set the adding strategy
		data.setAddStrategy(new UMLAddStrategy());
		
		// Add all of the pattern finders
		data.addPatternFinder(new SingletonPatternFinder());
		data.addPatternFinder(new PackageClusterPatternFinder());
		data.addPatternFinder(new DecoratorPatternFinder());
		data.addPatternFinder(new AdapterPatternFinder(whiteList));
		data.addPatternFinder(new CompositePatternFinder());
		
		// Create the output strategy
		UMLOutputStrategy outStrat = new UMLOutputStrategy();
		outStrat.setDataManager(data);

		// Add all of the output visitors for that output strategy
		outStrat.addOutputVisitor(new ClassOutputVisitor(), modMan, whiteList);
		outStrat.addOutputVisitor(new ExtendOutputVisitor(), modMan, whiteList);
		outStrat.addOutputVisitor(new ImplementOutputVisitor(), modMan, whiteList);
		outStrat.addOutputVisitor(new AssocOutputVisitor(), modMan, whiteList);
		outStrat.addOutputVisitor(new UsesOutputVisitor(), modMan, whiteList);
		
		// Add the output strategy
		data.setOutputStrategy(outStrat);
		
		for(String s : args) {
			System.out.println(s);
			data.add(new String[]{s});
		}
		
		data.findAllPatterns();
		
		StringBuffer sb = new StringBuffer();
		data.output(sb);
		
		System.out.println(sb.toString());
		
		BufferedWriter bwr = new BufferedWriter(new FileWriter(new File("milestone6Automatic.gv")));
        
        //write contents of StringBuffer to a file
        bwr.write(sb.toString());
       
        //flush the stream
        bwr.flush();
       
        //close the stream
        bwr.close();
        
        // runs the dot
        CommandLineRunner.runApplication("dot", "-Tpng milestone6Automatic.gv -o milestone6Automatic.png");
	}
}
