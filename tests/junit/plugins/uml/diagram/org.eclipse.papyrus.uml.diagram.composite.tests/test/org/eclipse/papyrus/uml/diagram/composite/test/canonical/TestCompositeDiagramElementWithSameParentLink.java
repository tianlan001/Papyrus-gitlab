/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
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
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.test.canonical;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.junit.framework.classification.InteractiveTest;
import org.eclipse.papyrus.uml.diagram.composite.CreateCompositeDiagramCommand;
import org.eclipse.papyrus.uml.diagram.composite.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.composite.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.composite.test.ICompositeDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestLinkWithParent;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class TestCompositeDiagramElementWithSameParentLink.
 */
public class TestCompositeDiagramElementWithSameParentLink extends TestLinkWithParent {
	@Override
	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}

	/**
	 * Gets the diagram command creation.
	 *
	 * @return the diagram command creation
	 * @see org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusTestCase#getDiagramCommandCreation()
	 */

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateCompositeDiagramCommand();
	}

	/**
	 * Gets the project name.
	 *
	 * @return the project name
	 * @see org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusTestCase#getProjectName()
	 */

	@Override
	protected String getProjectName() {
		return ICompositeDiagramTestsConstants.PROJECT_NAME;
	}

	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 * @see org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusTestCase#getFileName()
	 */

	@Override
	protected String getFileName() {
		return ICompositeDiagramTestsConstants.FILE_NAME;
	}


	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
	 * @see org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusTestCase#setUp()
	 */
	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		parentType = UMLElementTypes.Class_Shape;
	}


	/**
	 * Install environment.
	 *
	 * @param sourceType
	 *            the source type
	 * @param targetType
	 *            the target type
	 * @see org.eclipse.papyrus.uml.diagram.tests.canonical.TestLinkWithParent#installEnvironment(org.eclipse.gmf.runtime.emf.type.core.IElementType, org.eclipse.gmf.runtime.emf.type.core.IElementType)
	 */
	@Override
	public void installEnvironment(IElementType sourceType, IElementType targetType) {
		super.installEnvironment(sourceType, targetType);

		// create the target
		CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(targetType, getDiagramEditPart().getDiagramPreferencesHint());
		requestcreation.setLocation(DEFAULT_SOURCE_LOCATION);
		Command command = parent.getCommand(requestcreation);
		assertTrue(command.canExecute());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);

		// FIXME : get(0) : header; get(1) : container
		@SuppressWarnings("unchecked")
		List<GraphicalEditPart> children = (List<GraphicalEditPart>) parent.getChildren();
		target = children.get(3);
	}

	/**
	 * Init the configuration for a link owned by the parent. (Connector)
	 */
	protected void initConfLinkOwnedByParent() {
		// expected values before link creation
		beginRootSemanticOwnedElementSize = 1;
		beginDiagramEditPartChildrenSize = 1;
		beginRootEdgeSize = 0;
		beginSourceConnectionsSize = 0;
		beginRootViewChildrenSize = 1;
		// expected values after link creation
		endSourceConnectionsSize = beginSourceConnectionsSize + 1;
		endRootSemanticOwnedElementSize = beginRootSemanticOwnedElementSize;
		endDiagramEditPartChildrenSize = beginDiagramEditPartChildrenSize;
		endRootEdgeSize = beginRootEdgeSize + 1;
		endRootViewChildrenSize = beginRootViewChildrenSize;
	}


	/**
	 * Init the configuration for a link owned by the root. (Usage)
	 */
	protected void initConfLinkOwnedByDiagram() {
		// expected values before link creation
		beginRootSemanticOwnedElementSize = 1;
		beginDiagramEditPartChildrenSize = 1;
		beginRootEdgeSize = 0;
		beginSourceConnectionsSize = 0;
		beginRootViewChildrenSize = 1;
		// expected values after link creation
		endSourceConnectionsSize = beginSourceConnectionsSize + 1;
		endRootSemanticOwnedElementSize = beginRootSemanticOwnedElementSize + 1;
		endDiagramEditPartChildrenSize = beginDiagramEditPartChildrenSize;
		endRootEdgeSize = beginRootEdgeSize + 1;
		endRootViewChildrenSize = beginRootViewChildrenSize;
	}

	/**
	 * Test to manage port connector.
	 */
	@Test
	public void testToManagePortConnector() {
		initConfLinkOwnedByParent();
		testToManageLink(UMLElementTypes.Port_Shape, UMLElementTypes.Port_Shape, UMLElementTypes.Connector_Edge, UMLElementTypes.Class_Shape, true);
	}

	/**
	 * Test to manage port usage.
	 */
	@Test
	public void testToManagePortUsage() {
		initConfLinkOwnedByDiagram();
		testToManageLink(UMLElementTypes.Port_Shape, UMLElementTypes.Port_Shape, UMLElementTypes.Usage_Edge, UMLElementTypes.Class_Shape, true);
	}

	/**
	 * Test to manage port abstraction.
	 */
	@Test
	public void testToManagePortAbstraction() {
		initConfLinkOwnedByDiagram();
		testToManageLink(UMLElementTypes.Port_Shape, UMLElementTypes.Port_Shape, UMLElementTypes.Abstraction_Edge, UMLElementTypes.Class_Shape, true);
	}


	/**
	 * Test to manage port generalization.
	 */
	@Test
	public void testToManagePortGeneralization() {
		testImpossibleToManageLink(UMLElementTypes.Port_Shape, UMLElementTypes.Port_Shape, UMLElementTypes.Generalization_Edge);
	}

	/**
	 * Test to manage component.
	 */
	@Test
	@InteractiveTest
	public void testToDeployment() {
		testToManageLink(UMLElementTypes.Node_Shape_CN, UMLElementTypes.DeploymentSpecification_Shape_CN, UMLElementTypes.Deployment_Edge, UMLElementTypes.Class_Shape, true);
	}

}
