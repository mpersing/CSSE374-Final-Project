package asm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
	
	/**
	 * Test that the general information about a Class is parsed properly.
	 * 
	 * @throws IOException
	 */
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
	
	/**
	 * Test that methods in classes are properly parsed.
	 * 
	 * @throws IOException
	 */
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
	
	/**
	 * Test that fields in classes are properly parsed.
	 * 
	 * @throws IOException
	 */
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
	
	/**
	 * Test that the information stored for Interfaces matches what's
	 * expected.
	 * 
	 * @throws IOException
	 */
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
	
	/**
	 * Test that methods in an interface are properly parsed.
	 * 
	 * @throws IOException
	 */
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
	
	/**
	 * Make sure nothing breaks if the class extends null. The java.lang.Object
	 * class is the only class that extends null so it is used for this test.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testExtendsNull() throws IOException {
		loadClass("java.lang.Object");
		assertNotNull(this.dm);
		ArrayList<IClass> classList = this.dm.getClasses();
		IClass uut = classList.get(0);
		assertTrue(uut.getExtends() == null);
	}
	
	/**
	 * The testFactory test tests that the proper classes are added to the
	 * usesSet that will be displayed at the output. This test tests
	 * types in the return, the arguments, and the body of a method.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testFactory() throws IOException {
		loadClass("asm.TestElementFactory");
		assertNotNull(this.dm);
		ArrayList<IClass> classList = this.dm.getClasses();
		IClass uut = classList.get(0);
		Set<String> usesSet = uut.getUses();
		assertNotNull(usesSet);
		// created by new call
		assertTrue(usesSet.contains(new String("data.impl.Class")));
		assertTrue(usesSet.contains(new String("data.impl.Field")));
		assertTrue(usesSet.contains(new String("data.impl.Method")));
		// return type
		assertTrue(usesSet.contains(new String("data.api.IElement")));
		// argument type
		assertTrue(usesSet.contains(new String("asm.TestClassWithGenericField")));
		assertTrue(usesSet.size() == 5);
		Set<String> assocSet = uut.getAssoc();
		assertTrue(assocSet.isEmpty());
	}
	
	/**
	 * This test makes sure that the full field type shows in the UML,
	 * but the assoc arrow is only drawn to the class inside of the
	 * container.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testGenericField() throws IOException {
		loadClass("asm.TestClassWithGenericField");
		assertNotNull(this.dm);
		ArrayList<IClass> classList = this.dm.getClasses();
		IClass uut = classList.get(0);
		Set<String> assocSet = uut.getAssoc();
		assertNotNull(assocSet);
		assertTrue(assocSet.size() == 1);
		assertTrue(assocSet.contains(new String("asm.TestClass")));
		List<IField> fieldList = uut.getFields();
		assertTrue(fieldList.size() == 1);
		IField genericField = fieldList.get(0);
		String fieldType = genericField.getType();
		// need escape characters for dot language
		assertTrue(fieldType.equals("java.util.List\\<asm.TestClass\\>")); 
	}
	
	/**
	 * This tests to make sure that if a class appears in the assoc set to be
	 * drawn, it will not also appear in the uses set. Association should take
	 * priority over uses.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testUsesAssocPriority() throws IOException {
		loadClass("asm.TestClassWithGenericField");
		assertNotNull(this.dm);
		ArrayList<IClass> classList = this.dm.getClasses();
		IClass uut = classList.get(0);
		Set<String> assocSet = uut.getAssoc();
		Set<String> usesSet = uut.getUses();
		assertTrue(assocSet.contains(new String("asm.TestClass")));
		assertFalse(usesSet.contains(new String("asm.TestClass")));
		
	}

}
