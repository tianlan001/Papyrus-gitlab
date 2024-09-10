/*****************************************************************************
 * Copyright (c) 2010, 2014 CEA LIST and others.
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
 *  Christian W. Damus (CEA) - bug 440263
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.usecase.tests.canonical;

import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestLink;
import org.eclipse.papyrus.uml.diagram.usecase.CreateUseCaseDiagramCommand;
import org.eclipse.papyrus.uml.diagram.usecase.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.usecase.tests.IUseCaseDiagramTestsConstants;
import org.eclipse.papyrus.uml.service.types.element.UMLDIElementTypes;
import org.junit.Test;


/**
 * The Class TestUseCaseLink use to test link that are contained by the owner of the target and the source
 */
public class TestUseCaseLink extends TestLink {

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return org.eclipse.papyrus.uml.diagram.usecase.custom.edit.parts.CustomUMLDiagramUpdater.INSTANCE;
	}
	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return  new CreateUseCaseDiagramCommand();
	}
	/**
	 * Test to manage component.
	 */
	@Test
	public void testToManageDependency() {
		testToManageLink(UMLElementTypes.UseCase_Shape,UMLElementTypes.UseCase_Shape,UMLElementTypes.Dependency_Edge,UMLElementTypes.Package_Shape,true);
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
	public void testToManageDependencyUseCase_Actor() {
		testToManageLink(UMLElementTypes.UseCase_Shape,UMLElementTypes.Actor_Shape,UMLElementTypes.Dependency_Edge,UMLElementTypes.Package_Shape,true);
	}
	@Test
	public void testToManageAssociationUseCase_UseCase() {
		testToManageLink(UMLElementTypes.UseCase_Shape,UMLElementTypes.UseCase_Shape,UMLDIElementTypes.ASSOCIATION_NON_DIRECTED_EDGE,UMLElementTypes.Package_Shape,true);
	}
	@Test
	public void testToManageAssociationUseCase_Actor() {
		testToManageLink(UMLElementTypes.UseCase_Shape,UMLElementTypes.Actor_Shape,UMLDIElementTypes.ASSOCIATION_NON_DIRECTED_EDGE,UMLElementTypes.Package_Shape,true);
	}
	
	@Test
	public void testToManageAbstraction() {
		testToManageLink(UMLElementTypes.UseCase_Shape,UMLElementTypes.UseCase_Shape,UMLElementTypes.Abstraction_Edge,UMLElementTypes.Package_Shape,true);
	}
	@Test
	public void testToManageUsage() {
		testToManageLink(UMLElementTypes.UseCase_Shape,UMLElementTypes.UseCase_Shape,UMLElementTypes.Usage_Edge,UMLElementTypes.Package_Shape,true);
	}
	@Test
	public void testToManageRealization() {
		testToManageLink(UMLElementTypes.UseCase_Shape,UMLElementTypes.UseCase_Shape,UMLElementTypes.Realization_Edge,UMLElementTypes.Package_Shape,true);
	}
	
}
