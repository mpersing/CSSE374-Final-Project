package softwareDesignFinal;

import java.util.ArrayList;

public class Method {
	private class Parameter {
		public String Type, Name;
	}
	
	String name, returnType, accessMod, fancyMod, code;
	ArrayList<Parameter> paramList;
	public Method(String name, String returnType, String accessMod, String fancyMod, String code,
			ArrayList<Parameter> paramList) {
		super();
		this.name = name;
		this.returnType = returnType;
		this.accessMod = accessMod;
		this.fancyMod = fancyMod;
		this.code = code;
		this.paramList = paramList;
	}
	
}
