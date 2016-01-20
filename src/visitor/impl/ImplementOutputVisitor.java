package visitor.impl;

import data.api.IClass;
import data.api.IDataManager;

public class ImplementOutputVisitor extends OutputVisitor {
	/* Format:
	    edge [
	    		style = "dashed";
	            arrowhead = "empty"
	    ]
	 */
	@Override
	public void visit(IDataManager d){
		this.sb.append("\n        edge [\n                style = \"dashed\";\n                arrowhead = \"empty\"\n        ]\n");
	}
	
	/* Format:
	 	Class -> Interface
	 */
	@Override
	public void visit(IClass c){
		if (!classWhitelist.contains(c.getName())) return;
		if (c.isInterface()) return;
		
		String[] impls = c.getImplements();
		
		// If the class implements anything
		if (impls.length > 0){
			for (String i : impls){
				if (!classWhitelist.contains(i)) continue;
				
				this.sb.append("        ");
				this.sb.append("\"");
				this.sb.append(c.getName());
				this.sb.append("\"");
				this.sb.append(" -> ");
				this.sb.append("\"");
				this.sb.append(i);
				this.sb.append("\"");
				this.sb.append("\n");
			}
		}
	}
}
