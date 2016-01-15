package asm;

public class TestClassOverload {
	private void overloaded(String s) {
		TestClassWithGenericField d = new TestClassWithGenericField();
	}
	
	private void overloaded(int i) {
		TestElementFactory d = new TestElementFactory();
	}
	
	private void emptyMethod() {
	}
	
	private void recursive() {
		this.recursive();
	}
	
	private void sequenceTest() {
		String s = new String("test");
		StringBuffer sb = new StringBuffer();
		sb.append(s);
		sb.append(s);
		sb.reverse();
		sb.append(s);
	}
}
