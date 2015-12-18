package asm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import data.api.IClass;
import data.api.IField;
import data.api.IMethod;
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
		assertFalse(uut.isProtected());
		assertFalse(uut.isStatic());
		assertFalse(uut.isInterface());
		assertTrue(uut.getImplements().length == 0);
		assertTrue(uut.getExtends().equals("java/lang/Object"));
		assertTrue(uut.getName().equals("asm/TestClass"));
		assertTrue(uut.getMethods().size() == 1);
		assertTrue(uut.getFields().size() == 1);
	}
	
	@Test
	public void testClassMethods() throws IOException {
		loadClass("asm.TestClass");
		assertNotNull(this.dm);
		ArrayList<IClass> classList = this.dm.getClasses();
		IClass testClass = classList.get(0);
		IMethod uut = testClass.getMethods().get(0);
		assertTrue(uut.getName().equals("<init>")); // default constructor
		assertTrue(uut.getArguments().length == 0);
		assertTrue(uut.getReturnType().equals("void"));
	}
	
	@Test
	public void testClassFields() throws IOException {
		loadClass("asm.TestClass");
		assertNotNull(this.dm);
		ArrayList<IClass> classList = this.dm.getClasses();
		IClass classTest = classList.get(0);
		IField uut = classTest.getFields().get(0);
		assertFalse(uut.isPrivate());
		assertFalse(uut.isPublic());
		assertTrue(uut.isStatic());
		assertTrue(uut.isProtected());
		assertTrue(uut.getName().equals("testField"));
		assertTrue(uut.getType().equals("int"));
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