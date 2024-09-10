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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.papyrus.customization.nattableconfiguration.helper.TableConfigurationHelper;
import org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages;
import org.eclipse.papyrus.customization.nattableconfiguration.utils.NameSimplifier;
import org.eclipse.papyrus.customization.nattableconfiguration.utils.NattableConfigurationConstants;
import org.eclipse.papyrus.customization.nattableconfiguration.utils.NattableConfigurationEditingSupport;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisManagerRepresentation;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TableHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.FeatureLabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ILabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.NattablelabelproviderFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ObjectLabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.OperationLabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.impl.FeatureLabelProviderConfigurationImpl;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.impl.ObjectLabelProviderConfigurationImpl;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.impl.OperationLabelProviderConfigurationImpl;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * The axis header configuration wizard page which allow to define the axis managers, the label provider configurations (hidden for user).
 */
public abstract class AbstractAxisConfigurationWizardPage extends AbstractTableConfigurationWizardPage {

	/**
	 * The header axis configuration from the table configuration;
	 */
	protected final TableHeaderAxisConfiguration headerAxisConfiguration;

	/**
	 * The button to add an axis manager representation.
	 */
	protected Button addAxisManagerRepresentation;

	/**
	 * The button to remove an existing axis manager representation.
	 */
	protected Button removeAxisManagerRepresentation;

	/**
	 * The table viewer corresponding to the label provider configurations.
	 */
	protected TableViewer labelProviderConfigurationTableViewer;


	/**
	 * Constructor.
	 *
	 * @param pageName
	 *            The current page name.
	 * @param helper
	 *            The table configuration helper.
	 */
	public AbstractAxisConfigurationWizardPage(final String pageName, final TableConfigurationHelper helper) {
		super(pageName, helper);
		this.headerAxisConfiguration = getHeaderAxisConfiguration();
	}

	/**
	 * This allows to get the header axis configuration to edit.
	 * 
	 * @return The header axis configuration to edit.
	 */
	public abstract TableHeaderAxisConfiguration getHeaderAxisConfiguration();

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(final Composite parent) {
		// Manage the page complete
		setPageComplete(isPageComplete());

		// Create the parent composite
		final Composite container = new Composite(parent, SWT.BORDER);
		container.setLayout(new GridLayout(1, false));

		// Create the axis managers group
		final Group axisManagersGroup = new Group(container, SWT.NONE);
		axisManagersGroup.setText(Messages.AbstractAxisConfigurationWizardPage_axisManagersLabel);
		axisManagersGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		axisManagersGroup.setLayout(new GridLayout(2, false));

		// Create the table viewer (table, column and its layout)
		final TableViewer axisManagerTableViewer = createAxisManagersTableViewer(axisManagersGroup);

		// Create the buttons composite
		final Composite buttonsComposite = new Composite(axisManagersGroup, SWT.NONE);
		buttonsComposite.setLayout(new GridLayout(1, false));
		buttonsComposite.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		// Create the add button
		addAxisManagerRepresentation = new Button(buttonsComposite, SWT.PUSH);
		addAxisManagerRepresentation.setImage(Activator.getDefault().getImage(NattableConfigurationConstants.ADD_ICON_PATH));
		addAxisManagerRepresentation.setLayoutData(new GridData(SWT.CENTER, SWT.BEGINNING, false, false));
		// Manage the add action which allow to create an axis manager representation
		addAxisManagerRepresentation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final AxisManagerRepresentation createRepresentation = createAxisManagerRepresentation();
				headerAxisConfiguration.getAxisManagers().add(createRepresentation);

				manageLabelProviderConfiguration(createRepresentation);

				recalculateButtonsAvailability(axisManagerTableViewer.getStructuredSelection());
				axisManagerTableViewer.refresh();

				setPageComplete(isPageComplete());
			}
		});

		// Create the remove button
		removeAxisManagerRepresentation = new Button(buttonsComposite, SWT.PUSH);
		removeAxisManagerRepresentation.setImage(Activator.getDefault().getImage(NattableConfigurationConstants.DELETE_ICON_PATH));
		removeAxisManagerRepresentation.setLayoutData(new GridData(SWT.CENTER, SWT.BEGINNING, false, false));
		// Manage the remove action which allow to remove an axis manager representation
		removeAxisManagerRepresentation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final Iterator<?> selectedObjects = axisManagerTableViewer.getStructuredSelection().iterator();
				while (selectedObjects.hasNext()) {
					Object selectedObject = selectedObjects.next();

					if (selectedObject instanceof AxisManagerRepresentation) {
						final AxisManagerRepresentation axisManagerRepresentation = (AxisManagerRepresentation) selectedObject;
						removeLabelProviderConfiguration(axisManagerRepresentation.getHeaderLabelConfiguration(), axisManagerRepresentation);
						headerAxisConfiguration.getAxisManagers().remove(axisManagerRepresentation);
					}
				}

				recalculateButtonsAvailability(axisManagerTableViewer.getStructuredSelection());
				axisManagerTableViewer.refresh();

				setPageComplete(isPageComplete());
			}
		});

		// Manage the selection change
		axisManagerTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				recalculateButtonsAvailability(event.getSelection());
			}
		});
		axisManagerTableViewer.setSelection(new StructuredSelection());

		// Create the table viewer (table, column and its layout)
		labelProviderConfigurationTableViewer = createLabelProviderConfigurationsTableViewer(axisManagersGroup);

		createMoreComposite(container);

		setControl(container);
	}

	/**
	 * This allows to create an axis manager representation.
	 * 
	 * @return The created axis manager representation.
	 */
	public AxisManagerRepresentation createAxisManagerRepresentation() {
		return NattableaxisconfigurationFactory.eINSTANCE.createAxisManagerRepresentation();
	}

	/**
	 * This allows to calculate the buttons availability (remove button musn't be enabled when there is no selection).
	 * 
	 * @param selection
	 *            The current table viewer selection.
	 */
	protected void recalculateButtonsAvailability(final ISelection selection) {
		removeAxisManagerRepresentation.setEnabled(null != selection && !selection.isEmpty());
	}

	/**
	 * This allows to define other composite in the container.
	 * 
	 * @param parent
	 *            the parent composite.
	 */
	public void createMoreComposite(final Composite parent) {
		// Do nothing here
	}

	/**
	 * This allows to create the table viewer and its columns.
	 * 
	 * @param parent
	 *            The parent composite.
	 * @return The created table viewer.
	 */
	protected TableViewer createAxisManagersTableViewer(final Composite parent) {

		final TableViewer tableViewer = new TableViewer(parent, (SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER));

		// Create the columns
		createAxisManagersColumns(tableViewer);

		// Manage some table display
		final Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		// The content provider of the initial selection
		tableViewer.setContentProvider(new ArrayContentProvider());

		// Define the table input
		tableViewer.setInput(headerAxisConfiguration.getAxisManagers());

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
	protected abstract void createAxisManagersColumns(final TableViewer tableViewer);

	/**
	 * This allows to create the table viewer and its columns.
	 * 
	 * @param parent
	 *            The parent composite.
	 * @return The created table viewer.
	 */
	protected TableViewer createLabelProviderConfigurationsTableViewer(final Composite parent) {
		final TableViewer tableViewer = new TableViewer(parent, (SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER));

		// Create the columns
		createLabelProviderConfigurationsColumns(tableViewer);

		// Manage some table display
		final Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		// The content provider of the initial selection
		tableViewer.setContentProvider(new ArrayContentProvider());

		// Define the table input
		tableViewer.setInput(headerAxisConfiguration.getOwnedLabelConfigurations());

		// define layout for the viewer
		tableViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		tableViewer.refresh();
		return tableViewer;
	}

	/**
	 * This allows to create the table viewer columns.
	 * 
	 * @param tableViewer
	 *            The table viewer.
	 */
	protected void createLabelProviderConfigurationsColumns(final TableViewer tableViewer) {
		final Table table = tableViewer.getTable();

		// Define the titles and bounds of each columns
		final int[] bounds = { 200, 125, 125, 125, 125, 125, 125 };
		final String[] titles = { Messages.AbstractAxisConfigurationWizardPage_labelProviderConfigurationColumnLabel, Messages.AbstractAxisConfigurationWizardPage_displayIconColumnLabel, Messages.AbstractAxisConfigurationWizardPage_displayLabelColumnLabel,
				Messages.AbstractAxisConfigurationWizardPage_displayNameColumnLabel, Messages.AbstractAxisConfigurationWizardPage_displayTypeColumnLabel, Messages.AbstractAxisConfigurationWizardPage_displayMultiplicityColumnLabel,
				Messages.AbstractAxisConfigurationWizardPage_displayIsDerivedColumnLabel };

		// Create the first column for the axis manager id
		final TableViewerColumn labelProviderConfigurationColumn = createTableViewerColumn(tableViewer, titles[0], bounds[0]);
		final ColumnLabelProvider axisManagerIdLabelProvider = new ColumnLabelProvider() {

			@Override
			public String getText(final Object element) {
				String value = ""; //$NON-NLS-1$
				if (element instanceof ILabelProviderConfiguration) {
					final String className = element.getClass().getSimpleName();
					if (NameSimplifier.labelProviderConfigurationNames.containsKey(className)) {
						value = NameSimplifier.labelProviderConfigurationNames.get(className);
					}
				}
				return value;
			}
		};
		labelProviderConfigurationColumn.setLabelProvider(axisManagerIdLabelProvider);

		final ColumnLabelProvider nullLabelProvider = new ColumnLabelProvider() {

			@Override
			public String getText(final Object element) {
				return null;
			}
		};

		// Create the column for the display icon value
		final TableViewerColumn displayIconColumn = createTableViewerColumn(tableViewer, titles[1], bounds[1]);
		displayIconColumn.setLabelProvider(nullLabelProvider);
		// Create the check box cell editor
		displayIconColumn.setEditingSupport(new NattableConfigurationEditingSupport(tableViewer, nullLabelProvider) {

			@Override
			protected void setValue(final Object element, final Object value) {
				if (element instanceof ObjectLabelProviderConfiguration) {
					((ObjectLabelProviderConfiguration) element).setDisplayIcon((Boolean) value);
				} else if (element instanceof FeatureLabelProviderConfiguration) {
					((FeatureLabelProviderConfiguration) element).setDisplayIcon((Boolean) value);
				} else if (element instanceof OperationLabelProviderConfiguration) {
					((OperationLabelProviderConfiguration) element).setDisplayIcon((Boolean) value);
				}
			}

			@Override
			protected Object getValue(Object element) {
				return isDisplayIcon(element);
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				return new CheckboxCellEditor(table, SWT.CHECK | SWT.READ_ONLY);
			}
		});

		// Create the column for the display label value
		final TableViewerColumn displayLabelColumn = createTableViewerColumn(tableViewer, titles[2], bounds[2]);
		displayLabelColumn.setLabelProvider(nullLabelProvider);
		// Create the check box cell editor
		displayLabelColumn.setEditingSupport(new NattableConfigurationEditingSupport(tableViewer, nullLabelProvider) {

			@Override
			protected void setValue(final Object element, final Object value) {
				if (element instanceof ObjectLabelProviderConfiguration) {
					((ObjectLabelProviderConfiguration) element).setDisplayLabel((Boolean) value);
				} else if (element instanceof FeatureLabelProviderConfiguration) {
					((FeatureLabelProviderConfiguration) element).setDisplayLabel((Boolean) value);
				} else if (element instanceof OperationLabelProviderConfiguration) {
					((OperationLabelProviderConfiguration) element).setDisplayLabel((Boolean) value);
				}
			}

			@Override
			protected Object getValue(Object element) {
				return isDisplayLabel(element);
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				return new CheckboxCellEditor(table, SWT.CHECK | SWT.READ_ONLY);
			}
		});

		// Create the column for the display name value
		final TableViewerColumn displayNameColumn = createTableViewerColumn(tableViewer, titles[3], bounds[3]);
		displayNameColumn.setLabelProvider(nullLabelProvider);
		// Create the check box cell editor
		displayNameColumn.setEditingSupport(new NattableConfigurationEditingSupport(tableViewer, nullLabelProvider) {

			@Override
			protected void setValue(final Object element, final Object value) {
				if (element instanceof FeatureLabelProviderConfiguration) {
					((FeatureLabelProviderConfiguration) element).setDisplayName((Boolean) value);
				} else if (element instanceof OperationLabelProviderConfiguration) {
					((OperationLabelProviderConfiguration) element).setDisplayName((Boolean) value);
				}
			}

			@Override
			protected Object getValue(Object element) {
				return isDisplayName(element);
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				return new CheckboxCellEditor(table, SWT.CHECK | SWT.READ_ONLY);
			}

			@Override
			protected boolean canEdit(Object element) {
				return element instanceof FeatureLabelProviderConfiguration || element instanceof OperationLabelProviderConfiguration;
			}
		});

		// Create the column for the display type value
		final TableViewerColumn displayTypeColumn = createTableViewerColumn(tableViewer, titles[4], bounds[4]);
		displayTypeColumn.setLabelProvider(nullLabelProvider);
		// Create the check box cell editor
		displayTypeColumn.setEditingSupport(new NattableConfigurationEditingSupport(tableViewer, nullLabelProvider) {

			@Override
			protected void setValue(final Object element, final Object value) {
				if (element instanceof FeatureLabelProviderConfiguration) {
					((FeatureLabelProviderConfiguration) element).setDisplayType((Boolean) value);
				} else if (element instanceof OperationLabelProviderConfiguration) {
					((OperationLabelProviderConfiguration) element).setDisplayType((Boolean) value);
				}
			}

			@Override
			protected Object getValue(Object element) {
				return isDisplayType(element);
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				return new CheckboxCellEditor(table, SWT.CHECK | SWT.READ_ONLY);
			}

			@Override
			protected boolean canEdit(Object element) {
				return element instanceof FeatureLabelProviderConfiguration || element instanceof OperationLabelProviderConfiguration;
			}
		});

		// Create the column for the display multiplicity value
		final TableViewerColumn displayMultiplicityColumn = createTableViewerColumn(tableViewer, titles[5], bounds[5]);
		displayMultiplicityColumn.setLabelProvider(nullLabelProvider);
		// Create the check box cell editor
		displayMultiplicityColumn.setEditingSupport(new NattableConfigurationEditingSupport(tableViewer, nullLabelProvider) {

			@Override
			protected void setValue(final Object element, final Object value) {
				if (element instanceof FeatureLabelProviderConfiguration) {
					((FeatureLabelProviderConfiguration) element).setDisplayMultiplicity((Boolean) value);
				} else if (element instanceof OperationLabelProviderConfiguration) {
					((OperationLabelProviderConfiguration) element).setDisplayMultiplicity((Boolean) value);
				}
			}

			@Override
			protected Object getValue(Object element) {
				return isDisplayMultiplicity(element);
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				return new CheckboxCellEditor(table, SWT.CHECK | SWT.READ_ONLY);
			}

			@Override
			protected boolean canEdit(Object element) {
				return element instanceof FeatureLabelProviderConfiguration || element instanceof OperationLabelProviderConfiguration;
			}
		});

		// Create the column for the display is derived value
		final TableViewerColumn displayIsDerivedColumn = createTableViewerColumn(tableViewer, titles[6], bounds[6]);
		displayIsDerivedColumn.setLabelProvider(nullLabelProvider);
		// Create the check box cell editor
		displayIsDerivedColumn.setEditingSupport(new NattableConfigurationEditingSupport(tableViewer, nullLabelProvider) {

			@Override
			protected void setValue(final Object element, final Object value) {
				if (element instanceof FeatureLabelProviderConfiguration) {
					((FeatureLabelProviderConfiguration) element).setDisplayIsDerived((Boolean) value);
				}
			}

			@Override
			protected Object getValue(Object element) {
				return isDisplayIsDerived(element);
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				return new CheckboxCellEditor(table, SWT.CHECK | SWT.READ_ONLY);
			}

			@Override
			protected boolean canEdit(Object element) {
				return element instanceof FeatureLabelProviderConfiguration;
			}
		});

		// Redefine the paint item listener to manage custom display in tree table
		table.addListener(SWT.PaintItem, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (event.index > 0) {
					Item item = (Item) event.item;
					Image trailingImage = null;

					Object value = null;

					switch (event.index) {
					case 1:
						value = isDisplayIcon(item.getData());
						break;
					case 2:
						value = isDisplayLabel(item.getData());
						break;
					case 3:
						value = isDisplayName(item.getData());
						break;
					case 4:
						value = isDisplayType(item.getData());
						break;
					case 5:
						value = isDisplayMultiplicity(item.getData());
						break;
					case 6:
						value = isDisplayIsDerived(item.getData());
						break;
					default:
						break;
					}

					if (value instanceof Boolean) {
						trailingImage = (Boolean) value ? NattableConfigurationConstants.CHECKED : NattableConfigurationConstants.UNCHECKED;
					}

					if (null != trailingImage) {
						// Calculate the center position
						int itemWidth = table.getColumn(event.index).getWidth();
						int imageWidth = trailingImage.getBounds().width;
						int x = event.x + (itemWidth - imageWidth) / 2;
						int itemHeight = table.getItemHeight();
						int imageHeight = trailingImage.getBounds().height;
						int y = event.y + (itemHeight - imageHeight) / 2;

						// Draw the image
						event.gc.drawImage(trailingImage, x, y);
					}
				}
			}
		});
	}

	/**
	 * Get the display icon value of the element.
	 * 
	 * @param element
	 *            The element.
	 * @return Boolean value or <code>null</code>.
	 */
	protected Object isDisplayIcon(final Object element) {
		Object result = null;
		if (element instanceof ObjectLabelProviderConfiguration) {
			result = ((ObjectLabelProviderConfiguration) element).isDisplayIcon();
		} else if (element instanceof FeatureLabelProviderConfiguration) {
			result = ((FeatureLabelProviderConfiguration) element).isDisplayIcon();
		} else if (element instanceof OperationLabelProviderConfiguration) {
			result = ((OperationLabelProviderConfiguration) element).isDisplayIcon();
		}
		return result;
	}

	/**
	 * Get the display label value of the element.
	 * 
	 * @param element
	 *            The element.
	 * @return Boolean value or <code>null</code>.
	 */
	protected Object isDisplayLabel(final Object element) {
		Object result = null;
		if (element instanceof ObjectLabelProviderConfiguration) {
			result = ((ObjectLabelProviderConfiguration) element).isDisplayLabel();
		} else if (element instanceof FeatureLabelProviderConfiguration) {
			result = ((FeatureLabelProviderConfiguration) element).isDisplayLabel();
		} else if (element instanceof OperationLabelProviderConfiguration) {
			result = ((OperationLabelProviderConfiguration) element).isDisplayLabel();
		}
		return result;
	}

	/**
	 * Get the display name value of the element.
	 * 
	 * @param element
	 *            The element.
	 * @return Boolean value or <code>null</code>.
	 */
	protected Object isDisplayName(final Object element) {
		Object result = null;
		if (element instanceof FeatureLabelProviderConfiguration) {
			result = ((FeatureLabelProviderConfiguration) element).isDisplayName();
		} else if (element instanceof OperationLabelProviderConfiguration) {
			result = ((OperationLabelProviderConfiguration) element).isDisplayName();
		}
		return result;
	}

	/**
	 * Get the display type value of the element.
	 * 
	 * @param element
	 *            The element.
	 * @return Boolean value or <code>null</code>.
	 */
	protected Object isDisplayType(final Object element) {
		Object result = null;
		if (element instanceof FeatureLabelProviderConfiguration) {
			result = ((FeatureLabelProviderConfiguration) element).isDisplayType();
		} else if (element instanceof OperationLabelProviderConfiguration) {
			result = ((OperationLabelProviderConfiguration) element).isDisplayType();
		}
		return result;
	}

	/**
	 * Get the display multiplicity value of the element.
	 * 
	 * @param element
	 *            The element.
	 * @return Boolean value or <code>null</code>.
	 */
	protected Object isDisplayMultiplicity(final Object element) {
		Object result = null;
		if (element instanceof FeatureLabelProviderConfiguration) {
			result = ((FeatureLabelProviderConfiguration) element).isDisplayMultiplicity();
		} else if (element instanceof OperationLabelProviderConfiguration) {
			result = ((OperationLabelProviderConfiguration) element).isDisplayMultiplicity();
		}
		return result;
	}

	/**
	 * Get the display isDerived value of the element.
	 * 
	 * @param element
	 *            The element.
	 * @return Boolean value or <code>null</code>.
	 */
	protected Object isDisplayIsDerived(final Object element) {
		Object result = null;
		if (element instanceof FeatureLabelProviderConfiguration) {
			result = ((FeatureLabelProviderConfiguration) element).isDisplayIsDerived();
		}
		return result;
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
	protected TableViewerColumn createTableViewerColumn(final TableViewer tableViewer, final String title, final int width) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(width);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	/**
	 * Create the list of axis manager identifiers (managing the simple axis manager name and the others with the full identifier).
	 * 
	 * @param requiredProposedAxisManagers
	 *            The required axis managers.
	 * @param knownAxisCollection
	 *            The axis managers registered in the plugins extension.
	 * @return The list of axis manager identifiers.
	 */
	protected List<String> createAxisManagerIdItems(final List<String> requiredProposedAxisManagers, final Collection<String> knownAxisCollection) {
		final List<String> input = new ArrayList<String>();

		// Add the simple axis manager identifier
		for (final String requiredAxisManager : requiredProposedAxisManagers) {
			input.add(requiredAxisManager);
		}

		// Add the axis manager identifiers from the registered axis managers
		for (final String knownAxis : knownAxisCollection) {

			// The known axis will be added only if
			// - the input list does not contain the known axis
			// - and there is no added axis which contains the known axis
			// For example if "EMF Feature (org.eclipse.papyrus.infra.emf.nattable.feature.axis.manager)" is already in the input list,
			// "org.eclipse.papyrus.infra.emf.nattable.feature.axis.manager" will not be added
			if (!input.contains(knownAxis)) {
				boolean isKnownAxisAdded = false;

				for (final String addedAxis : input) {
					if (addedAxis.contains(knownAxis)) {
						isKnownAxisAdded = true;
						break;
					}
				}

				if (!isKnownAxisAdded) {
					input.add(knownAxis);
				}
			}
		}

		return input;
	}

	/**
	 * This allows to manage the label provider configuration (create, get and/or remove) and set it to the current axis manager representation in parameter.
	 * 
	 * @param axisManagerRepresentation
	 *            The current axis manager representation.
	 */
	protected void manageLabelProviderConfiguration(final AxisManagerRepresentation axisManagerRepresentation) {
		final String axisManagerId = axisManagerRepresentation.getAxisManagerId();

		if (null != axisManagerId && !axisManagerId.isEmpty()) {
			// No label provider configuration is affected to the axis manager representation, create or get an existing and set it to the representation
			if (null == axisManagerRepresentation.getHeaderLabelConfiguration()) {
				// set the new label provider configuration
				axisManagerRepresentation.setHeaderLabelConfiguration(createLabelProviderConfiguration(axisManagerId));
			} else {
				final ILabelProviderConfiguration existingLabelProviderConfiguration = axisManagerRepresentation.getHeaderLabelConfiguration();
				final String typeOfLabelProviderConfiguration = getTypeOfLabelProviderConfiguration(axisManagerId);

				// Check if it needs to set a new label provider configuration depending to the current label provider configuration type
				if (!existingLabelProviderConfiguration.getClass().getSimpleName().equals(typeOfLabelProviderConfiguration)) {
					// Remove the current label provider configuration
					removeLabelProviderConfiguration(existingLabelProviderConfiguration, axisManagerRepresentation);
					// Set it the new one
					axisManagerRepresentation.setHeaderLabelConfiguration(createLabelProviderConfiguration(axisManagerId));
				}
			}
		}
	}

	/**
	 * This allows to create (or get if already existing) the label provider configuration corresponding to the axis manager identifier.
	 * 
	 * @param axisManagerId
	 *            The axis manager identifier.
	 * @return The created (or gotten) label provider configuration.
	 */
	protected ILabelProviderConfiguration createLabelProviderConfiguration(final String axisManagerId) {
		ILabelProviderConfiguration createdLabelProviderConfiguration = null;

		final String typeOfLabelProviderConfiguration = getTypeOfLabelProviderConfiguration(axisManagerId);

		// Try to check if a label provider configuration with the same type is already existing
		final Iterator<ILabelProviderConfiguration> existingLabelProviderConfigurationsIterator = headerAxisConfiguration.getOwnedLabelConfigurations().iterator();
		while (existingLabelProviderConfigurationsIterator.hasNext() && null == createdLabelProviderConfiguration) {
			final ILabelProviderConfiguration existingLabelProviderConfiguration = existingLabelProviderConfigurationsIterator.next();
			if (existingLabelProviderConfiguration.getClass().getSimpleName().equals(typeOfLabelProviderConfiguration)) {
				createdLabelProviderConfiguration = existingLabelProviderConfiguration;
			}
		}

		// No label provider configuration with the same type that needed exists, so create it corresponding to the needed type
		if (null == createdLabelProviderConfiguration) {
			if (FeatureLabelProviderConfigurationImpl.class.getSimpleName().equals(typeOfLabelProviderConfiguration)) {
				createdLabelProviderConfiguration = NattablelabelproviderFactory.eINSTANCE.createFeatureLabelProviderConfiguration();
			} else if (OperationLabelProviderConfigurationImpl.class.getSimpleName().equals(typeOfLabelProviderConfiguration)) {
				createdLabelProviderConfiguration = NattablelabelproviderFactory.eINSTANCE.createOperationLabelProviderConfiguration();
			} else {
				createdLabelProviderConfiguration = NattablelabelproviderFactory.eINSTANCE.createObjectLabelProviderConfiguration();
			}

			// Add the label provider configuration to the header axis configuration
			headerAxisConfiguration.getOwnedLabelConfigurations().add(createdLabelProviderConfiguration);

			labelProviderConfigurationTableViewer.refresh();
		}

		return createdLabelProviderConfiguration;
	}

	/**
	 * This allows to remove the label provider configuration from the header axis configuration when it is not always used.
	 * 
	 * @param labelProviderConfiguration
	 *            The label provider configuration to remove.
	 * @param currentRepresentation
	 *            The current axis manager representation.
	 */
	protected void removeLabelProviderConfiguration(final ILabelProviderConfiguration labelProviderConfiguration, final AxisManagerRepresentation currentRepresentation) {
		if (null != labelProviderConfiguration) {
			boolean canBeRemoved = true;

			// check if the label provider configuration is not used in another axis manager representation
			final Iterator<AxisManagerRepresentation> axisManagerIterator = headerAxisConfiguration.getAxisManagers().iterator();
			while (axisManagerIterator.hasNext() && canBeRemoved) {
				final AxisManagerRepresentation axisManager = axisManagerIterator.next();
				if (!axisManager.equals(currentRepresentation)) {
					canBeRemoved = !labelProviderConfiguration.equals(axisManager.getHeaderLabelConfiguration());
				}
			}

			// The label provider configuration to remove is not used in another axis manager representation, so remove it from the header axis configuration
			if (canBeRemoved) {
				headerAxisConfiguration.getOwnedLabelConfigurations().remove(labelProviderConfiguration);

				labelProviderConfigurationTableViewer.refresh();
			}
		}
	}

	/**
	 * Get the label provider configuration class name corresponding to the axis manager.
	 * 
	 * @param axisManagerId
	 *            The axis manager identifier.
	 * @return The label provider configuration class name corresponding to the axis manager.
	 */
	protected String getTypeOfLabelProviderConfiguration(final String axisManagerId) {
		// By default, we need to set the object label provider configuration
		String result = ObjectLabelProviderConfigurationImpl.class.getSimpleName();
		if (NameSimplifier.labelProviderConfigurationByAxisManager.containsKey(axisManagerId)) {
			result = NameSimplifier.labelProviderConfigurationByAxisManager.get(axisManagerId);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
	 */
	@Override
	public boolean isPageComplete() {
		boolean isComplete = true;

		// The axis manager representation have to be filled
		final Iterator<AxisManagerRepresentation> axisManagerIterator = headerAxisConfiguration.getAxisManagers().iterator();
		while (axisManagerIterator.hasNext() && isComplete) {
			final AxisManagerRepresentation axisManager = axisManagerIterator.next();
			isComplete = null != axisManager.getAxisManagerId() && !axisManager.getAxisManagerId().isEmpty()
					&& null != axisManager.getLabelProviderContext() && !axisManager.getLabelProviderContext().isEmpty()
					&& null != axisManager.getHeaderLabelConfiguration();
		}

		return isComplete;
	}

}
