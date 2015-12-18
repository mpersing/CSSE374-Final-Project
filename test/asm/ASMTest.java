package asm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import data.api.IClass;
import data.impl.DataManager;

public class ASMTest {
	private DataManager dm;
	
	public ASMTest() {
		this.dm = null;
	}
	
	private void loadClass(String toLoad) throws IOException {
		this.dm = new DataManager();
		this.dm.add(toLoad);
	}
	
	@Test
	public void testClassDeclaration() throws IOException {
		loadClass("asm.TestClass");
		assertNotNull(this.dm);
		ArrayList<IClass> classList = this.dm.getClasses();
		IClass uut = classList.get(0);
		assertTrue(uut.isPublic());
		assertFalse(uut.isPrivate());
		assertTrue(uut.getImplements().length == 0);
		assertTrue(uut.getExtends().equals("java/lang/Object"));
		assertTrue(uut.getName().equals("asm/TestClass"));
	}
	
	@Test
	public void testExtendsNull() throws IOException {
		loadClass("java.lang.Object");
		assertNotNull(this.dm);
		ArrayList<IClass> classList = this.dm.getClasses();
		IClass uut = classList.get(0);
		assertTrue(uut.getExtends() == null);
	}

}
