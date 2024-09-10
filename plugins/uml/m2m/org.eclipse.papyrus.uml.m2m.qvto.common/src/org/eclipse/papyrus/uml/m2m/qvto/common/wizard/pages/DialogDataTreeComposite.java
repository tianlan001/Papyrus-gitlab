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
 *  Quentin Le Menez (CEA LIST) quentin.lemenez@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 496176
 *****************************************************************************/
package org.eclipse.papyrus.uml.m2m.qvto.common.wizard.pages;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.widgets.util.FileUtil;
import org.eclipse.papyrus.uml.m2m.qvto.common.Activator;
import org.eclipse.papyrus.uml.m2m.qvto.common.internal.utils.CreateProject;
import org.eclipse.papyrus.uml.m2m.qvto.common.wizard.TransformationWizardConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;


/**
 * 
 * Actual composite used to display the workspace and select the wanted elements
 * 
 * @author Quentin Le Menez
 *
 */
public class DialogDataTreeComposite extends ImportTreeComposite {
	
	/**
	 * The name of the newly created project's folders
	 */
	public static final String SRC_FOLDER = "src"; //$NON-NLS-1$

	public static final String RSC_FOLDER = "resources"; //$NON-NLS-1$

	protected AbstractDialogData dialogData;

	protected String systemDialogFiles;

	protected SelectionListener selectionButtonListener;

	protected SelectionListener systemSelectionListener;

	protected Button selectAllButton;

	protected Button deselectAllButton;

	protected Button systemSelectionButton;


	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 *            The parent composite
	 * @param style
	 *            The swt style used for this ConfigurationComposite
	 * @param dialogData
	 *            The DialogData in which is stored all the necessary informations
	 */
	public DialogDataTreeComposite(Composite parent, int style, AbstractDialogData dialogData) {
		super(parent, style, dialogData.getExtensions(), dialogData.getExtensionsNames());

		this.dialogData = dialogData;

		ISelection treeSelection = treeViewer.getSelection();
		if (treeSelection instanceof StructuredSelection) {
			setSelectedFiles(((StructuredSelection) treeSelection).toArray());
			this.dialogData.setSelectedFiles(getSelectedFiles());
		}
	}


	@Override
	protected void fireTreeSelectionEvent(SelectionChangedEvent event) {
		ISelection selection = event.getSelection();

		if (selection instanceof IStructuredSelection) {
			// Updates the selected files
			selectedFiles.clear();
			IStructuredSelection sSelection = (IStructuredSelection) selection;
			setSelectedFiles(sSelection.toArray());

			if (dialogData != null) {
				dialogData.setSelectedFiles(selectedFiles);
			}
		}

	}

	@Override
	protected void createSelectionButtons(Composite parent) {
		// The composite will contain three buttons, two general selection (all and none) and one for selections using a fileDialog window
		selectionButtonsComposite = new Composite(parent, SWT.NONE);
		selectionButtonsComposite.setLayout(new GridLayout());

		systemSelectionListener = new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				fireSystemBrowseEvent(event);
			}
		};

		systemSelectionButton = new Button(selectionButtonsComposite, SWT.PUSH);
		systemSelectionButton.setText("File System Selection");
		systemSelectionButton.addSelectionListener(systemSelectionListener);
		systemSelectionButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));


		selectionButtonListener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				fireButtonSelectionEvent(event);
			}
		};

		selectAllButton = new Button(selectionButtonsComposite, SWT.PUSH);
		selectAllButton.setText("Select All");
		selectAllButton.setData(true);
		selectAllButton.addSelectionListener(selectionButtonListener);
		selectAllButton.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		deselectAllButton = new Button(selectionButtonsComposite, SWT.PUSH);
		deselectAllButton.setText("Deselect All");
		deselectAllButton.setData(false);
		deselectAllButton.addSelectionListener(selectionButtonListener);
		deselectAllButton.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
	}


	/**
	 * 
	 * Handles the selectAll/deselectAll options
	 * 
	 * @param event
	 *            The event linked to the selection buttons
	 */
	protected void fireButtonSelectionEvent(SelectionEvent event) {
		if ((Boolean) ((Button) event.widget).getData()) {
			treeViewer.getTree().selectAll();
			// Updates the selectedFiles collection
			getNestedFiles(((IStructuredSelection) treeViewer.getSelection()).toArray());

			if (dialogData != null) {
				dialogData.setSelectedFiles(selectedFiles);
			}
		} else {
			treeViewer.getTree().deselectAll();
			selectedFiles.clear();

			if (dialogData != null) {
				dialogData.setSelectedFiles(selectedFiles);
			}
		}
	}

	/**
	 * 
	 * handle the selections outside of the workspace
	 * 
	 * @param event
	 *            The event linked to the button
	 */
	protected void fireSystemBrowseEvent(SelectionEvent event) {

		// Clears the previous selection
		dialogData.getSystemSelectedFiles().clear();
		// Instantiates a new selection window
		FileDialog dialog = new FileDialog(treeViewerComposite.getShell(), SWT.MULTI);
		dialog.setText("Select the files to import");
		dialog.setFilterExtensions(dialogData.getExtensions());
		dialog.setFilterNames(dialogData.getExtensionsNames());

		if (dialogData != null) {
			// Fetches the last visited folder or uses the Home of the current user
			if (dialogData.dialogSection.get(TransformationWizardConstants.FILEDIALOG_SELECTION_KEY) != null) {
				dialog.setFilterPath(dialogData.dialogSection.get(TransformationWizardConstants.FILEDIALOG_SELECTION_KEY));
			} else {
				String homeFolder = System.getProperty("user.home"); //$NON-NLS-1$
				dialog.setFilterPath(homeFolder);
			}

			// Opens the selection window and stores the selected files
			systemDialogFiles = dialog.open();
			if (systemDialogFiles != null) {
				// stores the last folder/directory visited
				dialogData.dialogSection.put(TransformationWizardConstants.FILEDIALOG_SELECTION_KEY, systemDialogFiles);
				String[] names = dialog.getFileNames();
				// Empty the list to avoid remembering old selections
				systemPaths.clear();
				for (int i = 0, n = names.length; i < n; i++) {
					StringBuffer buf = new StringBuffer(dialog.getFilterPath());
					if (buf.charAt(buf.length() - 1) != File.separatorChar) {
						buf.append(File.separatorChar);
					}
					buf.append(names[i]);
					systemPaths.add(buf.toString());
				}
				// clear the list to accept the new input
				dialogData.getSystemSelectedFiles().clear();
				dialogData.setSystemSelectedFiles(systemPaths);
			}
		}

		Collection<Object> systemSelection = dialogData.getSystemSelectedFiles();
		getProjects(systemSelection);
		if (!systemSelection.isEmpty()) {
			// The selected files are inside a project
			if (!foundProjects.isEmpty()) {
				for (Object object : foundProjects) {
					if (isWorkspaceObject(object)) {
						revealSelectedFiles(systemSelection);
					}
					// Creates the project before revealing any imported files
					else if (importProjects(foundProjects)) {
						revealSelectedFiles(systemSelection);
					}
				}
			}
			// We need to create a project in order to house the selected files
			else if (systemDialogFiles != null) {
				try {
					List<String> srcFolders = new ArrayList<String>();
					srcFolders.add(TransformationWizardConstants.SRC_FOLDER);
					CreateProject placeHolder = new CreateProject(dialogData.getSystemSelectedFiles());
					IProject newProject = placeHolder.getCreatedProject();
					Collection<Object> revealList = new ArrayList<Object>();
					// This case can happen by pressing the cancel button and therefore interrupting the creation of the project
					if (newProject != null) {
						for (IResource resource : Arrays.asList(newProject.getFolder(TransformationWizardConstants.RSC_FOLDER).members())) {
							if (resource instanceof IFile) {
								revealList.add((IFile) resource);
							}
						}
						revealSelectedFiles(revealList);
					}
				} catch (Exception e) {
					Activator.log.error(e);
				}
			}
		}

	}

	/**
	 * 
	 * Verify if the provided object is or is not inside the workspace
	 * 
	 * @param object
	 *            The object
	 * @return
	 *         True or False
	 */
	protected boolean isWorkspaceObject(Object object) {
		if (object instanceof File) {
			File file = (File) object;
			IFile ifile = FileUtil.getIFile(file.getAbsolutePath());
			// Assumes that any files outside the workbench are not IFiles and therefore wont be find, i.e. are null
			if (ifile != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * Handles the creation of the new workspace projects in case of a selection outside of an existing project or workspace
	 * 
	 * @param foundProjects
	 *            The projects previously found
	 * @return
	 *         The success of the creation
	 */
	protected boolean importProjects(Collection<Object> foundProjects) {
		Collection<Object> importedProjects = new LinkedList<Object>();
		boolean importStatus = false;
		for (Object object : foundProjects) {
			if (object instanceof File) {
				File file = (File) object;
				Path filePath = new Path(file.getAbsolutePath());
				try {
					IProjectDescription description = ResourcesPlugin.getWorkspace().loadProjectDescription(filePath);
					IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(description.getName());
					importedProjects.add(project);
					project.create(description, null);
					project.open(null);
					importStatus = true;
				} catch (CoreException e) {
					Activator.log.error(e);
				}
			}
		}

		dialogData.setImportedProjects(importedProjects);
		return importStatus;
	}

	@Override
	public void dispose() {
		if (selectionButtonListener != null) {
			selectAllButton.removeSelectionListener(selectionButtonListener);
			deselectAllButton.removeSelectionListener(selectionButtonListener);
		}
		if (systemSelectionListener != null) {
			systemSelectionButton.removeSelectionListener(systemSelectionListener);
		}

		super.dispose();
	}

}
