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
 *   Vincent LORENZO (CEA-LIST) vincent.lorenzo@cea.fr - Bug 493756
 *****************************************************************************/

package org.eclipse.papyrus.customization.nattableconfiguration.wizards;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.customization.nattableconfiguration.Activator;
import org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages;
import org.eclipse.papyrus.customization.nattableconfiguration.utils.NattableConfigurationConstants;
import org.eclipse.pde.internal.ui.wizards.IProjectProvider;
import org.eclipse.pde.internal.ui.wizards.plugin.NewProjectCreationOperation;
import org.eclipse.pde.internal.ui.wizards.plugin.NewProjectCreationPage;
import org.eclipse.pde.internal.ui.wizards.plugin.PluginContentPage;
import org.eclipse.pde.internal.ui.wizards.plugin.PluginFieldData;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;

/**
 * The wizard used to create a Papyrus Table Configuration Project
 */
public class CreateNattableConfigurationProjectWizard extends CreateAndEditTableConfigurationWizard implements IWorkbenchWizard {


	/**
	 * The page for the project creation.
	 */
	private NewProjectCreationPage nattableConfigurationProjectCreationPage;

	/**
	 * The project provider.
	 */
	private IProjectProvider projectProvider;

	/**
	 * The content page for the project creation.
	 */
	protected PluginContentPage contentPage;

	/**
	 * The fields data to manage the project creation
	 */
	private PluginFieldData pluginData;

	/**
	 * @see org.eclipse.papyrus.customization.nattableconfiguration.EditTableConfigurationWizard#addPages()
	 *
	 */
	@Override
	public void addPages() {
		pluginData = new PluginFieldData();

		nattableConfigurationProjectCreationPage = new NewProjectCreationPage(Messages.NattableConfigurationProjectCreationPage_pageName, pluginData, false,new StructuredSelection());
		addPage(nattableConfigurationProjectCreationPage);

		projectProvider = new IProjectProvider() {
			@Override
			public String getProjectName() {
				return nattableConfigurationProjectCreationPage.getProjectName();
			}

			@Override
			public IProject getProject() {
				return nattableConfigurationProjectCreationPage.getProjectHandle();
			}

			@Override
			public IPath getLocationPath() {
				return nattableConfigurationProjectCreationPage.getLocationPath();
			}
		};

		contentPage = new PluginContentPage("page2", projectProvider, nattableConfigurationProjectCreationPage, pluginData); //$NON-NLS-1$

		addPage(contentPage);
		super.addPages();
	}




	/**
	 * @see org.eclipse.papyrus.customization.nattableconfiguration.EditTableConfigurationWizard#performFinish()
	 *
	 * @return
	 */
	@Override
	public boolean performFinish() {
		boolean result = false;

		IProject createdProject = null;

		try {

			// Create the project
			getContainer().run(false, true, new NewProjectCreationOperation(pluginData, projectProvider, null));

			createdProject = getProject();

			// Set the project into the working sets
			final IWorkingSet[] workingSets = nattableConfigurationProjectCreationPage.getSelectedWorkingSets();
			if (0 < workingSets.length) {
				PlatformUI.getWorkbench().getWorkingSetManager().addToWorkingSets(createdProject, workingSets);
			}

			// Copy the about file
			copyAboutFile(createdProject);
			result = super.performFinish();
		} catch (final InvocationTargetException e) {
			Activator.log.error(e);
		} catch (final InterruptedException e) {
			Activator.log.error(e);
		}

		if (result) {
			result = saveResource();
		}
		refreshProject();
		return result;
	}
	
	/**
	 * This allows to get the project created folder.
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.customization.nattableconfiguration.wizards.CreateAndEditTableConfigurationWizard#getPathForTableConfigurationCreation()
	 */
	@Override
	protected String getPathForTableConfigurationCreation() {
		final String projectLocation = getProjectLocation();
		final StringBuilder builder = new StringBuilder(projectLocation);
		builder.append(NattableConfigurationConstants.CONFIG_FOLDER);
		
		return builder.toString();
	}

	/**
	 * This allows to copy the about file in the created project.
	 * 
	 * @param createdProject
	 *            The created project.
	 */
	protected void copyAboutFile(final IProject createdProject) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			final URL url = Activator.getDefault().getBundle().getResource(NattableConfigurationConstants.ABOUT_FILE_NAME);
			inputStream = url.openStream();

			final java.io.File newAboutFile = new java.io.File(createdProject.getLocation().toOSString() + java.io.File.separator + NattableConfigurationConstants.ABOUT_FILE_NAME);
			newAboutFile.createNewFile();

			outputStream = new FileOutputStream(newAboutFile);

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (final IOException e) {
			Activator.log.error(e);
		} finally {
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (final IOException e) {
					Activator.log.error(e);
				}
			}
			if (null != outputStream) {
				try {
					// outputStream.flush();
					outputStream.close();
				} catch (final IOException e) {
					Activator.log.error(e);
				}

			}
		}
	}

	
	/**
	 * @see org.eclipse.papyrus.customization.nattableconfiguration.wizards.CreateAndEditTableConfigurationWizard#getProject()
	 *
	 * @return
	 */
	@Override
	protected IProject getProject() {
		return this.projectProvider.getProject();
	}
	
	/**
	 * @see org.eclipse.papyrus.customization.nattableconfiguration.wizards.CreateAndEditTableConfigurationWizard#getProjectLocation()
	 *
	 * @return
	 */
	@Override
	protected String getProjectLocation() {
		IProject createdProject = getProject();
		return createdProject.getLocation().toString();
	}
}
