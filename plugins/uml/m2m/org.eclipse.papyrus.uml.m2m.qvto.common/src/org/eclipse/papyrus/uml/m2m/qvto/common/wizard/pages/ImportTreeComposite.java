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
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.services.labelprovider.service.impl.LabelProviderServiceImpl;
import org.eclipse.papyrus.infra.widgets.providers.IGraphicalContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.PatternViewerFilter;
import org.eclipse.papyrus.infra.widgets.providers.WorkspaceContentProvider;
import org.eclipse.papyrus.uml.m2m.qvto.common.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PatternFilter;


/**
 * 
 * Generic and reusable composite used to display the workspace and select the wanted elements
 * 
 * @author Quentin Le Menez
 *
 */
public abstract class ImportTreeComposite extends Composite {

	protected TreeViewer treeViewer;

	protected LabelProviderService labelProviderService;

	protected ILabelProvider treeViewerlabelProvider;

	protected WorkspaceContentProvider treeViewercontentProvider;

	protected ISelectionChangedListener treeViewerListener;

	protected final List<String> filterNames;

	protected final List<String> filterExtensions;

	protected Collection<Object> selectedFiles;

	protected Collection<String> systemPaths;

	protected FillLayout layout;

	protected Composite treeViewerComposite;

	protected Composite selectionButtonsComposite;

	protected Collection<Object> foundProjects;

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 *            The parent composite
	 * @param style
	 *            The swt style used for this ConfigurationComposite
	 * @param extensions
	 *            The default extensions used to filter the displayed results
	 * @param extensionsNames
	 *            The displayed names of those filters
	 */
	public ImportTreeComposite(Composite parent, int style, String[] extensions, String[] extensionsNames) {
		super(parent, style);
		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		selectedFiles = new LinkedList<Object>();
		filterNames = new LinkedList<String>();
		filterExtensions = new LinkedList<String>();
		systemPaths = new LinkedList<String>();
		foundProjects = new LinkedList<Object>();

		createTreeViewerComposite(this, extensions, extensionsNames);

		createSelectionButtons(this);
	}


	/**
	 * 
	 * Creates the visual representation of the workspace
	 * 
	 * @param parent
	 *            The parent Composite
	 * @param extensions
	 *            The default extensions used to filter the displayed results
	 * @param extensionsNames
	 *            The displayed names of those filters
	 */
	private void createTreeViewerComposite(Composite parent, String[] extensions, String[] extensionsNames) {
		treeViewerComposite = new Composite(parent, SWT.NONE);
		treeViewerComposite.setLayout(new GridLayout(1, true));
		treeViewerComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		Composite beforeTreeComposite = new Composite(treeViewerComposite, SWT.NONE);

		Composite treeComposite = new Composite(treeViewerComposite, SWT.NONE);
		treeComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		layout = new FillLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		treeComposite.setLayout(layout);

		treeViewer = new TreeViewer(treeComposite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		treeViewer.setFilters(new ViewerFilter[] { new PatternFilter() });

		labelProviderService = new LabelProviderServiceImpl();
		try {
			labelProviderService.startService();
		} catch (ServiceException ex) {
			Activator.log.error(ex);
		}

		treeViewerlabelProvider = labelProviderService.getLabelProvider();
		treeViewercontentProvider = new WorkspaceContentProvider();
		setFilters(extensions, extensionsNames);

		treeViewercontentProvider.setExtensionFilters(new LinkedHashMap<String, String>());
		for (int i = 0; i < Math.min(filterNames.size(), filterExtensions.size()); i++) {
			treeViewercontentProvider.addExtensionFilter(filterExtensions.get(i), filterNames.get(i));
		}

		treeViewer.setContentProvider(treeViewercontentProvider);
		treeViewer.setLabelProvider(treeViewerlabelProvider);

		defaultViewerInput();

		treeViewerListener = new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				fireTreeSelectionEvent(event);
			}
		};

		treeViewer.addSelectionChangedListener(treeViewerListener);

		// This is used to display both of the filters (before and after the treeViewer)
		if (treeViewercontentProvider instanceof IGraphicalContentProvider) {
			IGraphicalContentProvider graphicalContentProvider = treeViewercontentProvider;

			beforeTreeComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
			layout = new FillLayout();
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			beforeTreeComposite.setLayout(layout);
			graphicalContentProvider.createBefore(beforeTreeComposite);
			beforeTreeComposite.moveAbove(treeViewer.getTree());

			Composite afterTreeComposite = new Composite(treeViewerComposite, SWT.NONE);
			layout = new FillLayout();
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			afterTreeComposite.setLayout(layout);
			afterTreeComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
			graphicalContentProvider.createAfter(afterTreeComposite);
		}

	}

	/**
	 * 
	 * Sets the filters for the treeViewer, matching the names with the extensions
	 * 
	 * @param filterExtensions
	 *            The extensions
	 * @param filterNames
	 *            The associated names
	 */
	protected void setFilters(String[] filterExtensions, String[] filterNames) {
		if (filterExtensions.length != filterNames.length) {
			// This is a simple warning. Only valid filters will be retained.
			Activator.log.warn("FilterExtensions and FilterNames do not match");
		}

		setFilterNames(getFilterLabels(filterNames, filterExtensions));
		setFilterExtensions(filterExtensions);
	}

	/**
	 * 
	 * Builds the filter labels to be displayed
	 * 
	 * @param filterNames
	 * @param filterExtensions
	 * @return
	 *         The array containing the built labels
	 */
	protected String[] getFilterLabels(String[] filterNames, String[] filterExtensions) {
		int size = Math.min(filterNames.length, filterExtensions.length);
		String[] filters = new String[size];
		for (int i = 0; i < size; i++) {
			filters[i] = filterNames[i] + " (" + filterExtensions[i] + ")"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		return filters;
	}

	/**
	 * 
	 * Fills the local array to be manipulated
	 * 
	 * @param filterExtensions
	 *            The input extensions
	 */
	protected void setFilterExtensions(String[] filterExtensions) {
		this.filterExtensions.clear();
		this.filterExtensions.addAll(Arrays.asList(filterExtensions));
	}

	/**
	 * 
	 * Fills the local array to be manipulated
	 * 
	 * @param filterNames
	 *            The input names
	 */
	protected void setFilterNames(String[] filterNames) {
		this.filterNames.clear();
		this.filterNames.addAll(Arrays.asList(filterNames));
	}


	/**
	 * 
	 * This method allows to set the default input of the treeViewer
	 * 
	 */
	protected void defaultViewerInput() {
		treeViewer.setInput(File.listRoots());
		// Gets the selection in the workspace at the time of the launch
		ISelection workbenchSelection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		// Sets the first selection of the treeviewer from the selection in the workspace
		revealSelection(workbenchSelection);
		treeViewer.setSelection(workbenchSelection, true);
	}


	/**
	 * 
	 * Abstract method to be implemented by the child in order to create the useful buttons to manipulate the tree's elements
	 * 
	 * @param parent
	 *            The parent composite in which the new buttons will be created
	 */
	abstract void createSelectionButtons(Composite parent);

	/**
	 * 
	 * Abstract method to be implemented by the child in order to handle the treeViewer element selection
	 * 
	 * @param event
	 *            The event linked to the selections inside the treeViewer
	 */
	abstract void fireTreeSelectionEvent(SelectionChangedEvent event);


	/**
	 * 
	 * This method reveals the elements selected outside of the workspace or from the workspace selection at launch by expanding the tree
	 * 
	 * @param importedFiles
	 *            The list of selected files
	 */
	protected void revealSelectedFiles(Collection<Object> importedFiles) {
		// this method calls to expand any folders or projects containg the selected files in order to show the workspace selection
		Collection<IFile> ifiles = new ArrayList<IFile>();
		// Collection<IProject> iprojects = new ArrayList<IProject>();
		// Collection<IFolder> ifolders = new ArrayList<IFolder>();
		if (importedFiles != null && !importedFiles.isEmpty()) {
			for (Object object : importedFiles) {
				treeViewer.refresh();
				if (object instanceof File) {
					File file = (File) object;
					IFile ifile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(file.getAbsolutePath()));
					if (ifile != null) {
						ifiles.add(ifile);
						revealTreeElement(ifile);
					}
				}
				if (object instanceof IFile) {
					IFile ifile = (IFile) object;
					ifiles.add(ifile);
					revealTreeElement(ifile);
				}
				// if (object instanceof IFolder) {
				// IFolder ifolder = (IFolder) object;
				// ifolders.add(ifolder);
				// revealTreeElement(ifolder);
				// }
				// if (object instanceof IProject) {
				// IProject iproject = (IProject) object;
				// iprojects.add(iproject);
				// // As a project is a root element, no need to expand it
				// }
			}
		}

		treeViewer.setSelection(new StructuredSelection(ifiles.toArray()), true);
		// treeViewer.setSelection(new StructuredSelection(ifolders.toArray()), true);
		// treeViewer.setSelection(new StructuredSelection(iprojects.toArray()), true);
	}

	/**
	 * 
	 * Handles the workspace selection
	 * 
	 * @param iselection
	 *            The selection
	 */
	protected void revealSelection(ISelection iselection) {
		if (iselection instanceof IStructuredSelection) {
			IStructuredSelection sselection = (IStructuredSelection) iselection;
			revealSelectedFiles(Arrays.asList(sselection.toArray()));
		}
	}

	/**
	 * 
	 * Reveal each elements from the selected elements list
	 * 
	 * @param object
	 *            The selected object
	 */
	protected void revealTreeElement(Object object) {
		// verify the possibility of getting the file's parent and that the root directory is not already selected
		if (object instanceof IFile && !(object instanceof IProject)) {
			IFile ifile = (IFile) object;
			treeViewer.setExpandedState(ifile.getParent(), true);
			if (!(ifile.getParent() instanceof IProject)) {
				revealTreeElement(ifile.getParent());
			}
		}
		if (object instanceof IFolder && !(object instanceof IProject)) {
			IFolder ifolder = (IFolder) object;
			treeViewer.setExpandedState(ifolder.getParent(), true);
			if (!(ifolder.getParent() instanceof IProject)) {
				revealTreeElement(ifolder.getParent());
			}
		}
	}

	/**
	 * 
	 * This method is used to get the projects containing the selected objects
	 * 
	 * @param systemSelection
	 *            The selection outside of the workspace
	 * @return
	 *         The list of projects found
	 */
	protected Collection<Object> getProjects(Collection<Object> systemSelection) {
		if (systemSelection != null && !systemSelection.isEmpty()) {
			for (Object object : systemSelection) {
				if (object instanceof File) {
					File file = (File) object;
					getProject(file);
				}
			}
		}
		return foundProjects;
	}

	/**
	 * 
	 * This method is used to get the projects containing the file
	 * 
	 * @param file
	 *            The selected file
	 */
	protected void getProject(File file) {
		File parentFile = file.getParentFile();
		if (parentFile == null) {
			// No containing project has been found
			return;
		}

		Collection<File> parentChildren = Arrays.asList(parentFile.listFiles());
		for (File nestedFile : parentChildren) {
			// String fileExtension = Files.getFileExtension(nestedFile.getAbsolutePath());
			String fileExtension = getFileExtensions(nestedFile);
			if (fileExtension.equals(".project") && !foundProjects.contains(nestedFile)) { // $NON-NLS-1$
				// A containing project has been found
				foundProjects.add(nestedFile);
				return;
			}
		}

		getProject(parentFile);
	}

	/**
	 * 
	 * This method gathers the file extensions in order to filter them
	 * 
	 * @param file
	 *            The file
	 * @return
	 *         The file's extension
	 */
	protected String getFileExtensions(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 /* && fileName.lastIndexOf(".") != 0 */) { // $NON-NLS-1$
			return fileName.substring(fileName.lastIndexOf(".")); // $NON-NLS-1$
		} else {
			return "";
		}
	}

	/**
	 * 
	 * Constructs the list of the treeViewer's selected files
	 * 
	 * @param elements
	 */
	public void setSelectedFiles(Object[] elements) {
		// get the viewer selection to obtain the filtered files
		getNestedFiles(elements);
	}

	/**
	 * 
	 * getter used to access the selectedFiles list
	 * 
	 * @return
	 *         the list of selected files
	 */
	public Collection<Object> getSelectedFiles() {
		return selectedFiles;
	}

	/**
	 *
	 * Gets all the files from the user's selection in the viewer and updates the local selection list
	 *
	 * @param nestedElements
	 *            The array containing the selected elements, be they files or folders
	 */
	protected void getNestedFiles(Object[] nestedElements) {
		Collection<Object> projectList = new LinkedList<Object>();
		Collection<Object> folderList = new LinkedList<Object>();
		List<PatternViewerFilter> currentFilters = new ArrayList<PatternViewerFilter>();
		for (ViewerFilter filter : treeViewer.getFilters()) {
			if (filter instanceof PatternViewerFilter) {
				currentFilters.add((PatternViewerFilter) filter);
			}
		}

		for (Object element : nestedElements) {
			if (element instanceof IProject) {
				projectList.add(element);
			}
			if (element instanceof IFolder) {
				folderList.add(element);
			}
			if (element instanceof IFile) {
				Boolean isVisible = false;
				IFile selectedFile = (IFile) element;
				String fileExtension = "*." + selectedFile.getFileExtension(); //$NON-NLS-1$
				if (filterExtensions.contains(fileExtension) && !selectedFiles.contains(selectedFile)) {
					isVisible = true;
				}
				for (int index = 0; index < currentFilters.size() && isVisible; index++) {
					isVisible = currentFilters.get(index).isVisible(treeViewer, selectedFile.getParent(), selectedFile);
				}
				if (isVisible) {
					selectedFiles.add(selectedFile);
				}
			}
		}

		if (projectList.size() > 0) {
			for (Object element : projectList) {
				IProject selectedProject = (IProject) element;
				try {
					getNestedFiles(selectedProject.members());
				} catch (CoreException e) {
					Activator.log.error(e);
				}
			}
		}

		if (folderList.size() > 0) {
			for (Object element : folderList) {
				IFolder selectedFolder = (IFolder) element;
				try {
					getNestedFiles(selectedFolder.members());
				} catch (CoreException e) {
					Activator.log.error(e);
				}
			}
		}
	}

	@Override
	public void dispose() {
		if (treeViewerListener != null) {
			treeViewer.removeSelectionChangedListener(treeViewerListener);
		}
		super.dispose();
	}

}
