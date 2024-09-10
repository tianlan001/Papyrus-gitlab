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
package org.eclipse.papyrus.uml.diagram.component.test.canonical;

import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.uml.diagram.component.CreateComponentDiagramCommand;
import org.eclipse.papyrus.uml.diagram.component.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.component.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.component.test.IComponentDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestChildNode;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

/**
 * The Class TestComponentDiagramChildNode.
 */

public class TestComponentDiagramComponentChildNode extends TestChildNode {

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateComponentDiagramCommand();
	}

	@Override
	protected CreateViewRequest createViewRequestShapeContainer() {
		return CreateViewRequestFactory.getCreateShapeRequest(UMLElementTypes.Component_PackagedElementShape, getDiagramEditPart().getDiagramPreferencesHint());
	}

	@Override
	protected String getProjectName() {
		return IComponentDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return IComponentDiagramTestsConstants.FILE_NAME;
	}


	/**
	 * Test to manage Constraint
	 */
	@Test
	public void testToManagePropertyPart() {
		testToManageNodeWithMask(UMLElementTypes.Property_Shape, UMLPackage.eINSTANCE.getProperty(), UMLElementTypes.Component_PackagedElementShape, false, "Attribute", 0);
	}

	/**
	 * Test to manage Constraint
	 */
	@Test
	public void testToManagePort() {
		setTestAffixedNode(true);
		testToManageNode(UMLElementTypes.Port_Shape, UMLPackage.eINSTANCE.getPort(), UMLElementTypes.Component_PackagedElementShape, false, 4, 0, 1, 1, true, null, 0);
		setTestAffixedNode(false);
	}

	@FailingTest
	@Test
	public void testToManageComponent() {
		testToManageNode(UMLElementTypes.Component_PackagedElementShape_CCN, UMLPackage.eINSTANCE.getComponent(), UMLElementTypes.Component_PackagedElementShape, true, 0, 0, 1, 1, false, "Component", 0);
	}
}
