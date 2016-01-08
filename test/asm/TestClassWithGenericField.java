package asm;

import java.util.List;

public class TestClassWithGenericField {
	List<TestClass> genericTest;
	
	public void addTestClass(TestClass t) {
		genericTest.add(t);
	}

}
