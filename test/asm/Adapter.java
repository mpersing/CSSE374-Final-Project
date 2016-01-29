package asm;

public class Adapter implements ITarget {

	protected Adaptee adaptee;
	
	public Adapter(Adaptee adaptee){
		this.adaptee = adaptee;
	}
	
	@Override
	public void method1() {
		this.adaptee.m1();

	}

	@Override
	public void method2() {
		this.adaptee.m2();
		this.adaptee.m3();

	}

}
