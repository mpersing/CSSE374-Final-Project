package visitor.impl;

import java.util.List;

import data.api.IClass;
import data.api.IField;
import data.api.IMethod;

public class ClassOutputVisitor extends OutputVisitor {

	/* Format:
	 Animal [
     	label = "{Animal|+ name : string\l+ age : int\l|+ die() : void\l}"
     ]
	 */
	
	@Override
	public void visit(IClass c){
		// Open the class
		this.sb.append("        ");
		this.sb.append(c.getName());
		this.sb.append(" [ \n");
		
		// open the label
		this.sb.append("                label = \"{");
		this.sb.append(c.getName());
		this.sb.append("|");
		
		// Get the fields
		List<IField> fields = c.getFields();
		for (IField f : fields){
			// Decide prefix
			String pre = f.isPrivate()?"- ":f.isPublic()?"+ ":f.isProtected()?"# ":"";
			
			// The rest of the field
			this.sb.append(pre);
			this.sb.append(f.getName());
			this.sb.append(" : ");
			this.sb.append(f.getType());
			this.sb.append("\\l");
		}
		
		// Get the Methods
		List<IMethod> methods = c.getMethods();
		for (IMethod m : methods){
			// Decide prefix
			String pre = m.isPrivate()?"- ":m.isPublic()?"+ ":"";
			
			// The rest of the method
			this.sb.append(pre);
			this.sb.append(m.getName());
			this.sb.append("() : ");
			this.sb.append(m.getReturnType());
			this.sb.append("\\l");
		}
		
		// Close the label
		this.sb.append("}\"\n        ]\n");
	};
	
}
