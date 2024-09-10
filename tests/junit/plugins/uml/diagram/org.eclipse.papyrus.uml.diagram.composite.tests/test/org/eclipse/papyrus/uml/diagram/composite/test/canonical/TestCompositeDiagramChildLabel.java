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
package org.eclipse.papyrus.uml.diagram.composite.test.canonical;

import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.uml.diagram.composite.CreateCompositeDiagramCommand;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.DataTypeAttributeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.DataTypeOperationCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.EnumerationEnumerationLiteralCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.composite.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.composite.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.composite.test.ICompositeDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestChildLabel;
import org.junit.Test;

/**
 * The Class TestComponentDiagramChildNode.
 */

public class TestCompositeDiagramChildLabel extends TestChildLabel {

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}
	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateCompositeDiagramCommand();
	}

	@Override
	protected String getProjectName() {
		return ICompositeDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return ICompositeDiagramTestsConstants.FILE_NAME;
	}
	
	/**
	* Test to manage Property.
	*/
	@Test
	@FailingTest
	public void testToProperty() {
	testToManageTopNodeWithMask(UMLElementTypes.DataType_Shape,UMLElementTypes.Property_AttributeLabel, DataTypeAttributeCompartmentEditPart.VISUAL_ID,null);
	}
	/**
	* Test to manage Operation.
	*/
	@Test
	@FailingTest
	public void testToOperation() {
	testToManageTopNodeWithMask(UMLElementTypes.DataType_Shape,UMLElementTypes.Operation_OperationLabel, DataTypeOperationCompartmentEditPart.VISUAL_ID,null);
	}
	/**
	* Test to manage EnumerationLiteral.
	*/
	@Test
	@FailingTest
	public void testToEnumerationLiteral() {
	testToManageTopNode(UMLElementTypes.Enumeration_Shape,UMLElementTypes.EnumerationLiteral_LiteralLabel, EnumerationEnumerationLiteralCompartmentEditPart.VISUAL_ID,null);
	}

	
}
