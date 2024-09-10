/**
 * Copyright (c) 2017 CEA LIST.
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
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.uml.expressions.tests;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.expressions.umlexpressions.IsKindOfStereotypeExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsFactory;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * 
 * Tests for {@link IsKindOfStereotypeExpression}
 *
 */
@PluginResource("resources/expressionModelTest.di") //$NON-NLS-1$
public class IsKindOfStereotypeExpressionTests {

	private final EObject dummyEObject = EcoreFactory.eINSTANCE.createEClass();

	private final String INVALID_STRING_QN = "Invalid::Qualified::Name"; //$NON-NLS-1$

	private final String STEREOTYPED_CLASS_WITH_URI_NAME = "StereotypedWithURIClass"; //$NON-NLS-1$

	private final String STEREOTYPED_CLASS_WITHOUT_URI_NAME = "StereotypedWithoutURIClass"; //$NON-NLS-1$

	private final String STEREOTYPED_PROPERTY = "StereotypedWithURIProperty"; //$NON-NLS-1$
	
	private final String STEREOTYPED_CLASS_WITH_CHILD_STEREOTYPE = "StereotypedWithChildStereotype"; //$NON-NLS-1$

	private final String NOT_STEREOTYPED_CLASS_NAME = "NoStereotypedClass"; //$NON-NLS-1$

	private final String ELEMENT_STEREOTYPE_QUALIFIED_NAME = "RootElement::ANiceStereotypeForElement"; //$NON-NLS-1$
	
	private final String ELEMENT_CHILD_STEREOTYPE_QUALIFIED_NAME = "RootElement::AChildNiceStereotypeForElement"; //$NON-NLS-1$
	

	private final String PROPERTY_STEREOTYPED_QUALIFIED_NAME = "RootElement::ANiceStereotypeForProperty"; //$NON-NLS-1$

	private Class stereotypedClassWithURI;

	private Class stereotypedClassWithoutURI;

	private Class notStereotypedClass;
	
	private Class childStereotypedClass;

	private Property stereotypedProperty;

	private final String PROFILE_URI = "papyrusExpressionProfile"; //$NON-NLS-1$

	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();


	@Before
	public void init() {
		stereotypedClassWithURI = (Class) fixture.getModel().getMember(STEREOTYPED_CLASS_WITH_URI_NAME);
		stereotypedClassWithoutURI = (Class) fixture.getModel().getMember(STEREOTYPED_CLASS_WITHOUT_URI_NAME);
		notStereotypedClass = (Class) fixture.getModel().getMember(NOT_STEREOTYPED_CLASS_NAME);
		stereotypedProperty = (Property) stereotypedClassWithURI.getOwnedMember(STEREOTYPED_PROPERTY);
		childStereotypedClass = (Class) fixture.getModel().getMember(STEREOTYPED_CLASS_WITH_CHILD_STEREOTYPE);
	}

	/**
	 * The evaluate method must return <code>false</code> for these elements
	 * 
	 * <ul>
	 * <li>profileURI is <code>null</code>
	 * <li>
	 * <li>stereotypeQN is <code>null</code></li>
	 * <li>context is <code>null</code></li>
	 * </ul>
	 */
	@Test
	public void IsKindOfStereotypeExpression_Test1() {
		final IsKindOfStereotypeExpression exp = UMLExpressionsFactory.eINSTANCE.createIsKindOfStereotypeExpression();
		Assert.assertFalse(exp.evaluate(null));
	}

	/**
	 * The evaluate method must return <code>false</code> for these elements
	 * 
	 * <ul>
	 * <li>profileURI is <code>null</code></li>
	 * <li>stereotypeQN is <code>null</code></li>
	 * <li>context is an EObject, but not an UML Element</li>
	 * </ul>
	 */
	@Test
	public void IsKindOfStereotypeExpression_Test2() {
		final IsKindOfStereotypeExpression exp = UMLExpressionsFactory.eINSTANCE.createIsKindOfStereotypeExpression();
		Assert.assertFalse(exp.evaluate(dummyEObject));
	}


	/**
	 * The evaluate method must return <code>false</code> for these elements
	 * 
	 * <ul>
	 * <li>profileURI is <code>null</code></li>
	 * <li>stereotypeQN is invalid</li>
	 * <li>context is <code>null</code></li>
	 * </ul>
	 */
	@Test
	public void IsKindOfStereotypeExpression_Test3() {
		final IsKindOfStereotypeExpression exp = UMLExpressionsFactory.eINSTANCE.createIsKindOfStereotypeExpression();
		exp.setStereotypeQualifiedName(INVALID_STRING_QN);
		Assert.assertFalse(exp.evaluate(null));
	}

	/**
	 * The evaluate method must return <code>false</code> for these elements
	 * 
	 * <ul>
	 * <li>profileURI is <code>null</code></li>
	 * <li>stereotypeQN is invalid</li>
	 * <li>context is an EObject, but not an UML Element</li>
	 * </ul>
	 */
	@Test
	public void IsKindOfStereotypeExpression_Test4() {
		final IsKindOfStereotypeExpression exp = UMLExpressionsFactory.eINSTANCE.createIsKindOfStereotypeExpression();
		exp.setStereotypeQualifiedName(INVALID_STRING_QN);
		Assert.assertFalse(exp.evaluate(dummyEObject));
	}

	/**
	 * The evaluate method must return <code>true</code> for these elements
	 * 
	 * <ul>
	 * <li>profileURI is <code>null</code></li>
	 * <li>stereotypeQN is valid</li>
	 * <li>context is UML Stereotyped element with the expected stereotype</li>
	 * </ul>
	 */
	@Test
	public void IsKindOfStereotypeExpression_Test5() {
		final IsKindOfStereotypeExpression exp = UMLExpressionsFactory.eINSTANCE.createIsKindOfStereotypeExpression();
		exp.setStereotypeQualifiedName(ELEMENT_STEREOTYPE_QUALIFIED_NAME);
		Assert.assertTrue(exp.evaluate(this.stereotypedClassWithURI));
	}

	/**
	 * The evaluate method must return <code>false</code> for these elements
	 * 
	 * <ul>
	 * <li>profileURI is <code>null</code></li>
	 * <li>stereotypeQN is valid</li>
	 * <li>context is UML Stereotyped element but not with the expected property</li>
	 * </ul>
	 */
	@Test
	public void IsKindOfStereotypeExpression_Test6() {
		final IsKindOfStereotypeExpression exp = UMLExpressionsFactory.eINSTANCE.createIsKindOfStereotypeExpression();
		exp.setStereotypeQualifiedName(ELEMENT_STEREOTYPE_QUALIFIED_NAME);
		Assert.assertFalse(exp.evaluate(stereotypedProperty));
	}

	/**
	 * The evaluate method must return <code>false</code> for these elements
	 * 
	 * <ul>
	 * <li>profileURI is <code>null</code></li>
	 * <li>stereotypeQN is valid</li>
	 * <li>context is UML element not stereotyped</li>
	 * </ul>
	 */
	@Test
	public void IsKindOfStereotypeExpression_Test7() {
		final IsKindOfStereotypeExpression exp = UMLExpressionsFactory.eINSTANCE.createIsKindOfStereotypeExpression();
		exp.setStereotypeQualifiedName(ELEMENT_STEREOTYPE_QUALIFIED_NAME);
		Assert.assertFalse(exp.evaluate(notStereotypedClass));
	}

	/**
	 * The evaluate method must return <code>false</code> for these elements
	 * 
	 * <ul>
	 * <li>profileURI is <code>null</code></li>
	 * <li>stereotypeQN is valid</li>
	 * <li>context is UML element, but the stereotyped with the expected stereotype</li>
	 * </ul>
	 */
	@Test
	public void IsKindOfStereotypeExpression_Test8() {
		final IsKindOfStereotypeExpression exp = UMLExpressionsFactory.eINSTANCE.createIsKindOfStereotypeExpression();
		exp.setStereotypeQualifiedName(PROPERTY_STEREOTYPED_QUALIFIED_NAME);
		Assert.assertFalse(exp.evaluate(stereotypedClassWithURI));
	}

	/**
	 * The evaluate method must return <code>true</code> for these elements
	 * 
	 * <ul>
	 * <li>profileURI is <code>null</code></li>
	 * <li>stereotypeQN is valid</li>
	 * <li>context is UML element, stereotyped with the expected stereotype</li>
	 * </ul>
	 */
	@Test
	public void IsKindOfStereotypeExpression_Test9() {
		final IsKindOfStereotypeExpression exp = UMLExpressionsFactory.eINSTANCE.createIsKindOfStereotypeExpression();
		exp.setStereotypeQualifiedName(PROPERTY_STEREOTYPED_QUALIFIED_NAME);
		Assert.assertTrue(exp.evaluate(stereotypedProperty));
	}

	/**
	 * The evaluate method must return <code>true</code> for these elements
	 * 
	 * <ul>
	 * <li>profileURI is not <code>null</code></li>, but empty
	 * <li>stereotypeQN is valid</li>
	 * <li>context is UML element, stereotyped with the expected stereotype</li>
	 * </ul>
	 */
	@Test
	public void IsKindOfStereotypeExpression_Test10() {
		final IsKindOfStereotypeExpression exp = UMLExpressionsFactory.eINSTANCE.createIsKindOfStereotypeExpression();
		exp.setProfileURI(""); //$NON-NLS-1$
		exp.setStereotypeQualifiedName(PROPERTY_STEREOTYPED_QUALIFIED_NAME);
		Assert.assertTrue(exp.evaluate(stereotypedProperty));
	}

	/**
	 * The evaluate method must return <code>false</code> for these elements
	 * 
	 * <ul>
	 * <li>profileURI is <code>null</code></li>
	 * <li>stereotypeQN is valid</li>
	 * <li>context is UML element,nto stereotyped</li>
	 * </ul>
	 */
	@Test
	public void IsKindOfStereotypeExpression_Test11() {
		final IsKindOfStereotypeExpression exp = UMLExpressionsFactory.eINSTANCE.createIsKindOfStereotypeExpression();
		exp.setStereotypeQualifiedName(PROPERTY_STEREOTYPED_QUALIFIED_NAME);
		Assert.assertFalse(exp.evaluate(notStereotypedClass));
	}

	/**
	 * The evaluate method must return <code>false</code> for these elements
	 * 
	 * <ul>
	 * <li>profileURI is <code>null</code></li>
	 * <li>stereotypeQN is valid</li>
	 * <li>context is UML element stereotyped by a stereotype with the expected qualified name, but from the bas profile</li>
	 * </ul>
	 */
	@Test
	public void IsKindOfStereotypeExpression_Test12() {
		final IsKindOfStereotypeExpression exp = UMLExpressionsFactory.eINSTANCE.createIsKindOfStereotypeExpression();
		exp.setStereotypeQualifiedName(ELEMENT_STEREOTYPE_QUALIFIED_NAME);
		exp.setProfileURI(PROFILE_URI);
		Assert.assertFalse(exp.evaluate(this.stereotypedClassWithoutURI));
	}

	/**
	 * The evaluate method must return <code>true</code> for these elements
	 * 
	 * <ul>
	 * <li>profileURI is <code>null</code></li>
	 * <li>stereotypeQN is valid</li>
	 * <li>context is UML element stereotyped by a stereotype with the expected qualified name, but from the bas profile</li>
	 * </ul>
	 */
	@Test
	public void IsKindOfStereotypeExpression_Test13() {
		final IsKindOfStereotypeExpression exp = UMLExpressionsFactory.eINSTANCE.createIsKindOfStereotypeExpression();
		exp.setStereotypeQualifiedName(ELEMENT_STEREOTYPE_QUALIFIED_NAME);
		exp.setProfileURI(PROFILE_URI);
		Assert.assertTrue(exp.evaluate(this.stereotypedClassWithURI));
	}
	
	/**
	 * The evaluate method must return <code>true</code> for these elements
	 * 
	 * <ul>
	 * <li>profileURI is <code>null</code></li>
	 * <li>stereotypeQN is valid</li>
	 * <li>context is UML element stereotyped by a child stereotype and the parent stereotype is defined for the qualified name</li>
	 * </ul>
	 */
	@Test
	public void IsKindOfStereotypeExpression_Test14() {
		final IsKindOfStereotypeExpression exp = UMLExpressionsFactory.eINSTANCE.createIsKindOfStereotypeExpression();
		exp.setStereotypeQualifiedName(ELEMENT_STEREOTYPE_QUALIFIED_NAME);
		Assert.assertTrue(exp.evaluate(this.childStereotypedClass));
	}
}
