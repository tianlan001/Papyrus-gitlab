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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.m2m.qvto.common.wizard;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.papyrus.uml.m2m.qvto.common.Activator;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.ThreadConfig;
import org.eclipse.papyrus.uml.m2m.qvto.common.transformation.AbstractImportTransformationLauncher;
import org.eclipse.papyrus.uml.m2m.qvto.common.wizard.pages.AbstractDialogData;
import org.eclipse.papyrus.uml.m2m.qvto.common.wizard.pages.TransformationConfigPage;
import org.eclipse.papyrus.uml.m2m.qvto.common.wizard.pages.TransformationSelectionPage;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

/**
 * 
 * Abstract Wizard For Model Migration into Papyrus
 *
 */
public abstract class AbstractTransformationWizard extends Wizard implements IImportWizard {

	protected IWizardPage currentPage;

	protected final AbstractDialogData dialogData;

	protected TransformationSelectionPage selectionPage;

	protected TransformationConfigPage configPage;


	public AbstractTransformationWizard(final String wizardTitle, final AbstractDialogData dialogData) {
		setWindowTitle(wizardTitle);
		this.dialogData = dialogData;
	}

	@Override
	public void addPages() {
		this.addPage(this.selectionPage = createTransformationSelectionPage(this.dialogData));
		this.addPage(this.configPage = createTransformationConfigPage(this.dialogData));

	}

	/**
	 * 
	 * @param dialogData
	 * 
	 * @return
	 * 		the selection page to include into the wizard
	 */
	protected TransformationSelectionPage createTransformationSelectionPage(final AbstractDialogData dialogData) {
		return new TransformationSelectionPage(dialogData);
	};

	/**
	 * 
	 * @param dialogData
	 * @return
	 * 		the configuration page to include into the wizard
	 */
	protected TransformationConfigPage createTransformationConfigPage(final AbstractDialogData dialogData) {
		return new TransformationConfigPage(dialogData);
	};

	@Override
	public IWizardPage getNextPage(IWizardPage currentPage) {
		if (currentPage == selectionPage) {
			this.currentPage = configPage;
			// Resets the viewer input in order to show the newly selected elements from the selectionPage
			configPage.resetViewerInput();
			return configPage;
		}
		if (this.currentPage == configPage) {
			this.currentPage = selectionPage;
		}
		return null;
	}

	@Override
	public boolean canFinish() {
		if (currentPage == configPage) {
			return super.canFinish();
		}
		return false;
	}

	@Override
	public boolean performCancel() {
		removeWizardImportedProjects();
		return super.performCancel();
	}

	@Override
	public boolean performFinish() {
		// Set or update the unchecked elements for future executions of the plugin
		dialogData.setSelectionMap();
		importFiles();

		// Remove the imported projects from the workspace
		// removeWizardImportedProjects();

		return true;
	}


	/**
	 * 
	 * Remove any imported projects, through the wizard, from the workspace
	 * 
	 */
	public void removeWizardImportedProjects() {
		if (/* dialogData != null && */dialogData.getImportedProjects() != null) {
			for (Object object : dialogData.getImportedProjects()) {
				if (object instanceof IProject) {
					IProject project = (IProject) object;
					try {
						project.delete(false, true, null);
					} catch (CoreException e) {
						Activator.log.error(e);
					}
				}
			}
		}
	}

	/**
	 *
	 * Launch the transformation with the previously selected files and configuration parameters
	 *
	 */
	protected void importFiles() {
		ThreadConfig config = dialogData.getConfig();
		if (config == null) {
			return;
		}

		List<URI> urisToImport = new LinkedList<URI>();

		for (Object selectedFile : dialogData.getTransformationFiles()) {
			String path = null;
			if (selectedFile instanceof IFile) {
				path = ((IFile) selectedFile).getFullPath().toString();
			}
			if (path != null) {
				URI uri = URI.createPlatformResourceURI(path, true);
				urisToImport.add(uri);
			}
		}

		// The wizard's Shell will be disposed because the transformation is asynchronous. Use the Shell's parent instead
		AbstractImportTransformationLauncher launcher = createTransformationLauncher(config, this.getShell().getParent());
		launcher.run(urisToImport);
	}


	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// nothing
	}

	/**
	 * 
	 * @param config
	 *            the migration configuration
	 * @param baseControl
	 *            the base control used to launch the transformation itself
	 * @return
	 * 		the create Import Transformation Launcher
	 */
	protected abstract AbstractImportTransformationLauncher createTransformationLauncher(final ThreadConfig config, Control baseControl);

}