/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Thibault Le Ouay (Sherpa Engineering) t.leouay@sherpa-eng.com  - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.profile.tests.canonical;


import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.uml.diagram.profile.CreateProfileDiagramCommand;
import org.eclipse.papyrus.uml.diagram.profile.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.profile.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestTopNode;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;


public class TestProfileDiagramTopNode extends TestTopNode {

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}

	@Override
	protected CreateViewRequest createViewRequestShapeContainer() {
		return CreateViewRequestFactory.getCreateShapeRequest(UMLElementTypes.Package_Shape, getDiagramEditPart().getDiagramPreferencesHint());
	}

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return (ICreationCommand) new CreateProfileDiagramCommand();
	}

	@Override
	protected String getProjectName() {
		return "ProfileDiagramTestProject";
	}

	@Override
	protected String getFileName() {
		return "ProfileDiagramTest.profile.di";
	}

	@Test
	public void testToManageClass() {
		testToManageNode(UMLElementTypes.Class_Shape, UMLPackage.eINSTANCE.getClass_(), UMLElementTypes.Package_Shape, false);
	}

	@Test
	public void testToManagePackage() {
		testToManageNode(UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getPackage(), UMLElementTypes.Package_Shape, false);
	}

	@Test
	public void testToManageDataType() {
		testToManageNode(UMLElementTypes.DataType_Shape, UMLPackage.eINSTANCE.getDataType(), UMLElementTypes.Package_Shape, false);
	}

	@Test
	public void testToManageStereoType() {
		testToManageNode(UMLElementTypes.Stereotype_Shape, UMLPackage.eINSTANCE.getStereotype(), UMLElementTypes.Package_Shape, false);
	}

	@Test
	public void testToManageProfile() {
		testToManageNode(UMLElementTypes.Profile_Shape, UMLPackage.eINSTANCE.getProfile(), UMLElementTypes.Package_Shape, false);
	}
}
