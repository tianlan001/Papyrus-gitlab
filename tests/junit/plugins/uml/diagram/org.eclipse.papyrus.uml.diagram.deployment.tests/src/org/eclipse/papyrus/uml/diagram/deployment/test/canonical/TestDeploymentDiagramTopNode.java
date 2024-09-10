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

import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.uml.diagram.deployment.CreateDeploymentDiagramCommand;
import org.eclipse.papyrus.uml.diagram.deployment.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.deployment.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.deployment.test.IDeploymentDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestTopNode;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

/**
 * The Class TestDeploymentDiagramTopNode.
 */
public class TestDeploymentDiagramTopNode extends TestTopNode {

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateDeploymentDiagramCommand();
	}

	@Override
	protected CreateViewRequest createViewRequestShapeContainer() {
		return CreateViewRequestFactory.getCreateShapeRequest(UMLElementTypes.Package_Shape, getDiagramEditPart().getDiagramPreferencesHint());
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
	 * Test to manage i package.
	 */
	@Test
	public void testToManageIPackage() {
		testToManageNode(UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getPackage(), UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage Model
	 */
	@Test
	public void testToManageModel() {
		testToManageNode(UMLElementTypes.Model_Shape, UMLPackage.eINSTANCE.getModel(), UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage Node
	 */
	@Test
	public void testToManageNode() {
		testToManageNode(UMLElementTypes.Node_Shape, UMLPackage.eINSTANCE.getNode(), UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage Artifact
	 */
	@Test
	public void testToManageArtifact() {
		testToManageNode(UMLElementTypes.Artifact_Shape, UMLPackage.eINSTANCE.getArtifact(), UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage device
	 */
	@Test
	public void testToManageDevice() {
		testToManageNode(UMLElementTypes.Device_Shape, UMLPackage.eINSTANCE.getDevice(), UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage Execution Environment
	 */
	@Test
	public void testToManageExecutionEnvironment() {
		testToManageNode(UMLElementTypes.ExecutionEnvironment_Shape, UMLPackage.eINSTANCE.getExecutionEnvironment(), UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage Comment.
	 */
	@Test
	public void testToManageComment() {
		testToManageNode(UMLElementTypes.Comment_Shape, UMLPackage.eINSTANCE.getComment(), UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage Constraint.
	 */
	@Test
	public void testToManageConstraint() {
		testToManageNode(UMLElementTypes.Constraint_Shape, UMLPackage.eINSTANCE.getConstraint(), UMLElementTypes.Package_Shape, true);
	}


}
