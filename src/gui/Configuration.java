package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Configuration {

	private String[] inputFolderPaths;
	private String outputFolderPath;
	private String dotPath;
	private String[] toLoad;
	private Map<String, PhaseData> phaseData;
	private String[] phases;
	
	public Configuration(File f){
		// load everything in
		System.out.println("Config-ing");
		
		this.inputFolderPaths = new String[]{};
		this.outputFolderPath = "";
		this.dotPath = "";
		this.toLoad = new String[]{};
		this.phaseData = new HashMap<String, PhaseData>();
		this.phases = new String[]{};
		
		BufferedReader r;
		try {
			r = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			
			
			String line;
			while ((line = r.readLine()) != null){
				if (line.length() > 0){
					int i = line.indexOf(":");
					String name = line.substring(0, i);
					String other = line.substring(i+2);
					switch(name){
					case "Input-Folder":
						this.inputFolderPaths = other.split("\\|");
						break;
					
					case "Input-Classes":
						this.toLoad = other.split("\\|");
						break;
					
					case "Output-Folder":
						this.outputFolderPath = other;
						break;
					
					case "Dot-Path":
						this.dotPath = other;
						break;
					
					case "Phases":
						this.phases = other.split("\\|");
						this.phaseData.clear();
						for (String s : this.phases){
							this.phaseData.put(s, new PhaseData(s,null));
						}
						break;
						
					// catch the phases
					default:
						if (this.phaseData.containsKey(name)){
							int iArg = other.indexOf("{");
							String className = "";
							String argString = "";
							String[] args;
							// arguments given
							if (iArg > 0){
								System.out.println(other);
								className = other.substring(0, iArg-1);
								argString = other.substring(iArg+1, other.length()-1);
								args = argString.split("\\|");
							} else {
								// no arguments
								className = other;
								argString = "";
								args = null;
							}
							PhaseData pd = new PhaseData(className, args);
							this.phaseData.put(name, pd);
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String[] getToLoad(){
		return toLoad;
	}
	
	public String[] getInputFolderPath(){
		return this.inputFolderPaths;
	}
	
	public String getOutputFolderPath(){
		return this.outputFolderPath;
	}
	
	public String getDotPath(){
		return this.dotPath;
	}
	
	public String[] getPhases(){
		return this.phases;
	}
	
	public PhaseData getPhaseData(String phaseName){
		PhaseData result = null;
		if (this.phaseData.containsKey(phaseName)){
			result = this.phaseData.get(phaseName);
		}
		return result;
	}
	
	
}
