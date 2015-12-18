package visitor.impl;

import java.util.Arrays;

import data.api.IClass;
import data.api.IDataManager;
import data.api.IField;
import data.api.IMethod;

public class ClassOutputVisitor extends OutputVisitor {

	/* Format:
	 Animal [
     	label = "{Animal|+ name : string\l+ age : int\l|+ die() : void\l}"
     ]
	 */
	
	public void visit(IField f) {
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
		// Open the class
		this.sb.append("        ");
		this.sb.append(c.getName());
		this.sb.append(" [ \n");
		
		this.sb.append("                shape=\"record\",\n");
		
		// Open the label
		this.sb.append("                label = \"{");
		
		// If it is an interface
		if (c.isInterface()) this.sb.append("\\<\\<Interface\\>\\>\\n");
		
		// If it is abstract
		if (c.isAbstract() && !c.isInterface()) this.sb.append("\\<\\<Abstract\\>\\>\\n");
		
		// Write the name
		this.sb.append(c.getName());
		this.sb.append("|");
	}
	
	public void midVisit(IClass c) {
		this.sb.append("|");
	}
	
	public void postVisit(IClass c) {
		// Close the label
		this.sb.append("}\"\n        ];\n\n");
	}
	
	public void visit(IDataManager d){
		this.sb.append("digraph g {\n");
	}
	
	public void postVisit(IDataManager d){
		this.sb.append("}\n");
	}
	
}
