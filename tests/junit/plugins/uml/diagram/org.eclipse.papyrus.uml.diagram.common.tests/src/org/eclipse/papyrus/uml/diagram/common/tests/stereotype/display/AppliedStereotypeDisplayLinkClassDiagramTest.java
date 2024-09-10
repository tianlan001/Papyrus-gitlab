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

import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.IPapyrusEditPart;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.common.editparts.UMLConnectionNodeEditPart;
import org.eclipse.papyrus.uml.diagram.common.figure.edge.UMLEdgeFigure;
import org.eclipse.uml2.uml.Association;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * This Class tests the application and unapplication of stereotype on Links
 *
 * @author Céline JANSSENS
 *
 */
@SuppressWarnings("nls")
@PluginResource("/resources/StereotypeDisplay/StereotypeDisplayClassDiagramModel.di")
public class AppliedStereotypeDisplayLinkClassDiagramTest extends AbstractAppliedStereotypeDisplayTest {

	@Test
	public void testApplyStereotypeOnLink() {

		setElementAndTest(ASSOCIATION_NAME, Association.class);

		// Apply 1 stereotypes and test structure and Label content
		applyStereotype(Arrays.asList(stereotypeA));
		testStructure(1, 0, 0, 1);
		testLinkLabelContent(String.valueOf("\u00AB") + "stereoA" + String.valueOf("\u00BB"));
		testOrphanComment(1);

		// UnApply 1 stereotype and test structure and Label content
		unapplyStereotype(Arrays.asList(stereotypeA));
		testStructure(0, 0, 0, 0);
		testLinkLabelContent("");
		testOrphanComment(0);

	}

	/**
	 * Test the stereotype Link Label Text and compare it to the expected String
	 *
	 * @param string
	 */
	protected void testLinkLabelContent(String expectedLabel) {
		// Check the Label content
		Assert.assertNotNull(editPart);
		Assert.assertTrue(editPart instanceof UMLConnectionNodeEditPart);
		Assert.assertTrue(((IPapyrusEditPart) editPart).getPrimaryShape() instanceof UMLEdgeFigure);
		WrappingLabel label = ((UMLEdgeFigure) ((IPapyrusEditPart) editPart).getPrimaryShape()).getAppliedStereotypeLabel();

		if (expectedLabel == null) {
			Assert.assertNull("Label stereotype should be null", label);
		} else {
			Assert.assertNotNull("The Label Stereotype should not be null ", label);
			String labelText = label.getText();
			Assert.assertEquals("The label content is not the one expected", expectedLabel, labelText);

		}

	}


	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.tests.stereotype.display.AbstractAppliedStereotypeDisplayTest#testLabel(java.lang.String)
	 *
	 * @param expectedLabel
	 *            Expected Text for the Label
	 */
	@Override
	protected void testLabel(String expectedLabel) {
		testLinkLabelContent(expectedLabel);

	}



}
