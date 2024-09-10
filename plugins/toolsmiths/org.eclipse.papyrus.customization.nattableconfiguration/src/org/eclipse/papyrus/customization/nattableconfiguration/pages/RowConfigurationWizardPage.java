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

package org.eclipse.papyrus.customization.nattableconfiguration.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeColumnViewerLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.customization.nattableconfiguration.edition.FeatureFillingConfigurationDialog;
import org.eclipse.papyrus.customization.nattableconfiguration.edition.PasteEObjectConfigurationDialog;
import org.eclipse.papyrus.customization.nattableconfiguration.edition.TreeFillingConfigurationDialog;
import org.eclipse.papyrus.customization.nattableconfiguration.helper.TableConfigurationHelper;
import org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages;
import org.eclipse.papyrus.customization.nattableconfiguration.utils.NameSimplifier;
import org.eclipse.papyrus.customization.nattableconfiguration.utils.NattableConfigurationConstants;
import org.eclipse.papyrus.customization.nattableconfiguration.utils.NattableConfigurationEditingSupport;
import org.eclipse.papyrus.infra.nattable.manager.axis.AxisManagerFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EObjectAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EOperationAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EStructuralFeatureAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.FeatureIdAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.provider.NattableaxisItemProviderAdapterFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisManagerRepresentation;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.EStructuralFeatureValueFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.IAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.PasteEObjectConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TableHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.provider.NattableaxisconfigurationItemProviderAdapterFactory;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

/**
 * The row axis header configuration wizard page which allow to define the axis managers, the label provider configurations (hidden for user) and, maybe, the tree filling configuration.
 */
public class RowConfigurationWizardPage extends AbstractAxisConfigurationWizardPage {

	/**
	 * The eClass constant.
	 */
	private static final String E_CLASS = "eClass"; //$NON-NLS-1$

	/**
	 * The axis manager proposed to the user(even if the registered axis managers in plugins are not correctly managed).
	 */
	protected static final List<String> requiredProposedAxisManagers = new ArrayList<String>();

	/**
	 * The button to add an axis configuration.
	 */
	protected Button addAxisConfiguration;

	/**
	 * The button to edit an axis configuration.
	 */
	protected Button editAxisConfiguration;

	/**
	 * The button to remove an existing axis configuration.
	 */
	protected Button removeAxisConfiguration;

	/**
	 * Initialize the previous map.
	 */
	static {
		requiredProposedAxisManagers.add("EMF Feature (org.eclipse.papyrus.infra.emf.nattable.feature.axis.manager)"); //$NON-NLS-1$
		requiredProposedAxisManagers.add("EMF Operation (org.eclipse.papyrus.infra.emf.nattable.operation.axis.manager)"); //$NON-NLS-1$
		requiredProposedAxisManagers.add("UML Element (org.eclipse.papyrus.uml.nattable.element.axis.manager)"); //$NON-NLS-1$
		requiredProposedAxisManagers.add("UML Feature (org.eclipse.papyrus.uml.nattable.feature.axis.manager)"); //$NON-NLS-1$
		requiredProposedAxisManagers.add("UML Operation (org.eclipse.papyrus.uml.nattable.operation.axis.manager)"); //$NON-NLS-1$
		requiredProposedAxisManagers.add("UML Tree Axis (org.eclipse.papyrus.infra.uml.nattable.class.tree.axis.manager)"); //$NON-NLS-1$
	}

	/**
	 * Constructor.
	 *
	 * @param helper
	 *            The table configuration helper.
	 */
	public RowConfigurationWizardPage(final TableConfigurationHelper helper) {
		super(Messages.RowConfigurationWizardPage_pageName, helper);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.customization.nattableconfiguration.pages.AbstractAxisConfigurationWizardPage#getHeaderAxisConfiguration()
	 */
	@Override
	public TableHeaderAxisConfiguration getHeaderAxisConfiguration() {
		return helper.getTableConfiguration().getRowHeaderAxisConfiguration();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.customization.nattableconfiguration.pages.AbstractAxisConfigurationWizardPage#createMoreComposite(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createMoreComposite(final Composite parent) {
		// Create the axis managers group
		final Group axisManagersGroup = new Group(parent, SWT.NONE);
		axisManagersGroup.setText(Messages.RowConfigurationWizardPage_axisConfigurationsLabel);
		axisManagersGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		axisManagersGroup.setLayout(new GridLayout(2, false));

		// Create the axis configurations tree viewer
		final TreeViewer treeViewer = createAxisConfigurationTreeViewer(axisManagersGroup);

		// Create the buttons composite
		final Composite buttonsComposite = new Composite(axisManagersGroup, SWT.NONE);
		buttonsComposite.setLayout(new GridLayout(1, false));
		buttonsComposite.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		// Create the add button
		addAxisConfiguration = new Button(buttonsComposite, SWT.PUSH);
		addAxisConfiguration.setImage(Activator.getDefault().getImage(NattableConfigurationConstants.ADD_ICON_PATH));
		addAxisConfiguration.setLayoutData(new GridData(SWT.CENTER, SWT.BEGINNING, false, false));
		// Manage the add action which allow to create an axis configuration
		addAxisConfiguration.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {

				final SelectionListener selectionListener = new SelectionAdapter() {

					@Override
					public void widgetSelected(final SelectionEvent event) {
						final EClass eClass = (EClass) event.widget.getData(E_CLASS);
						final EObject instance = eClass.getEPackage().getEFactoryInstance().create(eClass);

						if (instance instanceof IAxisConfiguration) {
							headerAxisConfiguration.getOwnedAxisConfigurations().add((IAxisConfiguration) instance);

							editAxisConfiguration(instance);

							treeViewer.setSelection(new StructuredSelection(instance));
						}
					}

				};

				final Menu menu = new Menu(addAxisConfiguration);
				final MenuItem treeFillingItem = new MenuItem(menu, SWT.NONE);
				treeFillingItem.setText(Messages.RowConfigurationWizardPage_treeFillingConfigurationLabel);
				treeFillingItem.setData(E_CLASS, NattableaxisconfigurationPackage.eINSTANCE.getEClassifier("TreeFillingConfiguration")); //$NON-NLS-1$
				treeFillingItem.addSelectionListener(selectionListener);
				final MenuItem pasteEObjectItem = new MenuItem(menu, SWT.NONE);
				pasteEObjectItem.setText(Messages.RowConfigurationWizardPage_pasteConfigurationLabel);
				pasteEObjectItem.setData(E_CLASS, NattableaxisconfigurationPackage.eINSTANCE.getEClassifier("PasteEObjectConfiguration")); //$NON-NLS-1$
				pasteEObjectItem.addSelectionListener(selectionListener);
				final MenuItem featureAxisItem = new MenuItem(menu, SWT.NONE);
				featureAxisItem.setText(Messages.RowConfigurationWizardPage_featureAxisLabel);
				featureAxisItem.setData(E_CLASS, NattableaxisconfigurationPackage.eINSTANCE.getEClassifier("FeatureAxisConfiguration")); //$NON-NLS-1$
				featureAxisItem.addSelectionListener(selectionListener);
				final MenuItem featureValueFillingItem = new MenuItem(menu, SWT.NONE);
				featureValueFillingItem.setText(Messages.RowConfigurationWizardPage_eStructuralFeatureValueFillingConfigurationLabel);
				featureValueFillingItem.setData(E_CLASS, NattableaxisconfigurationPackage.eINSTANCE.getEClassifier("EStructuralFeatureValueFillingConfiguration")); //$NON-NLS-1$
				featureValueFillingItem.addSelectionListener(selectionListener);

				menu.setVisible(true);

				// The menu is blocking the thread
				final Display display = addAxisConfiguration.getDisplay();
				while (menu.isVisible()) {
					try {
						if (!display.readAndDispatch()) {
							display.sleep();
						}
					} catch (Throwable ex) {
						Activator.log.error(ex);
					}
				}
				if (!display.isDisposed()) {
					display.update();
				}
			}
		});

		// Create the edit button
		editAxisConfiguration = new Button(buttonsComposite, SWT.PUSH);
		editAxisConfiguration.setImage(Activator.getDefault().getImage(NattableConfigurationConstants.EDIT_ICON_PATH));
		editAxisConfiguration.setLayoutData(new GridData(SWT.CENTER, SWT.BEGINNING, false, false));
		// Manage the edit action which allow to edit an axis configuration
		editAxisConfiguration.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final Object selectedObject = treeViewer.getStructuredSelection().getFirstElement();
				editAxisConfiguration((EObject) selectedObject);
			}
		});

		// Create the remove button
		removeAxisConfiguration = new Button(buttonsComposite, SWT.PUSH);
		removeAxisConfiguration.setImage(Activator.getDefault().getImage(NattableConfigurationConstants.DELETE_ICON_PATH));
		removeAxisConfiguration.setLayoutData(new GridData(SWT.CENTER, SWT.BEGINNING, false, false));
		// Manage the remove action which allow to remove an axis configuration
		removeAxisConfiguration.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final Iterator<?> selectedObjects = treeViewer.getStructuredSelection().iterator();
				while (selectedObjects.hasNext()) {
					headerAxisConfiguration.getOwnedAxisConfigurations().remove(selectedObjects.next());
				}

				recalculateAxisConfigurationButtonsAvailability(treeViewer.getStructuredSelection());

				treeViewer.refresh();
			}
		});

		// Manage the selection change
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(final SelectionChangedEvent event) {
				recalculateAxisConfigurationButtonsAvailability(event.getSelection());
			}
		});

		treeViewer.setSelection(new StructuredSelection());
	}

	/**
	 * This allows to calculate the axis configurations buttons availability (remove button musn't be enabled when there is no selection).
	 * 
	 * @param selection
	 *            The current table viewer selection.
	 */
	protected void recalculateAxisConfigurationButtonsAvailability(final ISelection selection) {
		editAxisConfiguration.setEnabled(selection instanceof StructuredSelection && 1 == ((StructuredSelection) selection).size() && ((StructuredSelection) selection).getFirstElement() instanceof IAxisConfiguration);
		removeAxisConfiguration.setEnabled(null != selection && !selection.isEmpty());
	}

	/**
	 * This allows to edit axis configuration via a dialog corresponding to a type.
	 * 
	 * @param object
	 *            The object to edit.
	 */
	protected void editAxisConfiguration(final EObject object) {
		if (object instanceof PasteEObjectConfiguration) {
			final PasteEObjectConfiguration pasteEObjectConfiguration = (PasteEObjectConfiguration) object;
			final PasteEObjectConfigurationDialog dialog = new PasteEObjectConfigurationDialog(getShell(), pasteEObjectConfiguration);
			if (Window.OK == dialog.open()) {
				final PasteEObjectConfiguration modifiedPasteConfiguration = dialog.getModifiedPasteEObjectConfiguration();
				pasteEObjectConfiguration.setDetachedMode(modifiedPasteConfiguration.isDetachedMode());
				pasteEObjectConfiguration.setPastedElementId(modifiedPasteConfiguration.getPastedElementId());
				pasteEObjectConfiguration.setPasteElementContainementFeature(modifiedPasteConfiguration.getPasteElementContainementFeature());
				pasteEObjectConfiguration.getPostActions().addAll(modifiedPasteConfiguration.getPostActions());
			}
		} else if (object instanceof EStructuralFeatureValueFillingConfiguration) {
			final EStructuralFeatureValueFillingConfiguration featureFillingConfiguration = (EStructuralFeatureValueFillingConfiguration) object;
			final FeatureFillingConfigurationDialog dialog = new FeatureFillingConfigurationDialog(getShell(), featureFillingConfiguration);
			if (Window.OK == dialog.open()) {
				final EStructuralFeatureValueFillingConfiguration modifiedFeatureFillingConfiguration = dialog.getModifiedFeatureFillingConfiguration();
				featureFillingConfiguration.setListenFeature(modifiedFeatureFillingConfiguration.getListenFeature());
			}
		} else if (object instanceof TreeFillingConfiguration) {
			final TreeFillingConfiguration treeFillingConfiguration = (TreeFillingConfiguration) object;
			final TreeFillingConfigurationDialog dialog = new TreeFillingConfigurationDialog(getShell(), treeFillingConfiguration, headerAxisConfiguration.getOwnedLabelConfigurations(), getPasteConfigurations());
			if (Window.OK == dialog.open()) {
				final TreeFillingConfiguration modifiedTreeFillingConfiguration = dialog.getModifiedTreeFillingConfiguration();
				treeFillingConfiguration.setDepth(modifiedTreeFillingConfiguration.getDepth());
				treeFillingConfiguration.setLabelProvider(modifiedTreeFillingConfiguration.getLabelProvider());
				treeFillingConfiguration.setPasteConfiguration(modifiedTreeFillingConfiguration.getPasteConfiguration());
				final IAxis modifiedAxis = modifiedTreeFillingConfiguration.getAxisUsedAsAxisProvider();
				if (null != modifiedAxis) {
					final IAxis existingAxis = treeFillingConfiguration.getAxisUsedAsAxisProvider();
					if (null != existingAxis && modifiedAxis.getClass().getSimpleName().equals(existingAxis.getClass().getSimpleName())) {
						if (existingAxis instanceof EObjectAxis) {
							((EObjectAxis) existingAxis).setElement(((EObjectAxis) modifiedAxis).getElement());
						} else if (existingAxis instanceof EStructuralFeatureAxis) {
							((EStructuralFeatureAxis) existingAxis).setElement(((EStructuralFeatureAxis) modifiedAxis).getElement());
						} else if (existingAxis instanceof EOperationAxis) {
							((EOperationAxis) existingAxis).setElement(((EOperationAxis) modifiedAxis).getElement());
						} else if (existingAxis instanceof FeatureIdAxis) {
							((FeatureIdAxis) existingAxis).setElement(((FeatureIdAxis) modifiedAxis).getElement());
						}
					} else {
						treeFillingConfiguration.setAxisUsedAsAxisProvider(modifiedAxis);
					}
				} else {
					treeFillingConfiguration.setAxisUsedAsAxisProvider(null);
				}
			}
		}
	}

	/**
	 * Get the paste configuration available in the header axis configuration.
	 * 
	 * @return The list of paste configuration available in the header axis configuration.
	 */
	protected List<PasteEObjectConfiguration> getPasteConfigurations() {
		List<PasteEObjectConfiguration> pasteConfigurations = new ArrayList<PasteEObjectConfiguration>();
		for (final IAxisConfiguration axisConfiguration : headerAxisConfiguration.getOwnedAxisConfigurations()) {
			if (axisConfiguration instanceof PasteEObjectConfiguration) {
				pasteConfigurations.add((PasteEObjectConfiguration) axisConfiguration);
			}
		}
		return pasteConfigurations;
	}

	/**
	 * This allows to create the axis configurations tree viewer and its columns.
	 * 
	 * @param parent
	 *            The parent composite.
	 * @return The created tree viewer.
	 */
	protected TreeViewer createAxisConfigurationTreeViewer(final Composite parent) {
		final TreeViewer treeViewer = new TreeViewer(new Tree(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER));

		// Create the columns
		createAxisConfigurationsColumns(treeViewer);

		// Manage some table display
		final Tree tree = treeViewer.getTree();
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);

		// The content provider of the tree viewer
		// Here, we can use the content provider of the nattable axis configuration
		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory();
		adapterFactory.addAdapterFactory(new NattableaxisconfigurationItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new NattableaxisItemProviderAdapterFactory());
		final IContentProvider contentProvider = new AdapterFactoryContentProvider(adapterFactory) {

			@Override
			public Object[] getElements(final Object object) {
				final Object[] elements = super.getElements(object);
				final List<Object> elementsList = new ArrayList<Object>(Arrays.asList(elements));

				final Iterator<Object> elementsIterator = elementsList.iterator();

				// Display only the axis configurations
				while (elementsIterator.hasNext()) {
					if (!(elementsIterator.next() instanceof IAxisConfiguration)) {
						elementsIterator.remove();
					}
				}
				return elementsList.toArray();
			}
		};

		treeViewer.setContentProvider(contentProvider);

		// Define the table input
		treeViewer.setInput(headerAxisConfiguration);

		// define layout for the viewer
		treeViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		treeViewer.refresh();
		return treeViewer;
	}

	/**
	 * This allows to create the table viewer axis configurations columns.
	 * 
	 * @param tableViewer
	 *            The table viewer.
	 */
	protected void createAxisConfigurationsColumns(final TreeViewer treeViewer) {

		// Define the titles and bounds of each columns
		final int[] bounds = { 600 };
		final String[] titles = { Messages.RowConfigurationWizardPage_axisConfigurationsColumnName };

		// Create the first column for the axis manager id
		final TreeViewerColumn axisManagerIdColumn = createTreeViewerColumn(treeViewer, titles[0], bounds[0]);

		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory();
		adapterFactory.addAdapterFactory(new NattableaxisconfigurationItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new NattableaxisItemProviderAdapterFactory());
		final ILabelProvider labelProvider = new AdapterFactoryLabelProvider(adapterFactory);

		axisManagerIdColumn.setLabelProvider(new TreeColumnViewerLabelProvider(labelProvider));
	}

	/**
	 * This allows to create a tree viewer column in the tree viewer.
	 * 
	 * @param viewer
	 *            the tree viewer.
	 * @param title
	 *            The title of the column.
	 * @param width
	 *            The width of the column to create.
	 * @return The created tree viewer column.
	 */
	protected TreeViewerColumn createTreeViewerColumn(final TreeViewer viewer, final String title, final int width) {
		final TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, SWT.NONE);
		final TreeColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(width);
		column.setResizable(true);
		column.setMoveable(false);
		return viewerColumn;
	}

	/**
	 * @see org.eclipse.papyrus.customization.nattableconfiguration.pages.AbstractAxisConfigurationWizardPage#createAxisManagerRepresentation()
	 *
	 * @return
	 */
	@Override
	public AxisManagerRepresentation createAxisManagerRepresentation() {
		final AxisManagerRepresentation createdRepresentation = super.createAxisManagerRepresentation();
		// Set the label provider context
		createdRepresentation.setLabelProviderContext("org.eclipse.papyrus.infra.nattable.header.labelprovider"); //$NON-NLS-1$
		return createdRepresentation;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.customization.nattableconfiguration.pages.AbstractAxisConfigurationWizardPage#createColumns(org.eclipse.jface.viewers.TableViewer)
	 */
	@Override
	protected void createAxisManagersColumns(final TableViewer tableViewer) {

		final Table table = tableViewer.getTable();
		final Collection<String> knownAxis = AxisManagerFactory.INSTANCE.getAllRegisteredAxisManager().keySet();

		// Define the titles and bounds of each columns
		final int[] bounds = { 500, 500 };
		final String[] titles = { Messages.ConfigurationWizardPage_axisManagerIdColumnName, Messages.ConfigurationWizardPage_labelProviderContextColumnName };

		// Create the first column for the axis manager id
		final TableViewerColumn axisManagerIdColumn = createTableViewerColumn(tableViewer, titles[0], bounds[0]);
		final ColumnLabelProvider axisManagerIdLabelProvider = new ColumnLabelProvider() {

			@Override
			public String getText(final Object element) {
				final StringBuilder value = new StringBuilder();
				if (element instanceof AxisManagerRepresentation) {
					final String axisManagerId = ((AxisManagerRepresentation) element).getAxisManagerId();
					boolean axisManagerFound = NameSimplifier.axisManagerNames.containsKey(axisManagerId);
					if (axisManagerFound) {
						value.append(NameSimplifier.axisManagerNames.get(axisManagerId));
					} else {
						value.append(null != axisManagerId ? axisManagerId : ""); //$NON-NLS-1$
					}
				}
				return value.toString();
			}
		};
		axisManagerIdColumn.setLabelProvider(axisManagerIdLabelProvider);
		// Create the combo box of the axis manager identifier
		axisManagerIdColumn.setEditingSupport(new NattableConfigurationEditingSupport(tableViewer, axisManagerIdLabelProvider) {

			@Override
			protected void setValue(final Object element, final Object value) {
				if (element instanceof AxisManagerRepresentation) {
					// set the new value
					if (NameSimplifier.axisManagerNames.containsValue(value)) {
						for (final Entry<String, String> entry : NameSimplifier.axisManagerNames.entrySet()) {
							if (((String) value).contains(entry.getValue()) && ((String) value).contains(entry.getKey())) {
								((AxisManagerRepresentation) element).setAxisManagerId(entry.getKey());
							}
						}
					} else {
						((AxisManagerRepresentation) element).setAxisManagerId((String) value);
					}

					// Manage the label provider configuration depending to the new axis manager identifier
					manageLabelProviderConfiguration((AxisManagerRepresentation) element);

					tableViewer.refresh();
					setPageComplete(isPageComplete());
				}
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				return new ExtendedComboBoxCellEditor(table, createAxisManagerIdItems(requiredProposedAxisManagers, knownAxis), new LabelProvider(), SWT.NONE) {

					@Override
					public Object doGetValue() {
						// Redefine this to allow other value than the proposed ones from the combo
						if (getControl() instanceof CCombo) {
							return ((CCombo) getControl()).getText();
						}
						return super.doGetValue();
					}
				};
			}
		});

		// Create the second column for the label provider context
		final TableViewerColumn labelProviderContextColumn = createTableViewerColumn(tableViewer, titles[1], bounds[1]);
		final ColumnLabelProvider labelProviderContextLabelProvider = new ColumnLabelProvider() {

			@Override
			public String getText(final Object element) {
				String result = ""; //$NON-NLS-1$
				if (element instanceof AxisManagerRepresentation) {
					if (NameSimplifier.labelProviderContextNames.containsKey(((AxisManagerRepresentation) element).getLabelProviderContext())) {
						result = NameSimplifier.labelProviderContextNames.get(((AxisManagerRepresentation) element).getLabelProviderContext());
					} else {
						result = ((AxisManagerRepresentation) element).getLabelProviderContext();
					}
				}
				return result;
			}
		};
		labelProviderContextColumn.setLabelProvider(labelProviderContextLabelProvider);
	}
}
