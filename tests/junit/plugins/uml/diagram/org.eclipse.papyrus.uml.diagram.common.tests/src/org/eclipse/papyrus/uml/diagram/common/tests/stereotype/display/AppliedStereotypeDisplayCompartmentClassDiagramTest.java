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
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *   Celine Janssens (All4Tec) celine.janssens@all4tec.net - Bug 472342
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.tests.stereotype.display;

import java.util.Arrays;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.common.editparts.UMLCompartmentEditPart;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.junit.Assert;
import org.junit.Test;

/**
 * This Class Tests the application of stereotypes for the UML Compartment editPart into the Class Diagram
 *
 * @author Céline JANSSENS
 *
 */
@SuppressWarnings("nls")
@PluginResource("/resources/StereotypeDisplay/StereotypeDisplayClassDiagramModel.di")
public class AppliedStereotypeDisplayCompartmentClassDiagramTest extends AbstractAppliedStereotypeDisplayTest {


	/**
	 * Test the apply and unapply stereotype on a Interface
	 */
	@Test
	public void testApplyStereotypeOnEnumerationLiteral() {
		// Initialize data for test case

		setElementAndTest(ENUMERATION_ITEM_NAME, EnumerationLiteral.class);

		// Apply 1 stereotypes and test structure and Label content
		applyStereotype(Arrays.asList(stereotypeA));
		testStructure(1, 0, 1, 0);
		testLabel(String.valueOf("\u00AB") + "stereoA" + String.valueOf("\u00BB"));
		testOrphanComment(0);

		// UnApply 1 stereotype and test structure and Label content
		unapplyStereotype(Arrays.asList(stereotypeA));
		testStructure(0, 0, 0, 0);
		testCompartmentLabelContent("");
		testOrphanComment(0);

	}


	/**
	 * Test the Label of a UML Compartment element (Such as Operation , property or Enumeration Literal)
	 *
	 * @param expectedLabel
	 *            The expected Label
	 *
	 */
	protected void testCompartmentLabelContent(String expectedLabel) {
		// Check the Label content
		Assert.assertNotNull(editPart);
		Assert.assertTrue(editPart instanceof UMLCompartmentEditPart);
		String label = null;
		IFigure labelFigure = ((GraphicalEditPart) editPart).getFigure();

		Assert.assertTrue("Figure should be a PapyrusWrappingLabel", labelFigure instanceof WrappingLabel);
		label = ((WrappingLabel) labelFigure).getText();

		Assert.assertNotNull(label);
		Assert.assertTrue("The label content is not the one expected", label.startsWith(expectedLabel));

	}

	/**
	 * Test the apply and unapply stereotype on a Interface
	 */
	@Test
	public void testApplyStereotypeOnOperation() {
		// Initialize data for test case

		setElementAndTest(OPERATION_NAME, org.eclipse.uml2.uml.Operation.class);

		// Apply 1 stereotypes and test structure and Label content
		applyStereotype(Arrays.asList(stereotypeA));
		testStructure(1, 0, 1, 0);
		testCompartmentLabelContent(getStereotypeNameWithItsDelimiters(STEREO_A));
		testOrphanComment(0);

		// UnApply 1 stereotype and test structure and Label content
		unapplyStereotype(Arrays.asList(stereotypeA));
		testStructure(0, 0, 0, 0);
		testCompartmentLabelContent("");
		testOrphanComment(0);

	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.tests.stereotype.display.AbstractAppliedStereotypeDisplayTest#testLabel(java.lang.String)
	 *
	 * @param expectedLabel
	 *            The Expected text for this Label
	 */
	@Override
	protected void testLabel(String expectedLabel) {
		testCompartmentLabelContent(expectedLabel);

	}


	/**
	 * Test the apply and unapply stereotype on a property of a class
	 */
	@Test
	public void testApplyStereotypeOnProperty() {
		// Initialize data for test case

		setElementAndTest(ATTRIBUTE_NAME, org.eclipse.uml2.uml.Property.class);

		// Apply 1 stereotypes and test structure and Label content
		applyStereotype(Arrays.asList(stereotypeC));
		testStructure(1, 0, 1, 0);
		testCompartmentLabelContent(getStereotypeNameWithItsDelimiters(STEREO_C));
		testOrphanComment(0);


		// UnApply 1 stereotype and test structure and Label content
		unapplyStereotype(Arrays.asList(stereotypeC));
		testStructure(0, 0, 0, 0);
		testCompartmentLabelContent("");
		testOrphanComment(0);

	}

}
