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
package org.eclipse.papyrus.uml.diagram.deployment.test.canonical;

import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.uml.diagram.deployment.CreateDeploymentDiagramCommand;
import org.eclipse.papyrus.uml.diagram.deployment.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.deployment.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.deployment.test.IDeploymentDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestChildNode;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

/**
 * The Class TestDepoloymentDiagramChildNode.
 */

public class TestDeploymentDiagramForPackageChildNode extends TestChildNode {

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
	public void testToManagePackage() {
		testToManageNode(UMLElementTypes.Package_Shape_CN, UMLPackage.eINSTANCE.getPackage(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage Model
	 */
	@Test
	public void testToManageModel() {
		testToManageNode(UMLElementTypes.Model_Shape_CN, UMLPackage.eINSTANCE.getModel(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage Node
	 */
	@Test
	public void testToManageNode() {
		testToManageNode(UMLElementTypes.Node_Shape_CN, UMLPackage.eINSTANCE.getNode(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage Artifact
	 */
	@Test
	public void testToManageArtifact() {
		testToManageNode(UMLElementTypes.Artifact_Shape_CN, UMLPackage.eINSTANCE.getArtifact(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage device
	 */
	@Test
	public void testToManageDevice() {
		testToManageNode(UMLElementTypes.Device_Shape_CN, UMLPackage.eINSTANCE.getDevice(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage Execution Environment
	 */
	@Test
	public void testToManageExecutionEnvironment() {
		testToManageNode(UMLElementTypes.ExecutionEnvironment_Shape_CN, UMLPackage.eINSTANCE.getExecutionEnvironment(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage Comment.
	 */
	@Test
	public void testToManageComment() {
		testToManageNode(UMLElementTypes.Comment_Shape_CN, UMLPackage.eINSTANCE.getComment(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage Constraint.
	 */
	@Test
	public void testToManageConstraint() {
		testToManageNode(UMLElementTypes.Constraint_Shape_CN, UMLPackage.eINSTANCE.getConstraint(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage Model.
	 */
	@Test
	public void testToModel() {
		testToManageNode(UMLElementTypes.Model_Shape_CN, UMLPackage.eINSTANCE.getModel(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage Package.
	 */
	@Test
	public void testToPackage() {
		testToManageNode(UMLElementTypes.Package_Shape_CN, UMLPackage.eINSTANCE.getPackage(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage ExecutionEnvironment.
	 */
	@Test
	public void testToExecutionEnvironment() {
		testToManageNode(UMLElementTypes.ExecutionEnvironment_Shape_CN, UMLPackage.eINSTANCE.getExecutionEnvironment(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage Node.
	 */
	@Test
	public void testToNode() {
		testToManageNode(UMLElementTypes.Node_Shape_CN, UMLPackage.eINSTANCE.getNode(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage Artifact.
	 */
	@Test
	public void testToArtifact3() {
		testToManageNode(UMLElementTypes.Artifact_Shape_CN, UMLPackage.eINSTANCE.getArtifact(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage Comment.
	 */
	@Test
	public void testToComment() {
		testToManageNode(UMLElementTypes.Comment_Shape_CN, UMLPackage.eINSTANCE.getComment(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage Constraint.
	 */
	@Test
	public void testToConstraint() {
		testToManageNode(UMLElementTypes.Constraint_Shape_CN, UMLPackage.eINSTANCE.getConstraint(), UMLElementTypes.Package_Shape_CN, true);
	}
}
