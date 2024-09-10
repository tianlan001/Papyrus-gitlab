/*****************************************************************************
 * Copyright (c) 2010, 2013, 2021 CEA LIST.
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
 *  Ansgar Radermacher (CEA) - Support working sets ((bug 572328), avoid deprecated SubProgressMonitor
 *
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.wizards.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.uml.diagram.wizards.Activator;
import org.eclipse.papyrus.uml.diagram.wizards.messages.Messages;
import org.eclipse.papyrus.uml.diagram.wizards.pages.PapyrusProjectCreationPage;
import org.eclipse.papyrus.uml.diagram.wizards.pages.SelectArchitectureContextPage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

/**
 * The Wizard creates a new Project and a Papyrus Model inside it.
 */
public class NewPapyrusProjectWizard extends CreateModelWizard {

	/** The Constant WIZARD_ID. */
	public static final String WIZARD_ID = "org.eclipse.papyrus.uml.diagram.wizards.createproject"; //$NON-NLS-1$

	/** The new project page. */
	private PapyrusProjectCreationPage myProjectPage;

	@Override
	public boolean isCreateProjectWizard() {
		return true;
	}

	/**
	 * Inits the.
	 *
	 * @param workbench
	 *            the workbench
	 * @param selection
	 *            the selection {@inheritDoc}
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		super.init(workbench, selection);
		setWindowTitle(Messages.NewPapyrusProjectWizard_new_papyrus_project);
		setMyProjectPage(new PapyrusProjectCreationPage(Messages.NewPapyrusProjectWizard_0));
		getMyProjectPage().setDescription(Messages.NewPapyrusProjectWizard_1);
		setDefaultPageImageDescriptor(Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/papyrus/PapyrusProjectWizban_75x66.gif")); //$NON-NLS-1$
	}



	/**
	 * Adds the pages.
	 *
	 * {@inheritDoc}
	 */
	@Override
	public void addPages() {
		// Gives the CreateModelWizard the newProjectPage to display it after the selectDiagramCategoryPage
		setNewProjectPage(getMyProjectPage());

		if (getSelectedContexts() == null) {
			// If no one Overrides the id list then no specific behavior is expected
			// We therefore fall back to the original behavior: ask the user to pick a language
			selectArchitectureContextPage = new SelectArchitectureContextPage();
		}

		super.addPages();
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
			Activator.log.error(Messages.NewPapyrusProjectWizard_exception_on_opening, e);
			return false;
		}
		if (newProjectHandle == null) {
			return false;
		}
		return super.performFinish();
	}

	/**
	 * Creates the new project.
	 *
	 * @return the i project
	 * @throws CoreException
	 *             the core exception
	 */
	protected IProject createNewProject() throws CoreException {
		// get a project handle
		final IProject project = getMyProjectPage().getProjectHandle();

		// get a project descriptor
		java.net.URI projectLocationURI = null;
		if (!getMyProjectPage().useDefaults()) {
			projectLocationURI = getMyProjectPage().getLocationURI();
		}

		IProjectDescription projectDescription = null;
		NullProgressMonitor progressMonitor = new NullProgressMonitor();
		if (!project.exists()) {
			projectDescription = ResourcesPlugin.getWorkspace().newProjectDescription(project.getName());
			if (projectLocationURI != null) {
				projectDescription.setLocationURI(projectLocationURI);
			}
			project.create(projectDescription, SubMonitor.convert(progressMonitor, 1));
			project.open(SubMonitor.convert(progressMonitor, 1));
		} else {
			// projectDescription = project.getDescription();
			project.open(SubMonitor.convert(progressMonitor, 1));
		}
		IWorkingSet[] workingSets = getMyProjectPage().getSelectedWorkingSets();
		if (workingSets.length > 0) {
			PlatformUI.getWorkbench().getWorkingSetManager().addToWorkingSets(project, workingSets);
		}
		return project;
	}

	@Override
	protected URI createNewModelURI(String categoryId) {
		IPath newFilePath = getMyProjectPage().getProjectHandle().getFullPath().append(getMyProjectPage().getFileName() + "." + getDiagramFileExtension(categoryId)); //$NON-NLS-1$
		return URI.createPlatformResourceURI(newFilePath.toString(), true);
	}

	public PapyrusProjectCreationPage getMyProjectPage() {
		return myProjectPage;
	}

	public void setMyProjectPage(PapyrusProjectCreationPage myProjectPage) {
		this.myProjectPage = myProjectPage;
	}

	protected WizardNewProjectCreationPage createNewProjectCreationPage() {
		return this.myProjectPage;
	}

}
