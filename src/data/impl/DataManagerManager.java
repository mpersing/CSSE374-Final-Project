package data.impl;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import data.api.IDataManager;
import data.api.IUMLModifier;
import gui.Configuration;
import gui.PhaseData;
import visitor.impl.UMLOutputStrategy;

public class DataManagerManager {
	private IDataManager data;
	private IUMLModifier modMan;
	private UMLOutputStrategy outStrat;
	public Configuration config;
	private int progressInt;
	private String progressText;
		
	public DataManagerManager(){
		this.data = new DataManager();
		this.modMan = data.getUMLModifierManager();
		this.outStrat = new UMLOutputStrategy();
		this.outStrat.setDataManager(this.data);
		this.data.setOutputStrategy(this.outStrat);
		
		this.data.setAddStrategy(new UMLAddStrategy());
	}
	
	public boolean loadConfig(File f){
		boolean flag = false;
		this.config = new Configuration(f);
		flag = true;
		return flag;
	}
	
	public void analyze(){
		String[] phases = this.config.getPhases();
		
		// load each phase
		for (int ip = 0; ip < phases.length; ip++){
			String p = phases[ip];
			
			PhaseData pData = this.config.getPhaseData(p);
			
			// load the classes
			if (pData.getName().equals("Loader")){
				String[] toLoad = this.config.getToLoad();
				for(int il = 0; il < toLoad.length; il++) {
					String s = toLoad[il];
					try {
						data.add(new String[]{s});
					} catch (IOException e) {
						e.printStackTrace();
					}
					this.progressInt = (int) (100.0*(il+1.0)/(toLoad.length*phases.length));
					this.progressText = "Loading class " + pData.getName();
				}
			} else {
				// run all the other phases
				this.data.runPhase(pData.getName());
				this.progressInt = (int) (100.0*(ip+1)/phases.length);
				this.progressText = "Running phase " + pData.getName();
			}
		}
	}
	
	public int getProgressInt(){
		return this.progressInt;
	}
	
	public String getProgressText(){
		return this.progressText;
	}
	/* *
	
	// this.data.addPatternFinder(finder);
	
	// outStrat.setDataManager(data);

	//outStrat.addOutputVisitor(new ClassOutputVisitor(), modMan, whiteList);
	
	//data.setOutputStrategy(outStrat);
	
	// StringBuffer sb = new StringBuffer();
	// data.output(sb);
	
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
    /* */
    
}
