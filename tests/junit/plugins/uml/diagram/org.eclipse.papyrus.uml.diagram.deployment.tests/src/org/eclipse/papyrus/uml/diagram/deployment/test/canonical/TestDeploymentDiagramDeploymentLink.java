/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Nizar GUEDIDI (CEA LIST) - Initial API and implementation
 /*****************************************************************************/
package org.eclipse.papyrus.uml.diagram.deployment.test.canonical;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.uml.diagram.deployment.CreateDeploymentDiagramCommand;
import org.eclipse.papyrus.uml.diagram.deployment.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.deployment.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.deployment.test.IDeploymentDiagramTestsConstants;
import org.eclipse.uml2.uml.Element;
import org.junit.Test;

/**
 * The Class TestComponentDiagramLink.
 */
public class TestDeploymentDiagramDeploymentLink extends TestWithoutReconnectAMultilinkk {

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateDeploymentDiagramCommand();
	}

	@Override
	protected String getProjectName() {
		return IDeploymentDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return IDeploymentDiagramTestsConstants.FILE_NAME;
	}

	/**
	 * Test to manage Deployment
	 */

	@Test
	public void testToManageDeployment() {
		testToManageLink(UMLElementTypes.Artifact_Shape, UMLElementTypes.Node_Shape, UMLElementTypes.Deployment_Edge, UMLElementTypes.Package_Shape, false);
	}

	@Test
	public void testUnexecutableNodeArtifactDeployment() {
		testUnexecutableLink(UMLElementTypes.Node_Shape, UMLElementTypes.Artifact_Shape, UMLElementTypes.Deployment_Edge);
	}

	@Test
	public void testUnexecutableNodeNodeDeployment() {
		testUnexecutableLink(UMLElementTypes.Node_Shape, UMLElementTypes.Node_Shape, UMLElementTypes.Deployment_Edge);
	}

	@Test
	public void testUnexecutableArtifactArtifactDeployment() {
		testUnexecutableLink(UMLElementTypes.Artifact_Shape, UMLElementTypes.Artifact_Shape, UMLElementTypes.Deployment_Edge);
	}

	@Override
	protected void checkOwnedElementsAfterCreation(String prefix) {
		assertTrue(prefix + TEST_THE_EXECUTION, getRootSemanticModel().getOwnedElements().size() == 4);
		assertEquals(prefix + TEST_THE_EXECUTION, 0, ((Element) source.resolveSemanticElement()).getOwnedElements().size());
		assertEquals(prefix + TEST_THE_EXECUTION, 1, ((Element) target.resolveSemanticElement()).getOwnedElements().size());
	}

	@Override
	protected void checkOwnedElementsBeforeCreation(String prefix) {
		assertTrue(prefix + TEST_THE_UNDO, getRootSemanticModel().getOwnedElements().size() == 4);
		assertEquals(prefix + TEST_THE_UNDO, 0, ((Element) source.resolveSemanticElement()).getOwnedElements().size());
		assertEquals(prefix + TEST_THE_UNDO, 0, ((Element) target.resolveSemanticElement()).getOwnedElements().size());
	}

	private void testUnexecutableLink(IElementType sourceType, IElementType targetType, IElementType linkType) {
		installEnvironment(sourceType, targetType);
		Command command = target.getCommand(createConnectionViewRequest(linkType, source, target));
		assertTrue(CREATION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command == null || !command.canExecute());
	}

}
