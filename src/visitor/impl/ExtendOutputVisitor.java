package visitor.impl;

import data.api.IClass;
import data.api.IDataManager;

public class ExtendOutputVisitor extends OutputVisitor {

	/* Format:
        edge [
                arrowhead = "empty"
        ]
	 */
	@Override
	public void visit(IDataManager d){
		this.sb.append("\n        edge [\n                arrowhead = \"empty\"\n        ]\n");
	}
	
	/* Format:
	   Dog -> Animal
       Cat -> Animal
	 */
	@Override
	public void visit(IClass c) {
		if (!classWhitelist.contains(c.getName())) return;
		
		String[] exts = {c.getExtends()};
		if (c.isInterface()) exts = c.getImplements();
		
		// If the class extends anything
		for (String e : exts){
			if (!classWhitelist.contains(e)) continue;
			
			this.sb.append("\n        edge [\n                color = \"" + this.iumlmod.getArrowColor(c.getName(), e, "ext") + "\"\n        ]\n");
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
