/*****************************************************************************
 * Copyright (c) 2014-2015 CEA LIST.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Ed Seidewitz (IJI) - Initial implementation
 *  Ed Seidewitz (MDS) - Additional tests
 * 
 *****************************************************************************/

package org.eclipse.papyrus.uml.alf.tests.mapper;

import static org.junit.Assert.*;

import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.alf.MappingError;
import org.eclipse.papyrus.uml.alf.Model;
import org.eclipse.papyrus.uml.alf.ParsingError;
import org.eclipse.papyrus.uml.alf.SyntaxElement;
import org.eclipse.papyrus.uml.alf.tests.mapper.AlfCompiler;
import org.eclipse.papyrus.uml.alf.validation.ModelNamespaceFacade;
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.CallOperationAction;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.ReadStructuralFeatureAction;
import org.eclipse.uml2.uml.Reception;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.SignalEvent;
import org.eclipse.uml2.uml.StructuredActivityNode;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
public class CompilerTests {

	public static final String PLUGIN_DIRECTORY = "platform:/plugin/org.eclipse.papyrus.uml.alf.tests";

	private static AlfCompiler compiler = null;

	private static Package contextModel = null;
	
	@BeforeClass
	public static void setup() throws Exception {
		compiler = new AlfCompiler();
		contextModel = new Model();
	}

	@After
	public void clean() {
		contextModel.getPackagedElements().clear();
	}

	public static void assertTextualRepresentation(
			Element contextElement, String textualRepresentation) {
		assertEquals(textualRepresentation,
				compiler.getTextualRepresentation(contextElement));
	}

	public static void assertMultiplicityElement(MultiplicityElement element,
			int lower, int upper, boolean isOrdered, boolean isUnique) {
		assertEquals(lower, element.getLower());
		assertEquals(upper, element.getUpper());
		assertEquals(isOrdered, element.isOrdered());
		assertEquals(isUnique, element.isUnique());
	}

	public static void assertTypedElement(TypedElement element, String typeName) {
		Type type = element.getType();
		if (type == null) {
			assertNull(typeName);
		} else {
			assertEquals(typeName, type.getName());
		}
	}

	public static void assertProperty(Property property,
			VisibilityKind visibility,
			String name, int lower, int upper, boolean isOrdered, boolean isUnique,
			String type) {
		assertEquals(visibility, property.getVisibility());
		assertEquals(name, property.getName());
		assertTypedElement(property, type);
		assertMultiplicityElement(property, lower, upper, isOrdered, isUnique);
	}

	public static void assertParameter(Parameter parameter,
			String name, ParameterDirectionKind direction,
			int lower, int upper, boolean isOrdered, boolean isUnique,
			String type) {
		assertEquals(name, parameter.getName());
		assertEquals(direction, parameter.getDirection());
		assertTypedElement(parameter, type);
		assertMultiplicityElement(parameter, lower, upper, isOrdered, isUnique);
	}

	public static void assertParameter(Parameter actual, Parameter expected) {
		Type type = expected.getType();
		assertParameter(actual, 
				expected.getName(), expected.getDirection(), 
				expected.getLower(), expected.getUpper(), expected.isOrdered(), expected.isUnique(), 
				type == null? null: type.getName());
	}

	public static void assertOperation(Operation operation,
			VisibilityKind visibility,
			String name, int lower, int upper, boolean isOrdered, boolean isUnique,
			String type) {
		assertEquals(visibility, operation.getVisibility());
		assertEquals(name, operation.getName());
		assertParameter(operation.getReturnResult(), null,
				ParameterDirectionKind.RETURN_LITERAL,
				lower, upper, isOrdered, isUnique, type);
	}
	
	public static void assertMethod(Operation operation) {
		EList<Behavior> methods = operation.getMethods();
		assertEquals(1, methods.size());
		assertTrue(methods.get(0) instanceof Activity);
		
		Activity method = (Activity)methods.get(0);
		assertEquals(operation.getName() + "$method$1", method.getName());
		
		EList<Parameter> operationParameters = operation.getOwnedParameters();
		EList<Parameter> methodParameters = method.getOwnedParameters();
		assertEquals(operationParameters.size(), methodParameters.size());
		for (int i = 0; i < operationParameters.size(); i++) {
			assertParameter(operationParameters.get(i), methodParameters.get(i));
		}
	}
	
	public static <T extends PackageableElement> T map(T contextElement, SyntaxElement element)
			throws MappingError {
			List<EObject> alf = new BasicEList<EObject>();
			alf.add(element);
			compiler.map(contextElement, alf);
			return contextElement;
		}
		
	public static <T extends PackageableElement> T compile(T contextElement, String textualRepresentation, Namespace contextNamespace)
		throws ParsingError, MappingError {
		SyntaxElement element = (SyntaxElement)compiler.parse(textualRepresentation);
		ModelNamespaceFacade.getInstance().getContext(element).setContextNamespace(contextNamespace);
		map(contextElement, element);
		compiler.addTextualRepresentation(contextElement, textualRepresentation);
		return contextElement;
	}
	
	public static <T extends PackageableElement> T compile(T contextElement, String textualRepresentation)
			throws ParsingError, MappingError {
		contextModel.getPackagedElements().add(contextElement);
		return compile(contextElement, textualRepresentation, contextModel);
	}

	public static Class compileClass(String textualRepresentation)
			throws ParsingError, MappingError {
		return compile(UMLFactory.eINSTANCE.createClass(), textualRepresentation);
	}

	public static String TEST_CLASS_TEXT = "class Test { p : Integer; public q() : Boolean { } }";

	public static Class compileTestClass() throws ParsingError, MappingError {
		return compileClass(TEST_CLASS_TEXT);
	}

	@Test
	public void testNewClass() throws ParsingError, MappingError {
		Class testClass = compileTestClass();

		assertTextualRepresentation(testClass, TEST_CLASS_TEXT);
		assertEquals("Test", testClass.getName());
		assertProperty(testClass.getAttributes().get(0),
				VisibilityKind.PACKAGE_LITERAL, "p", 1, 1, false, true, "Integer");
		assertOperation(testClass.getOwnedOperations().get(0),
				VisibilityKind.PUBLIC_LITERAL, "q", 1, 1, false, true, "Boolean");
		assertMethod(testClass.getOwnedOperations().get(0));
		 
	}

	public static String ATTRIBUTE_UPDATE_TEXT = "class Test { private p : Boolean[*] ordered nonunique; public q() : Boolean { } }";

	@Test
	public void testAttributeMerging() throws ParsingError, MappingError {
		Class testClass = compileTestClass();

		Property attribute = testClass.getAttribute("p", null);
		ReadStructuralFeatureAction action = UMLFactory.eINSTANCE.createReadStructuralFeatureAction();
		action.setStructuralFeature(attribute);
		Activity activity = UMLFactory.eINSTANCE.createActivity();
		activity.getNodes().add(action);
		testClass.getNestedClassifiers().add(activity);

		compiler.compile(testClass, ATTRIBUTE_UPDATE_TEXT);

		assertTextualRepresentation(testClass, ATTRIBUTE_UPDATE_TEXT);
		assertProperty(attribute,
				VisibilityKind.PRIVATE_LITERAL, "p", 0, -1, true, false, "Boolean");
		assertOperation(testClass.getOwnedOperations().get(0),
				VisibilityKind.PUBLIC_LITERAL, "q", 1, 1, false, true, "Boolean");
		assertMethod(testClass.getOwnedOperations().get(0));
		assertEquals(attribute, action.getStructuralFeature());
	}

	public static String OPERATION_UPDATE_TEXT =
			"class UpdatedTest { p : Integer; private q(in a : Integer) : Integer[*] nonunique ordered { return a; } }";
	public static String Q_METHOD_TEXT =
			"activity 'q$method$1'(in a: Integer): Integer[0..*] sequence {\n\treturn a;\n}";

	@Test
	public void testOperationMerging() throws ParsingError, MappingError {
		Class testClass = compileTestClass();

		Operation operation = testClass.getOwnedOperation("q", null, null);
		CallOperationAction action = UMLFactory.eINSTANCE.createCallOperationAction();
		action.setOperation(operation);
		Activity activity = UMLFactory.eINSTANCE.createActivity();
		activity.getNodes().add(action);
		testClass.getNestedClassifiers().add(activity);

		compiler.compile(testClass, OPERATION_UPDATE_TEXT);

		assertTextualRepresentation(testClass, OPERATION_UPDATE_TEXT);
		assertEquals("UpdatedTest", testClass.getName());
		assertProperty(testClass.getAttributes().get(0),
				VisibilityKind.PACKAGE_LITERAL, "p", 1, 1, false, true, "Integer");
		assertOperation(operation,
				VisibilityKind.PRIVATE_LITERAL, "q", 0, -1, true, false, "Integer");
		assertParameter(operation.getOwnedParameters().get(1),
				"a", ParameterDirectionKind.IN_LITERAL, 1, 1, false, true, "Integer");
		assertEquals(operation, action.getOperation());
		assertMethod(operation);
		assertTextualRepresentation(testClass.getOwnedBehavior("q$method$1"), Q_METHOD_TEXT);
	}
	
	public static String STUB_OPERATION_UPDATE_TEXT ="class Test { p : Integer; public q(in a : Integer) : Boolean; }";
	
	@Test
	public void testStubOperationMerging() throws ParsingError, MappingError {
		Class testClass = compileTestClass();
		Operation operation = testClass.getOwnedOperation("q", null, null);
		
		compiler.compile(testClass, STUB_OPERATION_UPDATE_TEXT);
		
		assertTextualRepresentation(testClass, STUB_OPERATION_UPDATE_TEXT);
		assertOperation(operation,
				VisibilityKind.PUBLIC_LITERAL, "q", 1, 1, false, true, "Boolean");
		assertParameter(operation.getOwnedParameters().get(1),
				"a", ParameterDirectionKind.IN_LITERAL, 1, 1, false, true, "Integer");
		assertMethod(operation);
	}
	
	@Test
	public void testMethodRecompile() throws ParsingError, MappingError {
		Class testClass = compileTestClass();
		Operation operation = testClass.getOwnedOperation("q", null, null);
		Behavior method = operation.getMethods().get(0);		
		
		compile(method, compiler.getTextualRepresentation(method), testClass);
		
		assertEquals(operation, method.getSpecification());
		assertMethod(operation);
	}

	public static String SELF_REFERENCE_TEXT = "class Test { self : Test; }";

	@Test
	public void testReferenceUpdate() throws ParsingError, MappingError {
		Class testClass = compileTestClass();

		compiler.compile(testClass, SELF_REFERENCE_TEXT);

		assertTextualRepresentation(testClass, SELF_REFERENCE_TEXT);
		assertEquals(testClass, testClass.getAttribute("self", null).getType());
	}

	@Test
	public void testFeatureRemoval() throws ParsingError, MappingError {
		Class testClass = compileTestClass();

		compiler.compile(testClass, SELF_REFERENCE_TEXT);

		assertNotNull(testClass.getAttribute("self", testClass));
		assertNull(testClass.getAttribute("p", null));
		assertNull(testClass.getOperation("q", null, null));
	}

	public static final String PROPERTY_INITIALIZER_TEXT = "class Test { p : Integer = 1; }";
	public static final String DEFAULT_VALUE_ACTIVITY_TEXT = "activity 'p$defaultValue$1'(): Natural {\n  return 1;\n}";

	@Test
	public void testPropertyInitializer() throws ParsingError, MappingError {
		Class testClass = compileClass(PROPERTY_INITIALIZER_TEXT);

		assertNotNull(testClass.getAttribute("p", null));

		ValueSpecification defaultValue = testClass.getAttribute("p", null).getDefaultValue();

		assertTrue(defaultValue instanceof OpaqueExpression);
		assertTextualRepresentation(((OpaqueExpression) defaultValue).getBehavior(), DEFAULT_VALUE_ACTIVITY_TEXT);
	}

	@Test
	public void testPropertyInitializerUpdate() throws ParsingError, MappingError {
		Class testClass = compileTestClass();

		compiler.compile(testClass, PROPERTY_INITIALIZER_TEXT);

		assertNotNull(testClass.getAttribute("p", null));

		ValueSpecification defaultValue = testClass.getAttribute("p", null).getDefaultValue();

		assertTrue(defaultValue instanceof OpaqueExpression);
		assertTextualRepresentation(((OpaqueExpression) defaultValue).getBehavior(), DEFAULT_VALUE_ACTIVITY_TEXT);
	}

	public static Package compilePackage(String textualRepresentation)
			throws ParsingError, MappingError {
		return compile(UMLFactory.eINSTANCE.createPackage(), textualRepresentation);
	}

	public static String TEST_PACKAGE_TEXT = "package TestPackage { " +
			"public " + TEST_CLASS_TEXT + " }";

	public static Package compileTestPackage() throws ParsingError, MappingError {
		return compilePackage(TEST_PACKAGE_TEXT);
	}

	@Test
	public void testNewPackage() throws ParsingError, MappingError {
		Package testPackage = compileTestPackage();

		assertTextualRepresentation(testPackage, TEST_PACKAGE_TEXT);
		assertEquals("TestPackage", testPackage.getName());

		Class testClass = (Class) testPackage.getPackagedElement("Test");
		assertEquals(VisibilityKind.PUBLIC_LITERAL, testClass.getVisibility());
		assertProperty(testClass.getAttributes().get(0),
				VisibilityKind.PACKAGE_LITERAL, "p", 1, 1, false, true, "Integer");
		assertOperation(testClass.getOwnedOperations().get(0),
				VisibilityKind.PUBLIC_LITERAL, "q", 1, 1, false, true, "Boolean");
	}

	public static String PACKAGE_UPDATE_TEXT = "package TestPackage { " +
			"private " + ATTRIBUTE_UPDATE_TEXT +
			" public class TestRef { public ref : Test; } }";

	@Test
	public void testPackageMerging() throws ParsingError, MappingError {
		Package testPackage = compileTestPackage();

		Class testClass = (Class) testPackage.getPackagedElement("Test");
		Property attribute = testClass.getAttribute("p", null);

		compiler.compile(testPackage, PACKAGE_UPDATE_TEXT);

		assertTextualRepresentation(testPackage, PACKAGE_UPDATE_TEXT);
		assertEquals(VisibilityKind.PRIVATE_LITERAL, testClass.getVisibility());
		assertProperty(attribute,
				VisibilityKind.PRIVATE_LITERAL, "p", 0, -1, true, false, "Boolean");
		assertOperation(testClass.getOwnedOperations().get(0),
				VisibilityKind.PUBLIC_LITERAL, "q", 1, 1, false, true, "Boolean");

		Class testRefClass = (Class) testPackage.getPackagedElement("TestRef");
		assertEquals(VisibilityKind.PUBLIC_LITERAL, testRefClass.getVisibility());

		Property refAttribute = testRefClass.getOwnedAttributes().get(0);
		assertProperty(refAttribute,
				VisibilityKind.PUBLIC_LITERAL, "ref", 1, 1, false, true, "Test");
		assertEquals(testClass, refAttribute.getType());
	}

	public static DataType compileDataType(String textualRepresentation)
			throws ParsingError, MappingError {
		return compile(UMLFactory.eINSTANCE.createDataType(), textualRepresentation);
	}

	public static String TEST_DATATYPE_TEXT = "datatype Test { public p : Integer; }";

	public static DataType compileTestDataType() throws ParsingError, MappingError {
		return compileDataType(TEST_DATATYPE_TEXT);
	}

	@Test
	public void testNewDataType() throws ParsingError, MappingError {
		DataType testDataType = compileTestDataType();

		assertTextualRepresentation(testDataType, TEST_DATATYPE_TEXT);
		assertEquals("Test", testDataType.getName());
		assertProperty(testDataType.getAttributes().get(0),
				VisibilityKind.PUBLIC_LITERAL, "p", 1, 1, false, true, "Integer");
	}

	public static Signal compileSignal(String textualRepresentation)
			throws ParsingError, MappingError {
		return compile(UMLFactory.eINSTANCE.createSignal(), textualRepresentation);
	}

	public static String TEST_SIGNAL_TEXT = "signal Test { public p : Integer; }";

	public static Signal compileTestSignal() throws ParsingError, MappingError {
		return compileSignal(TEST_SIGNAL_TEXT);
	}

	@Test
	public void testNewSignal() throws ParsingError, MappingError {
		Signal testSignal = compileTestSignal();

		assertTextualRepresentation(testSignal, TEST_SIGNAL_TEXT);
		assertEquals("Test", testSignal.getName());
		assertProperty(testSignal.getAttributes().get(0),
				VisibilityKind.PUBLIC_LITERAL, "p", 1, 1, false, true, "Integer");
	}

	public static Association compileAssociation(String textualRepresentation)
			throws ParsingError, MappingError {
		return compile(UMLFactory.eINSTANCE.createAssociation(), textualRepresentation);
	}

	public static String TEST_ASSOCIATION_TEXT = "assoc Test { public x : Integer; public y : Integer[*]; }";

	public static Association compileTestAssociation() throws ParsingError, MappingError {
		return compileAssociation(TEST_ASSOCIATION_TEXT);
	}

	@Test
	public void testNewAssociation() throws ParsingError, MappingError {
		Association testAssociation = compileTestAssociation();

		assertTextualRepresentation(testAssociation, TEST_ASSOCIATION_TEXT);
		assertEquals("Test", testAssociation.getName());
		assertProperty(testAssociation.getOwnedEnds().get(0),
				VisibilityKind.PUBLIC_LITERAL, "x", 1, 1, false, true, "Integer");
		assertProperty(testAssociation.getOwnedEnds().get(1),
				VisibilityKind.PUBLIC_LITERAL, "y", 0, -1, false, true, "Integer");
	}

	public static Enumeration compileEnumeration(String textualRepresentation)
			throws ParsingError, MappingError {
		return compile(UMLFactory.eINSTANCE.createEnumeration(), textualRepresentation);
	}

	public static String TEST_ENUMERATION_TEXT = "enum Test { A, B, C }";

	public static Enumeration compileTestEnumeration() throws ParsingError, MappingError {
		return compileEnumeration(TEST_ENUMERATION_TEXT);
	}

	@Test
	public void testNewEnumeration() throws ParsingError, MappingError {
		Enumeration testEnumeration = compileTestEnumeration();

		assertTextualRepresentation(testEnumeration, TEST_ENUMERATION_TEXT);
		assertEquals("Test", testEnumeration.getName());
		List<EnumerationLiteral> literals = testEnumeration.getOwnedLiterals();
		assertEquals("A", literals.get(0).getName());
		assertEquals("B", literals.get(1).getName());
		assertEquals("C", literals.get(2).getName());
	}

	public static String TEST_RECEPTION_TEXT = "abstract active class Test { public signal S { } public receive S; }";

	public static Class compileTestReception() throws ParsingError, MappingError {
		return compileClass(TEST_RECEPTION_TEXT);
	}

	@Test
	public void testNewReception() throws ParsingError, MappingError {
		Class testClass = compileTestReception();

		assertTextualRepresentation(testClass, TEST_RECEPTION_TEXT);
		Signal signal = (Signal) testClass.getNestedClassifiers().get(0);
		assertEquals("S", signal.getName());

		Reception reception = testClass.getOwnedReceptions().get(0);
		assertEquals("S", reception.getName());
		assertEquals(VisibilityKind.PUBLIC_LITERAL, reception.getVisibility());
		assertEquals(signal, reception.getSignal());
	}

	public static String RECEPTION_UPDATE_TEXT = "abstract active class Test { public signal S { public a : Integer; } private receive S; }";

	@Test
	public void testReceptionUpdate() throws ParsingError, MappingError {
		Class testClass = compileTestReception();
		Signal signal = (Signal) testClass.getNestedClassifier("S");
		Reception reception = testClass.getOwnedReception("S", null, null);

		compiler.compile(testClass, RECEPTION_UPDATE_TEXT);

		assertEquals("S", reception.getName());
		assertEquals(VisibilityKind.PRIVATE_LITERAL, reception.getVisibility());
		assertEquals(signal, reception.getSignal());
		assertProperty(signal.getOwnedAttributes().get(0),
				VisibilityKind.PUBLIC_LITERAL, "a", 1, 1, false, true, "Integer");
	}
	
	public static String TEST_CLASSIFIER_BEHAVOR_TEXT = "active class Test { public p() { } } do { this.p(); }";
	public static String CLASSIFIER_BEHAVIOR_ACTIVITY_TEXT = "activity 'Test$behavior$1'() {\n\tthis.p();\n}";
	
	public static Class compileTestClassifierBehavior() throws ParsingError, MappingError {
		return compileClass(TEST_CLASSIFIER_BEHAVOR_TEXT);
	}
	
	@Test 
	public void testNewClassifierBehavior() throws ParsingError, MappingError {
		Class testClass = compileTestClassifierBehavior();
		
		assertTextualRepresentation(testClass, TEST_CLASSIFIER_BEHAVOR_TEXT);
		
		Behavior classifierBehavior = testClass.getClassifierBehavior();
		assertNotNull(classifierBehavior);
		assertTrue(testClass.getOwnedBehaviors().contains(classifierBehavior));
		assertEquals(testClass.getName() + "$behavior$1", classifierBehavior.getName());
		assertEquals(VisibilityKind.PRIVATE_LITERAL, classifierBehavior.getVisibility());
		assertTrue(classifierBehavior instanceof Activity);
		assertTextualRepresentation(classifierBehavior, CLASSIFIER_BEHAVIOR_ACTIVITY_TEXT);
	}

	@Test
	public void testClassifierBehaviorRecompile() throws ParsingError, MappingError {
		Class testClass = compileTestClassifierBehavior();
		
		Behavior classifierBehavior = testClass.getClassifierBehavior();
		assertNotNull(classifierBehavior);
		assertTrue(classifierBehavior instanceof Activity);
		Operation operation = testClass.getOperation("p", new BasicEList<String>(), new BasicEList<Type>());
		assertNotNull(operation);
		
		compile(classifierBehavior, compiler.getTextualRepresentation(classifierBehavior), testClass);		
		assertEquals(testClass.getClassifierBehavior(), classifierBehavior);
		assertEquals(VisibilityKind.PRIVATE_LITERAL, classifierBehavior.getVisibility());

		// Check that "this" is still resolved correctly.
		Activity activity = (Activity)classifierBehavior;
		assertEquals(1, activity.getStructuredNodes().size());
		StructuredActivityNode body = activity.getStructuredNodes().get(0);
		assertEquals(1, body.getNodes().size());
		ActivityNode node = body.getNodes().get(0);
		assertTrue(node instanceof StructuredActivityNode);
		StructuredActivityNode statement = (StructuredActivityNode)node;
		CallOperationAction action = null;
		for (ActivityNode activityNode: statement.getNodes()) {
			if (activityNode instanceof CallOperationAction) {
				action = (CallOperationAction)activityNode;
				break;
			}
		}
		assertNotNull(action);
		assertEquals(operation, action.getOperation());
	}
	
	public static String TEST_ACTIVE_CLASS_TEXT = "active class Test { public p() { } } do 'Test$behavior$1'";
	
	@Test
	public void testActiveClassRecompile() throws ParsingError, MappingError {
		Class testClass = compileTestClassifierBehavior();
		
		Behavior classifierBehavior = testClass.getClassifierBehavior();
		assertTextualRepresentation(classifierBehavior, CLASSIFIER_BEHAVIOR_ACTIVITY_TEXT);
		
		compiler.compile(testClass, TEST_ACTIVE_CLASS_TEXT);

		assertTextualRepresentation(testClass, TEST_ACTIVE_CLASS_TEXT);
		assertEquals(classifierBehavior, testClass.getClassifierBehavior());
		assertTrue(testClass.getOwnedBehaviors().contains(classifierBehavior));
		assertEquals(testClass.getName() + "$behavior$1", classifierBehavior.getName());
		assertEquals(VisibilityKind.PRIVATE_LITERAL, classifierBehavior.getVisibility());
		assertTextualRepresentation(classifierBehavior, CLASSIFIER_BEHAVIOR_ACTIVITY_TEXT);
		assertTrue(classifierBehavior instanceof Activity);
		assertTrue(((Activity)classifierBehavior).getNodes().size() > 0);
	}
	
	public static String TEST_SIGNAL_REFERENCE_TEXT = 
			"package P { public signal S { } public active class A { public receive S; } do { accept(S); } }";
	
	public static Package compileSignalReferencePackage() throws ParsingError, MappingError {
		return compilePackage(TEST_SIGNAL_REFERENCE_TEXT);
	}
	
    public static void assertSignalReference(Class testClass, Signal signal) {
		Behavior classifierBehavior = testClass.getClassifierBehavior();
		assertTrue(classifierBehavior instanceof Activity);
		
		Activity activity = (Activity)classifierBehavior;
		assertEquals(1, activity.getStructuredNodes().size());
		StructuredActivityNode body = activity.getStructuredNodes().get(0);
		assertEquals(1, body.getNodes().size());
		ActivityNode node = body.getNodes().get(0);
		assertTrue(node instanceof StructuredActivityNode);
		StructuredActivityNode statement = (StructuredActivityNode)node;
		
		AcceptEventAction action = null;
		for (ActivityNode activityNode: statement.getNodes()) {
			if (activityNode instanceof AcceptEventAction) {
				action = (AcceptEventAction)activityNode;
				break;
			}
		}
		assertNotNull(action);
		assertEquals(1, action.getTriggers().size());
		Trigger trigger = action.getTriggers().get(0);
		assertTrue(trigger.getEvent() instanceof SignalEvent);
		assertEquals(signal, ((SignalEvent)trigger.getEvent()).getSignal());		
    }
	
	@Test
	public void testSignalReference() throws ParsingError, MappingError {
		Package testPackage = compileSignalReferencePackage();
    	PackageableElement member = testPackage.getPackagedElement("A");
    	assertTrue(member instanceof Class);
    	Class testClass = (Class)member;
    	
		member = testPackage.getPackagedElement("S");
		assertNotNull(member);
		assertTrue(member instanceof Signal);
		Signal signal = (Signal)member;
		
		assertSignalReference(testClass, signal);
	}
	
	@Test
	public void testSignalReferenceRecompile() throws ParsingError, MappingError {
		Package testPackage = compileSignalReferencePackage();
    	PackageableElement member = testPackage.getPackagedElement("A");
    	assertTrue(member instanceof Class);
    	Class testClass = (Class)member;
		Behavior classifierBehavior = testClass.getClassifierBehavior();
		assertNotNull(classifierBehavior);

		compile(classifierBehavior, compiler.getTextualRepresentation(classifierBehavior), testClass);
		
		member = testPackage.getPackagedElement("S");
		assertNotNull(member);
		assertTrue(member instanceof Signal);
		Signal signal = (Signal)member;
		
		assertSignalReference(testClass, signal);
	}
}
