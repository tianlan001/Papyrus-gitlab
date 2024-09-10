/*****************************************************************************
 * Copyright (c) 2010, 2015 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 468646
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.usecase.tests.canonical;

import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestLinkOwnedBySource;
import org.eclipse.papyrus.uml.diagram.usecase.CreateUseCaseDiagramCommand;
import org.eclipse.papyrus.uml.diagram.usecase.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.usecase.tests.IUseCaseDiagramTestsConstants;
import org.junit.Test;


/**
 * The Class TestUseCaseLink use to test link that are contained by the owner of the target and the source
 */
public class TestUseCaseLinkOwnedBySource extends TestLinkOwnedBySource {

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return org.eclipse.papyrus.uml.diagram.usecase.custom.edit.parts.CustomUMLDiagramUpdater.INSTANCE;
	}

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateUseCaseDiagramCommand();
	}

	@Override
	protected String getProjectName() {
		return IUseCaseDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return IUseCaseDiagramTestsConstants.FILE_NAME;
	}

	@Test
	public void testToManageGeneralization() {
		testToManageLink(UMLElementTypes.Actor_Shape, UMLElementTypes.Actor_Shape, UMLElementTypes.Generalization_Edge, UMLElementTypes.Package_Shape, false);
	}

	@Test
	public void testToManagePackageMerge() {
		testToManageLink(UMLElementTypes.Package_Shape, UMLElementTypes.Package_Shape, UMLElementTypes.PackageMerge_Edge, UMLElementTypes.Package_Shape, true);
	}

	@Test
	public void testToManagePackageImport() {
		testToManageLink(UMLElementTypes.Package_Shape, UMLElementTypes.Package_Shape, UMLElementTypes.PackageImport_Edge, UMLElementTypes.Package_Shape, true);
	}

	@Test
	public void testToManageExtend() {
		testToManageLink(UMLElementTypes.UseCase_Shape, UMLElementTypes.UseCase_Shape, UMLElementTypes.Extend_Edge, UMLElementTypes.Package_Shape, false);
	}

	@Test
	public void testToManageInclude() {
		testToManageLink(UMLElementTypes.UseCase_Shape, UMLElementTypes.UseCase_Shape, UMLElementTypes.Include_Edge, UMLElementTypes.Package_Shape, false);
	}

}
