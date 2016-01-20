package asm;

public class EagerSingletonTest {

	private static final EagerSingletonTest instance = new EagerSingletonTest();
	
	private EagerSingletonTest() {
		
	}
	
	public static EagerSingletonTest getInstance() {
		return instance;
	}
	
}
