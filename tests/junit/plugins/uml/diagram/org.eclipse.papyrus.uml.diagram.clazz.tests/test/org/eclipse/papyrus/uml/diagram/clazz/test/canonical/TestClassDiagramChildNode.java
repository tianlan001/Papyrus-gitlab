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
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.uml.diagram.clazz.CreateClassDiagramCommand;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.CustomUMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.clazz.test.IClassDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestChildNode;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

/**
 * The Class TestClassDiagramChildNode.
 */
public class TestClassDiagramChildNode extends TestChildNode {

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return CustomUMLDiagramUpdater.INSTANCE;
	}

	@Override
	protected CreateViewRequest createViewRequestShapeContainer() {
		return CreateViewRequestFactory.getCreateShapeRequest(UMLElementTypes.Package_Shape, getDiagramEditPart().getDiagramPreferencesHint());
	}

	@Override
	public boolean isTestAffixedNode() {
		return true;
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
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isSemanticTest() {
		return true;
	}

	/**
	 * Test to manage component.
	 */
	@Test
	public void testToManageComponent() {
		testToManageNode(UMLElementTypes.Component_Shape_CN, UMLPackage.eINSTANCE.getComponent(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage instance specification.
	 */
	@Test
	public void testToManageInstanceSpecification() {
		testToManageNode(UMLElementTypes.InstanceSpecification_Shape_CN, UMLPackage.eINSTANCE.getInstanceSpecification(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage signal.
	 */
	@Test
	public void testToManageSignal() {
		testToManageNode(UMLElementTypes.Signal_Shape_CN, UMLPackage.eINSTANCE.getSignal(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage model.
	 */
	@Test
	@FailingTest
	public void testToManageModel() {
		testToManageNode(UMLElementTypes.Model_Shape_CN, UMLPackage.eINSTANCE.getModel(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage enumeration.
	 */
	@Test
	public void testToManageEnumeration() {
		testToManageNode(UMLElementTypes.Enumeration_Shape_CN, UMLPackage.eINSTANCE.getEnumeration(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage i package.
	 */
	@Test
	@FailingTest
	public void testToManageIPackage() {
		testToManageNode(UMLElementTypes.Package_Shape_CN, UMLPackage.eINSTANCE.getPackage(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage class.
	 */
	@Test
	public void testToManageClass() {
		testToManageNode(UMLElementTypes.Class_Shape_CN, UMLPackage.eINSTANCE.getClass_(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage InforamtionItem.
	 */
	@Test
	public void testToManageInformationItem() {
		testToManageNode(UMLElementTypes.InformationItem_Shape_CN, UMLPackage.eINSTANCE.getInformationItem(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage primitive type.
	 */
	@Test
	public void testToManagePrimitiveType() {
		testToManageNode(UMLElementTypes.PrimitiveType_Shape_CN, UMLPackage.eINSTANCE.getPrimitiveType(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage data type.
	 */
	@Test
	public void testToManageDataType() {
		testToManageNode(UMLElementTypes.DataType_Shape_CN, UMLPackage.eINSTANCE.getDataType(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage constraint.
	 */
	@Test
	public void testToManageConstraint() {
		testToManageNode(UMLElementTypes.Constraint_PackagedElementShape_CN, UMLPackage.eINSTANCE.getConstraint(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage comment.
	 */
	@Test
	public void testToManageComment() {
		testToManageNode(UMLElementTypes.Comment_Shape_CN, UMLPackage.eINSTANCE.getComment(), UMLElementTypes.Package_Shape_CN, true);
	}

	/**
	 * Test to manage comment.
	 */
	@Test
	public void testToManageInterface() {
		testToManageNode(UMLElementTypes.Interface_Shape_CN, UMLPackage.eINSTANCE.getInterface(), UMLElementTypes.Package_Shape_CN, true);
	}

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateClassDiagramCommand();
	}
}
