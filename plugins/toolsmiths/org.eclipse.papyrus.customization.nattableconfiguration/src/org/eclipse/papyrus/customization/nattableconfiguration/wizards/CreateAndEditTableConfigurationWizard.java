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
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.customization.nattableconfiguration.wizards;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.customization.nattableconfiguration.Activator;
import org.eclipse.papyrus.customization.nattableconfiguration.pages.NattableConfigurationFileCreationPage;
import org.eclipse.papyrus.customization.nattableconfiguration.utils.NattableConfigurationConstants;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TableHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.NattableconfigurationFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.infra.tools.util.FileUtils;
import org.eclipse.papyrus.internal.infra.nattable.model.resources.NattableConfigurationResource;
import org.eclipse.ui.IWorkbench;

/**
 * The wizard used to create and edit a Papyrus Table configuration in an existing project
 *
 */
public class CreateAndEditTableConfigurationWizard extends EditTableConfigurationWizard {

	/**
	 * the page used to create the table configuration
	 */
	private NattableConfigurationFileCreationPage page = null;

	/**
	 * the initial selection done by the used on which the wizard has been called
	 */
	private Object firstSelectedElement;

	/**
	 * 
	 * @see org.eclipse.papyrus.customization.nattableconfiguration.wizards.EditTableConfigurationWizard#addEditNattableConfigurationPage()
	 *
	 */
	@Override
	protected void addEditNattableConfigurationPage() {
		addPage(this.page = new NattableConfigurationFileCreationPage(this.helper));
	}

	/**
	 * Get the edited table configuration.
	 * 
	 * @return
	 * 		the edited table configuration.
	 */
	@Override
	protected TableConfiguration getEditedTableConfiguration(final Resource resource) {
		// 1. create the configuration itself
		TableConfiguration configuration = NattableconfigurationFactory.eINSTANCE.createTableConfiguration();

		// 2. create the row and the column header axis configuration
		final TableHeaderAxisConfiguration rowHeaderAxisConfiguration = NattableaxisconfigurationFactory.eINSTANCE.createTableHeaderAxisConfiguration();
		final TableHeaderAxisConfiguration columnHeaderAxisConfiguration = NattableaxisconfigurationFactory.eINSTANCE.createTableHeaderAxisConfiguration();
		configuration.setColumnHeaderAxisConfiguration(columnHeaderAxisConfiguration);
		configuration.setRowHeaderAxisConfiguration(rowHeaderAxisConfiguration);
		return configuration;
	}

	/**
	 * @see org.eclipse.papyrus.customization.nattableconfiguration.wizards.EditTableConfigurationWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 *
	 * @param workbench
	 * @param selection
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		super.init(workbench, selection);
		if (selection instanceof StructuredSelection) {
			this.firstSelectedElement = selection.getFirstElement();
		}
	}

	/**
	 * @see org.eclipse.papyrus.customization.nattableconfiguration.wizards.EditTableConfigurationWizard#performFinish()
	 *
	 * @return
	 */
	@Override
	public boolean performFinish() {
		StringBuilder nattableConfigurationFileName = new StringBuilder(this.page.getNattableConfigurationFileName());

		// Check if the file has the nattable configuration extension file, else add it

		if (!nattableConfigurationFileName.toString().contains(FileUtils.DOT_STRING)
				|| !NattableConfigurationResource.NATTABLE_CONFIGURATION_RESOURCE_FILE_EXTENSION.equals(nattableConfigurationFileName.toString().substring(nattableConfigurationFileName.lastIndexOf(FileUtils.DOT_STRING) + 1))) {
			nattableConfigurationFileName.append(FileUtils.DOT_STRING);
			nattableConfigurationFileName.append(NattableConfigurationResource.NATTABLE_CONFIGURATION_RESOURCE_FILE_EXTENSION);
		}

		final ResourceSet set = new ResourceSetImpl();
		final StringBuilder nattableConfFileURI = new StringBuilder();
		nattableConfFileURI.append(getPathForTableConfigurationCreation());
		nattableConfFileURI.append(nattableConfigurationFileName);
		this.initialResource = set.createResource(URI.createFileURI(nattableConfFileURI.toString()));
		// the configuration is added into the resource in the super class
		// initialResource.getContents().add(configuration);
		boolean result = super.performFinish();
		refreshProject();
		return result;
	}

	/**
	 * 
	 * @return
	 * 		the location to use to create the new Papyrus Table Configuration.
	 *         The location will be the folder selected by the user or for others selections, a folfer called {@link NattableConfigurationConstants#CONFIG_FOLDER} in others cases
	 */
	protected String getPathForTableConfigurationCreation() {
		String location = null;
		if (this.firstSelectedElement instanceof IFolder) {
			String projectLocation = ((IFolder) this.firstSelectedElement).getLocation().toString();
			StringBuilder builder = new StringBuilder(projectLocation);
			builder.append(FileUtils.SLASH_STRING);
			location = builder.toString();
		} else if (this.firstSelectedElement instanceof IResource) {
			String projectLocation = getProjectLocation();
			StringBuilder builder = new StringBuilder(projectLocation);
			builder.append(NattableConfigurationConstants.CONFIG_FOLDER);
			location = builder.toString();
		}
		return location;
	}

	/**
	 * This method allows to refresh the project (required to display the created table configuration)
	 */
	protected final void refreshProject() {
		try {
			getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e) {
			Activator.log.error(e);
		}
	}

	/**
	 * 
	 * @return
	 * 		the project in which is created the table configuration
	 */
	protected IProject getProject() {
		IProject project = null;
		if (this.firstSelectedElement instanceof org.eclipse.core.internal.resources.Resource) {
			project = ((org.eclipse.core.internal.resources.Resource) firstSelectedElement).getProject();
		}
		return project;
	}

	/**
	 * 
	 * @return
	 * 		the location of the project
	 */
	protected String getProjectLocation() {
		IProject project = getProject();
		if (null != project) {
			return project.getLocation().toString();
		}
		return null;
	}
}
