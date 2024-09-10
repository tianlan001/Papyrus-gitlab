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
 *  Christian W. Damus - bug 459701
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.test.canonical;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.uml.diagram.clazz.CreateClassDiagramCommand;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.CustomUMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.clazz.test.IClassDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestLinkOwnedBySource;
import org.junit.Test;

/**
 * The Class TestClassDiagramLink use to test link that are contained by the owner of the target and the source
 */
public class TestClassDiagramLinkOwnedBySource extends TestLinkOwnedBySource {

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
	 * Test to manage Association.
	 */
	@Test
	public void testToManageGeneralization() {
		testToManageLink(UMLElementTypes.Class_Shape, UMLElementTypes.Class_Shape, UMLElementTypes.Generalization_Edge, UMLElementTypes.Package_Shape, false);
	}

	/**
	 * Test to manage InterfaceRealization.
	 */
	@Test
	public void testToManageInterfaceRealization() {
		testToManageLink(UMLElementTypes.Class_Shape, UMLElementTypes.Interface_Shape, UMLElementTypes.InterfaceRealization_Edge, UMLElementTypes.Package_Shape, false);
	}

	/**
	 * Test to manage Substitution.
	 */
	@Test
	public void testToManageSubstitution() {
		testToManageLink(UMLElementTypes.Class_Shape, UMLElementTypes.Class_Shape, UMLElementTypes.Substitution_Edge, UMLElementTypes.Package_Shape, false);
	}

	/**
	 * Test to manage ElementImport.
	 */
	@Test
	public void testToManageElementImport() {
		testToManageLink(UMLElementTypes.Class_Shape, UMLElementTypes.Class_Shape, UMLElementTypes.ElementImport_Edge, UMLElementTypes.Package_Shape, true);
	}

	/**
	 * Test to manage ElementImport.
	 */
	@Test
	public void testToManagePackageImport() {
		testToManageLink(UMLElementTypes.Package_Shape, UMLElementTypes.Package_Shape, UMLElementTypes.PackageImport_Edge, UMLElementTypes.Package_Shape, true);
	}

	@TargetConfigurator(CreateRedefinableTemplateSignature.class)
	@Test
	public void testToManageTemplateBinding() {
		testToManageLink(UMLElementTypes.Class_Shape, UMLElementTypes.Class_Shape, UMLElementTypes.TemplateBinding_Edge, UMLElementTypes.Package_Shape, false);
	}

	// test comment link
	// test constraintLink
	// test containment link
	// test instancespecificationLink

	//
	// Configurators
	//

	public static class CreateRedefinableTemplateSignature implements FixtureEditPartConfigurator {
		public Command configureFixtureEditPart(IGraphicalEditPart editPart, IElementType elementType, boolean isSource) {
			CreateViewRequest request = CreateViewRequestFactory.getCreateShapeRequest(UMLElementTypes.RedefinableTemplateSignature_Shape, editPart.getDiagramPreferencesHint());
			return editPart.getCommand(request);
		}
	}
}
