/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 * Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Bug 507654 Change the save order
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.palette.dialog;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPaletteService.ProviderDescriptor;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.toolsmiths.palette.Messages;
import org.eclipse.papyrus.toolsmiths.palette.PaletteConstants;
import org.eclipse.papyrus.toolsmiths.palette.utils.PaletteUtils;
import org.eclipse.papyrus.infra.gmfdiag.common.Activator;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

/**
 * Wizard to export an existing Palette Definition and its associated models files.
 */
public class ExportPaletteConfigurationWizard extends AbstractPaletteConfigurationWizard {

	/** content page */
	protected PaletteConfigurationContentPage contentPage;

	/** the new file creation page of the wizard */
	private WizardNewFileCreationPage newFileCreationPage;

	/**
	 * Creates a new local Palette Wizard.
	 *
	 * @param part
	 *            the editor part where the palette is available
	 * @param descriptor
	 *            the descriptor to edit
	 */
	public ExportPaletteConfigurationWizard(final IEditorPart part, final ProviderDescriptor descriptor) {
		super(part, descriptor);
		setWindowTitle(Messages.ExportPaletteConfigurationWizard_ExportWiazrdLabel);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPages() {
		super.addPages();

		newFileCreationPage = new WizardNewFileCreationPage("", new StructuredSelection());//$NON-NLS-1$

		newFileCreationPage.setTitle(Messages.ExportPaletteConfigurationWizard_export_palette);
		newFileCreationPage.setDescription(Messages.ExportPaletteConfigurationWizard_Export_description);

		addPage(newFileCreationPage);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void saveResources() {

		// Get the wanted location
		String selectedPath = newFileCreationPage.getContainerFullPath().append(newFileCreationPage.getFileName()).toString();

		clearStringBuilder();
		stringBuilder.append(selectedPath);
		stringBuilder.append(PaletteConstants.ELEMENTTYPE_SEMENTIC_IDENTIFIER_POSTFIX);
		stringBuilder.append(STR_DOT);
		stringBuilder.append(PaletteConstants.ELEMENTTYPE_EXTENSION);
		elementTypeSemResource.setURI(URI.createFileURI(stringBuilder.toString()));

		clearStringBuilder();
		stringBuilder.append(selectedPath);
		stringBuilder.append(PaletteConstants.ELEMENTTYPE_UI_IDENTIFIER_POSTFIX);
		stringBuilder.append(STR_DOT);
		stringBuilder.append(PaletteConstants.ELEMENTTYPE_EXTENSION);
		elementTypeUIResource.setURI(URI.createFileURI(stringBuilder.toString()));

		clearStringBuilder();
		stringBuilder.append(selectedPath);
		stringBuilder.append(STR_DOT);
		stringBuilder.append(PaletteConstants.PALETTECONFIGURATION_EXTENSION);
		// Set new path
		paletteResource.setURI(URI.createFileURI(stringBuilder.toString()));

		// Save
		try {
			// Checks if model are not void and are useful
			if (!((ElementTypeSetConfiguration) elementTypeUIResource.getContents().get(0)).getElementTypeConfigurations().isEmpty()) {
				elementTypeSemResource.save(PaletteUtils.saveOptions);
				elementTypeUIResource.save(PaletteUtils.saveOptions);
			}
			paletteResource.save(PaletteUtils.saveOptions);
		} catch (IOException e) {
			Activator.log.error(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void deployModels() {
		// Nothings to loads
	}
}
