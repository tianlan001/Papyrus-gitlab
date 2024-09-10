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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.customization.nattableconfiguration.helper.TableConfigurationHelper;
import org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages;
import org.eclipse.papyrus.customization.nattableconfiguration.utils.NattableConfigurationConstants;
import org.eclipse.papyrus.customization.nattableconfiguration.utils.NattableConfigurationEditingSupport;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.nattable.comparator.IntegerFilterComparator;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EObjectAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EOperationAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EStructuralFeatureAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.FeatureIdAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IdAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.NattableaxisFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ObjectIdAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisManagerRepresentation;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AbstractAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.NattableaxisproviderFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.SlaveObjectAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * The slave configuration wizard page for the nattable configuration.
 */
public class SlaveConfigurationWizardPage extends AbstractTableConfigurationWizardPage {

	/**
	 * The table configuration.
	 */
	protected final TableConfiguration configuration;

	/**
	 * The label provider for the UML elements.
	 */
	protected final ILabelProvider labelProvider;

	/**
	 * The button which allow to add an axis.
	 */
	protected Button addAxis;

	/**
	 * The button which allow to remove the axis.
	 */
	protected Button removeAxis;

	/**
	 * The button which allow to move up the axis.
	 */
	protected Button upAxis;

	/**
	 * The button which allow to move down the axis.
	 */
	protected Button downAxis;

	/**
	 * Constructor.
	 *
	 * @param helper
	 *            The table configuration helper.
	 */
	public SlaveConfigurationWizardPage(final TableConfigurationHelper helper) {
		super(Messages.SlaveConfigurationWizardPage_pageName, helper);
		configuration = helper.getTableConfiguration();
		
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
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(final Composite parent) {

		// Create the parent composite
		final Composite container = new Composite(parent, SWT.BORDER);
		container.setLayout(new GridLayout(2, false));

		final SlaveObjectAxisProvider slave = getOrCreateSlaveObjectAxisProvider(configuration);

		// Create the table viewer (table, column and its layout)
		final TableViewer tableViewer = createTableViewer(container, slave);

		// Create the buttons composite
		final Composite buttonsComposite = new Composite(container, SWT.NONE);
		buttonsComposite.setLayout(new GridLayout(1, false));
		buttonsComposite.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		// Create the add button
		addAxis = new Button(buttonsComposite, SWT.PUSH);
		addAxis.setImage(Activator.getDefault().getImage(NattableConfigurationConstants.ADD_ICON_PATH));
		addAxis.setLayoutData(new GridData(SWT.CENTER, SWT.BEGINNING, false, false));
		// Manage the add action which allow to create an axis
		addAxis.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				int lastPosition = slave.getAxis().size();
				if (!tableViewer.getStructuredSelection().isEmpty()) {
					final Iterator<?> selectedObjects = tableViewer.getStructuredSelection().iterator();
					while (selectedObjects.hasNext()) {
						final Object selectedObject = selectedObjects.next();
						final int objectPosition = slave.getAxis().indexOf(selectedObject);
						if (objectPosition > lastPosition) {
							lastPosition = objectPosition;
						}
					}
				}

				final EStructuralFeatureAxis createdAxis = NattableaxisFactory.eINSTANCE.createEStructuralFeatureAxis();
				if (lastPosition + 1 < slave.getAxis().size()) {
					getSlaveObjectAxisProvider(configuration).getAxis().add(lastPosition + 1, createdAxis);
				} else {
					getSlaveObjectAxisProvider(configuration).getAxis().add(createdAxis);
				}
				recalculateButtonsAvailability(tableViewer.getStructuredSelection(), slave);
				tableViewer.refresh();
			}
		});

		// Create the remove button
		removeAxis = new Button(buttonsComposite, SWT.PUSH);
		removeAxis.setImage(Activator.getDefault().getImage(NattableConfigurationConstants.DELETE_ICON_PATH));
		removeAxis.setLayoutData(new GridData(SWT.CENTER, SWT.BEGINNING, false, false));
		// Manage the remove action which allow to remove an axis
		removeAxis.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final Iterator<?> selectedObjects = tableViewer.getStructuredSelection().iterator();
				while (selectedObjects.hasNext()) {
					slave.getAxis().remove(selectedObjects.next());
				}
				recalculateButtonsAvailability(tableViewer.getStructuredSelection(), slave);
				tableViewer.refresh();
			}
		});

		// Create the move up button
		upAxis = new Button(buttonsComposite, SWT.UP);
		upAxis.setImage(Activator.getDefault().getImage(NattableConfigurationConstants.UP_ICON_PATH));
		upAxis.setLayoutData(new GridData(SWT.CENTER, SWT.BEGINNING, false, false));
		// Manage the remove action which allow to move up an axis
		upAxis.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				// Create a list of index to move.
				// To move all the selected item correctly, get all index to move, sort the integer and move it from the first to the last
				// (to avoid multiple same object moved)
				final List<Integer> indexToMove = new ArrayList<Integer>();

				final Iterator<?> selectedObjects = tableViewer.getStructuredSelection().iterator();
				while (selectedObjects.hasNext()) {
					IAxis selectedAxis = (IAxis) selectedObjects.next();
					indexToMove.add(slave.getAxis().indexOf(selectedAxis));
				}

				// Sort the index to move
				indexToMove.sort(IntegerFilterComparator.getInstance());

				// Move the objects from the first to the last
				for (final Integer index : indexToMove) {
					slave.getAxis().move(index - 1, index);
				}

				recalculateButtonsAvailability(tableViewer.getStructuredSelection(), slave);
				tableViewer.refresh();
			}
		});

		// Create the move down button
		downAxis = new Button(buttonsComposite, SWT.DOWN);
		downAxis.setImage(Activator.getDefault().getImage(NattableConfigurationConstants.DOWN_ICON_PATH));
		downAxis.setLayoutData(new GridData(SWT.CENTER, SWT.BEGINNING, false, false));
		// Manage the remove action which allow to move down an axis
		downAxis.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				// Create a list of index to move.
				// To move all the selected item correctly, get all index to move, sort the integer and move it from the last to the first
				// (to avoid multiple same object moved)
				final List<Integer> indexToMove = new ArrayList<Integer>();

				final Iterator<?> selectedObjects = tableViewer.getStructuredSelection().iterator();
				while (selectedObjects.hasNext()) {
					final IAxis selectedAxis = (IAxis) selectedObjects.next();
					indexToMove.add(slave.getAxis().indexOf(selectedAxis));
				}

				// Sort the index to move
				indexToMove.sort(IntegerFilterComparator.getInstance());

				// Move the objects from the last to the first
				for (int it = indexToMove.size() - 1; it >= 0; it--) {
					int index = indexToMove.get(it);
					slave.getAxis().move(index + 1, index);
				}

				recalculateButtonsAvailability(tableViewer.getStructuredSelection(), slave);
				tableViewer.refresh();
			}
		});

		// Manage the selection change
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				recalculateButtonsAvailability(event.getSelection(), slave);
			}
		});
		tableViewer.setSelection(new StructuredSelection());

		setControl(container);
	}

	/**
	 * This allows to calculate if buttons availability depending to the selection.
	 * 
	 * @param selection
	 *            The current table viewer selection.
	 * @param slave
	 *            The slave axis provider.
	 */
	protected void recalculateButtonsAvailability(final ISelection selection, final SlaveObjectAxisProvider slave) {
		if (null != selection && !selection.isEmpty()) {
			// A selection is available, enabled the remove button
			removeAxis.setEnabled(true);

			// check if the is not the first or last position, because otherwise, the up or down button must be always disabled
			int firstPosition = -1;
			int lastPosition = -1;
			if (selection instanceof StructuredSelection) {
				final Iterator<?> selectedObjects = ((StructuredSelection) selection).iterator();
				while (selectedObjects.hasNext()) {
					final Object selectedObject = selectedObjects.next();
					final int objectPosition = slave.getAxis().indexOf(selectedObject);
					if (-1 == firstPosition || objectPosition < firstPosition) {
						firstPosition = objectPosition;
					}
					if (objectPosition > lastPosition) {
						lastPosition = objectPosition;
					}
				}
			}

			upAxis.setEnabled(firstPosition != 0);
			downAxis.setEnabled(lastPosition < slave.getAxis().size() - 1);
		} else {
			// Only add button must be enable when no selection occured
			removeAxis.setEnabled(false);
			upAxis.setEnabled(false);
			downAxis.setEnabled(false);
		}
	}

	/**
	 * This allows to create the table viewer and its columns.
	 * 
	 * @param parent
	 *            The parent composite.
	 * @param slave
	 *            The slave axis provider.
	 * @return The created table viewer.
	 */
	protected TableViewer createTableViewer(final Composite parent, final SlaveObjectAxisProvider slave) {

		final TableViewer tableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		// Create the columns
		createColumns(tableViewer);

		// Manage some table display
		final Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		// The content provider of the initial selection
		tableViewer.setContentProvider(new ArrayContentProvider());

		// Define the table input
		tableViewer.setInput(slave.getAxis());

		// define layout for the viewer
		tableViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		tableViewer.refresh();
		return tableViewer;
	}

	/**
	 * This allows to create the table viewer columns.
	 * 
	 * @param tableViewer
	 *            The table viewer.
	 */
	protected void createColumns(final TableViewer tableViewer) {

		final Table table = tableViewer.getTable();

		// Define the titles and bounds of each columns
		final int[] bounds = { 150, 250, 450, 100 };
		final String[] titles = { Messages.SlaveConfigurationWizardPage_typeColumnName, Messages.SlaveConfigurationWizardPage_elementColumnName, Messages.SlaveConfigurationWizardPage_managerColumnName, Messages.SlaveConfigurationWizardPage_aliasColumnName };

		// Create the first column and manage its display by the label provider of the initial selection
		final List<String> typesPossible = new ArrayList<String>();
		typesPossible.add("EObjectAxis"); //$NON-NLS-1$
		typesPossible.add("EOperationAxis"); //$NON-NLS-1$
		typesPossible.add("EStructuralFeatureAxis"); //$NON-NLS-1$
		typesPossible.add("FeatureIdAxis"); //$NON-NLS-1$
		typesPossible.add("ObjectAxis"); //$NON-NLS-1$

		// Create the first column for the type
		final TableViewerColumn firstColumn = createTableViewerColumn(tableViewer, titles[0], bounds[0]);
		final ColumnLabelProvider typeLabelProvider = new ColumnLabelProvider() {

			@Override
			public String getText(final Object element) {
				return ((IAxis) element).getClass().getSimpleName().substring(0, ((IAxis) element).getClass().getSimpleName().length() - 4);
			}
		};
		firstColumn.setLabelProvider(typeLabelProvider);
		// Create the combo box of the type
		firstColumn.setEditingSupport(new NattableConfigurationEditingSupport(tableViewer, typeLabelProvider) {

			@Override
			protected void setValue(final Object element, final Object value) {
				final IAxis axis = (IAxis) element;

				if (null == element || !((IAxis) element).getClass().getSimpleName().substring(0, ((IAxis) element).getClass().getSimpleName().length() - 4).equals(value) && !value.toString().isEmpty()) {
					// Create the axis
					IAxis recreatedAxis = null;
					if ("EObjectAxis".equals(value)) { //$NON-NLS-1$
						recreatedAxis = NattableaxisFactory.eINSTANCE.createEObjectAxis();
					} else if ("EOperationAxis".equals(value)) { //$NON-NLS-1$
						recreatedAxis = NattableaxisFactory.eINSTANCE.createEOperationAxis();
					} else if ("EStructuralFeatureAxis".equals(value)) { //$NON-NLS-1$
						recreatedAxis = NattableaxisFactory.eINSTANCE.createEStructuralFeatureAxis();
					} else if ("FeatureIdAxis".equals(value)) { //$NON-NLS-1$
						recreatedAxis = NattableaxisFactory.eINSTANCE.createFeatureIdAxis();
					} else if ("ObjectAxis".equals(value)) { //$NON-NLS-1$
						recreatedAxis = NattableaxisFactory.eINSTANCE.createObjectIdAxis();
					}

					recreatedAxis.setAlias(axis.getAlias());
					recreatedAxis.setManager(axis.getManager());

					SlaveObjectAxisProvider slave = getSlaveObjectAxisProvider(configuration);
					int indexAxisInParent = slave.getAxis().indexOf(axis);
					slave.getAxis().add(indexAxisInParent, recreatedAxis);
					slave.getAxis().remove(axis);

					tableViewer.refresh();
				}
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				return new ExtendedComboBoxCellEditor(table, typesPossible, new LabelProvider(), SWT.READ_ONLY);
			}
		});

		// Create the second viewer column and manage its display and edition
		final TableViewerColumn secondColumn = createTableViewerColumn(tableViewer, titles[1], bounds[1]);
		final ColumnLabelProvider elementLabelProvider = new ColumnLabelProvider() {

			@Override
			public String getText(final Object element) {
				String result = null;
				if (element instanceof IAxis) {
					final IAxis axis = (IAxis) element;
					if (null != axis.getElement()) {
						result = labelProvider.getText(axis.getElement());
					}
				}
				return null != result ? result : null;
			}

			@Override
			public Image getImage(final Object element) {
				Image result = null;
				if (element instanceof IAxis) {
					final IAxis axis = (IAxis) element;
					if (null != axis.getElement()) {
						result = labelProvider.getImage(axis.getElement());
					}
				}
				return null != result ? result : null;

			}
		};
		secondColumn.setLabelProvider(elementLabelProvider);
		secondColumn.setEditingSupport(new NattableConfigurationEditingSupport(tableViewer, labelProvider) {

			@Override
			protected void setValue(final Object element, final Object value) {
				final IAxis axis = (IAxis) element;

				if (null == axis.getElement() || !axis.getElement().equals(value)) {
					if (axis instanceof EObjectAxis && value instanceof EObject) {
						((EObjectAxis) axis).setElement((EObject) value);
					} else if (axis instanceof EOperationAxis && value instanceof EOperation) {
						((EOperationAxis) axis).setElement((EOperation) value);
					} else if (axis instanceof EStructuralFeatureAxis && value instanceof EStructuralFeature) {
						((EStructuralFeatureAxis) axis).setElement((EStructuralFeature) value);
					} else if (axis instanceof FeatureIdAxis && value instanceof String) {
						((FeatureIdAxis) axis).setElement((String) value);
					} else if (axis instanceof ObjectIdAxis && value instanceof String) {
						((ObjectIdAxis) axis).setElement((String) value);
					}
					tableViewer.refresh();
				}
			}

			@Override
			protected Object getValue(final Object element) {
				String result = null;
				if (element instanceof IAxis) {
					final IAxis axis = (IAxis) element;
					if (null != axis.getElement()) {
						result = labelProvider.getText(axis.getElement());
					}
				}
				return null != result ? result : ""; //$NON-NLS-1$
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				if (element instanceof IdAxis) {
					return new TextCellEditor(table);
				} else {
					final IAxis existingAxis = (IAxis) element;
					final Object initialValue = getValue(element);
					return new DialogCellEditor(table) {

						@Override
						protected Object openDialogBox(Control cellEditorWindow) {
							final ITreeSelectorDialog dialog = new TreeSelectorDialog(getShell()) {
								@Override
								protected void initViewerAndProvider() {
									super.initViewerAndProvider();
									// Set a comparator to sort the tree viewer
									getViewer().setComparator(new ViewerComparator(new StringComparator()));// should always be string element
								}
							};
							dialog.setTitle(org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages.FeatureFillingConfigurationDialog_listenFeatureDialogName);

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
									// EMF dependency, must not be done here, it should be better with a new content provider service
									boolean result = false;

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
							};

							dialog.setContentProvider(treeContentProvider);
							dialog.setLabelProvider(labelProvider);
							if (null != initialValue) {
								dialog.setInitialElementSelections(Collections.singletonList(initialValue));
							}
							Object value = null;
							int result = dialog.open();
							if (Window.OK == result) {
								Object[] newValue = dialog.getResult();
								if (null != newValue) {
									if (0 == newValue.length) {
										value = null;
									} else {
										value = newValue[0];
									}
								}
								setValue(value);
							}

							return value;
						}
					};
				}
			}
		});

		// Create the third viewer column and manage its display and edition
		final TableViewerColumn thirdColumn = createTableViewerColumn(tableViewer, titles[2], bounds[2]);
		final ColumnLabelProvider managerLabelProvider = new ColumnLabelProvider() {

			@Override
			public String getText(final Object element) {
				return null != ((IAxis) element).getManager() ? ((IAxis) element).getManager().getAxisManagerId() : ""; //$NON-NLS-1$
			}
		};
		thirdColumn.setLabelProvider(managerLabelProvider);
		// Create the combo box for the manager
		thirdColumn.setEditingSupport(new NattableConfigurationEditingSupport(tableViewer, managerLabelProvider) {

			@Override
			protected void setValue(final Object element, final Object value) {
				final IAxis axis = (IAxis) element;

				if (null != value && !value.equals(axis.getManager())) {
					// Change the manager
					axis.setManager((AxisManagerRepresentation) value);
					tableViewer.refresh();
				}
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				return new ExtendedComboBoxCellEditor(table, configuration.getColumnHeaderAxisConfiguration().getAxisManagers(), new LabelProvider() {
					@Override
					public String getText(final Object element) {
						return ((AxisManagerRepresentation) element).getAxisManagerId();
					}
				}, SWT.READ_ONLY);
			}
		});

		// Create the fourth viewer column and manage its display and edition
		final TableViewerColumn fourthColumn = createTableViewerColumn(tableViewer, titles[3], bounds[3]);
		final ColumnLabelProvider aliasLabelProvider = new ColumnLabelProvider() {

			@Override
			public String getText(final Object element) {
				return null != ((IAxis) element).getAlias() ? ((IAxis) element).getAlias() : ""; //$NON-NLS-1$
			}
		};
		fourthColumn.setLabelProvider(aliasLabelProvider);
		// Create the text editor for the manager
		fourthColumn.setEditingSupport(new NattableConfigurationEditingSupport(tableViewer, aliasLabelProvider) {

			@Override
			protected void setValue(final Object element, final Object value) {
				final IAxis axis = (IAxis) element;

				if (!value.toString().isEmpty() && !value.equals(axis.getAlias())) {
					// Change the manager
					axis.setAlias((String) value);
					tableViewer.refresh();
				}
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return new TextCellEditor(table);
			}
		});
	}

	/**
	 * This allows to create and return a table viewer column.
	 * 
	 * @param tableViewer
	 *            The parent table viewer of the column to create.
	 * @param title
	 *            The title of the column to create.
	 * @param width
	 *            The width of the column to create.
	 * @return The created viewer column.
	 */
	private TableViewerColumn createTableViewerColumn(final TableViewer tableViewer, final String title, final int width) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(width);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	/**
	 * This allows to get the slave axis provider of the nattable configuration.
	 * 
	 * @param configuration
	 *            The nattable configuration.
	 * @return The slave axis provider is exists or <code>null</code>.
	 */
	protected SlaveObjectAxisProvider getSlaveObjectAxisProvider(final TableConfiguration configuration) {
		SlaveObjectAxisProvider slave = null;

		// Try to get an existing slave axis provider
		if (0 < configuration.getColumnAxisProviders().size()) {
			final Iterator<AbstractAxisProvider> columnAxisProvidersIterator = configuration.getColumnAxisProviders().iterator();
			while (columnAxisProvidersIterator.hasNext() && null == slave) {
				AbstractAxisProvider axisProvider = columnAxisProvidersIterator.next();
				if (axisProvider instanceof SlaveObjectAxisProvider) {
					slave = (SlaveObjectAxisProvider) axisProvider;
				}
			}
		}

		return slave;
	}

	/**
	 * Get the existing slave axis provider or create it otherwise.
	 * 
	 * @param configuration
	 *            The nattable configuration.
	 * @return The existing or created axis provider.
	 */
	protected SlaveObjectAxisProvider getOrCreateSlaveObjectAxisProvider(final TableConfiguration configuration) {
		SlaveObjectAxisProvider slave = getSlaveObjectAxisProvider(configuration);

		// The slave doesn't exist, create it
		if (null == slave) {
			slave = NattableaxisproviderFactory.eINSTANCE.createSlaveObjectAxisProvider();
			configuration.getColumnAxisProviders().add(slave);
		}

		// Set the slave as default row axis provider if this is not already done
		if (null == configuration.getDefaultColumnAxisProvider()) {
			configuration.setDefaultColumnAxisProvider(slave);
		}

		return slave;
	}
}
