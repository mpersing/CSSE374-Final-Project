package asm;

public class LazySingletonTest {

	private static LazySingletonTest instance;
	
	private LazySingletonTest(){};
	
	public void initialize(){
		instance = new LazySingletonTest();
	}
	
	public LazySingletonTest getInstance(){
		return instance;
	}
	
}
