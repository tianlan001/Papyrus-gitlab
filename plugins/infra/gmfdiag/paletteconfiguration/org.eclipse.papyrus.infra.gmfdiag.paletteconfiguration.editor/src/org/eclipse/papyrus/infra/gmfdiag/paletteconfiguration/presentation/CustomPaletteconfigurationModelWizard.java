/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.presentation;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.presentation.PaletteConfigurationEditorPlugin;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.presentation.PaletteconfigurationModelWizard;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.utils.CreatePaletteItemUtil;

/**
 * Custom wizard for palette configuration model.
 */
public class CustomPaletteconfigurationModelWizard extends PaletteconfigurationModelWizard {


	/**
	 * {@inheritDoc}
	 */
	@Override
	public EObject createInitialModel() {

		// Create the initial model
		PaletteConfiguration palette = CreatePaletteItemUtil.createInitialModel();

		if (null != initialObjectCreationPage) {
			((CustomPaletteCreationPage) initialObjectCreationPage).updateModel(palette);
		}

		return palette;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPages() {
		// Create a page, set the title, and the initial model file name.
		newFileCreationPage = new PaletteconfigurationModelWizardNewFileCreationPage("Whatever", selection);//$NON-NLS-1$
		newFileCreationPage.setTitle(PaletteConfigurationEditorPlugin.INSTANCE.getString("_UI_PaletteconfigurationModelWizard_label"));//$NON-NLS-1$
		newFileCreationPage.setDescription(PaletteConfigurationEditorPlugin.INSTANCE.getString("_UI_PaletteconfigurationModelWizard_description"));//$NON-NLS-1$
		StringBuilder message = new StringBuilder();
		message.append(PaletteConfigurationEditorPlugin.INSTANCE.getString("_UI_PaletteconfigurationEditorFilenameDefaultBase"));//$NON-NLS-1$
		message.append(".");// $NON-NLS-N$
		message.append(FILE_EXTENSIONS.get(0));
		newFileCreationPage.setFileName(message.toString());
		addPage(newFileCreationPage);

		// Try and get the resource selection to determine a current directory for the file dialog.
		if (null != selection && !selection.isEmpty()) {
			// Get the resource...
			Object selectedElement = selection.iterator().next();
			if (selectedElement instanceof IResource) {
				// Get the resource parent, if its a file.
				IResource selectedResource = (IResource) selectedElement;
				if (selectedResource.getType() == IResource.FILE) {
					selectedResource = selectedResource.getParent();
				}

				// This gives us a directory...
				if (selectedResource instanceof IFolder || selectedResource instanceof IProject) {
					// Set this for the container.
					//
					newFileCreationPage.setContainerFullPath(selectedResource.getFullPath());

					// Make up a unique new name here.
					String defaultModelBaseFilename = PaletteConfigurationEditorPlugin.INSTANCE.getString("_UI_PaletteconfigurationEditorFilenameDefaultBase");//$NON-NLS-1$
					String defaultModelFilenameExtension = FILE_EXTENSIONS.get(0);
					String modelFilename = defaultModelBaseFilename + "." + defaultModelFilenameExtension;//$NON-NLS-1$
					for (int i = 1; ((IContainer) selectedResource).findMember(modelFilename) != null; ++i) {
						modelFilename = defaultModelBaseFilename + i + "." + defaultModelFilenameExtension;//$NON-NLS-1$
					}
					newFileCreationPage.setFileName(modelFilename);
				}
			}
		}

		initialObjectCreationPage = new CustomPaletteCreationPage(this, "Whatever2");//$NON-NLS-1$
		initialObjectCreationPage.setTitle(PaletteConfigurationEditorPlugin.INSTANCE.getString("_UI_PaletteconfigurationModelWizard_label"));//$NON-NLS-1$
		initialObjectCreationPage.setDescription(PaletteConfigurationEditorPlugin.INSTANCE.getString("_UI_Wizard_initial_object_description"));//$NON-NLS-1$
		addPage(initialObjectCreationPage);
	}
}
