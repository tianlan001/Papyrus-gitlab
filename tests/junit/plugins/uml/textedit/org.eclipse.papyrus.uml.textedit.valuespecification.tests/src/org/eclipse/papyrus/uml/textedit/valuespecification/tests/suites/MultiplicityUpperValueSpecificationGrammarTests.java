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
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.ui.contribution.MultiplicityUpperValueSpecificationXtextDirectEditorConfiguration;
import org.eclipse.papyrus.uml.xtext.integration.DefaultXtextDirectEditorConfiguration;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@PluginResource("/model/xtextValueSpecificationModel.uml")
public class MultiplicityUpperValueSpecificationGrammarTests extends AbstractGrammarTest<EStructuralFeature> {

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
		tester.parseText(testedProperty, UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE, "#unlimitedNatural=12");
		Assert.assertEquals(VisibilityKind.PROTECTED_LITERAL, testedProperty.getUpperValue().getVisibility());
		Assert.assertEquals("unlimitedNatural", testedProperty.getUpperValue().getName());
		Assert.assertEquals(UMLPackage.eINSTANCE.getLiteralUnlimitedNatural(), testedProperty.getUpperValue().eClass());
		Assert.assertEquals(12, ((LiteralUnlimitedNatural) testedProperty.getUpperValue()).getValue());

		// Check the update of the LiteralInteger
		tester.parseText(testedProperty, UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE, "*");
		Assert.assertEquals(VisibilityKind.PROTECTED_LITERAL, testedProperty.getUpperValue().getVisibility());
		Assert.assertEquals("unlimitedNatural", testedProperty.getUpperValue().getName());
		Assert.assertEquals(UMLPackage.eINSTANCE.getLiteralUnlimitedNatural(), testedProperty.getUpperValue().eClass());
		Assert.assertEquals(-1, ((LiteralUnlimitedNatural) testedProperty.getUpperValue()).getValue());

		// Check literal integer (even if this is superior to 0
		tester.parseText(testedProperty, UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE, "-integer=-8");
		Assert.assertEquals(VisibilityKind.PROTECTED_LITERAL, testedProperty.getUpperValue().getVisibility());
		Assert.assertEquals("-integer=-8", testedProperty.getUpperValue().getName());
		Assert.assertEquals(UMLPackage.eINSTANCE.getOpaqueExpression(), testedProperty.getUpperValue().eClass());

	}

	@Override
	public DefaultXtextDirectEditorConfiguration getEditor() {
		return new MultiplicityUpperValueSpecificationXtextDirectEditorConfiguration();
	}
}
