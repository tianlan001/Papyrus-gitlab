/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *	Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.newchild.ui.preferences;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.papyrus.infra.newchild.CreationMenuRegistry;
import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Folder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * Preferences page for new child contributions to set there visibility.
 */
public class Preferences extends PreferencePage implements IWorkbenchPreferencePage {

	/** The map of the checkboxes related to folders */
	private Map<Folder, Button> checkboxes = new HashMap<>();

	/** The map of modification on Folders. */
	private Map<Folder, Boolean> folders = new HashMap<>();

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
		// Nothing
	}

	/**
	 * Creation of the list of new child contribution.<br>
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite self = new Composite(parent, SWT.NONE);
		self.setLayout(new GridLayout(1, false));
		self.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createHeaderContents(self);

		Label label = new Label(self, SWT.NONE);
		label.setText("New Child:");

		CreationMenuRegistry creationMenuInstance = CreationMenuRegistry.getInstance();
		List<Folder> rootFolders = creationMenuInstance.getRootFolder();

		for (Folder folder : rootFolders) {
			boolean applied = folder.isVisible();
			Button checkbox = new Button(self, SWT.CHECK);

			checkbox.setText(getName(folder));
			checkbox.setToolTipText(getToolTipText(folder));
			checkbox.setSelection(applied);

			final Folder theFolder = folder;
			checkbox.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					boolean applied = ((Button) e.widget).getSelection();
					folders.put(theFolder, applied);
				}

			});
			checkboxes.put(folder, checkbox);
		}

		createFooterContents(self);

		return null;
	}

	/**
	 * Gets the tool type text for a folder.
	 */
	private String getToolTipText(final Folder folder) {
		URI uri = EcoreUtil.getURI(folder).trimFragment();
		StringBuilder text = new StringBuilder();
		if (null != uri) {

			String FileName = uri.lastSegment();
			text.append(FileName);
			text.append(" - ");//$NON-NLS-1$
			String pluginName = uri.segment(1);
			text.append(pluginName);
			String type = uri.segment(0);
			text.append("(");//$NON-NLS-1$
			text.append(type);
			text.append(")");//$NON-NLS-1$
		}
		return text.toString();
	}

	/**
	 * Gets the name to display of a folder.
	 */
	private String getName(final Folder folder) {
		URI uri = EcoreUtil.getURI(folder).trimFragment();

		StringBuilder name = new StringBuilder();
		name.append(uri.lastSegment());
		name.replace(name.indexOf("." + uri.fileExtension()), name.length(), "");//$NON-NLS-1$ //$NON-NLS-2$

		if (uri.isPlatformPlugin()) {
			name.append(" (Plugin)");//$NON-NLS-1$
		} else if (uri.isPlatformResource()) {
			name.append(" (Resource)");//$NON-NLS-1$
		}
		return name.toString();
	}

	/**
	 * Overridden by subclasses to create optional header content above the
	 * list of context check-boxes.
	 * 
	 * @param parent
	 *            the parent composite
	 */
	protected void createHeaderContents(Composite parent) {
		// Pass
	}

	/**
	 * Overridden by subclasses to create optional footer content below the
	 * list of context check-boxes.
	 * 
	 * @param parent
	 *            the parent composite
	 */
	protected void createFooterContents(Composite parent) {
		// Pass
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		return saveChanges();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performApply()
	 */
	@Override
	public void performApply() {
		saveChanges();
	}

	/**
	 * Save changes.
	 */
	public boolean saveChanges() {
		for (Entry<Folder, Boolean> folderEntry : folders.entrySet()) {
			CreationMenuRegistry.getInstance().setCreationMenuVisibility(folderEntry.getKey(), folderEntry.getValue());
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
	 */
	@Override
	public void performDefaults() {
		CreationMenuRegistry.getInstance().restoreDefault();
		List<Folder> rootFolder = CreationMenuRegistry.getInstance().getRootFolder();
		folders.clear();
		for (Folder folder : rootFolder) {
			Button button = checkboxes.get(folder);
			button.setSelection(folder.isVisible());
		}
	}

}
