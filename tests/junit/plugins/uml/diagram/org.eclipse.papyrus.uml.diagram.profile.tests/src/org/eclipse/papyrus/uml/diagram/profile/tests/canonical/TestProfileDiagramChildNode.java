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
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestChildNode;
import org.eclipse.uml2.uml.UMLPackage;
/**
 * @author Thibault
 *
 */
import org.junit.Test;

public class TestProfileDiagramChildNode extends TestChildNode {

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}

	protected CreateViewRequest createViewRequestShapeContainer() {
		return CreateViewRequestFactory.getCreateShapeRequest(UMLElementTypes.Package_Shape, getDiagramEditPart().getDiagramPreferencesHint());
	}

	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateProfileDiagramCommand();
	}

	protected String getProjectName() {
		return "ProfileDiagramTestProject";
	}

	protected String getFileName() {
		return "ProfileDiagramTest.profile.di";
	}

	@Test
	public void testToManageClassInPackage() {
		testToManageNode(UMLElementTypes.Class_Shape_CN, UMLPackage.eINSTANCE.getClass_(), UMLElementTypes.Package_Shape, false);
	}

	@Test
	public void testToManageDatatypeInPackage() {
		testToManageNode(UMLElementTypes.DataType_Shape_CN, UMLPackage.eINSTANCE.getDataType(), UMLElementTypes.Package_Shape, false);
	}

	@Test
	public void testToManagePackageInPackage() {
		testToManageNode(UMLElementTypes.Package_Shape_CN, UMLPackage.eINSTANCE.getPackage(), UMLElementTypes.Package_Shape, false);
	}

	@Test
	public void testToManageStereoTypeInPackage() {
		testToManageNode(UMLElementTypes.Stereotype_Shape_CN, UMLPackage.eINSTANCE.getStereotype(), UMLElementTypes.Package_Shape, false);
	}

	@Test
	public void testToManageProfileInPackage() {
		testToManageNode(UMLElementTypes.Profile_Shape_CN, UMLPackage.eINSTANCE.getProfile(), UMLElementTypes.Package_Shape, false);
	}

	@Test
	public void testToManageClassInProfile() {
		testToManageNode(UMLElementTypes.Class_Shape_CN, UMLPackage.eINSTANCE.getClass_(), UMLElementTypes.Profile_Shape, false);
	}

	@Test
	public void testToManageDatatypeInProfile() {
		testToManageNode(UMLElementTypes.DataType_Shape_CN, UMLPackage.eINSTANCE.getDataType(), UMLElementTypes.Profile_Shape, false);
	}

	@Test
	public void testToManagePackageInProfile() {
		testToManageNode(UMLElementTypes.Package_Shape_CN, UMLPackage.eINSTANCE.getPackage(), UMLElementTypes.Profile_Shape, false);
	}

	@Test
	public void testToManageStereoTypeInProfile() {
		testToManageNode(UMLElementTypes.Stereotype_Shape_CN, UMLPackage.eINSTANCE.getStereotype(), UMLElementTypes.Profile_Shape, false);
	}

	@Test
	public void testToManageProfileInProfile() {
		testToManageNode(UMLElementTypes.Profile_Shape_CN, UMLPackage.eINSTANCE.getProfile(), UMLElementTypes.Profile_Shape, false);
	}
}
