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

import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.uml.diagram.component.CreateComponentDiagramCommand;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceAttributeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceOperationCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.component.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.component.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.component.test.IComponentDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestChildLabel;
import org.junit.Test;

/**
 * The Class TestComponentDiagramChildNode.
 */

public class TestComponentDiagramChildLabel extends TestChildLabel {

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateComponentDiagramCommand();
	}

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
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
	 * Test to manage i package.
	 */
	@Test
	public void testToManageProperty() {
		testToManageTopNode(UMLElementTypes.Interface_ClassifierShape,UMLElementTypes.Property_InterfaceAttributeLabel,   InterfaceAttributeCompartmentEditPart.VISUAL_ID, "Attribute");
	}
	
	/**
	 * Test to manage i package.
	 */
	@Test
	public void testToManageOperation() {
		testToManageTopNode(UMLElementTypes.Interface_ClassifierShape,UMLElementTypes.Operation_InterfaceOperationLabel,   InterfaceOperationCompartmentEditPart.VISUAL_ID);
	}
	
	/**
	 * Test to manage i package.
	 */
	@Test
	public void testToManageReception() {
		testToManageTopNode(UMLElementTypes.Interface_ClassifierShape,UMLElementTypes.Reception_InterfaceReceptionLabel,  InterfaceOperationCompartmentEditPart.VISUAL_ID);
	}

	
}
