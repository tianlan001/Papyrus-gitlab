/*****************************************************************************
 * Copyright (c) 2010 CEA
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
 *   Soyatec - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.tests.bug.m7;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.LifelineEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.MessageCreateEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.tests.ISequenceDiagramTestsConstants;
import org.junit.Test;

/**
 * Create a lifeline if the target of Create message is not selected.
 *
 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=403134
 * @author Jin Liu (jin.liu@soyatec.com)
 */
public class TestMessageCreateWithLifeline_403134 extends AbstractNodeTest {

	@Override
	protected String getProjectName() {
		return ISequenceDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return ISequenceDiagramTestsConstants.FILE_NAME;
	}

	@FailingTest ("To be erased or rewritten to take new architecture into account")
	@Test
	public void test() {
		LifelineEditPart lifeline = (LifelineEditPart)createNode(UMLElementTypes.Lifeline_Shape, getRootEditPart(), new Point(100, 100), null);
		assertNotNull(lifeline);
		Point fromLocation = getAbsoluteBounds(lifeline).getTop().translate(0, 50);
		EditPart editPart = createLink(UMLElementTypes.Message_CreateEdge, lifeline.getViewer(), fromLocation, fromLocation.getTranslated(100, 0));
		//check result
		assertNotNull("editpart not found", editPart);
		assertTrue("not a messageCreate editpart", editPart instanceof MessageCreateEditPart);
		EditPart target = ((MessageCreateEditPart)editPart).getTarget();
		assertTrue("the target is not a lifeline", target instanceof LifelineEditPart);
	}
}
