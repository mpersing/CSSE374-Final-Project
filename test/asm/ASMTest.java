package asm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

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
		assertTrue(uut.isAbstract());
		assertTrue(uut.getImplements().length == 0);
		assertTrue(uut.getExtends().equals("java.lang.Object"));
		assertTrue(uut.getName().equals("asm.TestClass"));
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
	public void testInterfaceDeclaration() throws IOException {
		loadClass("asm.TestInterface");
		assertNotNull(this.dm);
		ArrayList<IClass> classList = this.dm.getClasses();
		IClass uut = classList.get(0);
		assertTrue(uut.isPublic());
		assertFalse(uut.isPrivate());
		assertFalse(uut.isProtected());
		assertFalse(uut.isStatic());
		assertTrue(uut.isInterface());
		assertTrue(uut.getImplements().length == 1);
		assertTrue(uut.getImplements()[0].equals("java.util.List"));
		assertTrue(uut.getExtends().equals("java.lang.Object"));
		assertTrue(uut.getName().equals("asm.TestInterface"));
		assertTrue(uut.getMethods().size() == 1);
		assertTrue(uut.getFields().size() == 0); // empty - don't need an explicit test for fields
	}
	
	@Test
	public void testInterfaceMethods() throws IOException {
		loadClass("asm.TestInterface");
		assertNotNull(this.dm);
		ArrayList<IClass> classList = this.dm.getClasses();
		IClass testClass = classList.get(0);
		IMethod uut = testClass.getMethods().get(0);
		assertTrue(uut.isPublic());
		assertFalse(uut.isPrivate());
		assertFalse(uut.isProtected());
		assertFalse(uut.isStatic());
		assertTrue(uut.getName().equals("kindaSecretFunction")); // default constructor
		assertTrue(uut.getArguments().length == 3);
		assertTrue(uut.getArguments()[0].equals("int"));
		assertTrue(uut.getArguments()[1].equals("int"));
		assertTrue(uut.getArguments()[2].equals("int"));
		assertTrue(uut.getReturnType().equals("java.lang.String"));
	}
	
	@Test
	public void testExtendsNull() throws IOException {
		loadClass("java.lang.Object");
		assertNotNull(this.dm);
		ArrayList<IClass> classList = this.dm.getClasses();
		IClass uut = classList.get(0);
		assertTrue(uut.getExtends() == null);
	}
	
	@Test
	public void testFactory() throws IOException {
		loadClass("asm.TestElementFactory");
		assertNotNull(this.dm);
		ArrayList<IClass> classList = this.dm.getClasses();
		IClass uut = classList.get(0);
		Set<String> usesSet = uut.getUses();
		assertNotNull(usesSet);
		assertTrue(usesSet.contains(new String("data.impl.Class")));
		assertTrue(usesSet.contains(new String("data.impl.Field")));
		assertTrue(usesSet.contains(new String("data.impl.Method")));
	}

}
