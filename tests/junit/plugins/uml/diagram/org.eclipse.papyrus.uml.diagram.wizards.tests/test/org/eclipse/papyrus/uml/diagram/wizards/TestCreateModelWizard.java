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


public class TestCreateModelWizard extends TestNewModelWizardBase {

	@Override
	protected IWorkbenchWizard createWizard() {
		return new InitModelWizard() {

			@Override
			public boolean isCreateFromExistingDomainModel() {
				return false;
			}

		};
	}

	@Test
	public void testOrderOfPages() {
		// actual pages: [SelectDiagramCategory -> SelectDiagramCategoryPage, SelectStorageProvider -> SelectStorageProviderPage,
		// NewPapyrusModel -> NewModelFilePage, NewCDOModel -> NewModelPage, SelectDiagramKind -> SelectDiagramKindPage]
		Class<?>[] expectedPages = new Class[] { SelectArchitectureContextPage.class, NewModelFilePage.class, SelectRepresentationKindPage.class, };

		IWorkbenchWizard wizard = initWizardDialog();
		testOrderOfPages(wizard, expectedPages);
	}

	@Test
	public void testDiagramFileExtentionLabel() {
		final String expectedExtension = "di"; // the extension is always *.di
		IWorkbenchWizard wizard = new InitModelWizard() {

			@Override
			public boolean isCreateFromExistingDomainModel() {
				return false;
			}

			@Override
			public String getDiagramFileExtension(String categoryId, String defaultExtension) {
				return expectedExtension;
			}

		};

		initWizardDialog(wizard);
		NewModelFilePage page = getPage(wizard, NewModelFilePage.class);
		assertEquals(expectedExtension, page.getFileExtension());
	}

	// @Test
	public void testDiagramFileExtenstionForProfile() {
		final String expectedExtension = "profile.di";
		InitModelWizard wizard = new InitModelWizard() {

			@Override
			public boolean isCreateFromExistingDomainModel() {
				return false;
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

	@Test
	public void testDiagramFileExtenstionForUML() {
		final String expectedExtension = "di";
		InitModelWizard wizard = new InitModelWizard() {

			@Override
			public boolean isCreateFromExistingDomainModel() {
				return false;
			}

			@Override
			protected String[] getSelectedContexts() {
				return new String[] { UMLArchitectureContextIds.UML };
			}

		};

		initWizardDialog(wizard);
		NewModelFilePage page = getPage(wizard, NewModelFilePage.class);
		assertEquals(expectedExtension, page.getFileExtension());
	}

}
