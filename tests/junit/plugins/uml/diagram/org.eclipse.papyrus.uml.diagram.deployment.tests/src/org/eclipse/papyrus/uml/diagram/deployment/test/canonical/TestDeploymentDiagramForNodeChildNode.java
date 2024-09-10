/*****************************************************************************
 * Copyright (c) 2009, 2015 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 480000
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
import org.eclipse.papyrus.uml.diagram.tests.rules.DisableDropStrategiesRule;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * The Class TestDepoloymentDiagramChildNode.
 */

public class TestDeploymentDiagramForNodeChildNode extends TestChildNode {

	@ClassRule
	public static final TestRule suppressDropStrategies = new DisableDropStrategiesRule();

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
		return CreateViewRequestFactory.getCreateShapeRequest(UMLElementTypes.Node_Shape, getDiagramEditPart().getDiagramPreferencesHint());
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
	 * Test to manage Device.
	 */
	@Test
	public void testToDevice() {
		testToManageNode(UMLElementTypes.Device_Shape_CCN, UMLPackage.eINSTANCE.getDevice(), UMLElementTypes.Node_Shape_CCN, false);
	}

	/**
	 * Test to manage ExecutionEnvironment.
	 */
	@Test
	public void testToExecutionEnvironment() {
		testToManageNode(UMLElementTypes.ExecutionEnvironment_Shape_CCN, UMLPackage.eINSTANCE.getExecutionEnvironment(), UMLElementTypes.Node_Shape_CCN, false);
	}

	/**
	 * Test to manage Node.
	 */
	@Test
	public void testToNode() {
		testToManageNode(UMLElementTypes.Node_Shape_CCN, UMLPackage.eINSTANCE.getNode(), UMLElementTypes.Node_Shape_CCN, false);
	}

	/**
	 * Test to manage Artifact.
	 */
	@Test
	public void testToArtifact() {
		testToManageNode(UMLElementTypes.Artifact_Shape_CCN, UMLPackage.eINSTANCE.getArtifact(), UMLElementTypes.Node_Shape_CCN, false);
	}
}
