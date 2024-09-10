/*****************************************************************************
 * Copyright (c) 2013, 2014 LIFL, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  LIFL - Initial API and implementation
 *  CEA LIST - Update tests and re-integrate into automation suite
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.wizards;

import static org.junit.Assert.assertEquals;

import org.eclipse.papyrus.uml.architecture.UMLArchitectureContextIds;
import org.eclipse.papyrus.uml.diagram.wizards.pages.NewModelFilePage;
import org.eclipse.papyrus.uml.diagram.wizards.pages.SelectArchitectureContextPage;
import org.eclipse.papyrus.uml.diagram.wizards.pages.SelectRepresentationKindPage;
import org.eclipse.papyrus.uml.diagram.wizards.wizards.InitModelWizard;
import org.eclipse.ui.IWorkbenchWizard;
import org.junit.Test;


public class TestInitModelWizard extends TestNewModelWizardBase {

	private final boolean isCreateFromExistingModel = true;

	@Override
	protected IWorkbenchWizard createWizard() {
		return new InitModelWizard() {

			@Override
			public boolean isCreateFromExistingDomainModel() {
				return true;
			}


			@Override
			protected String[] getSelectedContexts() {
				return new String[] { "Software Engineering::UML" };
			}

			@Override
			public String getDiagramFileExtension(String categoryId, String defaultExtension) {
				return "uml";
			}

		};
	}

	@Test
	public void testDiagramFileExtentionLabel() {
		final String expectedExtension = "di"; // the extension is always *.di
		IWorkbenchWizard wizard = new InitModelWizard() {

			@Override
			public boolean isCreateFromExistingDomainModel() {
				return isCreateFromExistingModel;
			}

			@Override
			public String getDiagramFileExtension(String categoryId, String defaultExtension) {
				return "tanya"; // arbitrary extension to avoid NPE
			}

		};

		initWizardDialog(wizard);
		NewModelFilePage page = getPage(wizard, NewModelFilePage.class);
		assertEquals(expectedExtension, page.getFileExtension());
	}

	@Test
	public void testOrderOfPages() {
		// actual pages: [SelectDiagramCategory -> SelectDiagramCategoryPage, SelectStorageProvider -> SelectStorageProviderPage,
		// NewPapyrusModel -> NewModelFilePage, NewCDOModel -> NewModelPage, SelectDiagramKind -> SelectDiagramKindPage, SelectRootPage -> SelectRootElementPage]
		Class<?>[] expectedPages = new Class[] { SelectArchitectureContextPage.class, NewModelFilePage.class, SelectRepresentationKindPage.class, /* SelectRootElementPage.class, */ };

		IWorkbenchWizard wizard = initWizardDialog();
		testOrderOfPages(wizard, expectedPages);
	}

	@Test
	public void testDiagramFileExtenstionForUML() {
		// 333849 - [Wizard] Init Diagram: Respect file extension of the UML model
		final String expectedExtension = "di"; // init-from-existing always uses this, even for profiles
		InitModelWizard wizard = new InitModelWizard() {

			@Override
			public boolean isCreateFromExistingDomainModel() {
				return isCreateFromExistingModel;
			}


			@Override
			public String getDiagramFileExtension(String categoryId, String defaultExtension) {
				return "profile.di"; // arbitrary extension to avoid NPE
			}

			@Override
			protected String[] getSelectedContexts() {
				return new String[] { UMLArchitectureContextIds.Profile };
			}

		};

		// ensure that the dialog would create a profile
		settings.saveArchitectureContexts(new String[] { UMLArchitectureContextIds.Profile });

		initWizardDialog(wizard);
		NewModelFilePage page = getPage(wizard, NewModelFilePage.class);
		assertEquals(expectedExtension, page.getFileExtension());
	}


}
