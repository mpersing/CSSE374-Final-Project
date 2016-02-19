package data.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.Class;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import data.api.IDataManager;
import data.api.IUMLModifier;
import gui.Configuration;
import gui.PhaseData;
import pattern.api.IPatternFinder;
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
				List<Map<String,File>> dirMapList = new ArrayList<Map<String,File>>();
				String[] toLoad = this.config.getInputFolderPath();
				int totalClassesToLoad = 0;
				for(int i = 0 ; i < toLoad.length ; ++i) {
					this.progressText = "Parsing directory " + toLoad[i];
					Map<String,File> dirMap = this.getAllClasses(toLoad[i]);
					dirMapList.add(dirMap);
					totalClassesToLoad += dirMap.size();
				}
				toLoad = this.config.getToLoad();
				totalClassesToLoad += toLoad.length;
				int classesLoaded = 0;
				for(int il = 0; il < toLoad.length; il++) {
					String s = toLoad[il];
					try {
						data.add(new String[]{s});
					} catch (IOException e) {
						e.printStackTrace();
					}
					this.progressInt = (int) (100.0*(++classesLoaded)/(totalClassesToLoad*phases.length));
					this.progressText = "Loading class " + pData.getName();
				}
				for(Map<String,File> dirMap : dirMapList) {
					for(Entry<String, File> e : dirMap.entrySet()) {
						try {
							data.addClass(e.getKey(), new FileInputStream(e.getValue()));
							this.progressInt = (int) (100.0*(++classesLoaded)/(totalClassesToLoad*phases.length));
							this.progressText = "Loading class " + e.getKey();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			} else {
				// set the phase data
				String className = pData.getName();
				try {
					Class c = Class.forName(className);
					Constructor cons;
					IPatternFinder pf;
					if(pData.getArgs() != null && pData.getArgs().length > 0) {
						cons = c.getConstructor(new Class[]{pData.getArgs().getClass()});
						pf = (IPatternFinder) cons.newInstance(pData.getArgs());
					} else {
						cons = c.getConstructor(new Class[]{});
						pf = (IPatternFinder) cons.newInstance();
					}
					this.data.addPatternFinderPhase(p, pf);
				} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
				// run all the other phases
				this.data.runPhase(p);
				this.progressInt = (int) (100.0*(ip+1)/phases.length);
				this.progressText = "Running phase " + pData.getName();
			}
		}
	}
	
	private Map<String,File> getAllClasses(String rootDir) {
		File[] arr = new File(rootDir).listFiles();
		Queue<File> toNavigate = new LinkedList<File>();
		Queue<String> toNavigateString = new LinkedList<String>();
		Map<String, File> classMap = new HashMap<String, File>();
		for(File f : arr) {
			toNavigate.add(f);
			toNavigateString.add("");
		}
		while(!toNavigate.isEmpty()) {
			File next = toNavigate.poll();
			String nextString = toNavigateString.poll();
			if(next.isDirectory()) {
				arr = next.listFiles();
				for(File f : arr) {
					toNavigate.add(f);
					toNavigateString.add(nextString + next.getName() + ".");
				}
			} else {
				if(next.getName().endsWith(".class")) {
					System.out.println(nextString + next.getName().substring(0,next.getName().length()-6));
					classMap.put(nextString + next.getName().substring(0,next.getName().length()-6), next);
				}
			}
		}
		return classMap;
	}
	
	public int getProgressInt(){
		return this.progressInt;
	}
	
	public String getProgressText(){
		return this.progressText;
	}
	
	public List<IUMLModifier> getList(){
		return this.modMan.getList();
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
