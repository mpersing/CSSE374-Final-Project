package asm;

import data.api.IElement;
import data.impl.Class;
import data.impl.Field;
import data.impl.Method;

public class TestElementFactory {
	
	public IElement makeElement(TestClassWithGenericField t, int chooser) {
		IElement e;
		if(chooser == 0) {
			e = new Class();
		}
		else if(chooser == 1) {
			e = new Field();
		}
		else {
			e = new Method();
		}
		return e;
	}

}
