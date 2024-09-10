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

import org.eclipse.papyrus.uml.diagram.wizards.pages.PapyrusProjectCreationPage;
import org.eclipse.papyrus.uml.diagram.wizards.pages.SelectArchitectureContextPage;
import org.eclipse.papyrus.uml.diagram.wizards.pages.SelectRepresentationKindPage;
import org.eclipse.papyrus.uml.diagram.wizards.wizards.NewPapyrusProjectWizard;
import org.eclipse.ui.IWorkbenchWizard;
import org.junit.Test;


public class TestNewProjectWizard extends TestNewModelWizardBase {


	@Override
	protected IWorkbenchWizard createWizard() {
		return new NewPapyrusProjectWizard();
	}

	@Test
	public void testOrderOfPages() {

		// actual pages: [SelectDiagramCategory -> SelectDiagramCategoryPage, PapyrusNewProjectPage -> PapyrusProjectCreationPage, SelectDiagramKind -> SelectDiagramKindPage]
		Class<?>[] expectedPages = new Class[] { SelectArchitectureContextPage.class, PapyrusProjectCreationPage.class, SelectRepresentationKindPage.class, };

		IWorkbenchWizard wizard = initWizardDialog();
		testOrderOfPages(wizard, expectedPages);
	}

	public void testProfileExtension() {
	}


}
