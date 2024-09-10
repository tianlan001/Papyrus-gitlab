/*****************************************************************************
 * Copyright (c) 2017, 2018 CEA LIST.
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
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Initial API and implementation
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Bug 539181
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.profilemigration.ui.preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.papyrus.infra.widgets.providers.CollectionContentProvider;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.Activator;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * Define the preference page for configuring the Profile migration tool
 *
 * ----------------------
 * list of check button to activate or not pop-up
 * ----------------------
 * list of cached file with a remove button
 *
 */
public class ProfileMigrationPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	/** ID of delete button. */
	private static final int DELETE_BUTTON_ID = 1;

	/** Icon for delete action button. */
	private static final Image DELETE_ICON = AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.papyrus.infra.widgets", "icons/Delete_12x12.gif").createImage(); //$NON-NLS-1$ //$NON-NLS-2$

	/** The list of files in cache */
	private static List<String> cachedFiles = new ArrayList<>();

	/** The original list of files in cache */
	private static List<String> originalcachedFiles = new ArrayList<>();

	/** The list of booleanFieldEditor one for each dialog type */
	private List<BooleanFieldEditor> booleanFieldEditor;

	/** The treeViewer to display cached files */
	private TreeViewer cachedFilesTreeViewer;

	/** The main container */
	private Composite mainContainer;

	/**
	 * Constructor.
	 *
	 */
	public ProfileMigrationPreferencePage() {
		booleanFieldEditor = new ArrayList<>();
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		originalcachedFiles = new ArrayList<>(cachedFiles);
	}

	@Override
	protected Control createContents(Composite parent) {
		mainContainer = parent;
		createFieldEditors();

		Group group = new Group(mainContainer, SWT.SCROLL_PAGE);
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		group.setText(Messages.ProfileMigrationPreferencePage_FileInCached);
		createTreeActionButtons(group);
		createCachedFilesPart(group);

		refreshTreeviewer();

		initialize();
		checkState();

		return mainContainer;
	}

	@Override
	protected void createFieldEditors() {
		Group group = new Group(mainContainer, SWT.SCROLL_PAGE);
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		group.setText(Messages.ProfileMigrationPreferencePage_ShowDialogs);
		for (Entry<String, String> entry : ProfileMigrationPreferenceConstants.mapPrefConstToLabel.entrySet()) {
			BooleanFieldEditor editor = new BooleanFieldEditor(entry.getKey(), entry.getValue(), group);
			booleanFieldEditor.add(editor);
			addField(editor);
			editor.setPreferenceStore(Activator.getDefault().getPreferenceStore());
		}
	}

	/**
	 * @see org.eclipse.jface.preference.PreferencePage#performCancel()
	 *
	 * @return
	 */
	@Override
	public boolean performCancel() {
		// set the original list of files
		cachedFiles.clear();
		cachedFiles.addAll(originalcachedFiles);
		return super.performCancel();
	}

	/**
	 * @see org.eclipse.jface.preference.PreferencePage#performApply()
	 *
	 */
	@Override
	protected void performApply() {
		// the new list of files is validate so the original list become the new one
		originalcachedFiles = new ArrayList<>(cachedFiles);
		super.performApply();
	}

	/**
	 * Create actions associate to tree viewer.
	 *
	 * @param parent
	 *            Composite where action buttons will be added
	 */
	private void createTreeActionButtons(Composite parent) {
		Composite buttonsPanel = new Composite(parent, SWT.NONE);
		buttonsPanel.setLayout(new GridLayout());
		buttonsPanel.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false, 1, 1));

		createButton(buttonsPanel, DELETE_BUTTON_ID, DELETE_ICON, null);

	}

	/**
	 * Create cached file list
	 *
	 * @param parent
	 *            Composite where the treeViewer will be added
	 */
	private void createCachedFilesPart(Composite parent) {
		// Create viewer
		cachedFilesTreeViewer = new TreeViewer(parent, SWT.BORDER);

		// Set standard collection content provider
		cachedFilesTreeViewer.setContentProvider(CollectionContentProvider.instance);
		cachedFilesTreeViewer.setLabelProvider(new LabelProvider());

		// Set layout
		Tree tree = cachedFilesTreeViewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
	}

	/**
	 * Method to create a button with an icon and no label.
	 *
	 * @param parent
	 *            the composite parent
	 * @param id
	 *            the id of the new button
	 * @param icon
	 *            an icon associate to the button (could be null)
	 * @param label
	 *            a label associate to the button (could be null)
	 * @return the newly created button
	 */
	protected Button createButton(Composite parent, int id, Image icon, String label) {
		((GridLayout) parent.getLayout()).numColumns++;
		Button button = new Button(parent, SWT.PUSH);
		button.setFont(JFaceResources.getDialogFont());
		button.setData(new Integer(id));
		button.setToolTipText(Messages.ProfileMigrationPreferencePage_deleteCachedFileTooptip);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				buttonPressed();
			}
		});

		if (label != null) {
			button.setText(label);
		}
		if (icon != null) {
			button.setImage(icon);
		}
		setButtonLayoutData(button);
		return button;
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
	 *
	 * @param buttonId
	 */
	protected void buttonPressed() {
		deleteAction();
	}

	/**
	 * Delete current selection of tree viewer.
	 */
	private void deleteAction() {
		ISelection selection = cachedFilesTreeViewer.getSelection();

		if (selection instanceof IStructuredSelection) {
			Object selectedElement = ((IStructuredSelection) selection).getFirstElement();
			if (selectedElement instanceof String) {
				cachedFiles.remove(selectedElement);
			}
			refreshTreeviewer();
		}
	}

	/**
	 * Refresh content of tree viewer according to selected theme/
	 *
	 * @param currentTheme
	 *            Current theme
	 */
	private void refreshTreeviewer() {
		// Set mirroring list as viewer input
		cachedFilesTreeViewer.getTree().setEnabled(true);
		cachedFilesTreeViewer.setInput(cachedFiles);
	}

	/**
	 * Add file to the cache
	 *
	 * @param fileName
	 */
	public static void addFile(String fileName) {
		cachedFiles.add(fileName);
	}

	/**
	 * Get the cache file list
	 *
	 * @return the cache file list
	 */
	public static List<String> getCachedFiles() {
		return cachedFiles;
	}

}
