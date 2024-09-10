/*******************************************************************************
 * Copyright (c) 2008, 2017, 2019 Obeo, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *     Tatiana Fesenko(CEA) - initial API and implementation
 *     Christian W. Damus (CEA) - Support creating models in repositories (CDO)
 *     Christian W. Damus - bug 471453
 *     Ansgar Radermacher (CEA LIST) - bug 551952
 *
 *******************************************************************************/
package org.eclipse.papyrus.uml.diagram.wizards.pages;


import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.uml.diagram.wizards.Activator;
import org.eclipse.papyrus.uml.diagram.wizards.messages.Messages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

/**
 * This WizardPage can create an empty .uml2 file for the PapyrusEditor.
 *
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public class NewModelFilePage extends WizardNewFileCreationPage implements INewPapyrusModelPage {

	/** The Constant DEFAULT_NAME. */
	public static final String DEFAULT_NAME = Messages.NewModelFilePage_default_diagram_name;

	/** The Constant DIAGRAM_EXTENSION. */
	public static final String DEFAULT_DIAGRAM_EXTENSION = "di"; //$NON-NLS-1$

	/** The Constant PAGE_ID. */
	public static final String PAGE_ID = "NewPapyrusModel"; //$NON-NLS-1$

	private NewModelWizardData wizardData;

	/**
	 * Instantiates a new new model file page.
	 *
	 * @param selection
	 *            the selection
	 * @param modelKindName
	 *            the kind of model to be created (translatable)
	 */
	public NewModelFilePage(IStructuredSelection selection, String modelKindName) {
		this(PAGE_ID, selection, modelKindName);
	}

	/**
	 * Instantiates a new new model file page.
	 *
	 * @param pageId
	 *            the page id
	 * @param selection
	 *            the selection
	 * @param modelKindName
	 *            the kind of model to be created (translatable)
	 */
	public NewModelFilePage(String pageId, IStructuredSelection selection, String modelKindName) {
		super(pageId, selection);
		setTitle(NLS.bind(Messages.NewModelFilePage_3, modelKindName));
		setDescription(NLS.bind(Messages.NewModelFilePage_page_desc, modelKindName));
		setFileExtension(DEFAULT_DIAGRAM_EXTENSION);
	}

	/**
	 * Creates the control.
	 *
	 * @param parent
	 *            the parent {@inheritDoc}
	 */
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		String filename = getFileName();
		if (filename.contains("%20")) {
			filename = filename.replaceAll("%20", " ");
		}
		setFileName(getUniqueFileName(getContainerFullPath(), filename, getFileExtension()));
		setPageComplete(validatePage());


	}

	/**
	 * @since 3.0
	 */
	@Override
	public void setNewModelWizardData(NewModelWizardData wizardData) {
		this.wizardData = wizardData;
	}

	/**
	 * @since 3.0
	 */
	@Override
	public NewModelWizardData getNewModelWizardData() {
		return wizardData;
	}

	/**
	 * Diagram extension changed.
	 *
	 * @param newExtension
	 *            the new extension
	 * @return the i status
	 */
	public IStatus diagramExtensionChanged(String newExtension) {
		String currentExtension = getFileExtension();
		if (!currentExtension.equals(newExtension) && this.getControl() != null) {

			// String oldFileName = getFileName();
			String newFileName = NewModelFilePage.getUniqueFileName(getContainerFullPath(), getFileName().replace(currentExtension, ""), newExtension);

			setFileName(newFileName);
			setFileExtension(newExtension);

			// String message1 = Messages.NewModelFilePage_new_diagram_category_needs_specific_extension;
			// String message2 = Messages.bind(Messages.NewModelFilePage_diagram_file_was_renamed, oldFileName, newFileName);
			// String message = message1 + message2;
			// Status resultStatus = new Status(Status.INFO, Activator.PLUGIN_ID, message);
			//
			// String errorMessage = getErrorMessage();
			// if(errorMessage != null) {
			// resultStatus = new Status(Status.ERROR, Activator.PLUGIN_ID, errorMessage);
			// }
			// return resultStatus;
		}
		return Status.OK_STATUS;
	}

	@Deprecated
	public IFile createFileHandle() {
		IPath containerFullPath = getContainerFullPath();
		String fileName = getFileName();
		IPath filePath = containerFullPath.append(fileName);
		return super.createFileHandle(filePath);
	}

	/**
	 * Gets the unique file name.
	 *
	 * @param containerFullPath
	 *            the container full path
	 * @param fileName
	 *            the file name
	 * @param extension
	 *            the extension
	 * @return the unique file name
	 */
	protected static String getUniqueFileName(IPath containerFullPath, String fileName, String extension) {
		if (extension == null) {
			extension = ""; //$NON-NLS-1$
		}

		if (containerFullPath == null) {
			containerFullPath = Path.EMPTY;
		}

		// First, try the same name as the container for a reasonable default
		if ((fileName == null) || fileName.trim().isEmpty()) {
			IPath testPath = containerFullPath.isEmpty() ? null : containerFullPath.append(containerFullPath.lastSegment()).addFileExtension(extension);
			if (!ResourcesPlugin.getWorkspace().getRoot().exists(testPath)) {
				fileName = testPath.removeFileExtension().lastSegment();
			}
		}

		if ((fileName == null) || fileName.trim().isEmpty()) {
			fileName = DEFAULT_NAME;
		}

		if (fileName.contains(".")) { //$NON-NLS-1$
			fileName = fileName.substring(0, fileName.lastIndexOf(".")); //$NON-NLS-1$
		}

		IPath filePath = containerFullPath.append(fileName);
		filePath = containerFullPath.append(fileName);
		filePath = filePath.addFileExtension(extension);

		int i = 1;
		while (ResourcesPlugin.getWorkspace().getRoot().exists(filePath)) {
			i++;
			filePath = containerFullPath.append(fileName + i);
			if (extension != null) {
				filePath = filePath.addFileExtension(extension);
			}
		}
		return filePath.lastSegment();
	}

	@Override
	protected void createAdvancedControls(Composite parent) {
		// Nothing: the standard "createAdvancedControls" method adds an option to "Link to file in the file system".
		// It is not properly integrated with Papyrus (Which creates 3 different files, and only one of them is actually linked to the file system)
	}

	@Override
	protected IStatus validateLinkedResource() {
		return Status.OK_STATUS; // Disable this method to avoid NPE (Because we override #createAdvancedControls)
	}

	@Override
	protected boolean validatePage() {
		if (wizardData != null) {
			wizardData.setModelFileName(getFileName());
		}

		return super.validatePage();
	}

	@Override
	protected void createLinkTarget() {
		// Disable this method to avoid NPE (Because we override #createAdvancedControls)
	}

	@Override
	public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().displayHelp("org.eclipse.papyrus.uml.diagram.wizards.FileChooser"); //$NON-NLS-1$

	}

	/**
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#createNewFile()
	 *
	 * @return
	 *         The model's IFile
	 */
	@Override
	public IFile createNewFile() {
		// This will generate a dialog if there are conflicts during the creation
		// TODO implement a local version of this method to catch the generated exceptions to stop the creation there
		// This will avoid saving problems in the end (CreateModelWizard>saveDiagram) and the possible corruption of the handled models
		// final IPath containerPath = getContainerFullPath();
		// IPath newFilePath = containerPath.append(getFileName());

		return super.createNewFile();
	}

	/**
	 * This method is used to avoid case conflicts between existing and newly created models
	 * As well as to prevent the user to create a model outside a container
	 *
	 * @see org.eclipse.jface.wizard.WizardPage#canFlipToNextPage()
	 *
	 * @return
	 */
	@Override
	public boolean canFlipToNextPage() {
		boolean canFlip = true;
		String existingModelName = "";

		for (String existingName : getExistingNames()) {
			if (existingName.equalsIgnoreCase(getFileName())) {
				canFlip = false;
				existingModelName = existingName;
				break;
			}
		}

		if (!canFlip) {
			this.setErrorMessage(Messages.NewModelFilePage_page_same_case_desc + existingModelName);
		}

		// Verify that the new model is created in a correct container
		if (canFlip && (getContainerFullPath() == null || ResourcesPlugin.getWorkspace().getRoot().findMember(getContainerFullPath()) == null)) {
			this.setMessage(Messages.NewModelFilePage_set_a_container);
			canFlip = false;
		}

		return canFlip && super.canFlipToNextPage();
	}

	/**
	 * This method fetches all the IFiles' name in the workspace
	 *
	 * @return
	 *         The list of the names
	 */
	public Collection<String> getExistingNames() {
		Collection<String> result = new LinkedList<>();

		try {
			IResource rootResource = ResourcesPlugin.getWorkspace().getRoot().findMember(getContainerFullPath());
			// Need to go through all the resources of the selected container to check if there is an homograph
			if (rootResource != null) {
				if (rootResource.getType() == IResource.PROJECT) {
					result.addAll(getMembersNames(((IProject) rootResource).members()));
				}
				if (rootResource.getType() == IResource.FOLDER) {
					result.addAll(getMembersNames(((IFolder) rootResource).members()));
				}
			}
		} catch (CoreException ce) {
			Activator.log.error(ce);
		}

		return result;
	}

	/**
	 * This method returns the names of the first level children of the selected element
	 *
	 * @param membersArray
	 * @return
	 */
	public Collection<String> getMembersNames(IResource[] membersArray) {
		Collection<String> membersList = new LinkedList<>();
		for (Iterator<IResource> arrayIter = Arrays.asList(membersArray).iterator(); arrayIter.hasNext();) {
			IResource iResource = arrayIter.next();
			membersList.add(iResource.getName());
		}
		return membersList;
	}

}
