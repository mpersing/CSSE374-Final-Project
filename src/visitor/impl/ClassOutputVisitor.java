package visitor.impl;

import java.util.Arrays;

import data.api.IClass;
import data.api.IField;
import data.api.IMethod;

public class ClassOutputVisitor extends OutputVisitor {

	/* Format:
	 Animal [
     	label = "{Animal|+ name : string\l+ age : int\l|+ die() : void\l}"
     ]
	 */
	
	private boolean validated;
	
	public void visit(IField f) {
		if(!validated) { return; }
		// Decide prefix
		String pre = f.isPrivate()?"- ":f.isPublic()?"+ ":f.isProtected()?"\\# ":"";
		
		// The rest of the field
		this.sb.append(pre);
		this.sb.append(f.getName());
		this.sb.append(" : ");
		this.sb.append(f.getType());
		this.sb.append("\\l");
	}
	
	public void visit(IMethod m) {
		if(!validated) { return; }
		// Decide prefix
		String pre = m.isPrivate()?"- ":m.isPublic()?"+ ":"";
		
		// The rest of the method
		this.sb.append(pre);
		this.sb.append(m.getName().replaceAll("[<>]",""));
		
		// get arguments
		this.sb.append("(");
		String args  = Arrays.asList(m.getArguments()).toString();
		// if there are any
		if (args.length() > 0){
			this.sb.append(args.toString().substring(1, args.length()-1));
		}
		this.sb.append(") : ");
		
		// get return type
		this.sb.append(m.getReturnType());
		this.sb.append("\\l");
	}
	
	@Override
	public void visit(IClass c){
		this.validated = false;
		// Return if not in the whitelist
		if (!classWhitelist.contains(c.getName())) return;
		this.validated = true;
		
		// Open the class
		this.sb.append("        ");
		this.sb.append("\"");
		this.sb.append(c.getName());
		this.sb.append("\"");
		this.sb.append(" [ \n");
		
		this.sb.append("                shape=\"record\",\n");
		
		// Set styling
		this.sb.append("                ");
		this.sb.append(iumlmod.getStyle(c.getName()));
		this.sb.append("\n");
		
		// Open the label
		this.sb.append("                label = \"{");
		
		// If it is an interface
		if (c.isInterface()) this.sb.append("\\<\\<Interface\\>\\>\\n");
		
		// If it is abstract
		if (c.isAbstract() && !c.isInterface()) this.sb.append("\\<\\<Abstract\\>\\>\\n");
		
		// Write the name
		this.sb.append(c.getName());
		
		// Set Subtext
		this.sb.append("\\n");
		this.sb.append(iumlmod.getSubtext(c.getName()));
		
		// Separate from fields
		this.sb.append("|");
	}
	
	public void midVisit(IClass c) {
		if (!classWhitelist.contains(c.getName())) return;
		this.sb.append("|");
	}
	
	public void postVisit(IClass c) {
		// Close the label
		if (!classWhitelist.contains(c.getName())) return;
		this.sb.append("}\"\n        ];\n\n");
	}
	
}
