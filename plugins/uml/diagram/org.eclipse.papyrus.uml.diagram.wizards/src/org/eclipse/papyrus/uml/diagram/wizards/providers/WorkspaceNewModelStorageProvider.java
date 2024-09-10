/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.wizards.providers;

import static org.eclipse.papyrus.uml.diagram.wizards.utils.WizardsHelper.adapt;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.papyrus.commands.Activator;
import org.eclipse.papyrus.uml.diagram.wizards.pages.NewDiagramForExistingModelPage;
import org.eclipse.papyrus.uml.diagram.wizards.pages.NewModelFilePage;
import org.eclipse.papyrus.uml.diagram.wizards.pages.SelectArchitectureContextPage;
import org.eclipse.papyrus.uml.diagram.wizards.utils.WizardsHelper;
import org.eclipse.papyrus.uml.diagram.wizards.wizards.CreateModelWizard;
import org.eclipse.papyrus.uml.diagram.wizards.wizards.InitModelWizard;

/**
 * This is the WorkspaceNewModelStorageProvider type. Enjoy.
 */
public class WorkspaceNewModelStorageProvider extends AbstractNewModelStorageProvider {

	private CreateModelWizard wizard;

	/** New model file page for the file. */
	private NewModelFilePage newModelFilePage;

	private SelectArchitectureContextPage newSelectArchitectureContextPage;

	public WorkspaceNewModelStorageProvider() {
		super();
	}

	@Override
	public boolean canHandle(IStructuredSelection initialSelection) {
		boolean result = false;

		for (Object next : initialSelection.toList()) {
			if (adapt(next, IResource.class) != null) {
				result = true;
				break;
			}
		}

		return result;
	}

	@Override
	public void init(CreateModelWizard wizard, IStructuredSelection selection) {
		super.init(wizard, selection);

		this.wizard = wizard;
		newModelFilePage = createNewModelFilePage(selection);
		newSelectArchitectureContextPage = createNewArchitectureContextPage(selection);
	}



	@Override
	public List<? extends IWizardPage> createPages() {
		if (newModelFilePage == null && newSelectArchitectureContextPage == null) {
			return Collections.emptyList();
		}

		return Arrays.asList(newSelectArchitectureContextPage, newModelFilePage);
	}

	@Override
	public IStatus validateArchitectureContexts(String... newContexts) {
		if (newModelFilePage != null) {
			String firstContext = newContexts.length > 0 ? newContexts[0] : null;
			if (newContexts.length > 0) {
				// 316943 - [Wizard] Wrong suffix for file name when creating a
				// profile model
				return newModelFilePage.diagramExtensionChanged(wizard.getDiagramFileExtension(firstContext));
			}

		}

		return super.validateArchitectureContexts(newContexts);
	}

	/**
	 * Creates the new model file page, if required.
	 *
	 * @param selection
	 *            the selection
	 *
	 * @return the new model file page, or {@code null} if none
	 */
	protected NewModelFilePage createNewModelFilePage(IStructuredSelection selection) {

		if (wizard.isCreateProjectWizard() || wizard.isCreateMultipleModelsWizard()) {

			return null;
		}

		// IFile selectedFile = getSelectedFile(selection);
		URI selectedResourceURI = WizardsHelper.getSelectedResourceURI(selection);

		if (isCreateFromExistingDomainModel() && selectedResourceURI != null) {
			return new NewDiagramForExistingModelPage(selection, wizard.getModelKindName(), getDiagramFileName(selectedResourceURI) + "." + wizard.getDiagramFileExtension(null), wizard.getDiagramFileExtension(null)); //$NON-NLS-1$
		}

		return new NewModelFilePage(selection, wizard.getModelKindName());
	}

	/**
	 * Suggests a name of diagram file for the domain model file without
	 * extension.
	 *
	 * @param domainModel
	 *            the domain model
	 * @return the diagram file name
	 */
	protected String getDiagramFileName(URI domainModelURI) {
		return domainModelURI.trimFileExtension().lastSegment();
	}

	protected boolean isCreateFromExistingDomainModel() {
		return wizard.isInitModelWizard() && ((InitModelWizard) wizard).isCreateFromExistingDomainModel();
	}

	@Override
	public URI createNewModelURI(String categoryId) {
		IFile newFile = newModelFilePage.createNewFile();
		URI fileURI = URI.createPlatformResourceURI(newFile.getFullPath().toString(), true);
		try {
			newFile.delete(true, new NullProgressMonitor());
		} catch (CoreException e) {
			Activator.log.error(e);
		}
		return fileURI;
		// return (newFile == null) ? null :
	}

	private SelectArchitectureContextPage createNewArchitectureContextPage(IStructuredSelection selection) {
		if (wizard.isCreateProjectWizard() || wizard.isCreateMultipleModelsWizard() || !wizard.isPapyrusRootWizard()) {
			return null;
		}
		return new SelectArchitectureContextPage();
	}

	@Override
	public SelectArchitectureContextPage getArchitectureContextPage() {
		return this.newSelectArchitectureContextPage;
	}

}
