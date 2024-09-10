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
package org.eclipse.papyrus.uml.diagram.wizards.pages;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.uml.diagram.wizards.messages.Messages;


/**
 * @author Thibault
 *
 */
public class NewDiagramForExistingModelPage extends NewModelFilePage {

	/** The my diagram file name. */
	private String myDiagramFileName;

	/**
	 * Instantiates a new new diagram for existing model page.
	 *
	 * @param selection
	 *        the selection
	 * @param defaultFileName
	 *        the default file name
	 * @param modelKindName
	 *        the user-presentable (translatable) name of the kind of
	 *        model to create
	 * @param diagramExtension
	 *        the diagram extension
	 */
	public NewDiagramForExistingModelPage(IStructuredSelection selection, String modelKindName, String defaultFileName, String diagramExtension) {

		super(selection, modelKindName);

		myDiagramFileName = defaultFileName;
		if(myDiagramFileName.contains("%20")) {
			myDiagramFileName = myDiagramFileName.replaceAll("%20", " ");
		}
		setFileName(myDiagramFileName);
		setFileExtension(diagramExtension);
		setTitle(Messages.InitModelWizard_init_papyrus_model);
		setDescription(Messages.InitModelWizard_init_papyrus_model_desc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#validatePage()
	 */
	@Override
	protected boolean validatePage() {
		if(!super.validatePage()) {
			return false;
		}
		if(!myDiagramFileName.equals(getFileName())) {
			setErrorMessage(Messages.bind(Messages.InitModelWizard_diagram_name_is_different_from_domain_model, myDiagramFileName));
			return false;
		}
		return true;
	};

}
