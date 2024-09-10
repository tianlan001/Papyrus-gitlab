/*******************************************************************************
 * Copyright (c) 2006, 2016-2017 Anyware Technologies and others. All rights reserved. 
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License 2.0 which accompanies this distribution, and is
t https://www.eclipse.org/legal/epl-2.0/
t
t SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Jacques Lescot (Anyware Technologies) - Initial API and implementation
 *    Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Bug 436947
 *    Christian W. Damus - bug 497865
 *    Benoit Maggi (CEA LIST) - bug 518307
 ******************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode.ui;

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.ui.dialogs.ResourceDialog;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeManager;
import org.eclipse.papyrus.infra.services.controlmode.ControlModePlugin;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequest;
import org.eclipse.papyrus.infra.services.controlmode.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * Dialog that will ask the user the target URI for the new resource holding the new controlled element
 *
 */
public class CreateModelFragmentDialog extends ResourceDialog {

	static final String DIALOG_SETTINGS = "CreateModelFragmentDialog"; //$NON-NLS-1$
	static final String SETTING_CREATE_SHARD = "createShard"; //$NON-NLS-1$

	/** The Constant DIALOG_TITLE. */
	private static final String DIALOG_TITLE = Messages.getString("CreateModelFragmentDialog.dialog.title"); //$NON-NLS-1$

	private URI uri;

	private Resource currentResource;
	private EObject objectToControl;

	private String defaultName;

	private Button shardButton;
	private boolean createShard;
	private ControlDecoration controlDecoration;

	/**
	 * The constructor
	 *
	 * @param parent
	 * @param theDomain
	 * @param theCurrentResource
	 * @param defaultName
	 */
	public CreateModelFragmentDialog(Shell parent, Resource theCurrentResource, String defaultName) {
		super(parent, DIALOG_TITLE, SWT.SAVE);
		this.currentResource = theCurrentResource;
		this.defaultName = defaultName;
	}

	/**
	 * @since 1.3
	 */
	public CreateModelFragmentDialog(Shell parent, EObject objectToControl, String defaultName) {
		this(parent, objectToControl.eResource(), defaultName);
		this.objectToControl = objectToControl;
	}

	@Override
	protected void prepareBrowseWorkspaceButton(Button browseWorkspaceButton) {
		browseWorkspaceButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				IFile file = null;
				String path = URI.createURI(computeDefaultURI()).lastSegment();
				file = WorkspaceResourceDialog.openNewFile(getShell(), null, null, path != null ? new Path(path) : null, null);
				if (file != null) {	
					uriField.setText(URI.createPlatformResourceURI(file.getFullPath().toString(), true).toString());
				}
			}

		});
	}

	public String computeDefaultURI() {
		String ext = currentResource.getURI().fileExtension();
		URI uri = currentResource.getURI().trimSegments(1);
		uri = uri.appendSegment(defaultName).appendFileExtension(ext);
		return uri.toString();
	}

	@Override
	protected Control createContents(Composite parent) {
		Control result = super.createContents(parent);
		this.uriField.setText(computeDefaultURI());
		// And the rest of the dialog initialization
		loadDialogState();

		// Is the sub-model unit option available?
		if (objectToControl != null && !ControlModeManager.getInstance().canCreateSubmodel(objectToControl)) {
			// We can only create a shard resource
			shardButton.setSelection(false);
			shardButton.setEnabled(false);
			createShard = true;
		}

		// Initial validation
		validateDialog();
		return result;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite result = (Composite) super.createDialogArea(parent);
		Control[] superContents = result.getChildren();

		// Note that the polarity of this button is the reverse of the option:
		// it asks the user whether to create a *non-shard*
		shardButton = new Button(result, SWT.CHECK);
		{
			FormData data = new FormData();
			data.top = new FormAttachment(uriField, CONTROL_OFFSET);
			data.left = new FormAttachment(0, CONTROL_OFFSET);
			data.right = new FormAttachment(100, -CONTROL_OFFSET);
			shardButton.setLayoutData(data);

			// And re-attach the separator
			Control separator = superContents[superContents.length - 1];
			((FormData) separator.getLayoutData()).top.control = shardButton;
		}
		shardButton.setText(Messages.getString("CreateModelFragmentDialog.submodel.checkbox")); //$NON-NLS-1$
		shardButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				createShard = !shardButton.getSelection();
				validateDialog();
			}
		});
		
		uriField.addModifyListener(__ -> validateDialog());
		controlDecoration = new ControlDecoration(uriField, SWT.TOP | SWT.LEFT);
		// This is guaranteed by contract with subclasses to be the first child
		Control buttonComposite = superContents[0];
		result.setTabList(new Control[] {uriField, buttonComposite, shardButton });

		return result;
	}

	/**
	 * Creates and, if it already exists, loads the specified resource. This implementation verifies
	 * that a resource can be opened for that URI, that the resource is not the object's current
	 * container, and that it is not read-only in the editing domain. If there is an existing
	 * resource with that URI, it prompts before overriding or adding to it.
	 *
	 * @see org.eclipse.emf.common.ui.dialogs.ResourceDialog#processResources()
	 */
	@Override
	protected boolean processResources() {
		this.uri = URI.createURI(getURIText());
		saveDialogState();
		return true;
	}

	/**
	 * Return the created Resource
	 *
	 * @return the Resource
	 */
	public URI getURI() {
		return uri;
	}

	/**
	 * Queries whether the user elected to create a 'shard' resource (by un-checking
	 * the 'create an independent sub-model resource' option).
	 * 
	 * @return whether to create a shard resource
	 * 
	 * @since 1.3
	 */
	public boolean isCreateShard() {
		return createShard;
	}

	private void loadDialogState() {
		IDialogSettings settings = DialogSettings.getOrCreateSection(ControlModePlugin.getDefault().getDialogSettings(), DIALOG_SETTINGS);
		loadDialogState(settings);
	}

	/**
	 * Initializes the dialog state from the given {@code settings}.
	 * Subclasses may extend (be sure to call {@code super}).
	 * 
	 * @param settings
	 *            the dialog settings to read from (never {@code null})
	 * @since 1.3
	 */
	protected void loadDialogState(IDialogSettings settings) {
		createShard = settings.getBoolean(SETTING_CREATE_SHARD);
		shardButton.setSelection(!createShard);
	}

	private void saveDialogState() {
		IDialogSettings settings = DialogSettings.getOrCreateSection(ControlModePlugin.getDefault().getDialogSettings(), DIALOG_SETTINGS);
		saveDialogState(settings);
	}

	/**
	 * Stores the dialog state to the given {@code settings}.
	 * Subclasses may extend (be sure to call {@code super}).
	 * 
	 * @param settings
	 *            the dialog settings to write to (never {@code null})
	 * @since 1.3
	 */
	protected void saveDialogState(IDialogSettings settings) {
		// Only store this setting if it's a choice
		if (shardButton.isEnabled()) {
			settings.put(SETTING_CREATE_SHARD, createShard);
		}
	}

	/**
	 * @since 1.3
	 */
	protected boolean validateDialog() {
		Diagnostic diagnostic = diagnosticDialog();
		switch (diagnostic.getSeverity()) {
		case Diagnostic.ERROR:
			FieldDecoration error = FieldDecorationRegistry.getDefault()
					.getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
			controlDecoration.setImage(error.getImage());
			controlDecoration.showHoverText(diagnostic.getMessage());
			controlDecoration.setDescriptionText(diagnostic.getMessage());
			controlDecoration.show();
			getButton(IDialogConstants.OK_ID).setEnabled(false);
			break;		
		case Diagnostic.WARNING:
			FieldDecoration warning = FieldDecorationRegistry.getDefault()
					.getFieldDecoration(FieldDecorationRegistry.DEC_WARNING);
			controlDecoration.setImage(warning.getImage());
			controlDecoration.showHoverText(diagnostic.getMessage());
			controlDecoration.setDescriptionText(diagnostic.getMessage());
			controlDecoration.show();
			getButton(IDialogConstants.OK_ID).setEnabled(true);
			break;
		case Diagnostic.OK:
		default:
			controlDecoration.hide();
			getButton(IDialogConstants.OK_ID).setEnabled(true);
			break;
		}
		
		return diagnostic.getSeverity() != Diagnostic.ERROR ;
	}
	
	/**
	 * @return diagnostic of the dialog
	 */
	protected Diagnostic diagnosticDialog() {
		URI uri = getURIs().stream().findAny().orElse(null);
		Diagnostic diagnostic = Diagnostic.OK_INSTANCE;
		// Can only create requests for validation if we have an object and a URI
		if (uri != null && objectToControl != null) {
			ControlModeRequest request = ControlModeRequest.createUIControlModelRequest(
					TransactionUtil.getEditingDomain(objectToControl),
					objectToControl,
					uri);
			diagnostic = ControlModeManager.getInstance().approveRequest(request);
		}
		if (Diagnostic.OK == diagnostic.getSeverity() && new ResourceSetImpl().getURIConverter().exists(uri, Collections.emptyMap())) {
		    diagnostic = new BasicDiagnostic(Diagnostic.WARNING, ControlModePlugin.PLUGIN_ID, 0, Messages.getString("CreateModelFragmentDialog.dialog.warning.alreadyexist"), null); //$NON-NLS-1$
		}
		return diagnostic;
	}
	
}
