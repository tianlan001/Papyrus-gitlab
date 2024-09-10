/*****************************************************************************
 * Copyright (c) 2012, 2014 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Initial API and implementation
 *  Christian W. Damus - bug 399859
 *  
 *****************************************************************************/
package org.eclipse.papyrus.uml.profile.service.ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.papyrus.uml.profile.providers.ProfileApplicationContentProvider;
import org.eclipse.papyrus.uml.profile.providers.ProfileApplicationLabelProvider;
import org.eclipse.papyrus.uml.tools.helper.IProfileApplicationDelegate;
import org.eclipse.papyrus.uml.tools.helper.ProfileApplicationDelegateRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;

public class RefreshProfileDialog extends SelectionDialog {

	protected final Map<Package, Collection<Profile>> profilesToReapply;

	protected Runnable callback;

	protected Package rootPackage;

	public RefreshProfileDialog(Shell parentShell, Package rootPackage) {
		super(parentShell);
		setBlockOnOpen(false);
		this.rootPackage = rootPackage;
		profilesToReapply = new HashMap<Package, Collection<Profile>>();
		setTitle("Some profiles have changed");
	}

	public void setCallback(Runnable callback) {
		this.callback = callback;
	}

	@Override
	public void create() {
		super.create();

		Label descriptionLabel = new Label(getDialogArea(), SWT.WRAP);
		descriptionLabel.setText("Some local profiles have changed. Select the ones you want to re-apply");

		TreeViewer viewer = new TreeViewer(getDialogArea());
		viewer.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Tree tree = viewer.getTree();

		TableLayout layout = new TableLayout();

		TreeColumn propertyColumn = new TreeColumn(tree, SWT.CENTER);
		propertyColumn.setText("Package");
		layout.addColumnData(new ColumnWeightData(30, 300, true));

		TreeColumn propertyVersionColumn = new TreeColumn(tree, SWT.CENTER);
		propertyVersionColumn.setText("Version");
		layout.addColumnData(new ColumnWeightData(30, 100, true));

		TreeColumn ownerColumn = new TreeColumn(tree, SWT.CENTER);
		ownerColumn.setText("Profile");
		layout.addColumnData(new ColumnWeightData(60, 150, true));

		TreeColumn ownerVersionColumn = new TreeColumn(tree, SWT.CENTER);
		ownerVersionColumn.setText("Version");
		layout.addColumnData(new ColumnWeightData(30, 100, true));

		TreeColumn checkColumn = new TreeColumn(tree, SWT.CENTER);
		checkColumn.setText("Reapply");
		layout.addColumnData(new ColumnWeightData(10, 70, true));

		tree.setLayout(layout);
		tree.setHeaderVisible(true);

		viewer.setContentProvider(new ProfileApplicationContentProvider(rootPackage));
		viewer.setLabelProvider(new ProfileApplicationLabelProvider());

		viewer.setInput(new Object());

		installEditors(viewer);

		getDialogArea().layout();

		getShell().pack();
	}

	protected void installEditors(TreeViewer viewer) {
		viewer.expandAll();
		for (TreeItem item : viewer.getTree().getItems()) {
			installEditors(item, rootPackage);
		}
	}

	protected void installEditors(TreeItem treeItem, final Object parentPackage) {
		Object currentDataItem = treeItem.getData();
		if (currentDataItem instanceof ProfileApplication) {

			ProfileApplication profileApplication = (ProfileApplication) currentDataItem;

			Tree tree = treeItem.getParent();

			final Button checkbox = new Button(tree, SWT.CHECK);

			checkbox.setSelection(true);

			IProfileApplicationDelegate delegate = getDelegate(profileApplication);
			final Package applyingPackage = delegate.getApplyingPackage(profileApplication);
			final Profile appliedProfile = delegate.getAppliedProfile(profileApplication);
			getProfilesToReapply(applyingPackage).add(appliedProfile);

			checkbox.addSelectionListener(new SelectionListener() {

				public void widgetSelected(SelectionEvent e) {
					if (checkbox.getSelection()) {
						getProfilesToReapply(applyingPackage).add(appliedProfile);
					} else {
						getProfilesToReapply(applyingPackage).remove(appliedProfile);
					}
				}

				public void widgetDefaultSelected(SelectionEvent e) {
					// Nothing
				}

			});

			TreeEditor editor = new TreeEditor(tree);
			editor.horizontalAlignment = SWT.CENTER;
			editor.grabHorizontal = true;

			editor.setEditor(checkbox, treeItem, 4);
		}


		for (TreeItem subitem : treeItem.getItems()) {
			installEditors(subitem, currentDataItem);
		}
	}

	protected IProfileApplicationDelegate getDelegate(ProfileApplication profileApplication) {
		return ProfileApplicationDelegateRegistry.INSTANCE.getDelegate(profileApplication);
	}

	@Override
	protected Composite getDialogArea() {
		return (Composite) super.getDialogArea();
	}

	protected Collection<Profile> getProfilesToReapply(Package currentPackage) {
		if (!profilesToReapply.containsKey(currentPackage)) {
			profilesToReapply.put(currentPackage, new LinkedHashSet<Profile>());
		}

		return profilesToReapply.get(currentPackage);
	}

	@Override
	protected void okPressed() {
		setSelectionResult(profilesToReapply.values().toArray());
		if (callback != null) {
			callback.run();
		}
		super.okPressed();
	}

	public Map<Package, Collection<Profile>> getProfilesToReapply() {
		return profilesToReapply;
	}
}
