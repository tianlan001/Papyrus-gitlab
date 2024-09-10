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

import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AppliedStereotypeLinkLabelDisplayEditPolicy;
import org.eclipse.papyrus.uml.diagram.component.CreateComponentDiagramCommand;
import org.eclipse.papyrus.uml.diagram.component.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.component.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.component.test.IComponentDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestLink;
import org.junit.Assert;
import org.junit.Test;

/**
 * The Class TestComponentDiagramLink.
 */
public class TestComponentDiagramLink extends TestLink {

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateComponentDiagramCommand();
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
	 * Test to manage Abstraction
	 */
	@Test
	@FailingTest
	public void testToManageAbstraction() {
		testToManageLink(UMLElementTypes.Component_PackagedElementShape, UMLElementTypes.Component_PackagedElementShape, UMLElementTypes.Abstraction_Edge, UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage Dependency
	 */
	@Test
	@FailingTest
	public void testToManageDependency() {
		testToManageLink(UMLElementTypes.Component_PackagedElementShape, UMLElementTypes.Component_PackagedElementShape, UMLElementTypes.Dependency_Edge, UMLElementTypes.Package_Shape, true);

	}

	/**
	 * Test to manage Manifestation
	 */
	@Test
	@FailingTest
	public void testToManageManifestation() {
		testToManageLink(UMLElementTypes.Component_PackagedElementShape, UMLElementTypes.Component_PackagedElementShape, UMLElementTypes.Manifestation_Edge, UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage Usage
	 */
	@Test
	@FailingTest
	public void testToManageUsage() {
		testToManageLink(UMLElementTypes.Component_PackagedElementShape, UMLElementTypes.Interface_ClassifierShape, UMLElementTypes.Usage_Edge, UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage Interface Realization
	 */
	@Test
	@FailingTest
	public void testToManageInterfaceRealization() {
		testToManageLink(UMLElementTypes.Component_PackagedElementShape, UMLElementTypes.Interface_ClassifierShape, UMLElementTypes.InterfaceRealization_Edge, UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage Component Realization
	 */
	@Test
	@FailingTest
	public void testToManageComponentRealization() {
		testToManageLink(UMLElementTypes.Component_PackagedElementShape, UMLElementTypes.Component_PackagedElementShape, UMLElementTypes.ComponentRealization_Edge, UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage Abstraction for Interface source
	 */
	@Test
	public void testToManageInterfaceAbstraction() {
		testToManageLink(UMLElementTypes.Interface_ClassifierShape, UMLElementTypes.Interface_ClassifierShape, UMLElementTypes.Abstraction_Edge, UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage Dependency for Interface source
	 */
	@Test
	public void testToManageInterfaceDependency() {
		testToManageLink(UMLElementTypes.Interface_ClassifierShape, UMLElementTypes.Interface_ClassifierShape, UMLElementTypes.Dependency_Edge, UMLElementTypes.Package_Shape, true);

	}

	/**
	 * Test to manage Manifestation for Interface source
	 */
	@Test
	public void testToManageInterfaceManifestation() {
		testToManageLink(UMLElementTypes.Interface_ClassifierShape, UMLElementTypes.Interface_ClassifierShape, UMLElementTypes.Manifestation_Edge, UMLElementTypes.Package_Shape, false);
	}

	/**
	 * Test to manage Usage for Interface source
	 */
	@Test
	public void testToManageInterfaceUsage() {
		testToManageLink(UMLElementTypes.Interface_ClassifierShape, UMLElementTypes.Interface_ClassifierShape, UMLElementTypes.Usage_Edge, UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to create a link.
	 *
	 * @param linkType
	 *            the type
	 */
	@Override
	public void testToCreateALink(IElementType linkType, String initialName) {
		testCreateLink(linkType, initialName);
	}

	/**
	 * htis method is used to test the created link editpart
	 *
	 * @param linkEditPart
	 */
	@Override
	protected void testLinkEditPart(ConnectionEditPart linkEditPart, String initialName) {
		super.testLinkEditPart(linkEditPart, initialName);
		EditPolicy policy = linkEditPart.getEditPolicy(AppliedStereotypeLinkLabelDisplayEditPolicy.STEREOTYPE_LABEL_POLICY);
		Assert.assertNotNull("the link must have an stereotype edipolicy.", policy); //$NON-NLS-1$
		Assert.assertTrue("the policy of the link must be an instance of AppliedStereotypeLinkLabelDisplayEditPolicy", policy instanceof AppliedStereotypeLinkLabelDisplayEditPolicy); //$NON-NLS-1$
		Assert.assertTrue("Expected link childs count is 2.", linkEditPart.getChildren().size() == 2);
	}
}
