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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.customization.nattableconfiguration.wizards;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.customization.nattableconfiguration.Activator;
import org.eclipse.papyrus.customization.nattableconfiguration.helper.TableConfigurationHelper;
import org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages;
import org.eclipse.papyrus.customization.nattableconfiguration.pages.ColumnConfigurationWizardPage;
import org.eclipse.papyrus.customization.nattableconfiguration.pages.EditGenericNattableConfigurationFieldsNattableWizardPage;
import org.eclipse.papyrus.customization.nattableconfiguration.pages.RowConfigurationWizardPage;
import org.eclipse.papyrus.customization.nattableconfiguration.pages.SlaveConfigurationWizardPage;
import org.eclipse.papyrus.customization.nattableconfiguration.utils.NattableConfigurationConstants;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.papyrus.infra.nattable.wizard.AbstractTableWizard;
import org.eclipse.papyrus.infra.ui.util.WorkbenchPartHelper;
import org.eclipse.papyrus.internal.infra.nattable.model.resources.NattableConfigurationResource;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

/**
 * The wizard to edit an existing TableConfiguration file
 */
public class EditTableConfigurationWizard extends AbstractTableWizard implements IWorkbenchWizard {

	/**
	 * The generate plugin activator setting.
	 */
	private static final String GENERATE_PlUGIN_ACTIVATOR = "generatePluginActivator"; //$NON-NLS-1$

	/**
	 * The ui plugin setting.
	 */
	private static final String UI_PLUGIN = "uiPlugin"; //$NON-NLS-1$

	/**
	 * The edited table configuration
	 */
	protected TableConfiguration configuration;

	/**
	 * The initial resource selected.
	 */
	protected Resource initialResource;

	/**
	 * the edited Table Configuration Helper;
	 */
	protected TableConfigurationHelper helper;


	/**
	 * 
	 * Constructor.
	 * 
	 * Thi sconstructor is used when we create a new Papyrus Table Configuration Poject from the wizard Dialog
	 *
	 */
	public EditTableConfigurationWizard() {
		final ImageDescriptor desc = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImageDescriptor(Activator.PLUGIN_ID, NattableConfigurationConstants.ICON_WIZBAN_PATH);
		setDefaultPageImageDescriptor(desc);
		setWindowTitle(Messages.CreateNattableConfigurationWizard_WizardTitke);
		setForcePreviousAndNextButtons(true);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.wizard.AbstractTableWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 *
	 * @param workbench
	 * @param selection
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		super.init(workbench, selection);
		this.initialResource = getSelectedResource(selection);
		this.configuration = getEditedTableConfiguration(this.initialResource);
		Assert.isNotNull(this.configuration);
		this.helper = new TableConfigurationHelper(configuration);
	}

	/**
	 * 
	 * @param currentSelection
	 *            the current selection
	 * @return
	 * 		the resource if it already exist or <code>null</code>;
	 */
	protected Resource getSelectedResource(final ISelection currentSelection) {
		Resource resource = null;

		if (currentSelection instanceof StructuredSelection && 1 == ((StructuredSelection) currentSelection).size()) {
			final Object selectedElement = ((StructuredSelection) currentSelection).getFirstElement();
			if (selectedElement instanceof File && NattableConfigurationResource.NATTABLE_CONFIGURATION_RESOURCE_FILE_EXTENSION.equals(((File) selectedElement).getFileExtension())) {

				INattableModelManager manager = null;
				// TODO required ?
				final IEditorPart currentPart = WorkbenchPartHelper.getCurrentActiveWorkbenchPart().getSite().getPage().getActiveEditor();
				if (null != currentPart) {
					manager = currentPart.getAdapter(INattableModelManager.class);
				}

				if (null != manager) {
					resource = TableEditingDomainUtils.getTableEditingDomain(manager.getTable()).getResourceSet().getResource(URI.createFileURI(((File) selectedElement).getLocation().toString()), true);
				} else {
					final ResourceSet set = new ResourceSetImpl();
					resource = set.getResource(URI.createFileURI(((File) selectedElement).getLocation().toString()), true);
				}
			}
		}

		return resource;
	}

	/**
	 * Get the edited table configuration.
	 * 
	 * @return
	 * 		the edited table configuration.
	 */
	protected TableConfiguration getEditedTableConfiguration(final Resource resource) {
		TableConfiguration configuration = null;
		if (null != resource) {
			if (!resource.getContents().isEmpty() && resource.getContents().get(0) instanceof TableConfiguration) {
				configuration = EcoreUtil.copy((TableConfiguration) resource.getContents().get(0));
			}
		}
		return configuration;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		addEditNattableConfigurationPage();
		addPage(new RowConfigurationWizardPage(this.helper));
		addPage(new ColumnConfigurationWizardPage(this.helper));
		addPage(new SlaveConfigurationWizardPage(this.helper));
	}

	protected void addEditNattableConfigurationPage(){
		addPage(new EditGenericNattableConfigurationFieldsNattableWizardPage(this.helper));
	}
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#getDialogSettings()
	 */
	@Override
	public IDialogSettings getDialogSettings() {
		DialogSettings dialogSettings = new DialogSettings(""); //$NON-NLS-1$
		dialogSettings.put(GENERATE_PlUGIN_ACTIVATOR, true);
		dialogSettings.put(UI_PLUGIN, false);
		return dialogSettings;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		
		boolean result = false;

		// The resource is modified directly without command (undo/redo impossible)
		// Save DiagramDialog at proper position
		if (null != this.initialResource) {
			this.initialResource.getContents().clear();
			this.initialResource.getContents().add(this.configuration);
			result = true;
		}
		if (result) {
			result = saveResource();
		}

		return result;
	}

	/**
	 * 
	 * @return
	 * 		<code>true</code> if the resource has been properly saved
	 */
	protected final boolean saveResource() {
		boolean result = true;
		final Map<Object, Object> saveOptions = new HashMap<Object, Object>();
		saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED, Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
		saveOptions.put(Resource.OPTION_LINE_DELIMITER, Resource.OPTION_LINE_DELIMITER_UNSPECIFIED);

		try {
			initialResource.save(saveOptions);
		} catch (final IOException e) {
			Activator.log.error(e);
			result = false;
		}
		return result;
	}
}
