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
		this.sb.append("        edge [\n                arrowhead = \"empty\"        ]");
	}
	
	/* Format:
	   Dog -> Animal
       Cat -> Animal
	 */
	@Override
	public void visit(IClass c) {
		String ext = c.getExtends();
		
		// If the class extends anything
		if(null != ext){
			this.sb.append("        ");
			this.sb.append(c.getName());
			this.sb.append(" -> ");
			this.sb.append(ext);
			this.sb.append("\n");
		}
	}
	
}
