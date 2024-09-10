/*****************************************************************************
 * Copyright (c) 2010, 2013 CEA LIST.
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
 *  Tatiana Fesenko (CEA LIST) - Initial API and implementation
 *  Christian W. Damus (CEA) - Support creating models in repositories (CDO)
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.wizards.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.uml.diagram.wizards.Activator;
import org.eclipse.papyrus.uml.diagram.wizards.messages.Messages;
import org.eclipse.papyrus.uml.diagram.wizards.pages.SelectArchitectureContextPage;

/**
 * The Wizard creates a new Project and a several Papyrus Models of different categories inside it.
 */
public class NewPapyrusProjectWithMultiModelsWizard extends NewPapyrusProjectWizard {

	/** The my do not create model for no diagrams. */
	private boolean myDoNotCreateModelForNoDiagrams;

	/** The Constant WIZARD_ID. */
	public static final String WIZARD_ID = "org.eclipse.papyrus.uml.diagram.wizards.createproject.several"; //$NON-NLS-1$


	/**
	 * Instantiates a new new papyrus project with multi models wizard.
	 */
	public NewPapyrusProjectWithMultiModelsWizard() {
		this(false);
	}

	/**
	 * Instantiates a new new papyrus project with multi models wizard.
	 *
	 * @param doNotCreateModelForNoDiagrams
	 *            the do not create model for no diagrams
	 */
	public NewPapyrusProjectWithMultiModelsWizard(boolean doNotCreateModelForNoDiagrams) {
		myDoNotCreateModelForNoDiagrams = doNotCreateModelForNoDiagrams;
	}

	@Override
	public boolean isCreateMultipleModelsWizard() {
		return true;
	}

	@Override
	protected SelectArchitectureContextPage createSelectArchitectureContextPage() {
		return new SelectArchitectureContextPage(true);
	}

	/**
	 * Perform finish.
	 *
	 * @return true, if successful {@inheritDoc}
	 */
	@Override
	public boolean performFinish() {
		IProject newProjectHandle;
		try {
			newProjectHandle = createNewProject();
		} catch (CoreException e) {
			Activator.log.error(Messages.NewPapyrusProjectWithMultiModelsWizard_exception_on_project_opening, e);
			return false;
		}
		if (newProjectHandle == null) {
			return false;
		}
		for (String contextId : getSelectedContexts()) {
			if (myDoNotCreateModelForNoDiagrams && getRepresentationKindsFor(contextId).isEmpty()) {
				// don't create model
				continue;
			}
			final URI newURI = createNewModelURI(contextId);
			String[] viewpointIds = getSelectedViewpoints(contextId);
			createAndOpenPapyrusModel(newURI, contextId, viewpointIds);
		}

		// saveDiagramCategorySettings();
		// saveDiagramKindSettings();
		return true;
	}

}
