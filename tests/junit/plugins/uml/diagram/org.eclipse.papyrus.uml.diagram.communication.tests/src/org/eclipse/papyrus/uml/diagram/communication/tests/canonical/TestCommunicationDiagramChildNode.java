/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.communication.tests.canonical;

import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.uml.diagram.communication.CreateCommunicationDiagramCommand;
import org.eclipse.papyrus.uml.diagram.communication.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.communication.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.communication.tests.ICommunicationDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestChildNode;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

/**
 * The Class TestClassDiagramChildNode.
 */
public class TestCommunicationDiagramChildNode extends TestChildNode {

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}
	@Override
	protected CreateViewRequest createViewRequestShapeContainer() {
		return null;
	}

	@Override
	public boolean isTestAffixedNode() {
		return false;
	}
	@Override
	protected String getProjectName() {
		return ICommunicationDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return ICommunicationDiagramTestsConstants.FILE_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isSemanticTest() {
		return false;
	}
	/**
	* Test to manage Lifeline.
	*/
	@Test
	public void testToLifeline() {
		testToManageNode(UMLElementTypes.Lifeline_Shape, UMLPackage.eINSTANCE.getLifeline(), UMLElementTypes.Interaction_Shape, false);
	}
	/**
	* Test to manage Constraint.
	*/
	@Test
	public void testToConstraint() {
		testToManageNode(UMLElementTypes.Constraint_Shape, UMLPackage.eINSTANCE.getConstraint(), UMLElementTypes.Interaction_Shape, false);
	}
	/**
	* Test to manage Comment.
	*/
	@Test
	public void testToComment() {
		testToManageNode(UMLElementTypes.Comment_Shape, UMLPackage.eINSTANCE.getComment(), UMLElementTypes.Interaction_Shape, false);
	}
	/**
	* Test to manage TimeObservation.
	*/
	@Test
	@FailingTest
	public void testToTimeObservation() {
		testToManageNode(UMLElementTypes.TimeObservation_Shape, UMLPackage.eINSTANCE.getTimeObservation(), UMLElementTypes.Interaction_Shape, false);
	}
	/**
	* Test to manage DurationObservation.
	*/
	@Test
	@FailingTest
	public void testToDurationObservation() {
		testToManageNode(UMLElementTypes.DurationObservation_Shape, UMLPackage.eINSTANCE.getDurationObservation(), UMLElementTypes.Interaction_Shape, false);
	}
	
	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateCommunicationDiagramCommand();
	}
}
