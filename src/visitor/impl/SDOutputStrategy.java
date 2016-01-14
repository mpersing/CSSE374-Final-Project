package visitor.impl;

import java.util.ArrayList;
import java.util.List;

import data.api.IClass;
import data.api.IDataManager;
import data.api.IMethod;
import data.impl.MethodCall;
import visitor.api.IOutputStrategy;

public class SDOutputStrategy implements IOutputStrategy {
	
	private IDataManager dm;
	
	private String rootClass;
	private String rootMethod;
	private int rootDepth;
	
	private List<String> existingClasses;
	private List<String> newClasses;
	private List<String> callSeq;
	
	public SDOutputStrategy() {
		this.existingClasses = new ArrayList<String>();
		this.newClasses = new ArrayList<String>();
		this.callSeq = new ArrayList<String>();
	}
	
	public void output(StringBuffer sb){
		this.existingClasses.add(this.rootClass);
		this.genListsRecurse(this.rootClass, this.rootMethod, rootDepth);
		
		this.outputLists(sb);
	}
	
	private void genListsRecurse(String c, String method, int depth) {
		if(depth == -1) {
			return;
		}
		IClass newClass = dm.getClass(c);
		if(newClass == null) {
			System.out.println("New class not found");
			return;
		}
		IMethod calledMethod = newClass.getMethod(method);
		if(calledMethod == null) {
			System.out.println("New method not found");
			return;
		}
		List<MethodCall> mcList = calledMethod.getMethodCalls();
		String toAdd;
		for(MethodCall m : mcList) {
			String key = m.getKey();
			boolean needNew = false;
			if(!classAlreadyAdded(m.getClassToCall())) {
				if(key.contains("<init>")) {
					this.newClasses.add(m.getClassToCall());
					needNew = true;
				} else {
					this.existingClasses.add(m.getClassToCall());
				}
			}
			toAdd = new String();
			toAdd = this.classNameToInstanceName(c) + ":" + m.getReturnType() + "=" + this.classNameToInstanceName(m.getClassToCall()) + "." + m.getKey();
			if(!needNew) {
				toAdd = toAdd.replace("<init>", "newCalledAgain");
			}
			callSeq.add(toAdd + "\n");
			this.genListsRecurse(m.getClassToCall(), m.getKey(), depth - 1);
		}
	}
	
	private void outputLists(StringBuffer sb) {
		for(String s : this.existingClasses) {
			sb.append(this.classNameToInstanceName(s) + ":" + s + "\n");
		}
		for(String s : this.newClasses) {
			sb.append("/" + this.classNameToInstanceName(s) + ":" + s + "\n");
		}
		sb.append("\n");
		for(String s : this.callSeq) {
			sb.append(s.replace("<init>", "new"));
		}
	}
	
	private boolean inNewClasses(String s) {
		for(String s1 : this.newClasses) {
			if(s1.equals(s)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean inExistingClasses(String s) {
		for(String s1 : this.existingClasses) {
			if(s1.equals(s)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean classAlreadyAdded(String s) {
		return this.inExistingClasses(s) || this.inNewClasses(s);
	}
	
	public void setRoot(String className, String methodSig, int depth) {
		this.rootClass = className;
		this.rootMethod = methodSig;
		this.rootDepth = depth;
	}
	
	public void setDataManager(IDataManager dm) {
		this.dm = dm;
	}
	
	private String classNameToInstanceName(String c) {
		return c.replace(".", "").toLowerCase();
	}

}
