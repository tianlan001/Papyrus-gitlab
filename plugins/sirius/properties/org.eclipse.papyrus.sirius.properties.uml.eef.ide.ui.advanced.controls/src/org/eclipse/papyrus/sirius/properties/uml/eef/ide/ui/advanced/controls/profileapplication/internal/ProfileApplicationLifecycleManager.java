/*******************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.uml.eef.ide.ui.advanced.controls.profileapplication.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.eef.EEFWidgetDescription;
import org.eclipse.eef.common.ui.api.IEEFFormContainer;
import org.eclipse.eef.core.api.EditingContextAdapter;
import org.eclipse.eef.core.api.controllers.IEEFWidgetController;
import org.eclipse.eef.ide.ui.api.widgets.AbstractEEFWidgetLifecycleManager;
import org.eclipse.eef.ide.ui.ext.widgets.reference.internal.EEFExtReferenceUIPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFProfileApplicationDescription;
import org.eclipse.papyrus.sirius.properties.uml.eef.ide.ui.advanced.controls.Activator;
import org.eclipse.papyrus.uml.profile.ui.dialogs.ElementImportTreeSelectionDialog.ImportSpec;
import org.eclipse.papyrus.uml.profile.ui.dialogs.ProfileTreeSelectionDialog;
import org.eclipse.papyrus.uml.properties.profile.ui.dialogs.RegisteredProfileSelectionDialog;
import org.eclipse.papyrus.uml.tools.importsources.PackageImportSourceDialog;
import org.eclipse.papyrus.uml.tools.profile.definition.IPapyrusVersionConstants;
import org.eclipse.papyrus.uml.tools.profile.definition.Version;
import org.eclipse.papyrus.uml.tools.providers.UMLLabelProvider;
import org.eclipse.papyrus.uml.tools.utils.ProfileUtil;
import org.eclipse.sirius.common.interpreter.api.IInterpreter;
import org.eclipse.sirius.common.interpreter.api.IVariableManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;

/**
 * This lifecycle manager is used to handle the Profile Application Widget
 * to display {@link Profile}, {@link ProfileApplication} and manage them with Add/Remove buttons.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
@SuppressWarnings("restriction")
public class ProfileApplicationLifecycleManager extends AbstractEEFWidgetLifecycleManager {

	/**
	 * Title of the first table column.
	 */
	private static final String NAME_COLUMN_TITLE = "Name"; //$NON-NLS-1$

	/**
	 * Title of the second table column.
	 */
	private static final String LOCATION_COLUMN_TITLE = "Location"; //$NON-NLS-1$


	/**
	 * Title of the third table column.
	 */
	private static final String VERSION_COLUMN_TITLE = "Version"; //$NON-NLS-1$

	/**
	 * Tooltip of the "Apply profile" button.
	 */
	private static final String APPLY_BUTTON_TOOLTIP = "Apply profile"; //$NON-NLS-1$

	/**
	 * Tooltip of the "Remove" button.
	 */
	private static final String REMOVE_BUTTON_TOOLTIP = "Remove selected elements"; //$NON-NLS-1$

	/**
	 * Tooltip of the "Apply registered profiles" button.
	 */
	private static final String APPLY_REGISTERED_BUTTON_TOOLTIP = "Apply registered profiles"; //$NON-NLS-1$

	/**
	 * Tooltip of the "Reapply profile" button.
	 */
	private static final String REAPPLY_BUTTON_TOOLTIP = "Reapply profile"; //$NON-NLS-1$

	/**
	 * The name of the EAnnotation used to retrieve the version of a profile.
	 */
	private static final String PAPYRUS_VERSION_EANNOTATION = IPapyrusVersionConstants.PAPYRUS_EANNOTATION_SOURCE;

	/**
	 * The minimum width of the button.
	 */
	private static final int MINIMUM_BUTTON_WIDTH = 80;

	/**
	 * The description of the profile application.
	 */
	private EEFProfileApplicationDescription description;

	/**
	 * The main object managed by the widget.
	 */
	private Package target;

	/**
	 * The widget controller.
	 */
	private ProfileApplicationController controller;

	/**
	 * The main composite which contains the table and buttons.
	 */
	private Composite mainComposite;

	/**
	 * The table of the widget used to display all managed profiles.
	 */
	private TableViewer tableViewer;

	/**
	 * The "Apply profile" button used to add profiles existing in the workspace to the table.
	 */
	private Button applyProfileButton;

	/**
	 * The "Remove" button used to unapply selected profiles and remove them from the table.
	 */
	private Button removeButton;

	/**
	 * The "Apply registered profile" button used to add profiles existing in plugin resources to the table.
	 */
	private Button applyRegisteredProfileButton;

	/**
	 * The "Reapply" button used to refresh and reapply profiles.
	 */
	private Button reapplyButton;

	/**
	 * The selection listener for the table viewer.
	 */
	private ISelectionChangedListener tableSelectionChangedListener;

	/**
	 * The listener for the "Apply profile" button.
	 */
	private ButtonSelectionListener applyProfileButtonListener;

	/**
	 * The listener for the "Remove" button.
	 */
	private ButtonSelectionListener removeButtonListener;

	/**
	 * The listener for the "Apply registered profiles" button.
	 */
	private ButtonSelectionListener applyRegisteredProfileButtonListener;

	/**
	 * The listener for the "Reapply profile" button.
	 */
	private ButtonSelectionListener reapplyButtonListener;

	/**
	 * 
	 * The constructor.
	 *
	 * @param controlDescription
	 *            the description of the profile application
	 * @param target
	 *            the object managed by the widget
	 * @param variableManagerthe
	 *            variable manager which contain variables
	 *            used by Interpreter to evaluate AQL expression
	 * @param interpreter
	 *            the interpreter to evaluate AQL expressions
	 * @param editingContextAdapter
	 *            the adapter used to modify model elements
	 */
	public ProfileApplicationLifecycleManager(EEFProfileApplicationDescription controlDescription, Package target, IVariableManager variableManager, IInterpreter interpreter, EditingContextAdapter editingContextAdapter) {
		super(variableManager, interpreter, editingContextAdapter);
		this.description = controlDescription;
		this.target = target;
	}

	@Override
	protected void createMainControl(Composite parent, IEEFFormContainer formContainer) {
		this.mainComposite = new Composite(parent, SWT.NONE);
		GridLayout mainCompositeLayout = new GridLayout(2, false);
		this.mainComposite.setLayout(mainCompositeLayout);
		GridData mainCompositeLayoutData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.mainComposite.setLayoutData(mainCompositeLayoutData);

		this.createTable(this.mainComposite);

		Composite buttonsComposite = this.createButtons(this.mainComposite);
		Point computedSize = buttonsComposite.computeSize(-1, -1);
		GridData tableGridData = (GridData) tableViewer.getTable().getLayoutData();
		tableGridData.heightHint = computedSize.y - tableViewer.getTable().getItemHeight();

		this.controller = new ProfileApplicationController(description, variableManager, interpreter, editingContextAdapter);
	}

	/**
	 * Create the table with "Name", "Location", and "Version" columns.
	 * 
	 * @param parent
	 *            the parent composite
	 */
	protected void createTable(Composite parent) {
		int style = SWT.READ_ONLY | SWT.VIRTUAL;
		int selectionStyle = SWT.FULL_SELECTION | SWT.SINGLE;
		int tableStyle = SWT.V_SCROLL | SWT.BORDER;
		style = style | selectionStyle | tableStyle;
		Table table = new Table(parent, style);
		GridData tableGridData = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		tableGridData.widthHint = 150;
		table.setLayoutData(tableGridData);
		this.tableViewer = new TableViewer(table);
		TableLayout tableLayout = new TableLayout(true);
		table.setLayout(tableLayout);
		table.setHeaderVisible(true);
		table.setHeaderBackground(Display.getCurrent().getSystemColor(SWT.COLOR_LIST_BACKGROUND));

		new TableColumn(table, SWT.NONE).setText(NAME_COLUMN_TITLE);
		tableLayout.addColumnData(new ColumnWeightData(20, 150, true));

		new TableColumn(table, SWT.NONE).setText(LOCATION_COLUMN_TITLE);
		tableLayout.addColumnData(new ColumnWeightData(30, 400, true));

		new TableColumn(table, SWT.NONE).setText(VERSION_COLUMN_TITLE);
		tableLayout.addColumnData(new ColumnWeightData(10, 50, true));

		this.tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		this.tableViewer.setLabelProvider(new ProfileColumnsLabelProvider(new UMLLabelProvider()));
		this.tableViewer.setInput(getInput());

	}

	/**
	 * The input displays in the table should contains only applied Profiles, but externalized
	 * Profiles as profile application should be searched through all profiles and added independently.
	 * 
	 * @return the set of profiles to display in the table
	 */
	private Set<Profile> getInput() {
		Set<Profile> profiles = new HashSet<>(this.target.getAppliedProfiles());
		for (Profile p : target.getAllAppliedProfiles()) {
			if (null != target.getProfileApplication(p, true)) {
				profiles.add(p);
			}
		}
		return profiles;
	}

	/**
	 * Create the buttons composite and layout buttons.
	 * 
	 * @param parent
	 *            the parent composite
	 * @return the buttons composite
	 */
	protected Composite createButtons(Composite parent) {
		Composite buttonsComposite = new Composite(parent, SWT.NONE);
		GridLayout buttonCompositeGridLayout = new GridLayout(1, false);
		buttonCompositeGridLayout.marginHeight = 0;
		buttonsComposite.setLayout(buttonCompositeGridLayout);
		GridData buttonCompositeGridData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		buttonsComposite.setLayoutData(buttonCompositeGridData);

		Image addImage = ExtendedImageRegistry.INSTANCE.getImage(
				EEFExtReferenceUIPlugin.getPlugin().getImage(EEFExtReferenceUIPlugin.Implementation.ADD_ICON_PATH));
		Image removeImage = ExtendedImageRegistry.INSTANCE.getImage(
				EEFExtReferenceUIPlugin.getPlugin().getImage(EEFExtReferenceUIPlugin.Implementation.REMOVE_ICON_PATH));
		Image applyRegisteredImage = ExtendedImageRegistry.INSTANCE.getImage(Activator.getDefault().getImage(Activator.ADD_REGISTERED_ICON));
		Image reaplyImage = ExtendedImageRegistry.INSTANCE.getImage(Activator.getDefault().getImage(Activator.REFRESH_ICON));

		this.applyProfileButton = this.createButton(buttonsComposite, addImage);
		this.removeButton = this.createButton(buttonsComposite, removeImage);
		this.applyRegisteredProfileButton = this.createButton(buttonsComposite, applyRegisteredImage);
		this.reapplyButton = this.createButton(buttonsComposite, reaplyImage);

		return buttonsComposite;
	}

	@Override
	protected IEEFWidgetController getController() {
		return this.controller;
	}

	@Override
	protected EEFWidgetDescription getWidgetDescription() {
		return this.description;
	}

	@Override
	public void aboutToBeShown() {
		super.aboutToBeShown();

		this.tableSelectionChangedListener = new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (tableViewer.getSelection() instanceof IStructuredSelection) {
					refresh();
				}
			}
		};
		this.tableViewer.addSelectionChangedListener(tableSelectionChangedListener);

		this.initializeApplyProfileButton();
		this.initializeRemoveButton();
		this.initializeApplyRegisteredProfileButton();
		this.initializeReapplyButton();
	}

	/**
	 * Initialize the "Apply profile" button with listener and tooltip.
	 */
	protected void initializeApplyProfileButton() {
		this.applyProfileButtonListener = new ButtonSelectionListener(this.editingContextAdapter, () -> this.applyProfileButtonCallback());
		this.applyProfileButton.addSelectionListener(applyProfileButtonListener);
		this.applyProfileButton.setToolTipText(APPLY_BUTTON_TOOLTIP);
	}

	/**
	 * Invoked when clicking the "Apply profile" button.
	 */
	private void applyProfileButtonCallback() {
		Map<String, String> extensionFilters = new LinkedHashMap<>();
		extensionFilters.put("*.profile.uml", "UML Profiles (*.profile.uml)"); //$NON-NLS-1$ //$NON-NLS-2$
		extensionFilters.put("*.uml", "UML (*.uml)"); //$NON-NLS-1$ //$NON-NLS-2$
		extensionFilters.put("*", "All (*)"); //$NON-NLS-1$ //$NON-NLS-2$
		Collection<Package> packages = PackageImportSourceDialog.open(Display.getCurrent().getActiveShell(), "Apply Profiles...", Collections.singletonList(this.target), extensionFilters); //$NON-NLS-1$
		// If nothing is selected : abort
		if ((packages == null) || packages.isEmpty()) {
			return;
		}

		if (packages.size() > 0) {
			final Collection<Profile> profilesToApply = new LinkedList<>();
			final Profile onlyOneProfile = ProfileUtil.getTheOnlyOneProfile(packages);
			if (null == onlyOneProfile) {
				ProfileTreeSelectionDialog profileDialog = new ProfileTreeSelectionDialog(Display.getCurrent().getActiveShell(), packages);
				if (profileDialog.open() != Window.OK) {
					throw new OperationCanceledException();
				}
				Collection<ImportSpec<Profile>> profilesImportToApply = profileDialog.getResult();
				for (ImportSpec<Profile> importProfile : profilesImportToApply) {
					profilesToApply.add(importProfile.getElement());
				}
			} else {
				profilesToApply.add(onlyOneProfile);
			}

			applyProfiles(profilesToApply);
		}
		refresh();
	}

	/**
	 * Apply all profiles that have not already been applied.
	 * 
	 * @param profilesToApply
	 *            the list of profiles to apply if they have not already been applied.
	 */
	private void applyProfiles(final Collection<Profile> profilesToApply) {
		EList<Profile> appliedProfiles = this.target.getAppliedProfiles();
		for (Profile profile : profilesToApply) {
			if (!appliedProfiles.contains(profile)) {
				this.target.applyProfile(profile);
			}
		}
	}

	/**
	 * Initialize the "Remove" button with listener and tooltip.
	 */
	protected void initializeRemoveButton() {
		this.removeButtonListener = new ButtonSelectionListener(this.editingContextAdapter, () -> this.removeButtonCallback());
		this.removeButton.addSelectionListener(removeButtonListener);
		this.removeButton.setToolTipText(REMOVE_BUTTON_TOOLTIP);
	}

	/**
	 * Invoked when clicking the "Remove" button.
	 */
	private void removeButtonCallback() {
		List<Profile> selectedProfiles = selectionToList(this.tableViewer.getSelection());
		unapplyProfiles(selectedProfiles);
		refresh();
	}

	/**
	 * Unapply all specified profiles.
	 * 
	 * @param profilesToUnapply
	 *            the list of profiles to unapply
	 */
	private void unapplyProfiles(List<Profile> profilesToUnapply) {
		for (Profile profileToRemove : profilesToUnapply) {
			this.target.unapplyProfile(profileToRemove);
		}
	}

	/**
	 * Initialize the "Apply registered profile" button with listener and tooltip.
	 */
	protected void initializeApplyRegisteredProfileButton() {
		this.applyRegisteredProfileButtonListener = new ButtonSelectionListener(this.editingContextAdapter, () -> this.applyRegisteredProfileButtonCallback());
		this.applyRegisteredProfileButton.addSelectionListener(applyRegisteredProfileButtonListener);
		this.applyRegisteredProfileButton.setToolTipText(APPLY_REGISTERED_BUTTON_TOOLTIP);
	}

	/**
	 * Invoked when clicking the "Apply registered profile" button.
	 */
	private void applyRegisteredProfileButtonCallback() {
		RegisteredProfileSelectionDialog profileSelectionDialog = new RegisteredProfileSelectionDialog(Display.getCurrent().getActiveShell(), this.target);
		List<Profile> profilesToApply = profileSelectionDialog.run();
		applyProfiles(profilesToApply);
		refresh();
	}

	/**
	 * Initialize the "Reapply profile" button with listener and tooltip.
	 */
	protected void initializeReapplyButton() {
		this.reapplyButtonListener = new ButtonSelectionListener(this.editingContextAdapter, () -> this.reapplyButtonCallback());
		this.reapplyButton.addSelectionListener(reapplyButtonListener);
		this.reapplyButton.setToolTipText(REAPPLY_BUTTON_TOOLTIP);
	}

	/**
	 * Invoked when clicking the "Reapply profile" button.
	 */
	private void reapplyButtonCallback() {
		List<Profile> profilesToApply = selectionToList(this.tableViewer.getSelection());
		unapplyProfiles(profilesToApply);
		applyProfiles(profilesToApply);
		refresh();
	}

	private List<ProfileApplication> getSelectedProfileApplications() {
		List<Profile> selectionToList = selectionToList(this.tableViewer.getSelection());
		List<ProfileApplication> profileApplications = new ArrayList<>();
		for (Profile selectedProfile : selectionToList) {
			ProfileApplication profileApplication = this.target.getProfileApplication(selectedProfile, true);
			if (profileApplication != null) {
				profileApplications.add(profileApplication);
			}
		}
		return profileApplications;
	}

	@Override
	public void aboutToBeHidden() {
		super.aboutToBeHidden();
		if (this.tableViewer != null) {
			this.tableViewer.removeSelectionChangedListener(this.tableSelectionChangedListener);
		}
		this.removeListener(this.applyProfileButton, this.applyProfileButtonListener);
		this.removeListener(this.removeButton, this.removeButtonListener);
		this.removeListener(this.applyRegisteredProfileButton, this.applyRegisteredProfileButtonListener);
		this.removeListener(this.reapplyButton, this.reapplyButtonListener);
	}

	/**
	 * Removes the given listener from the given button.
	 *
	 * @param button
	 *            The button
	 * @param listener
	 *            The listener to remove
	 */
	protected void removeListener(Button button, ButtonSelectionListener listener) {
		if (button != null && !button.isDisposed()) {
			button.removeSelectionListener(listener);
		}
	}

	@Override
	protected void setEnabled(boolean isEnabled) {
		Table table = this.tableViewer.getTable();
		TableItem[] selection = table.getSelection();
		boolean elementIsSelected = selection != null && selection.length > 0;


		if (this.applyProfileButton != null && !this.applyProfileButton.isDisposed()) {
			this.applyProfileButton.setEnabled(isEnabled);
		}
		if (this.applyRegisteredProfileButton != null && !this.applyRegisteredProfileButton.isDisposed()) {
			this.applyRegisteredProfileButton.setEnabled(isEnabled);
		}
		if (this.removeButton != null && !this.removeButton.isDisposed()) {
			if (elementIsSelected) {
				List<ProfileApplication> profileApplications = getSelectedProfileApplications();
				boolean allExternalized = false;
				for (ProfileApplication next : profileApplications) {
					allExternalized = next.getApplyingPackage() == this.target;
					if (!allExternalized) {
						break;
					}
				}
				this.removeButton.setEnabled(isEnabled && allExternalized);
			} else {
				this.removeButton.setEnabled(false);
			}
		}
		if (this.reapplyButton != null && !this.reapplyButton.isDisposed()) {
			if (elementIsSelected) {
				boolean enableReapply = false;
				for (Profile selectedProfile : selectionToList(this.tableViewer.getSelection())) {
					if (ProfileUtil.isDirty(this.target, selectedProfile)) {
						enableReapply = true;
						break;
					}
				}
				this.reapplyButton.setEnabled(enableReapply && isEnabled);
			} else {
				this.reapplyButton.setEnabled(false);
			}
		}
	}

	@Override
	public void refresh() {
		super.refresh();
		if (!this.tableViewer.getTable().isDisposed()) {
			this.tableViewer.setInput(getInput());
			this.tableViewer.refresh();
		}
		this.controller.refresh();

		setEnabled(isEnabled());
	}

	@Override
	protected Control getValidationControl() {
		return this.mainComposite;
	}

	/**
	 * Returns a list containing all the objects of the given selection.
	 *
	 * @param selection
	 *            The selection
	 * @return The objects of the given selection
	 */
	private List<Profile> selectionToList(ISelection selection) {
		List<Profile> profiles = new ArrayList<>();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			for (Object object : structuredSelection.toArray()) {
				if (object instanceof Profile) {
					profiles.add((Profile) object);
				}
			}
		}
		return profiles;
	}

	/**
	 * Creates a button used to manage the language feature.
	 * 
	 * @param parent
	 *            the parent composite
	 * @param image
	 *            the image of the button
	 * @return the created button
	 */
	protected Button createButton(Composite parent, Image image) {
		Button button = new Button(parent, SWT.NONE);
		button.setImage(image);

		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.minimumWidth = MINIMUM_BUTTON_WIDTH;
		button.setLayoutData(gridData);

		return button;
	}

	/**
	 * Copied from
	 * org.eclipse.eef.ide.ui.ext.widgets.reference.internal.AbstractEEFExtReferenceLifecycleManager.ButtonSelectionListener.
	 * Utility class used to encapsulate the selection listener.
	 *
	 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
	 */
	protected static class ButtonSelectionListener implements SelectionListener {

		/**
		 * The context adapter.
		 */
		private EditingContextAdapter editingContextAdapter;

		/**
		 * The behavior to execute when the button is clicked.
		 */
		private Runnable runnable;

		/**
		 * The constructor.
		 *
		 * @param editingContextAdapter
		 *            The {@link EditingContextAdapter}
		 * @param runnable
		 *            The behavior to execute when the button is
		 *            clicked
		 */
		public ButtonSelectionListener(EditingContextAdapter editingContextAdapter, Runnable runnable) {
			this.editingContextAdapter = editingContextAdapter;
			this.runnable = runnable;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent event) {
			this.editingContextAdapter.performModelChange(this.runnable);
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetDefaultSelected(SelectionEvent event) {
			this.editingContextAdapter.performModelChange(this.runnable);
		}
	}

	/**
	 * Copied from org.eclipse.papyrus.uml.properties.widgets.ProfileApplicationEditor.ProfileColumnsLabelProvider.
	 */
	protected class ProfileColumnsLabelProvider extends StyledCellLabelProvider {

		private ILabelProvider defaultLabelProvider;
		private IStyledLabelProvider styledLabelProvider;

		public ProfileColumnsLabelProvider(IBaseLabelProvider defaultLabelProvider) {
			if (defaultLabelProvider instanceof ILabelProvider) {
				this.defaultLabelProvider = (ILabelProvider) defaultLabelProvider;
			}
			if (defaultLabelProvider instanceof IStyledLabelProvider) {
				this.styledLabelProvider = (IStyledLabelProvider) defaultLabelProvider;
			}
		}

		@Override
		public void update(ViewerCell cell) {
			if (cell.getColumnIndex() == 0) {
				updateName(cell);
				return;
			}

			Profile profile;
			if ((EObject) cell.getElement() instanceof Profile) {
				profile = (Profile) (EObject) cell.getElement();
			} else {
				cell.setText(""); //$NON-NLS-1$
				return;
			}

			switch (cell.getColumnIndex()) {
			case 1:
				updateLocation(cell, profile);
				break;
			case 2:
				updateVersion(cell, profile);
				break;
			default:
				break;
			}

		}

		public void updateName(ViewerCell cell) {
			cell.setImage(getImage(cell.getElement()));

			StyledString styledText = getStyledText(cell.getElement());
			cell.setText(styledText.getString());
			cell.setStyleRanges(styledText.getStyleRanges());
		}

		public void updateLocation(ViewerCell cell, Profile profile) {
			String location = "Unknown"; //$NON-NLS-1$
			if (profile.eIsProxy()) {
				location = EcoreUtil.getURI(profile).trimFragment().toString();
			} else if (profile.eResource() != null) {
				URI uri = profile.eResource().getURI();
				if (uri != null) {
					location = uri.toString();
				}
			}

			cell.setText(location);
		}

		public void updateVersion(ViewerCell cell, Profile profile) {
			String versionText = getProfileVersion(profile);
			cell.setText(versionText);
		}

		private String getProfileVersion(Profile profile) {
			Version version = Version.emptyVersion;
			if (profile != null) {
				EAnnotation eAnnotation = profile.getDefinition().getEAnnotation(PAPYRUS_VERSION_EANNOTATION);
				if (eAnnotation != null) {
					String detailsVersion = eAnnotation.getDetails().get(VERSION_COLUMN_TITLE);
					version = new Version(detailsVersion);
				}
			}
			return version.toString();
		}

		public Image getImage(Object element) {
			Image image = null;
			if (defaultLabelProvider != null) {
				image = defaultLabelProvider.getImage(element);
			}
			return image;
		}

		public StyledString getStyledText(Object element) {
			StyledString result;
			if (styledLabelProvider != null) {
				result = styledLabelProvider.getStyledText(element);
			} else {
				String string;
				if (defaultLabelProvider != null) {
					string = defaultLabelProvider.getText(element);
				} else {
					string = ""; //$NON-NLS-1$
				}
				result = new StyledString(string);
			}
			ProfileApplication application = null;
			if (element instanceof Profile) {
				application = target.getProfileApplication((Profile) element, true);
			} else if (element instanceof ProfileApplication) {
				application = (ProfileApplication) element;
			}

			if (application != null && application.getApplyingPackage() != target) {
				String qualifier = " (profile application)"; //$NON-NLS-1$
				result.append(qualifier, StyledString.DECORATIONS_STYLER);
			}

			return result;
		}
	}
}
