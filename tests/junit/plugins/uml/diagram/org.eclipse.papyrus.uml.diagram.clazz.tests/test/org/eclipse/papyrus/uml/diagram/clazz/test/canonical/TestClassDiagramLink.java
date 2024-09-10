/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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

import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.uml.diagram.clazz.CreateClassDiagramCommand;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.CustomUMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.clazz.test.IClassDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestLink;
import org.eclipse.papyrus.uml.service.types.element.UMLDIElementTypes;
import org.junit.Test;

/**
 * The Class TestClassDiagramLink use to test link that are contained by the owner of the target and the source
 */
public class TestClassDiagramLink extends TestLink {

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
	@FailingTest
	public void testToManageDependency() {
		testToManageLink(UMLElementTypes.Component_Shape, UMLElementTypes.Component_Shape, UMLElementTypes.Dependency_Edge, UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage Association.
	 */
	@Test
	@FailingTest
	public void testToManageAssociation() {
		testToManageLink(UMLElementTypes.Class_Shape, UMLElementTypes.Class_Shape, UMLDIElementTypes.ASSOCIATION_NON_DIRECTED_EDGE, UMLElementTypes.Package_Shape, true,"srcMulA_cla");
	}

	//test generatlization
	//test interfaceRealization
	//test substitution
	/**
	 * Test to manage Realization
	 */
	@Test
	@FailingTest
	public void testToManageRealization() {
		testToManageLink(UMLElementTypes.Class_Shape, UMLElementTypes.Class_Shape, UMLElementTypes.Realization_Edge, UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage Abstraction
	 */
	@Test
	@FailingTest
	public void testToManageAbstraction() {
		testToManageLink(UMLElementTypes.Class_Shape, UMLElementTypes.Class_Shape, UMLElementTypes.Abstraction_Edge, UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage Usage
	 */
	@Test
	@FailingTest
	public void testToManageUsage() {
		testToManageLink(UMLElementTypes.Class_Shape, UMLElementTypes.Class_Shape, UMLElementTypes.Usage_Edge, UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage Usage
	 */
	@Test
	@FailingTest
	public void testToManageInformationFlow() {
		testToManageLink(UMLElementTypes.Class_Shape, UMLElementTypes.Class_Shape, UMLElementTypes.InformationFlow_Edge, UMLElementTypes.Package_Shape, true);
	}
	//test Element import
	//testPackageImport
	//test comment link
	//test constraintLink
	//test template binding
	//test instancespecificationLink
}
