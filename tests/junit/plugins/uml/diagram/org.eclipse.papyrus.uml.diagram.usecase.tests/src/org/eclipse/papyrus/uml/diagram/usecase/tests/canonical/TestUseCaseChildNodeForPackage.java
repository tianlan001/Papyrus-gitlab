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
package org.eclipse.papyrus.uml.diagram.usecase.tests.canonical;

import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestChildNode;
import org.eclipse.papyrus.uml.diagram.usecase.CreateUseCaseDiagramCommand;
import org.eclipse.papyrus.uml.diagram.usecase.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.usecase.tests.IUseCaseDiagramTestsConstants;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;



/**
 * The Class TestUseCaseChildNodeForPackage.
 */
public class TestUseCaseChildNodeForPackage extends TestChildNode {

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return org.eclipse.papyrus.uml.diagram.usecase.custom.edit.parts.CustomUMLDiagramUpdater.INSTANCE;
	}

	@Override
	protected CreateViewRequest createViewRequestShapeContainer() {
		return CreateViewRequestFactory.getCreateShapeRequest(UMLElementTypes.Package_Shape, getDiagramEditPart().getDiagramPreferencesHint());
	}

	@Override
	protected String getProjectName() {
		return IUseCaseDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return IUseCaseDiagramTestsConstants.FILE_NAME;
	}

	/**
	 * Test to manage component.
	 */
	@Test
	public void testToManagePackage() {
		testToManageNode(UMLElementTypes.Package_Shape_CN, UMLPackage.eINSTANCE.getPackage(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage component.
	 */
	@Test
	public void testToManageActor() {
		testToManageNode(UMLElementTypes.Actor_Shape_CN, UMLPackage.eINSTANCE.getActor(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage component.
	 */
	@Test
	public void testToManageComment() {
		testToManageNode(UMLElementTypes.Comment_Shape_CN, UMLPackage.eINSTANCE.getComment(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage component.
	 */
	@Test
	public void testToManageConstraint() {
		testToManageNode(UMLElementTypes.Constraint_Shape_CN, UMLPackage.eINSTANCE.getConstraint(), UMLElementTypes.Package_Shape_CN, true);
	}

	@Test
	public void testToManageComponent() {
		testToManageNode(UMLElementTypes.Component_Shape_CN, UMLPackage.eINSTANCE.getComponent(), UMLElementTypes.Package_Shape_CN, true);
	}

	@Test
	public void testToManageUseCase() {
		testToManageNode(UMLElementTypes.UseCase_Shape_CN, UMLPackage.eINSTANCE.getUseCase(), UMLElementTypes.Package_Shape_CN, true);
	}

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateUseCaseDiagramCommand();
	}

}
