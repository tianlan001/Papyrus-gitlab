/*****************************************************************************
 * Copyright (c) 2015, 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and Implementation
 *	 Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Bug 471584 : Stereotype Display Unit Tests
 *   Celine Janssens (All4Tec) celine.janssens@all4tec.net - Bug 472342
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.tests.stereotype.display;

import java.util.Arrays;

import org.eclipse.draw2d.IFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.IPapyrusEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.PapyrusWrappingLabel;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.common.figure.node.NodeNamedElementFigure;
import org.eclipse.uml2.uml.Enumeration;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * This Class tests the Stereotype Display of a Class Diagram
 *
 * @author Céline JANSSENS
 *
 */
@PluginResource("/resources/StereotypeDisplay/StereotypeDisplayClassDiagramModel.di")
public class AppliedStereotypeDisplayNodeClassDiagramTest extends AbstractAppliedStereotypeDisplayTest {


	/**
	 * Test the apply and unapply stereotype on a class
	 */
	@Test
	public void testApplyStereotypeOnClass() {

		setElementAndTest(CLASS_NAME, org.eclipse.uml2.uml.Class.class);

		// Apply 2 stereotypes and test structure and Label content
		applyStereotype(Arrays.asList(stereotypeA, stereotypeB));
		testStructure(2, 2, 2, 1);
		testNodeLabelContent(getStereotypeNameWithItsDelimiters(STEREO_A, STEREO_B));
		testOrphanComment(1);


		// UnApply 1 stereotype and test structure and Label content
		unapplyStereotype(Arrays.asList(stereotypeB));
		testStructure(1, 1, 1, 1);
		testNodeLabelContent(getStereotypeNameWithItsDelimiters(STEREO_A));
		testOrphanComment(1);

		// UnApply 1 stereotype and test structure and Label content
		unapplyStereotype(Arrays.asList(stereotypeA));
		testStructure(0, 0, 0, 0);
		testNodeLabelContent(null);
		testOrphanComment(0);

	}

	/**
	 * Test the apply and unapply stereotype on a Interface
	 */
	@Test
	public void testApplyStereotypeOnInterface() {
		// Initialize data for test case
		setElementAndTest(INTERFACE_NAME, org.eclipse.uml2.uml.Interface.class);

		// Apply 1 stereotypes and test structure and Label content
		applyStereotype(Arrays.asList(stereotypeA));
		testStructure(1, 1, 1, 1);
		testNodeLabelContent(getStereotypeNameWithItsDelimiters(STEREO_A));
		testOrphanComment(1);


		// UnApply 1 stereotype and test structure and Label content
		unapplyStereotype(Arrays.asList(stereotypeA));
		testStructure(0, 0, 0, 0);
		testNodeLabelContent(null);
		testOrphanComment(0);

	}

	/**
	 * Test the apply and unapply stereotype on a Enumeration
	 */
	@Test
	public void testApplyStereotypeOnEnumeration() {
		// Initialize data for test case
		setElementAndTest(ENUMERATION_NAME, Enumeration.class);


		// Apply 1 stereotypes and test structure and Label content
		applyStereotype(Arrays.asList(stereotypeA));
		testStructure(1, 1, 1, 1);
		testNodeLabelContent(getStereotypeNameWithItsDelimiters(STEREO_A));
		testOrphanComment(1);


		// UnApply 1 stereotype and test structure and Label content
		unapplyStereotype(Arrays.asList(stereotypeA));
		testStructure(0, 0, 0, 0);
		testNodeLabelContent(null);
		testOrphanComment(0);

	}


	/**
	 * Test the apply and unapply stereotype on a Interface
	 */
	@Test
	public void testApplyStereotypeOnPackage() {
		// Initialize data for test case
		setElementAndTest(PACKAGE_NAME, org.eclipse.uml2.uml.Package.class);


		// Apply 1 stereotypes and test structure and Label content
		applyStereotype(Arrays.asList(stereotypeB));
		testStructure(1, 1, 1, 1);
		testNodeLabelContent(getStereotypeNameWithItsDelimiters(STEREO_B));
		testOrphanComment(1);


		// UnApply 1 stereotype and test structure and Label content
		unapplyStereotype(Arrays.asList(stereotypeB));
		testStructure(0, 0, 0, 0);
		testNodeLabelContent(null);
		testOrphanComment(0);

	}


	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.tests.stereotype.display.AbstractAppliedStereotypeDisplayTest#testLabel(java.lang.String)
	 *
	 * @param expectedLabel
	 *            Expected text of the Label
	 */
	@Override
	protected void testLabel(final String expectedLabel) {
		testNodeLabelContent(expectedLabel);

	}

	/**
	 * Tests the Content of Named Node Label
	 *
	 * @param expectedLabel
	 *            The expected label Text
	 */
	protected void testNodeLabelContent(final String expectedLabel) {
		// Check the Label content
		Assert.assertNotNull(editPart);
		Assert.assertTrue(editPart instanceof IPapyrusEditPart);
		final IFigure figure = ((IPapyrusEditPart) editPart).getPrimaryShape();
		Assert.assertTrue(figure instanceof NodeNamedElementFigure);
		PapyrusWrappingLabel label = ((NodeNamedElementFigure) figure).getStereotypesLabel();

		if (expectedLabel == null) {
			Assert.assertNull("Label stereotype should be null", label);
		} else {
			Assert.assertNotNull("The Label Stereotype should not be null ", label);
			String labelText = label.getText();
			Assert.assertEquals("The label content is not the one expected", expectedLabel, labelText);

		}

	}



}
