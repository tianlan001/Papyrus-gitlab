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
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - new implementation for palette configuration model case
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.palette.dialog;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;

import org.eclipse.emf.common.util.URI;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditorWithFlyOutPalette;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.papyrus.infra.gmfdiag.common.Activator;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PaletteUtil;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPalettePreferences;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPaletteService.ProviderDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;
import org.eclipse.papyrus.toolsmiths.palette.Messages;
import org.eclipse.papyrus.toolsmiths.palette.PaletteConstants;
import org.eclipse.papyrus.toolsmiths.palette.PaletteConstants.PaletteModelContextEnum;
import org.eclipse.papyrus.toolsmiths.palette.utils.PaletteUtils;
import org.eclipse.ui.IEditorPart;

/**
 * Wizard to update or create an Palette configuration
 */
public class PaletteConfigurationWizard extends AbstractPaletteConfigurationWizard {

	/** content page */
	protected WizardPage contentPage;

	/** info page */
	protected WizardPage infoPage;

	/**
	 * Creates a NewLocalPaletteWizard.
	 *
	 * @param part
	 *            the editor part where the palette is available
	 * @param descriptor
	 *            the descriptor to edit
	 */
	public PaletteConfigurationWizard(final IEditorPart part, final ProviderDescriptor descriptor) {
		super(part, descriptor);
	}

	/** constructor in case of new palette configuration model */
	public PaletteConfigurationWizard(final IEditorPart part) {
		this(part, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPages() {
		super.addPages();

		// create pages
		infoPage = createPaletteInformationPage();
		contentPage = createPaletteContentPage();

		// Add pages
		addPage(infoPage);
		addPage(contentPage);
	}

	/**
	 * Create the content page.
	 */
	protected WizardPage createPaletteContentPage() {
		// second page: describe the paletteContent
		WizardPage page = new PaletteConfigurationContentPage(editorPart, editingDomain);
		page.setTitle(Messages.PaletteConfigurationWizard_NewPaletteContentPageWizard_Title);
		page.setDescription(Messages.PaletteConfigurationWizard_NewPaletteContentPageWizard_Description);

		// init content page
		((PaletteConfigurationContentPage) page).setPriority(priority);
		return page;
	}

	/**
	 * Create the information page.
	 */
	protected WizardPage createPaletteInformationPage() {
		WizardPage page = new PaletteConfigurationInformationPage("", (PaletteConfiguration) paletteResource.getContents().get(0));//$NON-NLS-1$
		if (PaletteModelContextEnum.New.equals(paletteContext)) {
			page.setTitle(Messages.PaletteConfigurationWizard_NewPaletteInfoPageWizard_Title);
		} else {
			page.setTitle(Messages.PaletteConfigurationWizard_EditionPaletteInfoPageWizard_Tilte);
		}
		page.setDescription(Messages.PaletteConfigurationWizard_PaletteInfoPageWizard_Description);
		return page;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void deployModels() {

		// Loads element types models.
		deployElementTypesModels();

		// loads modified palette model
		deployPaletteModel();

		// Force the update
		PaletteUtil.getAvailableEntriesSet(editorPart, priority);
	}

	/**
	 * load palette models on Papyrus preferences.
	 */
	protected void deployPaletteModel() {
		// Get the palette model
		PaletteConfiguration paletteModel = (PaletteConfiguration) paletteResource.getContents().get(0);

		// The palette id
		String paletteId = paletteModel.getId();

		// Update palette preferences
		switch (paletteContext) {

		case New:
			// New model
			PapyrusPalettePreferences.addLocalExtendedPalette(paletteId, paletteId, priority, PaletteUtils.getEditorIdValue(editorPart), new HashSet(paletteModel.getRequiredProfiles()));
			break;

		case Local:
			// Edition of a local extended palette
			// Refresh by deletion and re add it.
			PapyrusPalettePreferences.deleteLocalExtendedPalette(descriptor.getContributionID());
			PapyrusPalettePreferences.addLocalExtendedPalette(paletteId, paletteId, priority, PaletteUtils.getEditorIdValue(editorPart), new HashSet(paletteModel.getRequiredProfiles()));
			break;

		case Plugin:
			// Edition of palette redefinition from plugin
			// Case of redefinition of read-only palette configuration model
			String path = PapyrusPalettePreferences.getPaletteRedefinition(descriptor.getContributionID());
			if (null != path) {
				// Register the redefinition
				PapyrusPalettePreferences.registerLocalRedefinition(descriptor.getContributionID(), path); // if already exists: removes, then add
			}
			break;

		case Workspace:
			// Workspace palette case
			String pathWS = paletteResource.getURI().toPlatformString(false);
			// If identifier is changed: need to delete palette
			if (null != descriptor && !paletteId.equals(descriptor.getContributionID())) {
				PapyrusPalettePreferences.deleteWorkspaceExtendedPalette(descriptor.getContributionID());// Delete it in case of id change
			}
			PapyrusPalettePreferences.addWorkspaceExtendedPalette(paletteId, paletteId, pathWS, priority, PaletteUtils.getEditorIdValue(editorPart), new HashSet(paletteModel.getRequiredProfiles()));

			break;

		default:
			break;
		}


		// toggle visibility to refresh the content
		if (editorPart instanceof DiagramEditorWithFlyOutPalette) {
			PapyrusPalettePreferences.changePaletteVisibility(paletteId, editorPart, false);
			PapyrusPalettePreferences.changePaletteVisibility(paletteId, editorPart, true);
		}

	}

	/**
	 * deploy element types models.
	 */
	protected void deployElementTypesModels() {
		// deploy element types configuration files
		if (!elementTypeSemResource.getContents().isEmpty() && !elementTypeUIResource.getContents().isEmpty()) {
			ElementTypeSetConfigurationRegistry.getInstance().unload(getClientContext(), ((ElementTypeSetConfiguration) elementTypeSemResource.getContents().get(0)).getIdentifier());
			ElementTypeSetConfigurationRegistry.getInstance().unload(getClientContext(), ((ElementTypeSetConfiguration) elementTypeUIResource.getContents().get(0)).getIdentifier());

			ElementTypeSetConfigurationRegistry.getInstance().loadElementTypeSetConfiguration(getClientContext(), ((ElementTypeSetConfiguration) elementTypeSemResource.getContents().get(0)));
			ElementTypeSetConfigurationRegistry.getInstance().loadElementTypeSetConfiguration(getClientContext(), ((ElementTypeSetConfiguration) elementTypeUIResource.getContents().get(0)));
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void saveResources() {
		// Get the palette model
		PaletteConfiguration paletteModel = (PaletteConfiguration) paletteResource.getContents().get(0);

		// The palette id
		String paletteId = paletteModel.getId();

		// Save resources

		// Id is changed => needs to rename files
		if (PaletteModelContextEnum.Local.equals(paletteContext) && !paletteId.equals(descriptor.getContributionID())) {

			// needs to change the file name for local extended palette model
			String newPalettePath = paletteResource.getURI().toFileString().replace(descriptor.getContributionID(), paletteId);
			paletteResource.setURI(URI.createFileURI(newPalettePath));

			String newElementTypeUIResourcePath = elementTypeUIResource.getURI().toFileString().replace(descriptor.getContributionID(), paletteId);
			elementTypeUIResource.setURI(URI.createFileURI(newElementTypeUIResourcePath));

			String newElementTypeSemResourcePath = elementTypeSemResource.getURI().toFileString().replace(descriptor.getContributionID(), paletteId);
			elementTypeSemResource.setURI(URI.createFileURI(newElementTypeSemResourcePath));

		} else if (PaletteModelContextEnum.New.equals(paletteContext)) {
			// set file names for new palette and element type
			clearStringBuilder();
			stringBuilder.append(paletteId);
			stringBuilder.append(STR_DOT);
			stringBuilder.append(PaletteConstants.PALETTECONFIGURATION_EXTENSION);
			String newPalettePath = paletteResource.getURI().toFileString().replace(paletteResource.getURI().lastSegment().replace("%20", " "), stringBuilder.toString());//$NON-NLS-1$ //$NON-NLS-2$
			paletteResource.setURI(URI.createFileURI(newPalettePath));

			clearStringBuilder();
			stringBuilder.append(paletteId);
			stringBuilder.append(PaletteConstants.ELEMENTTYPE_UI_IDENTIFIER_POSTFIX);
			stringBuilder.append(STR_DOT);
			stringBuilder.append(PaletteConstants.ELEMENTTYPE_EXTENSION);
			String newElementTypeUIResourcePath = elementTypeUIResource.getURI().toFileString().replace(elementTypeUIResource.getURI().lastSegment().replace("%20", " "), stringBuilder.toString());//$NON-NLS-1$ //$NON-NLS-2$
			elementTypeUIResource.setURI(URI.createFileURI(newElementTypeUIResourcePath));

			clearStringBuilder();
			stringBuilder.append(paletteId);
			stringBuilder.append(PaletteConstants.ELEMENTTYPE_SEMENTIC_IDENTIFIER_POSTFIX);
			stringBuilder.append(STR_DOT);
			stringBuilder.append(PaletteConstants.ELEMENTTYPE_EXTENSION);
			String newElementTypeSemResourcePath = elementTypeSemResource.getURI().toFileString().replace(elementTypeSemResource.getURI().lastSegment().replace("%20", " "), stringBuilder.toString());//$NON-NLS-1$ //$NON-NLS-2$
			elementTypeSemResource.setURI(URI.createFileURI(newElementTypeSemResourcePath));
		}

		// Save if necessary
		try {
			paletteResource.save(PaletteUtils.saveOptions);
			// Checks if model are not void and are useful
			if (!((ElementTypeSetConfiguration) elementTypeUIResource.getContents().get(0)).getElementTypeConfigurations().isEmpty()) {
				elementTypeUIResource.save(PaletteUtils.saveOptions);
				elementTypeSemResource.save(PaletteUtils.saveOptions);
			} else {
				ElementTypeSetConfigurationRegistry.getInstance().unload(getClientContext(), ((ElementTypeSetConfiguration) elementTypeSemResource.getContents().get(0)).getIdentifier());
				ElementTypeSetConfigurationRegistry.getInstance().unload(getClientContext(), ((ElementTypeSetConfiguration) elementTypeUIResource.getContents().get(0)).getIdentifier());
				elementTypeUIResource.delete(Collections.EMPTY_MAP);
				elementTypeSemResource.delete(Collections.EMPTY_MAP);
			}
		} catch (IOException e) {
			Activator.log.error(e);
		}
	}

	/**
	 * @return The client context of element types.
	 * 
	 *         TODO: switch it with point of you context if needed.
	 */
	protected String getClientContext() {
		return "org.eclipse.papyrus.infra.services.edit.TypeContext";//$NON-NLS-1$
	}


}
