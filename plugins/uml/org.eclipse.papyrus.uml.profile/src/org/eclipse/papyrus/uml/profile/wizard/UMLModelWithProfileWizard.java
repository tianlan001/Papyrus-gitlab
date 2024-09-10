/*****************************************************************************
 * Copyright (c) 2008, 2014 CEA LIST and others.
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
 *  Chokri Mraidha (CEA LIST) Chokri.Mraidha@cea.fr - Initial API and implementation
 *  Patrick Tessier (CEA LIST) Patrick.Tessier@cea.fr - modification
 *  Christian W. Damus (CEA) - bug 422257
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.profile.wizard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.uml.extensionpoints.Registry;
import org.eclipse.papyrus.uml.extensionpoints.profile.IRegisteredProfile;
import org.eclipse.papyrus.uml.tools.utils.PackageUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.editor.presentation.UMLModelWizard;

/**
 * Wizard to create a new Model with applied profile
 */
public class UMLModelWithProfileWizard extends UMLModelWizard {

	/** the third page = applied profiles selection */
	protected IWizardPage profilePage;

	/** list of registered profiles to be applied */
	protected Collection<IRegisteredProfile> rProfilesToApply = new HashSet<IRegisteredProfile>();

	/**
	 * Creates a new UMLModelWithProfileWizard.
	 */
	public UMLModelWithProfileWizard() {
		super();
		initProfileList();
	}

	/**
	 * Inits the profile list
	 */
	protected void initProfileList() {

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.uml2.uml.editor.presentation.UMLModelWizard#addPages()
	 */
	@Override
	public void addPages() {
		// creates original pages
		super.addPages();

		// creates profile selection page
		profilePage = new UMLModelWizardProfilesPage();
		addPage(profilePage);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.uml2.uml.editor.presentation.UMLModelWizard#createInitialModel()
	 */
	@Override
	protected EObject createInitialModel() {
		EClass eClass = (EClass) umlPackage.getEClassifier(initialObjectCreationPage.getInitialObjectName());
		EObject rootObject = umlFactory.create(eClass);

		applySelectedProfiles(rootObject, rProfilesToApply);

		return rootObject;
	}

	/**
	 * Apply profiles to the root element.
	 *
	 * @param root
	 *            the element where profiles are applied
	 * @param rProfilesToApply
	 *            the list of profiles to apply
	 * @return the number of applied profiles
	 */
	protected int applySelectedProfiles(EObject root, Collection<IRegisteredProfile> rProfilesToApply) {
		// number of applied profiles
		int num = 0;
		Iterator<IRegisteredProfile> it = rProfilesToApply.iterator();
		while (it.hasNext()) {
			num += applySelectedProfile(root, it.next());
		}
		return num;
	}

	/**
	 * Apply profiles to the root element.
	 *
	 * @param root
	 *            the element where profiles are applied
	 * @param rProfile
	 *            the profile to apply
	 * @return the number of applied profiles
	 */
	protected int applySelectedProfile(EObject root, IRegisteredProfile rProfile) {
		// number of applied profiles
		int num = 0;
		if (root instanceof org.eclipse.uml2.uml.Package) {
			URI modelUri = rProfile.getUri();
			Resource modelResource = EMFHelper.getResourceSet(root).getResource(modelUri, true);
			if (modelResource.getContents().get(0) instanceof Profile) {
				// two case : qualified names is equal to "" => whole profile must be applied
				// not equal to "" => specific profiles must be applied
				if ("".equals(rProfile.getQualifiedNames())) {
					PackageUtil.applyProfile((org.eclipse.uml2.uml.Package) root, (Profile) modelResource.getContents().get(0), true);
					num++;
				} else {
					// apply subprofiles corresponding to the qualified names selected
					// try to parse the qualified names
					String[] profiles = rProfile.getQualifiedNames().split(",");
					List<String> subprofilesList = new ArrayList<String>();
					// make a collection with String with no space
					for (int j = 0; j < profiles.length; j++) {
						String string = profiles[j].trim();
						subprofilesList.add(string);
					}

					return applySelectedProfile((Package) root, (Profile) modelResource.getContents().get(0), subprofilesList);
				}
			}
		}
		return num;
	}

	protected int applySelectedProfile(Package root, Profile profile, List<String> subprofilesList) {
		int num = 0;
		Iterator<EObject> it = profile.eAllContents();
		while (it.hasNext()) {
			EObject o = it.next();
			if (o instanceof Profile) {
				String name = ((Profile) o).getQualifiedName();
				if (subprofilesList.contains(name)) {
					PackageUtil.applyProfile(root, (Profile) o, true);
					num++;
				}
			}
		}

		return num;
	}

	/**
	 * Page where user can select profiles to be applied to a new UML model
	 */
	public class UMLModelWizardProfilesPage extends WizardPage {

		protected Table table;

		protected TableViewer tableViewer;

		protected final static int NUMBER_COLUMNS = 1;

		/**
		 * @param selection
		 */
		public UMLModelWizardProfilesPage() {
			super("com.cea.papyrus.profilespage");
			this.setTitle("Profile application");
			this.setMessage("Choose the set of profiles that will be applied to the new model");
		}

		/**
		 * The framework calls this to see if the file is correct.
		 *
		 * @generated
		 */
		protected boolean validatePage() {
			return true;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
		 */
		@Override
		public void setVisible(boolean visible) {
			super.setVisible(visible);
			if (visible) {

			}
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
		 */
		public void createControl(Composite parent) {
			Composite composite = new Composite(parent, SWT.NONE);

			// layout for main composite
			GridLayout layout = new GridLayout();
			layout.numColumns = 1;
			layout.verticalSpacing = 12;
			composite.setLayout(layout);

			GridData data = new GridData();
			data.verticalAlignment = GridData.FILL;
			data.grabExcessVerticalSpace = true;
			data.horizontalAlignment = GridData.FILL;
			composite.setLayoutData(data);

			createTable(composite);
			fillTable();

			setPageComplete(validatePage());
			setControl(composite);

		}

		private void createTable(Composite parent) {
			table = new Table(parent, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.HIDE_SELECTION | SWT.CHECK);

			GridData gridData = new GridData(GridData.FILL_BOTH);
			gridData.grabExcessVerticalSpace = true;
			gridData.horizontalSpan = 3;
			table.setLayoutData(gridData);

			table.setLinesVisible(true);
			table.setHeaderVisible(true);

			table.addSelectionListener(new SelectionListener() {

				public void widgetDefaultSelected(SelectionEvent e) {
					// do nothing
				}

				public void widgetSelected(SelectionEvent e) {
					if (SWT.CHECK == e.detail) {
						// => add or remove the element to the list of profiles to apply
						// 1. get item
						TableItem item = (TableItem) e.item;

						// 2. get data associated to this item = Registered profile
						IRegisteredProfile rProfile = null;
						if (item != null) {
							rProfile = (IRegisteredProfile) item.getData();
						}

						// 3. if not null, check if the profiel must be applied or not
						if (rProfile != null) {
							if (item.getChecked()) {
								// profile must be applied
								rProfilesToApply.add(rProfile);
							} else {
								rProfilesToApply.remove(rProfile);
							}
						}
					}
				}
			});

			// 2nd column with profile name
			TableColumn column = new TableColumn(table, SWT.LEFT, 0);
			column.setText("Profile");
			column.setWidth(400);

		}

		/**
		 * Fill table with table items
		 */
		private void fillTable() {
			// for each profile, proposes a line with a selection box.
			IRegisteredProfile[] rProfiles = Registry.getRegisteredProfiles().toArray(new IRegisteredProfile[0]);
			for (int i = 0; i < rProfiles.length; i++) {
				TableItem item = new TableItem(table, SWT.CHECK);
				IRegisteredProfile rProfile = rProfiles[i];
				item.setData(rProfile);
				item.setText(rProfile.getName());
				item.setChecked(rProfilesToApply.contains(rProfile));
			}
		}
	}

	protected void addProfile(String name, String path) {
		addProfile(name, path, "");
	}

	protected void addProfile(String name, String path, String qualifiedNames) {
		IRegisteredProfile profile = Registry.getRegisteredProfile(name, path);
		if (profile != null) {
			// get the profiles to be applied
			// retrieve sub profiles in the profile => should explore the profile and add elements that match the qualified names
			rProfilesToApply.add(profile);
		} else {
			org.eclipse.papyrus.uml.profile.Activator.getDefault().getLog().log(
					new Status(IStatus.ERROR, org.eclipse.papyrus.uml.profile.Activator.PLUGIN_ID, IStatus.ERROR, "impossible to find profile: " + name + " in: " + path, null));
		}
	}

}
