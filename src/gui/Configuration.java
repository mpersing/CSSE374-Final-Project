package gui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Configuration {

	private String inputFolderPath;
	private String outputFolderPath;
	private String dotPath;
	private String[] toLoad;
	private Map<String, PhaseData> phaseData;
	private String[] phases;
	
	public Configuration(File f){
		// load everything in
		System.out.println("Config-ing");
		
		this.inputFolderPath = "";
		this.outputFolderPath = "";
		this.dotPath = "";
		this.toLoad = new String[]{};
		this.phaseData = new HashMap<String, PhaseData>();
		this.phases = new String[]{};
	}
	
	public String[] getToLoad(){
		return toLoad;
	}
	
	public String getInputFolderPath(){
		return this.inputFolderPath;
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
