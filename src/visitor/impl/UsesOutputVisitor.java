package visitor.impl;

import data.api.IClass;
import data.api.IDataManager;

public class UsesOutputVisitor extends OutputVisitor {
	/* Format:
    edge [
            arrowhead = "empty"
    ]
 */
@Override
public void visit(IDataManager d){
	this.sb.append("\n        edge [\n                style = \"dashed\";\n                arrowhead = \"vee\"\n        ]\n");
}

/* Format:
   Dog -> Animal
   Cat -> Animal
 */
@Override
public void visit(IClass c) {
	if (!classWhitelist.contains(c.getName())) return;
	// If the class uses anything
	for (String e : c.getUses()){
		if(!classWhitelist.contains(e)) continue;
		
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
