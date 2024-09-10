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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.customization.nattableconfiguration.edition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.customization.nattableconfiguration.utils.NattableConfigurationConstants;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.PasteEObjectConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.StringComparator;
import org.eclipse.papyrus.infra.services.edit.utils.ElementTypeUtils;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.services.labelprovider.service.impl.LabelProviderServiceImpl;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.editors.ITreeSelectorDialog;
import org.eclipse.papyrus.infra.widgets.editors.TreeSelectorDialog;
import org.eclipse.papyrus.infra.widgets.providers.CompoundFilteredRestrictedContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.FlattenableRestrictedFilteredContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IRestrictedContentProvider;
import org.eclipse.papyrus.infra.widgets.selectors.ReferenceSelector;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.SelectionDialog;

/**
 * The dialog which allow to edit the paste configuration features.
 */
public class PasteEObjectConfigurationDialog extends SelectionDialog {

	/**
	 * A copy of initial paste configuration to modify with this dialog.
	 */
	protected final PasteEObjectConfiguration modifiedPasteConfiguration;

	/**
	 * The label provider for the UML elements.
	 */
	protected final ILabelProvider labelProvider;

	/**
	 * The CLabel composite for the containment feature.
	 */
	protected CLabel containmentFeatureText;

	/**
	 * The browse button which allow to edit the containment feature.
	 */
	protected Button browseValuesButton;

	/**
	 * The delete button which allow to unset the containment feature value.
	 */
	protected Button deleteValuesButton;

	/**
	 * Constructor.
	 *
	 * @param parentShell
	 *            The parent shell of the dialog.
	 * @param pasteConfiguration
	 *            The initial paste configuration to edit.
	 */
	public PasteEObjectConfigurationDialog(final Shell parentShell, final PasteEObjectConfiguration pasteConfiguration) {
		super(parentShell);
		this.modifiedPasteConfiguration = EcoreUtil.copy(pasteConfiguration);
		setTitle(org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages.PasteEObjectConfigurationDialog_pasteEObjectConfigurationDialogName);
		
		LabelProviderService labelProviderService = new LabelProviderServiceImpl();
		try {
			labelProviderService.startService();
		} catch (ServiceException ex) {
			Activator.log.error(ex);
		}

		labelProvider = labelProviderService.getLabelProvider();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	@Override
	public void create() {
		super.create();
		getShell().setImage(Activator.getDefault().getImage(NattableConfigurationConstants.PAPYRUS_ICON_PATH));

		final Composite parent = new Composite((Composite) getDialogArea(), SWT.NONE);
		parent.setLayout(new GridLayout(4, false));
		parent.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		getShell().setSize(600, 400);

		// Create the fields
		createFields(parent);

		getShell().pack();
		Point size = getShell().getSize();
		int minWidth = 600;
		if (size.x < minWidth) {
			size.x = minWidth;
		}
		getShell().setSize(size);
	}

	/**
	 * This allows to create the fields to edit the paste configuration.
	 * 
	 * @param parent
	 *            The parent composite.
	 */
	protected void createFields(final Composite parent) {
		// Create the detached mode checkbox
		final Label detachedModeLabel = new Label(parent, SWT.NONE);
		detachedModeLabel.setText(org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages.PasteEObjectConfigurationDialog_detachedModeLabel);
		detachedModeLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		final Button detachedModeButton = new Button(parent, SWT.CHECK);
		detachedModeButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 3, 1));
		detachedModeButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				modifiedPasteConfiguration.setDetachedMode(detachedModeButton.getSelection());
			}
		});
		detachedModeButton.setSelection(modifiedPasteConfiguration.isDetachedMode());

		// Create the pasted element id combo box
		final Label pastedElementIdLabel = new Label(parent, SWT.NONE);
		pastedElementIdLabel.setText(org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages.PasteEObjectConfigurationDialog_pastedElementIdLabel);
		pastedElementIdLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		final Combo pastedElementIdCombo = new Combo(parent, SWT.READ_ONLY);
		pastedElementIdCombo.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 3, 1));
		pastedElementIdCombo.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				modifiedPasteConfiguration.setPastedElementId(pastedElementIdCombo.getText());
			}
		});

		final Collection<IElementType> possibleValues = ElementTypeUtils.getAllExistingElementTypes();
		final List<String> possibleValuesAsString = new ArrayList<String>(possibleValues.size());

		int indexToSelect = -1;
		int index = 0;

		for (final IElementType elementType : possibleValues) {
			possibleValuesAsString.add(elementType.getDisplayName());
			if (elementType.getDisplayName().equals(modifiedPasteConfiguration.getPastedElementId())) {
				indexToSelect = index;
			}
			index++;
		}

		pastedElementIdCombo.setItems((String[]) possibleValuesAsString.toArray(new String[possibleValuesAsString.size()]));
		if (-1 != indexToSelect) {
			pastedElementIdCombo.select(indexToSelect);
		}

		// Create the containment feature labels and dialog
		final Label containmentFeatureLabel = new Label(parent, SWT.NONE);
		containmentFeatureLabel.setText(org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages.PasteEObjectConfigurationDialog_containmentFeatureLabel);
		containmentFeatureLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		containmentFeatureText = new CLabel(parent, SWT.BORDER);
		containmentFeatureText.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		browseValuesButton = new Button(parent, SWT.PUSH);
		browseValuesButton.setImage(Activator.getDefault().getImage(NattableConfigurationConstants.BROWSE_ICON_PATH));
		browseValuesButton.setToolTipText(org.eclipse.papyrus.infra.widgets.messages.Messages.ReferenceDialog_EditValue);
		browseValuesButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				browseAction(modifiedPasteConfiguration.getPasteElementContainementFeature());
			}
		});

		deleteValuesButton = new Button(parent, SWT.PUSH);
		deleteValuesButton.setImage(Activator.getDefault().getImage(NattableConfigurationConstants.DELETE_ICON_PATH));
		deleteValuesButton.setToolTipText(org.eclipse.papyrus.infra.widgets.messages.Messages.ReferenceDialog_UnsetValue);
		deleteValuesButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				setPasteElementContainementFeature(null);
			}
		});

		setPasteElementContainementFeature(modifiedPasteConfiguration.getPasteElementContainementFeature());

		// TODO : Post actions
	}

	/**
	 * The action executed when the "browse" button is selected Choose a value
	 * from a selection of already created objects.
	 * 
	 * @param value
	 *            The initial value of the containment feature.
	 */
	protected void browseAction(final Object value) {
		ITreeSelectorDialog dialog = new TreeSelectorDialog(getShell()) {
			@Override
			protected void initViewerAndProvider() {
				super.initViewerAndProvider();
				// Set a comparator to sort the tree viewer
				getViewer().setComparator(new ViewerComparator(new StringComparator()));// should always be string element
			}
		};
		dialog.setTitle(org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages.PasteEObjectConfigurationDialog_containmentFeatureDialogName);

		final CompoundFilteredRestrictedContentProvider contentProvider = new CompoundFilteredRestrictedContentProvider();
		final ReferenceSelector selector = new ReferenceSelector(false) {

			@Override
			public void createControls(final Composite parent) {
				super.createControls(parent);
				this.treeViewer.setComparator(new ViewerComparator(new StringComparator()));// should always be string element
			}
		};

		selector.setLabelProvider(labelProvider);
		selector.setContentProvider(contentProvider);
		final ITreeContentProvider treeContentProvider = new FlattenableRestrictedFilteredContentProvider((IRestrictedContentProvider) contentProvider, selector) {

			@Override
			public boolean isValidValue(final Object element) {
				// EMF dependency, must not be done here, it should be better with a new content provider service
				return element instanceof EReference && ((EReference) element).isMany() && element != EcorePackage.eINSTANCE.getEModelElement_EAnnotations();
			}
		};

		dialog.setContentProvider(treeContentProvider);
		dialog.setLabelProvider(labelProvider);
		if (null != value) {
			dialog.setInitialElementSelections(Collections.singletonList(value));
		}
		int result = dialog.open();
		if (Window.OK == result) {
			Object[] newValue = dialog.getResult();
			if (null == newValue) {
				return;
			}

			if (0 == newValue.length) {
				setPasteElementContainementFeature(null);
			} else {
				Object selectedValue = newValue[0];
				setPasteElementContainementFeature((EStructuralFeature) selectedValue);
			}
		}
	}

	/**
	 * This allows to set the containment feature and refresh the button corresponding to the containment feature value.
	 * 
	 * @param structuralFeature
	 *            The structural feature to set.
	 */
	protected void setPasteElementContainementFeature(final EStructuralFeature structuralFeature) {
		if (null == modifiedPasteConfiguration.getPasteElementContainementFeature() || !structuralFeature.equals(modifiedPasteConfiguration.getPasteElementContainementFeature())) {
			modifiedPasteConfiguration.setPasteElementContainementFeature(structuralFeature);
		}
		containmentFeatureText.setImage(labelProvider.getImage(modifiedPasteConfiguration.getPasteElementContainementFeature()));
		containmentFeatureText.setText(labelProvider.getText(modifiedPasteConfiguration.getPasteElementContainementFeature()));

		deleteValuesButton.setEnabled(null != structuralFeature);
	}

	/**
	 * Returns the modified paste configuration.
	 * 
	 * @return The modified paste configuration.
	 */
	public PasteEObjectConfiguration getModifiedPasteEObjectConfiguration() {
		return modifiedPasteConfiguration;
	}

}
