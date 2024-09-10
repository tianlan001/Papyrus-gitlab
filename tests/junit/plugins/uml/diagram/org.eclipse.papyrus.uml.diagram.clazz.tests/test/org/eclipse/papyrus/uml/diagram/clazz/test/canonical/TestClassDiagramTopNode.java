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
package org.eclipse.papyrus.uml.diagram.clazz.test.canonical;

import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.uml.diagram.clazz.CreateClassDiagramCommand;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.CustomUMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.clazz.test.IClassDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestTopNode;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

/**
 * The Class TestClassDiagramTopNode.
 */
public class TestClassDiagramTopNode extends TestTopNode {

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return CustomUMLDiagramUpdater.INSTANCE;
	}

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateClassDiagramCommand();
	}

	@Override
	protected String getProjectName() {
		return IClassDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return IClassDiagramTestsConstants.FILE_NAME;
	}

	/**
	 * Test to manage component.
	 */
	@Test
	public void testToManageComponent() {
		testToManageNode(UMLElementTypes.Component_Shape, UMLPackage.eINSTANCE.getComponent(), UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage instance specification.
	 */
	@Test
	public void testToManageInstanceSpecification() {
		testToManageNode(UMLElementTypes.InstanceSpecification_Shape, UMLPackage.eINSTANCE.getInstanceSpecification(), UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage signal.
	 */
	@Test
	public void testToManageSignal() {
		testToManageNode(UMLElementTypes.Signal_Shape, UMLPackage.eINSTANCE.getSignal(), UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage model.
	 */
	@Test
	public void testToManageModel() {
		testToManageNode(UMLElementTypes.Model_Shape, UMLPackage.eINSTANCE.getModel(), UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage enumeration.
	 */
	@Test
	public void testToManageEnumeration() {
		testToManageNode(UMLElementTypes.Enumeration_Shape, UMLPackage.eINSTANCE.getEnumeration(), UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage i package.
	 */
	@Test
	public void testToManageIPackage() {
		testToManageNode(UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getPackage(), UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage class.
	 */
	@Test
	public void testToManageClass() {
		testToManageNode(UMLElementTypes.Class_Shape, UMLPackage.eINSTANCE.getClass_(), UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage primitive type.
	 */
	@Test
	public void testToManagePrimitiveType() {
		testToManageNode(UMLElementTypes.PrimitiveType_Shape, UMLPackage.eINSTANCE.getPrimitiveType(), UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage data type.
	 */
	@Test
	public void testToManageDataType() {
		testToManageNode(UMLElementTypes.DataType_Shape, UMLPackage.eINSTANCE.getDataType(), UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage constraint.
	 */
	@Test
	public void testToManageConstraint() {
		testToManageNode(UMLElementTypes.Constraint_PackagedElementShape, UMLPackage.eINSTANCE.getConstraint(), UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage comment.
	 */
	@Test
	public void testToManageComment() {
		testToManageNode(UMLElementTypes.Comment_Shape, UMLPackage.eINSTANCE.getComment(), UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage component.
	 */
	@Test
	public void testToManageInformationItem() {
		testToManageNode(UMLElementTypes.InformationItem_Shape, UMLPackage.eINSTANCE.getInformationItem(), UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage component.
	 */
	@Test
	public void testToManageInterface() {
		testToManageNode(UMLElementTypes.Interface_Shape, UMLPackage.eINSTANCE.getInterface(), UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage component.
	 */
	@Test
	public void testToManageTimeObservation() {
		testToManageNode(UMLElementTypes.TimeObservation_Shape, UMLPackage.eINSTANCE.getTimeObservation(), UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage component.
	 */
	@Test
	public void testToManageDurationObservation() {
		testToManageNode(UMLElementTypes.DurationObservation_Shape, UMLPackage.eINSTANCE.getDurationObservation(), UMLElementTypes.Package_Shape, true);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CreateViewRequest createViewRequestShapeContainer() {
		return CreateViewRequestFactory.getCreateShapeRequest(UMLElementTypes.Package_Shape, getDiagramEditPart().getDiagramPreferencesHint());
	}
}
