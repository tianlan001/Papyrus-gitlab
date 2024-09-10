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

import java.util.Collections;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages;
import org.eclipse.papyrus.customization.nattableconfiguration.utils.NattableConfigurationConstants;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.EStructuralFeatureValueFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.StringComparator;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.editors.ITreeSelectorDialog;
import org.eclipse.papyrus.infra.widgets.editors.TreeSelectorDialog;
import org.eclipse.papyrus.infra.widgets.providers.CompoundFilteredRestrictedContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.FlattenableRestrictedFilteredContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IRestrictedContentProvider;
import org.eclipse.papyrus.infra.widgets.selectors.ReferenceSelector;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.services.labelprovider.service.impl.LabelProviderServiceImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.SelectionDialog;

/**
 * The dialog which allow to edit the feature filling configuration features.
 */
public class FeatureFillingConfigurationDialog extends SelectionDialog {

	/**
	 * A copy of initial feature filling configuration to modify with this dialog.
	 */
	protected final EStructuralFeatureValueFillingConfiguration modifiedFeatureFillingConf;

	/**
	 * The label provider for the UML elements.
	 */
	protected final ILabelProvider labelProvider;

	/**
	 * The CLabel composite for the listen feature.
	 */
	protected CLabel listenFeatureText;

	/**
	 * The browse button which allow to edit the listen feature.
	 */
	protected Button browseValuesButton;

	/**
	 * The delete button which allow to unset the listen feature value.
	 */
	protected Button deleteValuesButton;
	
	
	/**
	 * Constructor.
	 *
	 * @param parentShell
	 *            The parent shell of the dialog.
	 * @param featureFillingConf
	 *            The initial feature filling configuration to edit.
	 */
	public FeatureFillingConfigurationDialog(final Shell parentShell, final EStructuralFeatureValueFillingConfiguration featureFillingConf) {
		super(parentShell);
		this.modifiedFeatureFillingConf = EcoreUtil.copy(featureFillingConf);
		setTitle(org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages.FeatureFillingConfigurationDialog_featureFillingConfigurationDialogName);
		
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
	 * This allows to create the fields to edit the feature filling configuration.
	 * 
	 * @param parent
	 *            The parent composite.
	 */
	protected void createFields(final Composite parent) {
		// Create the listen feature labels and dialog
		final Label listenFeatureLabel = new Label(parent, SWT.NONE);
		listenFeatureLabel.setText(Messages.FeatureFillingConfigurationDialog_listenFeature);
		listenFeatureLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		listenFeatureText = new CLabel(parent, SWT.BORDER);
		listenFeatureText.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		browseValuesButton = new Button(parent, SWT.PUSH);
		browseValuesButton.setImage(Activator.getDefault().getImage(NattableConfigurationConstants.BROWSE_ICON_PATH));
		browseValuesButton.setToolTipText(org.eclipse.papyrus.infra.widgets.messages.Messages.ReferenceDialog_EditValue);
		browseValuesButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				browseAction(modifiedFeatureFillingConf.getListenFeature());
			}
		});

		deleteValuesButton = new Button(parent, SWT.PUSH);
		deleteValuesButton.setImage(Activator.getDefault().getImage(NattableConfigurationConstants.DELETE_ICON_PATH));
		deleteValuesButton.setToolTipText(org.eclipse.papyrus.infra.widgets.messages.Messages.ReferenceDialog_UnsetValue);
		deleteValuesButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				setListenFeature(null);
			}
		});

		setListenFeature(modifiedFeatureFillingConf.getListenFeature());
	}

	/**
	 * The action executed when the "browse" button is selected Choose a value
	 * from a selection of already created objects.
	 * 
	 * @param value
	 *            The initial value of the listen feature.
	 */
	protected void browseAction(final Object value) {
		final ITreeSelectorDialog dialog = new TreeSelectorDialog(getShell()) {
			@Override
			protected void initViewerAndProvider() {
				super.initViewerAndProvider();
				// Set a comparator to sort the tree viewer
				getViewer().setComparator(new ViewerComparator(new StringComparator()));// should always be string element
			}
		};
		dialog.setTitle(org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages.FeatureFillingConfigurationDialog_listenFeatureDialogName);

		final CompoundFilteredRestrictedContentProvider contentProvider = new CompoundFilteredRestrictedContentProvider();
		final ReferenceSelector selector = new ReferenceSelector(false) {

			@Override
			public void createControls(Composite parent) {
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
				setListenFeature(null);
			} else {
				setListenFeature((EStructuralFeature) newValue[0]);
			}
		}
	}

	/**
	 * This allows to set the listen feature and refresh the button corresponding to the listen feature value.
	 * 
	 * @param structuralFeature
	 *            The structural feature to set.
	 */
	protected void setListenFeature(final EStructuralFeature structuralFeature) {
		if (null == modifiedFeatureFillingConf.getListenFeature() || !structuralFeature.equals(modifiedFeatureFillingConf.getListenFeature())) {
			modifiedFeatureFillingConf.setListenFeature(structuralFeature);
		}
		listenFeatureText.setImage(labelProvider.getImage(modifiedFeatureFillingConf.getListenFeature()));
		listenFeatureText.setText(labelProvider.getText(modifiedFeatureFillingConf.getListenFeature()));

		deleteValuesButton.setEnabled(null != structuralFeature);
	}

	/**
	 * Returns the modified feature filling configuration.
	 * 
	 * @return The modified feature filling configuration.
	 */
	public EStructuralFeatureValueFillingConfiguration getModifiedFeatureFillingConfiguration() {
		return modifiedFeatureFillingConf;
	}

}
