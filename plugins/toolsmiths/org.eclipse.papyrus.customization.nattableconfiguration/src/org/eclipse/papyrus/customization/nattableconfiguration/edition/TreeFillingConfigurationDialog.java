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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages;
import org.eclipse.papyrus.customization.nattableconfiguration.utils.NameSimplifier;
import org.eclipse.papyrus.customization.nattableconfiguration.utils.NattableConfigurationConstants;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EObjectAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EOperationAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EStructuralFeatureAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.FeatureIdAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.NattableaxisFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.PasteEObjectConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ILabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.StringComparator;
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
import org.eclipse.swt.custom.StackLayout;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.SelectionDialog;

/**
 * The dialog which allow to edit the tree filling configuration features.
 */
public class TreeFillingConfigurationDialog extends SelectionDialog {

	/**
	 * The list of possible axis creation.
	 */
	protected final static List<String> possibleAxisCreation = new ArrayList<String>();

	static {
		possibleAxisCreation.add("EObjectAxis"); //$NON-NLS-1$
		possibleAxisCreation.add("EOperationAxis"); //$NON-NLS-1$
		possibleAxisCreation.add("EStructuralFeatureAxis"); //$NON-NLS-1$
		possibleAxisCreation.add("FeatureIdAxis"); //$NON-NLS-1$
		possibleAxisCreation.add("ObjectAxis"); //$NON-NLS-1$
	}


	/**
	 * A copy of initial tree filling configuration to modify with this dialog.
	 */
	protected final TreeFillingConfiguration modifiedTreefillingConfiguration;

	/**
	 * The list of existing label provider configurations for the tree filling configuration label provider reference.
	 */
	protected final List<ILabelProviderConfiguration> existingLabelProviderConfigurations;

	/**
	 * The list of existing paste configurations for the tree filling configuration paste configuration reference.
	 */
	protected final List<PasteEObjectConfiguration> existingPasteConfigurations;

	/**
	 * The label provider for the UML elements.
	 */
	protected final ILabelProvider labelProvider;

	/**
	 * The stack layout which allow to display the correct element composite corresponding to the type of axis.
	 */
	protected StackLayout stackLayout;

	/**
	 * The parent element composite which contains the stack layout.
	 */
	protected Composite compositeElement;

	/**
	 * The composite for the element referenced from the axis.
	 */
	protected Composite compositeElementReference;

	/**
	 * The composite for the string element from the axis.
	 */
	protected Composite compositeElementString;

	/**
	 * The CLabel composite for the element reference.
	 */
	protected CLabel elementCLabel;

	/**
	 * The CLabel composite for the element string.
	 */
	protected Text elementText;

	/**
	 * The browse button which allow to edit the element.
	 */
	protected Button browseValuesButton;

	/**
	 * The delete button which allow to unset the element value.
	 */
	protected Button deleteValuesButton;

	/**
	 * Constructor.
	 *
	 * @param parentShell
	 *            The parent shell of the dialog.
	 * @param treefillingConfiguration
	 *            The initial tree filling configuration to edit.
	 * @param existingLabelProvidersConfiguration
	 *            The list of existing label provider configurations for the tree filling configuration label provider reference.
	 * @param existingPasteConfigurations
	 *            The list of existing paste configurations for the tree filling configuration paste configuration reference.
	 */
	public TreeFillingConfigurationDialog(final Shell parentShell, final TreeFillingConfiguration treefillingConfiguration, final List<ILabelProviderConfiguration> existingLabelProvidersConfiguration,
			final List<PasteEObjectConfiguration> existingPasteConfigurations) {
		super(parentShell);
		this.modifiedTreefillingConfiguration = EcoreUtil.copy(treefillingConfiguration);
		this.existingLabelProviderConfigurations = existingLabelProvidersConfiguration;
		this.existingPasteConfigurations = existingPasteConfigurations;
		setTitle(Messages.TreeFillingConfigurationDialog_treeFillingConfigurationDialogName);
		
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
	 * This allows to create the fields to edit the tree filling configuration.
	 * 
	 * @param parent
	 *            The parent composite.
	 */
	protected void createFields(final Composite parent) {
		// Create the depth level combo
		createDepthLevelCombo(parent);

		// Create the label provider configuration combo
		createLabelProviderCombo(parent);

		// Create the paste configuration combo
		createPasteConfigurationCombo(parent);

		// Create the axis group management
		createAxisGroup(parent);

		setAxisUsedAsAxisProvider(modifiedTreefillingConfiguration.getAxisUsedAsAxisProvider());
		if (null != modifiedTreefillingConfiguration.getAxisUsedAsAxisProvider()) {
			setAxisElement(modifiedTreefillingConfiguration.getAxisUsedAsAxisProvider().getElement());
		}

	}

	/**
	 * This allows to create the depth level combo.
	 * 
	 * @param parent
	 *            The parent composite.
	 */
	protected void createDepthLevelCombo(final Composite parent) {
		final Label depthLabel = new Label(parent, SWT.NONE);
		depthLabel.setText(org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages.TreeFillingConfigurationDialog_depthLabel);
		depthLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		final Combo depthCombo = new Combo(parent, SWT.READ_ONLY);
		depthCombo.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 3, 1));
		depthCombo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				modifiedTreefillingConfiguration.setDepth(Integer.valueOf(depthCombo.getText()));
			}
		});
		final Collection<String> possiblesDepth = new ArrayList<String>(10);
		for (int i = 0; i < 10; i++) {
			possiblesDepth.add(String.valueOf(i));
		}
		depthCombo.setItems((String[]) possiblesDepth.toArray(new String[possiblesDepth.size()]));
		depthCombo.select(modifiedTreefillingConfiguration.getDepth());
	}

	/**
	 * This allows to create the label provider combo.
	 * 
	 * @param parent
	 *            The parent composite.
	 */
	protected void createLabelProviderCombo(final Composite parent) {
		final Label labelProviderConfigurationLabel = new Label(parent, SWT.NONE);
		labelProviderConfigurationLabel.setText(org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages.TreeFillingConfigurationDialog_labelProviderLabel);
		labelProviderConfigurationLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		final Combo labelProviderConfigurationCombo = new Combo(parent, SWT.READ_ONLY);
		labelProviderConfigurationCombo.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 3, 1));
		labelProviderConfigurationCombo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				if (labelProviderConfigurationCombo.getText().isEmpty()) {
					modifiedTreefillingConfiguration.setLabelProvider(null);
				} else {
					modifiedTreefillingConfiguration.setLabelProvider(existingLabelProviderConfigurations.get(labelProviderConfigurationCombo.getSelectionIndex() - 1));
				}
			}
		});

		final List<String> labelProviders = new ArrayList<String>(existingLabelProviderConfigurations.size());
		for (final ILabelProviderConfiguration existingLabelProviderConf : existingLabelProviderConfigurations) {
			final String className = existingLabelProviderConf.getClass().getSimpleName();
			if (NameSimplifier.labelProviderConfigurationNames.containsKey(className)) {
				final String shortLabelProviderName = NameSimplifier.labelProviderConfigurationNames.get(className);
				String finalShortLabelProviderName = shortLabelProviderName;
				int index = 1;
				while (labelProviders.contains(finalShortLabelProviderName)) {
					final StringBuilder builder = new StringBuilder();
					builder.append(shortLabelProviderName);
					builder.append("_"); //$NON-NLS-1$
					builder.append(index);
					finalShortLabelProviderName = builder.toString();
					index++;
				}
				labelProviders.add(finalShortLabelProviderName);
			}
		}

		labelProviderConfigurationCombo.setItems((String[]) labelProviders.toArray(new String[labelProviders.size()]));
		if (null != modifiedTreefillingConfiguration.getLabelProvider()) {
			int index = existingLabelProviderConfigurations.indexOf(modifiedTreefillingConfiguration.getLabelProvider());
			labelProviderConfigurationCombo.select(index);
		}
	}

	/**
	 * This allows to create the paste configuration combo.
	 * 
	 * @param parent
	 *            The parent composite.
	 */
	protected void createPasteConfigurationCombo(final Composite parent) {
		final Label pasteConfigurationLabel = new Label(parent, SWT.NONE);
		pasteConfigurationLabel.setText(org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages.TreeFillingConfigurationDialog_pasteConfigurationLabel);
		pasteConfigurationLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		final Combo pasteConfigurationCombo = new Combo(parent, SWT.READ_ONLY);
		pasteConfigurationCombo.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 3, 1));
		pasteConfigurationCombo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				if (pasteConfigurationCombo.getText().isEmpty()) {
					modifiedTreefillingConfiguration.setPasteConfiguration(null);
				} else {
					modifiedTreefillingConfiguration.setPasteConfiguration(existingPasteConfigurations.get(pasteConfigurationCombo.getSelectionIndex() - 1));
				}
			}
		});

		final List<String> pasteConfigurations = new ArrayList<String>(existingPasteConfigurations.size());
		pasteConfigurations.add(""); //$NON-NLS-1$
		for (final PasteEObjectConfiguration existingPasteConf : existingPasteConfigurations) {
			final StringBuilder builder = new StringBuilder();
			builder.append("Paste Conf: '"); //$NON-NLS-1$
			builder.append(existingPasteConf.getPastedElementId());
			builder.append("' in '"); //$NON-NLS-1$
			builder.append(existingPasteConf.getPasteElementContainementFeature().getName());
			builder.append("'"); //$NON-NLS-1$
			pasteConfigurations.add(builder.toString());
		}

		pasteConfigurationCombo.setItems((String[]) pasteConfigurations.toArray(new String[pasteConfigurations.size()]));
		if (null != modifiedTreefillingConfiguration.getPasteConfiguration()) {
			int index = existingPasteConfigurations.indexOf(modifiedTreefillingConfiguration.getPasteConfiguration());
			pasteConfigurationCombo.select(index);
		}
	}

	/**
	 * This allows to create the axis group.
	 * 
	 * @param parent
	 *            The parent composite.
	 */
	protected void createAxisGroup(final Composite parent) {
		final Group axisGroup = new Group(parent, SWT.NONE);
		axisGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 4, 1));
		axisGroup.setLayout(new GridLayout(4, false));
		axisGroup.setText(org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages.TreeFillingConfigurationDialog_axisUsedAsAxisProviderLabel);

		// Create the paste configuration combo
		final Label axisTypeLabel = new Label(axisGroup, SWT.NONE);
		axisTypeLabel.setText(org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages.TreeFillingConfigurationDialog_typeLabel);
		axisTypeLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		final Combo axisTypeCombo = new Combo(axisGroup, SWT.READ_ONLY);
		axisTypeCombo.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 3, 1));
		axisTypeCombo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				final IAxis existingAxis = modifiedTreefillingConfiguration.getAxisUsedAsAxisProvider();

				if (axisTypeCombo.getText().isEmpty() && null != existingAxis) {
					setAxisUsedAsAxisProvider(null);
				} else {
					if (null == existingAxis || !existingAxis.getClass().getSimpleName().substring(0, existingAxis.getClass().getSimpleName().length() - 4).equals(axisTypeCombo.getText())) {
						IAxis recreatedAxis = null;
						if ("EObjectAxis".equals(axisTypeCombo.getText())) { //$NON-NLS-1$
							recreatedAxis = NattableaxisFactory.eINSTANCE.createEObjectAxis();
						} else if ("EOperationAxis".equals(axisTypeCombo.getText())) { //$NON-NLS-1$
							recreatedAxis = NattableaxisFactory.eINSTANCE.createEOperationAxis();
						} else if ("EStructuralFeatureAxis".equals(axisTypeCombo.getText())) { //$NON-NLS-1$
							recreatedAxis = NattableaxisFactory.eINSTANCE.createEStructuralFeatureAxis();
						} else if ("FeatureIdAxis".equals(axisTypeCombo.getText())) { //$NON-NLS-1$
							recreatedAxis = NattableaxisFactory.eINSTANCE.createFeatureIdAxis();
						} else if ("ObjectAxis".equals(axisTypeCombo.getText())) { //$NON-NLS-1$
							recreatedAxis = NattableaxisFactory.eINSTANCE.createObjectIdAxis();
						}

						if (null != existingAxis) {
							recreatedAxis.setAlias(existingAxis.getAlias());
						}

						setAxisUsedAsAxisProvider(recreatedAxis);
					}
				}
			}
		});

		// Add the possibility to reset it
		final List<String> possiblesAxis = new ArrayList<String>(possibleAxisCreation);
		possiblesAxis.add(0, ""); //$NON-NLS-1$

		axisTypeCombo.setItems((String[]) possiblesAxis.toArray(new String[possiblesAxis.size()]));
		boolean foundAxis = false;

		// Manage the initial value corresponding to the current axis used
		if (null != modifiedTreefillingConfiguration.getAxisUsedAsAxisProvider()) {
			final IAxis existingAxis = modifiedTreefillingConfiguration.getAxisUsedAsAxisProvider();
			final String existingAxisClassName = existingAxis.getClass().getSimpleName().substring(0, existingAxis.getClass().getSimpleName().length() - 4);

			for (int index = 0; index < possiblesAxis.size() && !foundAxis; index++) {
				final String axisClass = possiblesAxis.get(index);

				if (existingAxisClassName.equals(axisClass)) {
					axisTypeCombo.select(index);
					foundAxis = true;
				}
			}
		} else {
			axisTypeCombo.select(0);
		}

		// Create the stack layout to display the composite string element or the reference element
		stackLayout = new StackLayout();

		// The parent composite for the element value
		compositeElement = new Composite(axisGroup, SWT.NONE);
		compositeElement.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		compositeElement.setLayout(stackLayout);

		// Create the composite element reference for the element reference (contrary to String value)
		compositeElementReference = new Composite(compositeElement, SWT.NONE);
		compositeElementReference.setLayout(new GridLayout(4, false));
		// Create the element labels and dialog for the reference
		final Label elementLabel = new Label(compositeElementReference, SWT.NONE);
		elementLabel.setText(org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages.TreeFillingConfigurationDialog_elementLabel);
		elementLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		elementCLabel = new CLabel(compositeElementReference, SWT.BORDER);
		elementCLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		browseValuesButton = new Button(compositeElementReference, SWT.PUSH);
		browseValuesButton.setImage(Activator.getDefault().getImage(NattableConfigurationConstants.BROWSE_ICON_PATH));
		browseValuesButton.setToolTipText(org.eclipse.papyrus.infra.widgets.messages.Messages.ReferenceDialog_EditValue);
		browseValuesButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				Object element = null;
				final IAxis existingAxis = modifiedTreefillingConfiguration.getAxisUsedAsAxisProvider();

				if (existingAxis instanceof EObjectAxis) {
					element = ((EObjectAxis) existingAxis).getElement();
				} else if (existingAxis instanceof EStructuralFeatureAxis) {
					element = ((EStructuralFeatureAxis) existingAxis).getElement();
				} else if (existingAxis instanceof EOperationAxis) {
					element = ((EOperationAxis) existingAxis).getElement();
				}
				browseAction(element);
			}
		});

		deleteValuesButton = new Button(compositeElementReference, SWT.PUSH);
		deleteValuesButton.setImage(Activator.getDefault().getImage(NattableConfigurationConstants.DELETE_ICON_PATH));
		deleteValuesButton.setToolTipText(org.eclipse.papyrus.infra.widgets.messages.Messages.ReferenceDialog_UnsetValue);
		deleteValuesButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				setAxisElement(null);
			}
		});

		// Create the composite element reference for the element reference (contrary to String value)
		compositeElementString = new Composite(compositeElement, SWT.NONE);
		compositeElementString.setLayout(new GridLayout(4, false));
		// Create the element label and the text for the string value
		final Label secondElementLabel = new Label(compositeElementString, SWT.NONE);
		secondElementLabel.setText(org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages.TreeFillingConfigurationDialog_elementLabel);
		secondElementLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		elementText = new Text(compositeElementString, SWT.BORDER);
		elementText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		elementText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(final ModifyEvent e) {
				setAxisElement(elementText.getText());
			}
		});
	}

	/**
	 * The action executed when the "browse" button is selected Choose a value
	 * from a selection of already created objects
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
		dialog.setTitle(org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages.TreeFillingConfigurationDialog_elementDialogName);

		final CompoundFilteredRestrictedContentProvider contentProvider = new CompoundFilteredRestrictedContentProvider() {

			@Override
			public Object[] getChildren(final Object parentElement) {
				List<Object> childrenList = Arrays.asList(super.getChildren(parentElement));
				if (parentElement instanceof EClass) {
					childrenList = new ArrayList<Object>(childrenList);
					final EClass eClass = (EClass) parentElement;
					if (isIgnoringInheritedElements()) {
						childrenList.addAll(eClass.getEOperations());
					} else {
						childrenList.addAll(eClass.getEAllOperations());
					}
					childrenList.remove(EcorePackage.eINSTANCE.getEModelElement_EAnnotations());
				}
				return childrenList.toArray();
			}
		};
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
				return isTypeNeeded(element);
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
				setAxisElement(null);
			} else {
				setAxisElement(newValue[0]);
			}
		}
	}

	/**
	 * This allows to check if the element in dialog must be used for the axis element.
	 * 
	 * @param element
	 *            The element selected to check.
	 * @return <code>true</code> if the element can be set to the axis, <code>false</code> otherwise.
	 */
	protected boolean isTypeNeeded(final Object element) {
		boolean result = false;

		final IAxis existingAxis = modifiedTreefillingConfiguration.getAxisUsedAsAxisProvider();
		if (null != existingAxis) {
			result = element != EcorePackage.eINSTANCE.getEModelElement_EAnnotations();

			// Depending to the axis, objects displayed are not the same
			if (existingAxis instanceof EObjectAxis && result) {
				result = element instanceof EClass;
			} else if (existingAxis instanceof EStructuralFeatureAxis && result) {
				result = element instanceof EStructuralFeature;
			} else if (existingAxis instanceof EOperationAxis && result) {
				result = element instanceof EOperation;
			}
		}

		return result;
	}

	/**
	 * This allows to set the axis and refresh the button corresponding to the axis value.
	 * 
	 * @param newAxis
	 *            The axis to set to the tree filling configuration.
	 */
	protected void setAxisUsedAsAxisProvider(final IAxis newAxis) {
		if (null == modifiedTreefillingConfiguration.getAxisUsedAsAxisProvider() || !modifiedTreefillingConfiguration.getAxisUsedAsAxisProvider().equals(newAxis)) {
			modifiedTreefillingConfiguration.setAxisUsedAsAxisProvider(newAxis);
		}

		// This allows to refresh the composite to display for the axis element value
		stackLayout.topControl = newAxis instanceof FeatureIdAxis ? compositeElementString : compositeElementReference;
		compositeElement.layout();
		if (stackLayout.topControl.equals(compositeElementString)) {
			elementText.setText(null != newAxis && null != newAxis.getElement() ? ((FeatureIdAxis) newAxis).getElement() : ""); //$NON-NLS-1$
		}

		setAxisElement(null != newAxis ? newAxis.getElement() : null);
	}

	/**
	 * This allows to set the axis and refresh the button corresponding to the axis value.
	 * 
	 * @param newAxis
	 *            The axis to set to the tree filling configuration.
	 */
	protected void setAxisElement(final Object element) {
		final IAxis existingAxis = modifiedTreefillingConfiguration.getAxisUsedAsAxisProvider();
		if (null != existingAxis) {
			if (existingAxis instanceof EObjectAxis && (null == element || element instanceof EObject)) {
				((EObjectAxis) existingAxis).setElement((EObject) element);
			} else if (existingAxis instanceof EStructuralFeatureAxis && (null == element || element instanceof EStructuralFeature)) {
				((EStructuralFeatureAxis) existingAxis).setElement((EStructuralFeature) element);
			} else if (existingAxis instanceof EOperationAxis && (null == element || element instanceof EOperation)) {
				((EOperationAxis) existingAxis).setElement((EOperation) element);
			} else if (existingAxis instanceof FeatureIdAxis && (null == element || element instanceof String)) {
				((FeatureIdAxis) existingAxis).setElement((String) element);
			}
		}

		// Manage the enabling and displayed composites
		if (stackLayout.topControl.equals(compositeElementReference)) {
			elementCLabel.setEnabled(null != existingAxis);
			elementCLabel.setImage(labelProvider.getImage(element));
			elementCLabel.setText(labelProvider.getText(element));

			browseValuesButton.setEnabled(null != existingAxis);
			deleteValuesButton.setEnabled(null != existingAxis && null != element);
		}
	}

	/**
	 * Returns the modified tree filling configuration.
	 * 
	 * @return The modified tree filling configuration.
	 */
	public TreeFillingConfiguration getModifiedTreeFillingConfiguration() {
		return modifiedTreefillingConfiguration;
	}
}
