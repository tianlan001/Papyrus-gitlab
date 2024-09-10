/*****************************************************************************
 * Copyright (c) 2015, 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Pauline DEVILLE (CEA LIST) - Bug 493312 - [Wizard] Apply multiple profiles in new model wizard 
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.wizards.profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.uml.diagram.wizards.Activator;
import org.eclipse.papyrus.uml.diagram.wizards.messages.Messages;
import org.eclipse.papyrus.uml.diagram.wizards.widget.ExtensionFilter;
import org.eclipse.papyrus.uml.extensionpoints.profile.IRegisteredProfile;
import org.eclipse.papyrus.uml.extensionpoints.profile.RegisteredProfile;
import org.eclipse.papyrus.uml.extensionpoints.utils.Util;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.uml2.uml.Profile;

/**
 * @author Quentin Le Menez
 *
 */
public class ProfileChooserComposite extends Composite {

	/**
	 * @since 3.1
	 */
	public static final String PROFILE_SEPARATOR = "\n"; //$NON-NLS-1$

	private Text textField;

	private Button workspaceButton;

	private Button registeredButton;

	private LinkedList<ViewerFilter> filters;

	private ElementTreeSelectionDialog treeDialog;

	private boolean workspaceProfile;

	private String[] workspaceFilters = new String[] { "profile.uml" }; //$NON-NLS-1$

	private List<IFile> selectedFile;

	private List<Object> selectedProfile;

	/**
	 * Constructor.
	 *
	 */
	public ProfileChooserComposite(Composite parent) {
		super(parent, SWT.NONE);

		this.selectedFile = new ArrayList<>();
		this.selectedProfile = new ArrayList<>();

		this.setLayout(new GridLayout(2, true));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createProfileChooser(this);
	}

	/**
	 * Creates the Profile selection composite
	 * 
	 * @param parent
	 *            The parent composite
	 */
	private void createProfileChooser(Composite parent) {

		filters = new LinkedList<ViewerFilter>();
		setFilterExtensions(workspaceFilters);

		textField = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		GridData fieldGrid = new GridData(SWT.FILL, SWT.FILL, true, false);
		fieldGrid.verticalSpan = 10;
		fieldGrid.horizontalSpan = 2;
		textField.setLayoutData(fieldGrid);

		treeDialog = new ElementTreeSelectionDialog(getShell(), new ProfileChooserLabelProvider(), new ProfileChooserContentProvider());
		treeDialog.setInput(RegisteredProfile.getRegisteredProfiles());
		treeDialog.setAllowMultiple(true);
		treeDialog.setTitle(Messages.ProfileChooserComposite_RegisteredProfilesDialog);

		SelectionListener selectionListener = new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				refreshListFromTextField(); // since we can modified the text field we have to refresh list to display selection
				if (e.widget.equals(workspaceButton)) {
					IFile[] ifile = WorkspaceResourceDialog.openFileSelection(getShell(), null, null, true, selectedFile.toArray(), filters);

					if (ifile.length > 0) {
						selectedFile = new ArrayList<>(Arrays.asList(ifile));
						refreshTextField();
					}
				}
				if (e.widget.equals(registeredButton)) {
					treeDialog.setInitialSelections(selectedProfile.toArray());
					treeDialog.open();
					Object[] profiles = treeDialog.getResult();

					if (profiles != null) {
						selectedProfile = new ArrayList<>(Arrays.asList(profiles));
						refreshTextField();
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// nothing to do
			}

		};

		workspaceButton = new Button(parent, SWT.NONE);
		workspaceButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		workspaceButton.addSelectionListener(selectionListener);
		workspaceButton.setText(Messages.ProfileChooserComposite_WorkspaceSelectionButton);

		registeredButton = new Button(parent, SWT.NONE);
		registeredButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		registeredButton.addSelectionListener(selectionListener);
		registeredButton.setText(Messages.ProfileChooserComposite_RegisteredSelectionButton);


	}

	/**
	 * This method refresh selectedElement and selectedProfile according to the text field
	 * 
	 * @since 3.1
	 */
	private void refreshListFromTextField() {
		if (textField.isDisposed()) {
			return;
		}

		List<String> stringList = Arrays.asList(textField.getText().split(PROFILE_SEPARATOR));
		Iterator<IFile> fileIterator = selectedFile.iterator();
		while (fileIterator.hasNext()) {
			IFile file = fileIterator.next();
			if (!stringList.contains(file.getFullPath().toString().trim())) {
				fileIterator.remove();
			}
		}

		Iterator<Object> profileIterator = selectedProfile.iterator();
		while (profileIterator.hasNext()) {
			Object profile = profileIterator.next();
			if (profile instanceof IRegisteredProfile) {
				String profilePath = registeredProfileToString((IRegisteredProfile) profile);
				if (!stringList.contains(profilePath)) {
					profileIterator.remove();
				}
			}
		}
	}

	/**
	 * This method refresh the text field according to the content of selectedProfile and selectedFile.
	 * 
	 * @since 3.1
	 */
	private void refreshTextField() {
		StringBuilder builder = new StringBuilder();
		for (Object profile : selectedProfile) {
			if (profile instanceof IRegisteredProfile) {
				builder.append(registeredProfileToString(((IRegisteredProfile) profile)));
				builder.append(PROFILE_SEPARATOR);
			}
		}
		for (IFile file : selectedFile) {
			builder.append(fileProfileToString(file));
			builder.append(PROFILE_SEPARATOR);
		}
		// remove last profile separator
		if (!builder.toString().isEmpty()) {
			builder.delete(builder.length() - 1, builder.length());
		}
		textField.setText(builder.toString());
	}

	/**
	 * Check that the provided path matches against a known Profile and that it is defined.
	 * 
	 * @return
	 * 		The IStatus of the fetched profile.
	 */
	public IStatus getProfileDefinitionStatus() {
		ResourceSet resourceSet = Util.createTemporaryResourceSet();
		try {
			List<String> profilePath = getProfilesURI();
			if (profilePath.isEmpty()) {
				return Status.OK_STATUS;
			}

			for (String pp : profilePath) {
				URI profileURI = URI.createURI(pp);
				Resource resource = resourceSet.getResource(profileURI, true);

				if (resource == null) {
					return new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.ProfileChooserComposite_ProfileStatus_NullResource);
				}
				if (resource.getContents().isEmpty()) {
					return new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.ProfileChooserComposite_ProfileStatus_EmptyResource);
				}

				EObject eObject = resource.getContents().get(0);
				if (!(eObject instanceof Profile)) {
					return new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.ProfileChooserComposite_ProfileStatus_NotAProfile);
				}

				Profile profile = (Profile) eObject;
				if (!profile.isDefined()) {
					return new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.ProfileChooserComposite_ProfileStatus_ProfileNotDefined);
				}
			}
			return Status.OK_STATUS;

		} catch (WrappedException ex) {
			return new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.ProfileChooserComposite_ProfileStatus_UnavailableResource, ex);
		} finally {
			// Unload the created resourceSet to avoid memory leaks
			EMFHelper.unload(resourceSet);
		}
	}

	/**
	 * Gets the selected profile's path.
	 * 
	 * @return
	 * 		the selected profile's path.
	 * 
	 * @deprecated since 3.1
	 *             Use getProfilesURI() instead.
	 */
	public String getProfileURI() {
		if (textField.isDisposed()) {
			return null;
		}
		String path = textField.getText();
		if (path.trim().equals("")) { //$NON-NLS-1$
			return null;
		}

		if (workspaceProfile) {
			return path.trim();
		} else {
			return URI.createURI(path).toString();
		}
	}

	/**
	 * Gets selected profiles' path.
	 * 
	 * @return
	 * 		the list of selected profile's path.
	 * 
	 * @since 3.1
	 */
	public List<String> getProfilesURI() {
		List<String> result = new ArrayList<>();
		List<String> stringList = new ArrayList<>();

		if (textField.isDisposed()) {
			return result;
		}

		String path = textField.getText();
		if (path.trim().equals("")) { //$NON-NLS-1$
			return result;
		}

		stringList = Arrays.asList(path.split(PROFILE_SEPARATOR));
		for (String elt : stringList) {
			result.add(elt.trim());
		}

		return result;
	}

	/**
	 * Get the text representing the file profile.
	 * 
	 * @param file
	 *            The file.
	 * @return text to display.
	 * 
	 * @since 3.1
	 */
	protected String fileProfileToString(IFile file) {
		return file.getFullPath().toString();
	}

	/**
	 * Get the text representing the registered profile.
	 * 
	 * @param registeredProfile
	 *            The registered profile.
	 * @return text to display
	 * 
	 * @since 3.1
	 */
	protected String registeredProfileToString(IRegisteredProfile registeredProfile) {
		return registeredProfile.getPath().toString();
	}

	/**
	 * Sets the file extensions that this FileChooser accepts
	 * Files that don't match one of these extensions will be hidden
	 * 
	 * @param extensions
	 */
	public void setFilterExtensions(String[] extensions) {
		filters.clear();
		ExtensionFilter filter = new ExtensionFilter(extensions);
		filters.add(filter);
	}

	public Text getTextField() {
		return textField;
	}

	/**
	 * @see org.eclipse.swt.widgets.Widget#dispose()
	 *
	 */
	@Override
	public void dispose() {
		super.dispose();
		selectedFile.clear();
		selectedProfile.clear();
	}

}
