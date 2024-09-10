/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.textedit.valuespecification.tests.suites;


import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.xtext.AbstractGrammarTest;
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.ui.contribution.MultiplicityLowerValueSpecificationXtextDirectEditorConfiguration;
import org.eclipse.papyrus.uml.xtext.integration.DefaultXtextDirectEditorConfiguration;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@PluginResource("/model/xtextValueSpecificationModel.uml")
public class MultiplicityLowerValueSpecificationGrammarTests extends AbstractGrammarTest<EStructuralFeature> {

	/**
	 * The root model.
	 */
	protected Model rootModel;

	/**
	 * The tested property.
	 */
	protected Property testedProperty;

	@Before
	public void loadTestModel() {
		rootModel = findElement(Model.class, "model");

		final Class createdClass = (Class) rootModel.createPackagedElement("Class1", UMLPackage.eINSTANCE.getClass_());
		testedProperty = createdClass.createOwnedAttribute("Property", null, UMLPackage.eINSTANCE.getProperty());
	}

	@Test
	public void testParser() throws Exception {

		// Check literal integer (even if this is superior to 0
		tester.parseText(testedProperty, UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE, "#unlimitedNatural=12");
		Assert.assertEquals(VisibilityKind.PROTECTED_LITERAL, testedProperty.getLowerValue().getVisibility());
		Assert.assertEquals("unlimitedNatural", testedProperty.getLowerValue().getName());
		Assert.assertEquals(UMLPackage.eINSTANCE.getLiteralInteger(), testedProperty.getLowerValue().eClass());
		Assert.assertEquals(12, ((LiteralInteger) testedProperty.getLowerValue()).getValue());

		// Check the update of the LiteralInteger
		tester.parseText(testedProperty, UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE, "--8");
		Assert.assertEquals(VisibilityKind.PRIVATE_LITERAL, testedProperty.getLowerValue().getVisibility());
		Assert.assertEquals("unlimitedNatural", testedProperty.getLowerValue().getName());
		Assert.assertEquals(UMLPackage.eINSTANCE.getLiteralInteger(), testedProperty.getLowerValue().eClass());
		Assert.assertEquals(-8, ((LiteralInteger) testedProperty.getLowerValue()).getValue());

		// Check literal integer (even if this is superior to 0
		tester.parseText(testedProperty, UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE, "+integer=*");
		Assert.assertEquals(VisibilityKind.PRIVATE_LITERAL, testedProperty.getLowerValue().getVisibility());
		Assert.assertEquals("+integer=*", testedProperty.getLowerValue().getName());
		Assert.assertEquals(UMLPackage.eINSTANCE.getOpaqueExpression(), testedProperty.getLowerValue().eClass());

	}

	@Override
	public DefaultXtextDirectEditorConfiguration getEditor() {
		return new MultiplicityLowerValueSpecificationXtextDirectEditorConfiguration();
	}
}
