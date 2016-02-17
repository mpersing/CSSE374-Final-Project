package asm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import data.api.IClass;
import data.api.IField;
import data.api.IMethod;
import data.api.IUMLModifier;
import data.impl.DataManager;
import data.impl.MethodCall;
import data.impl.SDAddStrategy;
import data.impl.UMLAddStrategy;
import pattern.impl.AdapterPatternFinder;
import pattern.impl.CompositePatternFinder;
import pattern.impl.DecoratorPatternFinder;
import pattern.impl.SingletonPatternFinder;
import visitor.impl.SDOutputStrategy;
import visitor.impl.UMLOutputStrategy;

public class ASMTest {
	private DataManager dm;
	
	public ASMTest() {
		this.dm = null;
		this.dm = new DataManager();
	}
	
	private void loadClass(String toLoad) throws IOException {
		this.dm.setAddStrategy(new UMLAddStrategy());
		this.dm.setOutputStrategy(new UMLOutputStrategy());
		this.dm.add(new String[]{toLoad});
	}
	
	private void loadClassMethodRecurse(String classN, String methodSig, int depth) throws IOException {
		this.dm = new DataManager();
		this.dm.setAddStrategy(new SDAddStrategy());
		this.dm.setOutputStrategy(new SDOutputStrategy());
		this.dm.add(new String[]{classN,methodSig,Integer.toString(depth)});
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
		Collection<IClass> classCol = this.dm.getClasses();
		IClass uut = classCol.iterator().next();
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
		Collection<IClass> classList = this.dm.getClasses();
		IClass testClass = classList.iterator().next();
		IMethod uut = testClass.getMethods().iterator().next();
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
		Collection<IClass> classList = this.dm.getClasses();
		IClass classTest = classList.iterator().next();
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
		Collection<IClass> classList = this.dm.getClasses();
		IClass uut = classList.iterator().next();
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
		Collection<IClass> classList = this.dm.getClasses();
		IClass testClass = classList.iterator().next();
		IMethod uut = testClass.getMethods().iterator().next();
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
		Collection<IClass> classList = this.dm.getClasses();
		IClass uut = classList.iterator().next();
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
		Collection<IClass> classList = this.dm.getClasses();
		IClass uut = classList.iterator().next();
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
		Collection<IClass> classList = this.dm.getClasses();
		IClass uut = classList.iterator().next();
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
		Collection<IClass> classList = this.dm.getClasses();
		IClass uut = classList.iterator().next();
		Set<String> assocSet = uut.getAssoc();
		Set<String> usesSet = uut.getUses();
		assertTrue(assocSet.contains(new String("asm.TestClass")));
		assertFalse(usesSet.contains(new String("asm.TestClass")));
	}
	
	/**
	 * This test makes sure the proper overloaded method is chosen in the add
	 * strategy.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testMethodOverload() throws IOException {
		loadClassMethodRecurse("asm.TestClassOverload", "overloaded(java.lang.String)", 5);
		assertNotNull(this.dm);
		IClass uut = this.dm.getClass("asm.TestClassWithGenericField");
		assertNotNull(uut);
		uut = this.dm.getClass("asm.TestElementFactory");
		assertNull(uut);
	}
	
	/**
	 * This test makes sure empty methods are read correctly.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testEmptyMethod() throws IOException {
		loadClassMethodRecurse("asm.TestClassOverload", "emptyMethod()", 5);
		assertNotNull(this.dm);
		IClass uut = this.dm.getClass("asm.TestClassOverload");
		assertNotNull(uut);
		IMethod uutm = uut.getMethod("emptyMethod()");
		assertNotNull(uutm);
		List<MethodCall> calls = uutm.getMethodCalls();
		assertTrue(calls.isEmpty());
	}
	
	/**
	 * This test makes sure recursion doesn't go forever.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testRecursive() throws IOException {
		loadClassMethodRecurse("asm.TestClassOverload", "recursive()", 5);
		assertNotNull(this.dm);
		IClass uut = this.dm.getClass("asm.TestClassOverload");
		assertNotNull(uut);
		IMethod uutm = uut.getMethod("recursive()");
		assertNotNull(uutm);
		List<MethodCall> calls = uutm.getMethodCalls();
		assertFalse(calls.isEmpty());
		MethodCall m = calls.get(0);
		assertTrue(m.getKey().equals("recursive()"));
		assertTrue(m.getClassToCall().equals("asm.TestClassOverload"));
		assertTrue(m.getReturnType().equals("void"));
		assertTrue(m.getArgTypes().length == 0);
	}
	
	/**
	 * Tests a general sequence of method calls to various classes.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testGeneralSequence() throws IOException {
		loadClassMethodRecurse("asm.TestClassOverload", "sequenceTest()", 5);
		assertNotNull(this.dm);
		IClass uut = this.dm.getClass("asm.TestClassOverload");
		assertNotNull(uut);
		IMethod uutm = uut.getMethod("sequenceTest()");
		assertNotNull(uutm);
		List<MethodCall> calls = uutm.getMethodCalls();
		assertFalse(calls.isEmpty());
		/*
		 * Call sequence to test:
		 * String s = new String("test");
		 * StringBuffer sb = new StringBuffer();
		 * sb.append(s);
		 * sb.append(s);
		 * sb.reverse();
		 * sb.append(s);
		*/
		MethodCall m = calls.get(0);
		assertTrue(m.getKey().equals("<init>(java.lang.String)"));
		assertTrue(m.getClassToCall().equals("java.lang.String"));
		assertTrue(m.getReturnType().equals("void"));
		assertTrue(m.getArgTypes().length == 1);
		m = calls.get(1);
		assertTrue(m.getKey().equals("<init>()"));
		assertTrue(m.getClassToCall().equals("java.lang.StringBuffer"));
		assertTrue(m.getReturnType().equals("void"));
		assertTrue(m.getArgTypes().length == 0);
		m = calls.get(2);
		assertTrue(m.getKey().equals("append(java.lang.String)"));
		assertTrue(m.getClassToCall().equals("java.lang.StringBuffer"));
		assertTrue(m.getReturnType().equals("java.lang.StringBuffer"));
		assertTrue(m.getArgTypes().length == 1);
		m = calls.get(3);
		assertTrue(m.getKey().equals("append(java.lang.String)"));
		assertTrue(m.getClassToCall().equals("java.lang.StringBuffer"));
		assertTrue(m.getReturnType().equals("java.lang.StringBuffer"));
		assertTrue(m.getArgTypes().length == 1);
		m = calls.get(4);
		assertTrue(m.getKey().equals("reverse()"));
		assertTrue(m.getClassToCall().equals("java.lang.StringBuffer"));
		assertTrue(m.getReturnType().equals("java.lang.StringBuffer"));
		assertTrue(m.getArgTypes().length == 0);
		m = calls.get(5);
		assertTrue(m.getKey().equals("append(java.lang.String)"));
		assertTrue(m.getClassToCall().equals("java.lang.StringBuffer"));
		assertTrue(m.getReturnType().equals("java.lang.StringBuffer"));
		assertTrue(m.getArgTypes().length == 1);
	}
	
	/**
	 * Tests a lazy singleton detection.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testLazySingleton() throws IOException {
		loadClass("asm.LazySingletonTest");
		this.dm.addPatternFinder(new SingletonPatternFinder());
		this.dm.findAllPatterns();
		IUMLModifier mm = this.dm.getUMLModifierManager();
		assertTrue(mm.getStyle("asm.LazySingletonTest").equals("color=blue"));
		assertTrue(mm.getSubtext("asm.LazySingletonTest").contains("\\<\\<Singleton\\>\\>"));
	}
	
	/**
	 * Tests a eager singleton detection.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testEagerSingleton() throws IOException {
		loadClass("asm.EagerSingletonTest");
		this.dm.addPatternFinder(new SingletonPatternFinder());
		this.dm.findAllPatterns();
		IUMLModifier mm = this.dm.getUMLModifierManager();
		assertTrue(mm.getStyle("asm.EagerSingletonTest").equals("color=blue"));
		assertTrue(mm.getSubtext("asm.EagerSingletonTest").contains("\\<\\<Singleton\\>\\>"));
	}
	
	/**
	 * Tests a Runtime singleton detection.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testRuntimeSingleton() throws IOException {
		loadClass("java.lang.Runtime");
		this.dm.addPatternFinder(new SingletonPatternFinder());
		this.dm.findAllPatterns();
		IUMLModifier mm = this.dm.getUMLModifierManager();
		assertTrue(mm.getStyle("java.lang.Runtime").equals("color=blue"));
		assertTrue(mm.getSubtext("java.lang.Runtime").contains("\\<\\<Singleton\\>\\>"));
	}
	
	/**
	 * Tests a Desktop singleton detection. It doesn't have itself in a field.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testDesktopSingleton() throws IOException {
		loadClass("java.awt.Desktop");
		this.dm.addPatternFinder(new SingletonPatternFinder());
		this.dm.findAllPatterns();
		IUMLModifier mm = this.dm.getUMLModifierManager();
		assertFalse(mm.getStyle("java.awt.Desktop").equals("color=blue"));
		assertFalse(mm.getSubtext("java.awt.Desktop").contains("\\<\\<Singleton\\>\\>"));
	}
	
	/**
	 * Tests a Calendar singleton detection. It can't be instantiated. (it's abstract)
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCalendarSingleton() throws IOException {
		loadClass("java.util.Calendar");
		this.dm.addPatternFinder(new SingletonPatternFinder());
		this.dm.findAllPatterns();
		IUMLModifier mm = this.dm.getUMLModifierManager();
		assertFalse(mm.getStyle("java.util.Calendar").equals("color=blue"));
		assertFalse(mm.getSubtext("java.util.Calendar").contains("\\<\\<Singleton\\>\\>"));
	}
	
	/**
	 * Tests singleton detection on FilterInputStream. It's not a singleton.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testFilterInputStreamSingleton() throws IOException {
		loadClass("java.io.FilterInputStream");
		this.dm.addPatternFinder(new SingletonPatternFinder());
		this.dm.findAllPatterns();
		IUMLModifier mm = this.dm.getUMLModifierManager();
		assertFalse(mm.getStyle("java.io.FilterInputStream").equals("color=blue"));
		assertFalse(mm.getSubtext("java.io.FilterInputStream").contains("\\<\\<Singleton\\>\\>"));
	}
	
	/**
	 * Tests decorator detection on test classes.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testDecoratorDetection() throws IOException {
		loadClass("asm.AbstractDecorator");
		loadClass("asm.ConcreteComponent");
		loadClass("asm.ConcreteDecorator1");
		loadClass("asm.ConcreteDecorator2");
		loadClass("asm.IComponent");
		this.dm.addPatternFinder(new DecoratorPatternFinder());
		this.dm.findAllPatterns();
		IUMLModifier mm = this.dm.getUMLModifierManager();
		// check style
		assertTrue(mm.getStyle("asm.AbstractDecorator").equals("style=filled, fillcolor=green,"));
		assertTrue(mm.getStyle("asm.ConcreteDecorator1").equals("style=filled, fillcolor=green,"));
		assertTrue(mm.getStyle("asm.ConcreteDecorator2").equals("style=filled, fillcolor=green,"));
		assertTrue(mm.getStyle("asm.IComponent").equals("style=filled, fillcolor=green,"));
		assertTrue(mm.getStyle("asm.ConcreteComponent").equals(""));
		// check subtext
		assertTrue(mm.getSubtext("asm.AbstractDecorator").equals("\\<\\<decorator\\>\\>"));
		assertTrue(mm.getSubtext("asm.ConcreteDecorator1").equals("\\<\\<decorator\\>\\>"));
		assertTrue(mm.getSubtext("asm.ConcreteDecorator2").equals("\\<\\<decorator\\>\\>"));
		assertTrue(mm.getSubtext("asm.IComponent").equals("\\<\\<component\\>\\>"));
		assertTrue(mm.getSubtext("asm.ConcreteComponent").equals(""));
		
	}
	
	/**
	 * Tests detection of the adapter pattern
	 * 
	 * @throws IOException
	 */
	@Test
	public void testAdapterDetection() throws IOException {
		loadClass("asm.ITarget");
		loadClass("asm.Adapter");
		loadClass("asm.Adaptee");		
		this.dm.addPatternFinder(new AdapterPatternFinder(this.dm.getWhitelist()));
		this.dm.findAllPatterns();
		IUMLModifier mm = this.dm.getUMLModifierManager();
		
		// Check style
		assertTrue(mm.getStyle("asm.ITarget").equals("style=filled, fillcolor=red,"));
		assertTrue(mm.getStyle("asm.Adapter").equals("style=filled, fillcolor=red,"));
		assertTrue(mm.getStyle("asm.Adaptee").equals("style=filled, fillcolor=red,"));
		
		// Check subtext
		assertTrue(mm.getSubtext("asm.ITarget").equals("\\<\\<Target\\>\\>"));
		assertTrue(mm.getSubtext("asm.Adapter").equals("\\<\\<Adapter\\>\\>"));
		assertTrue(mm.getSubtext("asm.Adaptee").equals("\\<\\<Adaptee\\>\\>"));
	}
	
	/**
	 * Tests a very simple composite pattern
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSimpleComposite() throws IOException {
		loadClass("simplecomposite.LeafA");
		loadClass("simplecomposite.LeafB");
		loadClass("simplecomposite.MyComponent");
		loadClass("simplecomposite.MyComposite");
		this.dm.addPatternFinder(new CompositePatternFinder());
		this.dm.findAllPatterns();
		IUMLModifier mm = this.dm.getUMLModifierManager();
		
		final String styleTarget = "style=filled, fillcolor=yellow,";
		// Check style
		assertTrue(mm.getStyle("simplecomposite.LeafA").equals(styleTarget));
		assertTrue(mm.getStyle("simplecomposite.LeafB").equals(styleTarget));
		assertTrue(mm.getStyle("simplecomposite.MyComponent").equals(styleTarget));
		assertTrue(mm.getStyle("simplecomposite.MyComposite").equals(styleTarget));
		
		final String leafSub = "\\<\\<Leaf\\>\\>";
		final String compositeSub = "\\<\\<Composite\\>\\>";
		final String componentSub = "\\<\\<Component\\>\\>";
		// Check subtext
		assertTrue(mm.getSubtext("simplecomposite.LeafA").equals(leafSub));
		assertTrue(mm.getSubtext("simplecomposite.LeafB").equals(leafSub));
		assertTrue(mm.getSubtext("simplecomposite.MyComponent").equals(componentSub));
		assertTrue(mm.getSubtext("simplecomposite.MyComposite").equals(compositeSub));
	}
	
	/**
	 * Tests a detailed composite pattern
	 * 
	 * @throws IOException
	 */
	@Test
	public void testDetailedComposite() throws IOException {
		loadClass("detailedcomposite.CompositeA");
		loadClass("detailedcomposite.CompositeB");
		loadClass("detailedcomposite.IParentofComponent");
		loadClass("detailedcomposite.LeafA");
		loadClass("detailedcomposite.MidLeaf");
		loadClass("detailedcomposite.MyComponent");
		loadClass("detailedcomposite.MyComposite");
		loadClass("detailedcomposite.SideLeaf");
		loadClass("detailedcomposite.SideLeafChild");
		this.dm.addPatternFinder(new CompositePatternFinder());
		this.dm.findAllPatterns();
		IUMLModifier mm = this.dm.getUMLModifierManager();
		
		final String styleTarget = "style=filled, fillcolor=yellow,";
		// Check style
		assertTrue(mm.getStyle("detailedcomposite.CompositeA").equals(styleTarget));
		assertTrue(mm.getStyle("detailedcomposite.CompositeB").equals(styleTarget));
		assertFalse(mm.getStyle("detailedcomposite.IParentofComponent").equals(styleTarget));
		assertTrue(mm.getStyle("detailedcomposite.LeafA").equals(styleTarget));
		assertTrue(mm.getStyle("detailedcomposite.MidLeaf").equals(styleTarget));
		assertTrue(mm.getStyle("detailedcomposite.MyComponent").equals(styleTarget));
		assertTrue(mm.getStyle("detailedcomposite.MyComposite").equals(styleTarget));
		assertTrue(mm.getStyle("detailedcomposite.SideLeaf").equals(styleTarget));
		assertTrue(mm.getStyle("detailedcomposite.SideLeafChild").equals(styleTarget));
		
		final String leafSub = "\\<\\<Leaf\\>\\>";
		final String compositeSub = "\\<\\<Composite\\>\\>";
		final String componentSub = "\\<\\<Component\\>\\>";
		// Check subtext
		assertTrue(mm.getSubtext("detailedcomposite.CompositeA").equals(compositeSub));
		assertTrue(mm.getSubtext("detailedcomposite.CompositeB").equals(compositeSub));
		assertFalse(mm.getSubtext("detailedcomposite.IParentofComponent").equals(" "));
		assertTrue(mm.getSubtext("detailedcomposite.LeafA").equals(leafSub));
		assertTrue(mm.getSubtext("detailedcomposite.MidLeaf").equals(leafSub));
		assertTrue(mm.getSubtext("detailedcomposite.MyComponent").equals(componentSub));
		assertTrue(mm.getSubtext("detailedcomposite.MyComposite").equals(compositeSub));
		assertTrue(mm.getSubtext("detailedcomposite.SideLeaf").equals(leafSub));
		assertTrue(mm.getSubtext("detailedcomposite.SideLeafChild").equals(leafSub));
	}

}
