/**
 * Copyright (c) 2017 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors:
 *  Maged Elaasar - Initial API and implementation
 *  
 * 
 */
package org.eclipse.papyrus.infra.ui.architecture.preferences;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.ui.CommonUIPlugin;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * A dialog to select architecture models from the file system or workspace by URIs
 *
 * @since 1.0
 */
public class ArchitectureModelSelectDialog extends Dialog {
	protected static final int CONTROL_OFFSET = 10;
	protected String title;
	protected int style;
	protected Text uriField;
	protected String uriText;
	protected String[] initialSelection;

	/**
	 * Constructor.
	 *
	 * @param parent
	 * @param title
	 * @param initialSelection
	 */
	public ArchitectureModelSelectDialog(Shell parent, String title, String[] initialSelection) {
		super(parent);
		this.title = title != null ? title : CommonUIPlugin.INSTANCE.getString("_UI_ResourceDialog_title");
		this.style = SWT.MULTI | SWT.OPEN;

		setShellStyle(getShellStyle() | SWT.MAX | SWT.RESIZE);
		this.initialSelection = initialSelection;
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(title);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		{
			FormLayout layout = new FormLayout();
			composite.setLayout(layout);

			GridData data = new GridData();
			data.verticalAlignment = GridData.FILL;
			data.grabExcessVerticalSpace = true;
			data.horizontalAlignment = GridData.FILL;
			data.grabExcessHorizontalSpace = true;
			if (!EMFPlugin.IS_RESOURCES_BUNDLE_AVAILABLE) {
				data.widthHint = 330;
			}
			composite.setLayoutData(data);
		}

		Composite buttonComposite = new Composite(composite, SWT.NONE);

		Label resourceURILabel = new Label(composite, SWT.LEFT);
		{
			resourceURILabel.setText(
					CommonUIPlugin.INSTANCE.getString("_UI_ResourceURIs_label"));
			FormData data = new FormData();
			data.left = new FormAttachment(0, CONTROL_OFFSET);
			data.top = new FormAttachment(0, CONTROL_OFFSET);
			resourceURILabel.setLayoutData(data);
		}

		{
			FormData data = new FormData();
			data.top = new FormAttachment(resourceURILabel, CONTROL_OFFSET, SWT.CENTER);
			data.left = new FormAttachment(resourceURILabel, CONTROL_OFFSET);
			data.right = new FormAttachment(100, -CONTROL_OFFSET);
			buttonComposite.setLayoutData(data);

			buttonComposite.setLayout(new FormLayout());
		}

		uriField = new Text(composite, SWT.BORDER);
		{
			FormData data = new FormData();
			data.top = new FormAttachment(buttonComposite, CONTROL_OFFSET);
			data.left = new FormAttachment(0, CONTROL_OFFSET);
			data.right = new FormAttachment(100, -CONTROL_OFFSET);
			uriField.setLayoutData(data);
			uriField.setText(String.join(" ", initialSelection));
		}

		Button browseFileSystemButton = new Button(buttonComposite, SWT.PUSH);
		browseFileSystemButton.setText(CommonUIPlugin.INSTANCE.getString("_UI_BrowseFileSystem_label"));
		prepareBrowseFileSystemButton(browseFileSystemButton);

		if (EMFPlugin.IS_RESOURCES_BUNDLE_AVAILABLE) {
			Button browseWorkspaceButton = new Button(buttonComposite, SWT.PUSH);
			{
				FormData data = new FormData();
				data.right = new FormAttachment(100);
				browseWorkspaceButton.setLayoutData(data);
			}
			{
				FormData data = new FormData();
				data.right = new FormAttachment(browseWorkspaceButton, -CONTROL_OFFSET);
				browseFileSystemButton.setLayoutData(data);
			}
			browseWorkspaceButton.setText(CommonUIPlugin.INSTANCE.getString("_UI_BrowseWorkspace_label"));
			prepareBrowseWorkspaceButton(browseWorkspaceButton);
		} else {
			FormData data = new FormData();
			data.right = new FormAttachment(100);
			browseFileSystemButton.setLayoutData(data);
		}

		Label separatorLabel = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		{
			FormData data = new FormData();
			data.top = new FormAttachment(uriField, (int) (1.5 * CONTROL_OFFSET));
			data.left = new FormAttachment(0, -CONTROL_OFFSET);
			data.right = new FormAttachment(100, CONTROL_OFFSET);
			separatorLabel.setLayoutData(data);
		}

		composite.setTabList(new Control[] { uriField, buttonComposite });
		return composite;
	}

	/**
	 * Configures the Browse File System button
	 * @param browseFileSystemButton
	 */
	protected void prepareBrowseFileSystemButton(Button browseFileSystemButton) {
		browseFileSystemButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				FileDialog fileDialog = new FileDialog(getShell(), style);
				fileDialog.open();

				String filterPath = fileDialog.getFilterPath();
				String[] fileNames = fileDialog.getFileNames();
				StringBuffer uris = new StringBuffer();

				for (int i = 0, len = fileNames.length; i < len; i++) {
					uris.append(URI.createFileURI(filterPath + File.separator + fileNames[i]).toString());
					uris.append("  ");
				}
				uriField.setText((uriField.getText() + "  " + uris.toString()).trim());
			}
		});
	}

	/**
	 * Prepares the Browse Workspace button
	 * @param browseWorkspaceButton
	 */
	protected void prepareBrowseWorkspaceButton(Button browseWorkspaceButton) {
		browseWorkspaceButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				StringBuffer uris = new StringBuffer();

				IFile[] files = WorkspaceResourceDialog.openFileSelection(getShell(), null, null, true,
						null, null);
				for (int i = 0, len = files.length; i < len; i++) {
					uris.append(URI.createPlatformResourceURI(files[i].getFullPath().toString(), true));
					uris.append("  ");
				}
				uriField.setText((uriField.getText() + "  " + uris.toString()).trim());
			}
		});
	}

	@Override
	protected void okPressed() {
		uriText = getURIText();
		super.okPressed();
	}

	/**
	 * @return the value of the model URIs as a string
	 */
	public String getURIText() {
		return uriField != null && !uriField.isDisposed() ? uriField.getText() : uriText;
	}

	/**
	 * @return the value of the model URIs as a list
	 */
	public List<URI> getURIs() {
		List<URI> uris = new ArrayList<URI>();
		if (getURIText() != null) {
			for (StringTokenizer stringTokenizer = new StringTokenizer(getURIText()); stringTokenizer.hasMoreTokens();) {
				String uri = stringTokenizer.nextToken();
				uris.add(URI.createURI(uri));
			}
		}
		return uris;
	}

}