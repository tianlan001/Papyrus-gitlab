/*****************************************************************************
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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.expressions.tests;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.expressions.umlexpressions.HasAppliedStereotypesExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsFactory;
import org.eclipse.uml2.uml.Class;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for  {@link HasAppliedStereotypesExpression}
 *
 */
@PluginResource("resources/expressionModelTest.di") //$NON-NLS-1$
public class HasAppliedStereotypeExpressionTests {

	private final EObject dummyEObject = EcoreFactory.eINSTANCE.createEClass();

	private final String STEREOTYPED_CLASS_WITH_URI_NAME = "StereotypedWithURIClass"; //$NON-NLS-1$

	private final String NOT_STEREOTYPED_CLASS_NAME = "NoStereotypedClass"; //$NON-NLS-1$

	private Class stereotypedClassWithURI;

	private Class notStereotypedClass;



	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();


	@Before
	public void init() {
		stereotypedClassWithURI = (Class) fixture.getModel().getMember(STEREOTYPED_CLASS_WITH_URI_NAME);
		notStereotypedClass = (Class) fixture.getModel().getMember(NOT_STEREOTYPED_CLASS_NAME);
	}



	/**
	 * The method evaluates must return <code>false</code> for
	 * <ul>
	 * <li>context is <code>null</code></li>
	 * </ul>
	 */
	@Test
	public void HasStereotypeExpressionTests_Test1() {
		final HasAppliedStereotypesExpression exp = UMLExpressionsFactory.eINSTANCE.createHasAppliedStereotypesExpression();
		Assert.assertFalse(exp.evaluate(null));
	}

	/**
	 * The method evaluates must return <code>false</code> for
	 * <ul>
	 * <li>context is an EObject</li>
	 * </ul>
	 */
	@Test
	public void HasStereotypeExpressionTests_Test2() {
		final HasAppliedStereotypesExpression exp = UMLExpressionsFactory.eINSTANCE.createHasAppliedStereotypesExpression();
		Assert.assertFalse(exp.evaluate(this.dummyEObject));
	}

	/**
	 * The method evaluates must return <code>true</code> for
	 * <ul>
	 * <li>context is an UML Element stereotyped</li>
	 * </ul>
	 */
	@Test
	public void HasStereotypeExpressionTests_Test3() {
		final HasAppliedStereotypesExpression exp = UMLExpressionsFactory.eINSTANCE.createHasAppliedStereotypesExpression();
		Assert.assertTrue(exp.evaluate(this.stereotypedClassWithURI));
	}

	/**
	 * The method evaluates must return <code>false</code> for
	 * <ul>
	 * <li>context is an UML Element not stereotyped</li>
	 * </ul>
	 */
	@Test
	public void HasStereotypeExpressionTests_Test4() {
		final HasAppliedStereotypesExpression exp = UMLExpressionsFactory.eINSTANCE.createHasAppliedStereotypesExpression();
		Assert.assertFalse(exp.evaluate(this.notStereotypedClass));
	}
}
