/*****************************************************************************
 * Copyright (c) 2021 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.services.labelprovider.service.impl.LabelProviderServiceImpl;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.messages.Messages;
import org.eclipse.papyrus.infra.widgets.providers.SingleProjectContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.WorkspaceContentProvider;
import org.eclipse.papyrus.infra.widgets.util.FileUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * Adapted code from StringFileSelector
 *
 * This widget allows to select a workspace file for String field
 * The String field is read-only.
 * The file selection can be restricted to the current project or displayed all workspace project using the property showOnlyCurrentProject
 * The files to show can be filtered by their extension
 *
 * @since 4.1
 *
 */
public class StringWorkspaceFileSelector extends StringEditor {

	/**
	 * the delete button
	 */
	private Button deleteButton;

	/**
	 * the button to browse the workspace
	 */
	private Button browseWorkspace;

	/**
	 * the list of the name of the file filters
	 */
	private List<String> filterNames;

	/**
	 * the list of the file extension to filter
	 */
	private List<String> filterExtensions;

	/**
	 * boolean indicating if the elements must be read only
	 */
	private boolean readOnly = false;

	/**
	 * boolean indicating if the browse workspace must show only the current project or all workspace project
	 */
	private boolean showOnlyCurrentProject = false;

	/**
	 * the name of the current project
	 */
	protected String currentProjectName;

	/**
	 *
	 * Constructor.
	 *
	 * @param parent
	 * @param style
	 */
	public StringWorkspaceFileSelector(Composite parent, int style) {
		super(parent, style | SWT.READ_ONLY); // the string field is read only
		super.setReadOnly(readOnly);
		((GridLayout) getLayout()).numColumns = 5;

		deleteButton = factory.createButton(this, "", SWT.PUSH); //$NON-NLS-1$
		deleteButton.setImage(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage("/icons/Delete_12x12.gif")); //$NON-NLS-1$
		deleteButton.setLayoutData(new GridData());
		browseWorkspace = factory.createButton(this, Messages.StringWorkspaceFileSelector_BrowseWorkspace, SWT.PUSH);
		browseWorkspace.setLayoutData(new GridData());

		filterNames = new LinkedList<>();
		filterExtensions = new LinkedList<>();

		deleteButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				setResult(""); //$NON-NLS-1$
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Nothing
			}

		});

		browseWorkspace.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				LabelProviderService labelProviderService = new LabelProviderServiceImpl();
				try {
					labelProviderService.startService();
				} catch (ServiceException ex) {
					Activator.log.error(ex);
				}

				ILabelProvider labelProvider = labelProviderService.getLabelProvider();

				IFile currentFile = getIFile(text.getText());

				TreeSelectorDialog dialog = new TreeSelectorDialog(getShell());
				if (labelText != null) {
					dialog.setTitle(labelText);
				}

				WorkspaceContentProvider contentProvider = createWorkspaceContentProvider();

				if (!(filterExtensions.isEmpty() || filterNames.isEmpty())) {
					// The filters have been defined
					contentProvider.setExtensionFilters(new LinkedHashMap<String, String>()); // Reset the default filters

					// Use our own filters
					for (int i = 0; i < Math.min(filterNames.size(), filterExtensions.size()); i++) {
						contentProvider.addExtensionFilter(filterExtensions.get(i), filterNames.get(i));
					}
				}

				dialog.setContentProvider(contentProvider);
				dialog.setLabelProvider(labelProvider);


				if (currentFile != null && currentFile.exists()) {
					dialog.setInitialSelections(new Object[] { currentFile });
				}

				int code = dialog.open();
				if (code == Window.OK) {
					Object[] result = dialog.getResult();
					if (result.length > 0) {
						Object file = result[0];
						if (file instanceof IFile) {
							setResult((IFile) file);
						}
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Nothing
			}

		});
	}

	/**
	 *
	 * @param path
	 *            a file path
	 * @return
	 *         the IFile corresponding to the path. This method is required to be overridden by others projects if required
	 */
	protected IFile getIFile(final String path) {
		return FileUtil.getIFile(path);
	}

	/**
	 *
	 * @return
	 *         a new {@link WorkspaceContentProvider}
	 */
	protected WorkspaceContentProvider createWorkspaceContentProvider() {
		if (this.showOnlyCurrentProject && this.currentProjectName != null && !this.currentProjectName.isEmpty()) {
			return new SingleProjectContentProvider(this.currentProjectName);
		}
		return new WorkspaceContentProvider();
	}

	/**
	 *
	 * @param file
	 *            the selected file
	 */
	protected void setResult(IFile file) {
		setResult(file.getFullPath().toString());
	}

	/**
	 *
	 * @param path
	 *            the path of the selected file
	 */
	protected void setResult(String path) {
		this.text.setText(path);
		notifyChange();
	}

	/**
	 * Set the filters extension and their name
	 *
	 * @param filterExtensions
	 *            the filters on file extension
	 * @param filterNames
	 *            the name of the filters
	 *
	 */
	public void setFilters(String[] filterExtensions, String[] filterNames) {
		if (filterExtensions.length != filterNames.length) {
			// This is a simple warning. Only valid filters will be retained.
			Activator.log.warn("FilterExtensions and FilterNames do not match"); //$NON-NLS-1$
		}

		setFilterNames(getFilterLabels(filterNames, filterExtensions));
		setFilterExtensions(filterExtensions);
	}

	/**
	 *
	 * @param filterNames
	 *            the name of the filters
	 * @param filterExtensions
	 *            the filters on file extension
	 * @return
	 *         the label to display for each filter
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
	 * @param filterExtensions
	 *            the filters on file extension
	 */
	public void setFilterExtensions(String[] filterExtensions) {
		this.filterExtensions = Arrays.asList(filterExtensions);
	}

	/**
	 *
	 * @param filterNames
	 *            the name of the filters
	 */
	public void setFilterNames(String[] filterNames) {
		this.filterNames = Arrays.asList(filterNames);
	}

	/**
	 *
	 * @param filteredExtension
	 *            the filter extension
	 * @param filterName
	 *            the name of the filter
	 */
	public void addFilteredExtension(String filteredExtension, String filterName) {
		if (filteredExtension != null) {
			if (filterName == null) {
				filterName = filteredExtension;
			}

			filterExtensions.add(filteredExtension);
			filterNames.add(filterName);
		}
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.StringEditor#getEditableType()
	 *
	 * @return
	 *         the String type
	 */
	@Override
	public Object getEditableType() {
		return String.class;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.StringEditor#setReadOnly(boolean)
	 *
	 * @param readOnly
	 *            boolean indicating if the text field and the button must be read-only or not
	 */
	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		this.readOnly = readOnly;
		updateButtons();
	}

	/**
	 * this method update the button status
	 */
	private void updateButtons() {
		this.deleteButton.setEnabled(!readOnly);
		this.browseWorkspace.setEnabled(!readOnly);
	}

	/**
	 *
	 * @param showOnlyCurrentProject
	 *            boolean indicating if we want to show only the content of the current project or all workspace project
	 */
	public void setShowOnlyCurrentProject(final boolean showOnlyCurrentProject) {
		this.showOnlyCurrentProject = showOnlyCurrentProject;
	}

	/**
	 *
	 * @param projectName
	 *            the name of the current project
	 */
	public void setProjectName(final String projectName) {
		this.currentProjectName = projectName;
	}

}
