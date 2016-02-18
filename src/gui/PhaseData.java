package gui;

public class PhaseData {
	private String name;
	private String[] args;
	
	public PhaseData(String name, String[] args){
		this.name = name;
		this.args = args;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String[] getArgs(){
		return this.args;
	}
}