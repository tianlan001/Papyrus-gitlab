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
package org.eclipse.papyrus.uml.diagram.component.test.canonical;

import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.uml.diagram.component.CreateComponentDiagramCommand;
import org.eclipse.papyrus.uml.diagram.component.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.component.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.component.test.IComponentDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestTopNode;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

/**
 * The Class TestComponentDiagramTopNode.
 */
public class TestComponentDiagramTopNode extends TestTopNode {

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateComponentDiagramCommand();
	}

	@Override
	protected boolean isSemanticTest() {
		return true;
	}

	@Override
	protected String getProjectName() {
		return IComponentDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return IComponentDiagramTestsConstants.FILE_NAME;
	}

	@Override
	protected CreateViewRequest createViewRequestShapeContainer() {
		return CreateViewRequestFactory.getCreateShapeRequest(UMLElementTypes.Package_Shape, getDiagramEditPart().getDiagramPreferencesHint());
	}

	/**
	 * Test to manage Package
	 */
	@Test
	public void testToManagePackage() {
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
	 * Test to manage Component
	 */
	@Test
	public void testToManageComponent() {
		testToManageNode(UMLElementTypes.Component_PackagedElementShape, UMLPackage.eINSTANCE.getComponent(), UMLElementTypes.Package_Shape, true);

	}

	/**
	 * Test to manage Interface
	 */
	@Test
	public void testToManageInterface() {
		testToManageNode(UMLElementTypes.Interface_ClassifierShape, UMLPackage.eINSTANCE.getInterface(), UMLElementTypes.Package_Shape, true);

	}

	/**
	 * Test to manage Comment
	 */
	@Test
	public void testToManageComment() {
		testToManageNode(UMLElementTypes.Comment_Shape, UMLPackage.eINSTANCE.getComment(), UMLElementTypes.Package_Shape, true);

	}

	/**
	 * Test to manage Constraint
	 */
	@Test
	public void testToManageConstraint() {
		testToManageNode(UMLElementTypes.Constraint_Shape, UMLPackage.eINSTANCE.getConstraint(), UMLElementTypes.Package_Shape, true);

	}



}
