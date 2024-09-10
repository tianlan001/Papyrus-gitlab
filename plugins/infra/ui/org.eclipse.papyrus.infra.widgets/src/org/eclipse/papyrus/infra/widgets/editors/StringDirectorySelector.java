/*****************************************************************************
 * Copyright (c) 2018 CEA LIST.
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

import java.io.File;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.services.labelprovider.service.impl.LabelProviderServiceImpl;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.messages.Messages;
import org.eclipse.papyrus.infra.widgets.providers.WorkspaceContentProvider;
import org.eclipse.papyrus.infra.widgets.util.FileUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;

/**
 * A Widget for editing Strings with File paths
 * The file paths may be absolute (FileSystem paths) or relative to the workspace (Workspace paths)
 * Adapted from {@link StringFileSelector}
 *
 * @since 3.2
 * @author Vincent Lorenzo
 */
public class StringDirectorySelector extends StringEditor {

	private Button browse;

	private Button browseWorkspace;

	private boolean allowWorkspace = true, allowFileSystem = true;

	private boolean readOnly = false;

	private boolean allowIProject = true;

	public void setAllowIProject(boolean allowIProject) {
		this.allowIProject = allowIProject;
	}

	public void setAllowIFolder(boolean allowIFolder) {
		this.allowIFolder = allowIFolder;
	}

	public void setAllowIWorkspaceRoot(boolean allowIWorkspaceRoot) {
		this.allowIWorkspaceRoot = allowIWorkspaceRoot;
	}

	private boolean allowIFolder = true;

	private boolean allowIWorkspaceRoot = true;

	public StringDirectorySelector(Composite parent, int style) {
		super(parent, style);
		((GridLayout) getLayout()).numColumns = 5;

		browse = factory.createButton(this, Messages.StringFileSelector_Browse, SWT.PUSH);
		browse.setLayoutData(new GridData());
		browseWorkspace = factory.createButton(this, Messages.StringFileSelector_BrowseWorkspace, SWT.PUSH);
		browseWorkspace.setLayoutData(new GridData());

		browse.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				File file = FileUtil.getFolder(text.getText());

				DirectoryDialog dialog = new DirectoryDialog(getShell());
				if (labelText != null) {
					dialog.setText(labelText);
				}
				if (null != file) {
					dialog.setFilterPath(file.getAbsolutePath());
				}
				String result = dialog.open();
				if (result == null) { // Cancel
					return;
				}
				setResult(result);
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

				IFile currentFile = FileUtil.getIFile(text.getText());

				TreeSelectorDialog dialog = new TreeSelectorDialog(getShell());
				if (labelText != null) {
					dialog.setTitle(labelText);
				}

				WorkspaceContentProvider contentProvider = new WorkspaceContentProvider() {
					/**
					 * @see org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider#isValidValue(java.lang.Object)
					 *
					 * @param element
					 * @return
					 */
					@Override
					public boolean isValidValue(Object element) {
						return isValidFolder(element);
					}

				};


				dialog.setContentProvider(contentProvider);
				dialog.setLabelProvider(labelProvider);


				if (currentFile != null && currentFile.exists()) {
					dialog.setInitialSelections(new IFile[] { currentFile });
				}

				int code = dialog.open();
				if (code == Window.OK) {
					Object[] result = dialog.getResult();
					if (result.length > 0) {
						Object file = result[0];
						if (isValidFolder(result[0])) {
							setResult((IContainer) file);
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

	protected void setResult(IContainer file) {
		text.setText(file.getFullPath().toString());
		notifyChange();
	}

	protected void setResult(File file) {
		if (file.isDirectory()) {
			text.setText(file.getAbsolutePath());
			notifyChange();
		}
	}

	protected void setResult(String path) {
		text.setText(path);
		notifyChange();
	}

	@Override
	public Object getEditableType() {
		return String.class;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		this.readOnly = readOnly;
		updateButtons();
	}

	public void setAllowWorkspace(boolean allowWorkspace) {
		this.allowWorkspace = allowWorkspace;
		updateButtons();
	}

	public void setAllowFileSystem(boolean allowFileSystem) {
		this.allowFileSystem = allowFileSystem;
		updateButtons();
	}

	private void updateButtons() {
		boolean enableWorkspace = !readOnly && allowWorkspace;
		boolean enableFileSystem = !readOnly && allowFileSystem;
		// ((GridData)browseWorkspace.getLayoutData()).exclude = !allowWorkspace;
		// ((GridData)browse.getLayoutData()).exclude = !allowFileSystem;
		browseWorkspace.setEnabled(enableWorkspace);
		browse.setEnabled(enableFileSystem);
	}

	/**
	 * 
	 * @param element
	 *            an element
	 * @return
	 * 		<code>true</code> if the element can be considered as a valid selection (so a kind of directory), according to the class parameters
	 */
	private boolean isValidFolder(final Object element) {
		if (element instanceof IProject && allowIProject) {
			return true;
		}
		if (element instanceof IFolder && allowIFolder) {
			return true;
		}
		if (element instanceof IWorkspaceRoot && allowIWorkspaceRoot) {
			return true;
		}
		return false;
	}

}
