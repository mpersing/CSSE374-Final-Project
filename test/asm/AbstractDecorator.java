package asm;

public abstract class AbstractDecorator implements IComponent {
	
	protected IComponent c;
	
	protected AbstractDecorator(IComponent c) {
	}

}
