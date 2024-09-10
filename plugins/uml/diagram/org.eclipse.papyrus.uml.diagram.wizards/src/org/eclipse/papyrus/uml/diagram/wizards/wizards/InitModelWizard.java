/*****************************************************************************
 * Copyright (c) 2010, 2017 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 471453
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.wizards.wizards;

import static org.eclipse.papyrus.uml.diagram.wizards.utils.WizardsHelper.getSelectedResourceURI;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.uml.diagram.wizards.command.PapyrusModelFromExistingDomainModelCommand;
import org.eclipse.papyrus.uml.diagram.wizards.messages.Messages;
import org.eclipse.papyrus.uml.diagram.wizards.pages.NewModelFilePage;
import org.eclipse.papyrus.uml.diagram.wizards.pages.SelectRepresentationKindPage;
import org.eclipse.papyrus.uml.diagram.wizards.pages.SelectRootElementPage;
import org.eclipse.papyrus.uml.tools.model.UmlModel;
import org.eclipse.ui.IWorkbench;

/**
 *
 * The New Papyrus Model Wizard.
 * If being invoke on the *.uml file - initializes a new Papyrus diagram for the selected domain model.
 * If is selected is empty or in not uml - creates a new Papyrus Model.
 */
public class InitModelWizard extends CreateModelWizard {

	/** Select the root element containing the new diagram. */
	private SelectRootElementPage selectRootElementPage;

	/** The is init from existing domain model. */
	private boolean isInitFromExistingDomainModel;

	@Override
	public boolean isInitModelWizard() {
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
		URI uri = getSelectedResourceURI(selection);
		isInitFromExistingDomainModel = isSupportedDomainModelResource(uri);
		super.init(workbench, selection);
		selectRootElementPage = createSelectRootElementPage(selection);
		if (isCreateFromExistingDomainModel()) {
			// Init Model not Create a new one
			setWindowTitle(Messages.InitModelWizard_init_papyrus_diagram);
		}
	}

	/**
	 * Creates the select root element page.
	 *
	 * @param selection
	 *            the initial workbench selection
	 * @return the select root element page
	 */
	protected SelectRootElementPage createSelectRootElementPage(IStructuredSelection selection) {
		if (!isCreateFromExistingDomainModel()) {
			// create model - nothing to choose from
			return null;
		}
		return new SelectRootElementPage(selection);
	}

	/**
	 * Creates the select diagram kind page.
	 *
	 * @return the select diagram kind page {@inheritDoc}
	 */
	@Override
	protected SelectRepresentationKindPage doCreateSelectRepresentationKindPage() {
		if (isCreateFromExistingDomainModel()) {
			return new SelectRepresentationKindPage(false, createContextProvider(),
					SelectRepresentationKindPage.DEFAULT_CREATION_COMMAND_REGISTRY);
		}

		return super.doCreateSelectRepresentationKindPage();
	}

	/**
	 * Adds the pages.
	 *
	 * {@inheritDoc}
	 */
	@Override
	public void addPages() {
		super.addPages();
		// remove the page from the view
		// addPageIfNotNull(selectRootElementPage);
	};

	/**
	 * Returns true is the file can be served as a model model for the diagram.
	 *
	 * @param file
	 *            the file
	 * @return true, if is supported domain model file
	 */
	public static boolean isSupportedDomainModelFile(IFile file) {
		// if(file != null && UmlModel.UML_FILE_EXTENSION.equals(file.getFileExtension())){System.err.println("is SupportedDomainModelFile");}
		// else {System.err.println("not SupportedDomainModelFile");}
		return file != null && isSupportedDomainModelResource(URI.createPlatformResourceURI(file.getFullPath().toString(), true));
	}

	/**
	 * Returns true if the resource can be served as a model model for the diagram.
	 *
	 * @param uri
	 *            the resource's URI
	 * @return true, if is supported domain model resource
	 */
	public static boolean isSupportedDomainModelResource(URI uri) {
		return (uri != null) && UmlModel.UML_FILE_EXTENSION.equals(uri.fileExtension());
	}

	/**
	 * Checks if is supported domain model file.
	 *
	 * @param sselection
	 *            the sselection
	 * @return true, if is supported domain model file
	 */
	public static boolean isSupportedDomainModelFile(IStructuredSelection sselection) {
		URI uri = getSelectedResourceURI(sselection);
		return isSupportedDomainModelResource(uri);
	}

	/**
	 * Creates the papyrus models.
	 *
	 * @param diResourceSet
	 *            the di resource set
	 * @param newURI
	 *            the URI of the new model's principal resource
	 */
	@Override
	protected void createPapyrusModels(ModelSet modelSet, URI newURI) {
		if (isCreateFromExistingDomainModel()) {
			RecordingCommand command = new PapyrusModelFromExistingDomainModelCommand(modelSet, newURI, getRoot());
			getCommandStack(modelSet).execute(command);
		} else {
			super.createPapyrusModels(modelSet, newURI);
		}
	}

	/**
	 * Inits the domain model.
	 *
	 * @param diResourceSet
	 *            the di resource set
	 * @param contextId
	 *            the architecture context id
	 * @param viewpointIds
	 *            the architecture viewpoint ids
	 */
	@Override
	protected void initDomainModel(ModelSet modelSet, String contextId, String[] viewpointIds) {
		if (isCreateFromExistingDomainModel()) {
			// do nothing
		} else {
			super.initDomainModel(modelSet, contextId, viewpointIds);
		}
	}

	/**
	 * Inits the diagrams.
	 *
	 * @param diResourceSet
	 *            the di resource set
	 * @param contextName
	 *            the architecture context name {@inheritDoc}
	 */
	@Override
	protected void initDiagrams(ModelSet modelSet, String contextName) {
		initDiagrams(modelSet, getRoot(), contextName);
	}

	/**
	 * Checks if is creates the from existing domain model.
	 *
	 * @return true, if is creates the from existing domain model
	 */
	public boolean isCreateFromExistingDomainModel() {
		return isInitFromExistingDomainModel;
	}

	@Override
	public String getDiagramFileExtension(String contextId) {
		if (isCreateFromExistingDomainModel()) {
			return NewModelFilePage.DEFAULT_DIAGRAM_EXTENSION;
		}
		return super.getDiagramFileExtension(contextId);
	}

	/**
	 * Gets the root.
	 *
	 * @return the root
	 */
	private EObject getRoot() {
		if (selectRootElementPage != null) {
			// return selectRootElementPage.getModelElement();
			return SelectRepresentationKindPage.getModelRoot();
		}
		return null;
	}



}
