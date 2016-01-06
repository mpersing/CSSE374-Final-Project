package visitor.impl;

import data.api.IClass;
import data.api.IDataManager;

public class AssocOutputVisitor extends OutputVisitor {

	/* Format:
        edge [
                arrowhead = "empty"
        ]
	 */
	@Override
	public void visit(IDataManager d){
		this.sb.append("\n        edge [\n                style = \"solid\";\n                arrowhead = \"vee\"\n        ]\n");
	}
	
	/* Format:
	   Dog -> Animal
       Cat -> Animal
	 */
	@Override
	public void visit(IClass c) {
		
		// If the class composes or aggregates anything
		for (String e : c.getAssoc()){
			this.sb.append("        ");
			this.sb.append("\"");
			this.sb.append(c.getName());
			this.sb.append("\"");
			this.sb.append(" -> ");
			this.sb.append("\"");
			this.sb.append(e);
			this.sb.append("\"");
			this.sb.append("\n");
		}
	}
}
