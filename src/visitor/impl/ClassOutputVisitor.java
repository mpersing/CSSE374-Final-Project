package visitor.impl;

import data.api.IClass;

public class ClassOutputVisitor extends OutputVisitor {

	/* Format:
	 Animal [
     	label = "{Animal|+ name : string\l+ age : int\l|+ die() : void\l}"
     ]
	 */
	
	@Override
	public void visit(IClass c){
		// Get the name
		this.sb.append("");
	};
	
}
