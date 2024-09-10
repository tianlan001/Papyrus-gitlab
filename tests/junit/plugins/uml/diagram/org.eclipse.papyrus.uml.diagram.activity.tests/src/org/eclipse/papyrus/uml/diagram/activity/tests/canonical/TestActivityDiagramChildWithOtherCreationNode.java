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
package org.eclipse.papyrus.uml.diagram.activity.tests.canonical;

import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.activity.tests.IActivityDiagramTestsConstants;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Ignore;
import org.junit.Test;


/**
 * The Class TestClassDiagramChildNode.
 */
public class TestActivityDiagramChildWithOtherCreationNode extends AbstractTestActivityChildNode {

	@Override
	protected String getProjectName() {
		return IActivityDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}

	@Override
	protected String getFileName() {
		return IActivityDiagramTestsConstants.FILE_NAME;
	}

	/**
	 * Test to manage callbehaviorAction
	 */
	@Test
	@Ignore
	public void testToManageCallbehaviorAction() {
		testToManageNode(UMLElementTypes.CallBehaviorAction_Shape, UMLPackage.eINSTANCE.getCallBehaviorAction(), UMLElementTypes.StructuredActivityNode_Shape, false);
	}

	/**
	 * Test to manage callOperationAction
	 */
	@Ignore
	@Test
	public void testToManageCallOperationAction() {
		testToManageNode(UMLElementTypes.CallOperationAction_Shape, UMLPackage.eINSTANCE.getCallOperationAction(), UMLElementTypes.StructuredActivityNode_Shape, false);
	}

	/**
	 * Test to manage SendSignalAction Node.
	 */
	@Ignore
	@Test
	public void testToManageSendSignalAction() {
		testToManageNode(UMLElementTypes.SendSignalAction_Shape, UMLPackage.eINSTANCE.getSendSignalAction(), UMLElementTypes.StructuredActivityNode_Shape, false);
	}

	/**
	 * Test to SendObjectAction Node.
	 */
	@Test
	public void testToManageSendObjectAction() {
		testToManageNode(UMLElementTypes.SendObjectAction_Shape, UMLPackage.eINSTANCE.getSendObjectAction(), UMLElementTypes.StructuredActivityNode_Shape, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CreateViewRequest createViewRequestShapeContainer() {
		return null;
	}


}
