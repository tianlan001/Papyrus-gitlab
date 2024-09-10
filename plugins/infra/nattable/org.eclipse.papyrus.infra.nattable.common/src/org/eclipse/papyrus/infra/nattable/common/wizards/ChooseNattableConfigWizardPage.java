/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Juan Cadavid (CEA LIST) juan.cadavid@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 482443
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.common.wizards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.papyrus.infra.nattable.common.Activator;
import org.eclipse.papyrus.infra.nattable.common.helper.TableViewPrototype;
import org.eclipse.papyrus.infra.nattable.common.messages.Messages;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.infra.nattable.nattableconfiguration.NattableConfigurationRegistry;
import org.eclipse.papyrus.infra.nattable.representation.PapyrusTable;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * Wizard page (unique) to select the desired table configurations.
 *
 */
public class ChooseNattableConfigWizardPage extends WizardPage {

	/**
	 * The selected view prototypes in the wizard.
	 */
	private Map<ViewPrototype, Integer> selectedViewPrototypes = new HashMap<ViewPrototype, Integer>();

	/**
	 * The table name by view prototype.
	 */
	private Map<ViewPrototype, String> tableNames = new HashMap<ViewPrototype, String>();

	/**
	 * The context of the future table.
	 */
	private EObject context;

	/**
	 * The checked image for the table to select the view prototypes.
	 */
	private static final Image CHECKED = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(Activator.PLUGIN_ID, "icons/checked.gif"); //$NON-NLS-1

	/**
	 * The unchecked image for the table to select the view prototypes.
	 */
	private static final Image UNCHECKED = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(Activator.PLUGIN_ID, "icons/unchecked.gif"); //$NON-NLS-1$

	/**
	 * Constructor.
	 *
	 * @param pageName
	 *            The page name.
	 * @param context
	 *            The context of the table to create.
	 */
	protected ChooseNattableConfigWizardPage(final String pageName, final EObject context) {
		super(pageName);
		this.context = context;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(final Composite parent) {
		setPageComplete(false);
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;

		createTableViewer(container);
		setControl(container);
	}

	/**
	 * Create the table to display the catalog of existing view prototypes corresponding to the tables viewpoint.
	 *
	 * @param container
	 *            The container composite.
	 */
	public void createTableViewer(final Composite container) {
		final TableViewer viewer = new TableViewer(container, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		viewer.setContentProvider(new ArrayContentProvider());
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		ViewerColumn colCheckbox = createTableViewerColumn("", 23, viewer); //$NON-NLS-1$
		Collection<ViewPrototype> viewPrototypes = loadConfigs();

		// Calculate the TableConfiguration for the icon and the description of table
		final Map<ViewPrototype, TableConfiguration> tableConfigurations = new HashMap<ViewPrototype, TableConfiguration>(viewPrototypes.size());
		for (ViewPrototype viewPrototype : viewPrototypes) {
			// TODO : The following code line must be replaced by TableEditorCreationHelper.getTableConfigurationURI when the API for table creation is merged
			tableConfigurations.put(viewPrototype, getTableConfiguration((TableViewPrototype) viewPrototype));
		}

		colCheckbox.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(final Object element) {
				return ""; //$NON-NLS-1$
			}

			@Override
			public Image getImage(final Object element) {
				if (selectedViewPrototypes.containsKey(element)) {
					return CHECKED;
				} else {
					return UNCHECKED;
				}
			}

		});
		colCheckbox.setEditingSupport(new EditingSupport(viewer) {

			private CheckboxCellEditor checkboxCellEditor;

			@Override
			protected void setValue(final Object element, final Object value) {
				if (Boolean.TRUE.equals(checkboxCellEditor.getValue())) {
					selectedViewPrototypes.put((ViewPrototype) element, 1);
				} else {
					selectedViewPrototypes.remove(element);
				}
				viewer.update(element, null);
			}

			@Override
			protected Object getValue(final Object element) {
				return selectedViewPrototypes.containsKey(element);
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				checkboxCellEditor = new CheckboxCellEditor(container, SWT.CHECK | SWT.READ_ONLY);
				return checkboxCellEditor;
			}

			@Override
			protected boolean canEdit(final Object element) {
				return true;
			}
		});
		TableViewerColumn colType = createTableViewerColumn(Messages.ChooseNattableConfigWizardPage_6, 250, viewer);
		colType.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(final Object element) {
				ViewPrototype viewPrototype = (ViewPrototype) element;
				return viewPrototype.getRepresentationKind().getImplementationID();
			}

			@Override
			public Image getImage(final Object element) {
				final ViewPrototype viewPrototype = (ViewPrototype) element;
				final TableConfiguration tableConfiguration = tableConfigurations.get(viewPrototype);
				Image image = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(tableConfiguration.getIconPath());
				return image;
			}


		});

		TableViewerColumn colName = createTableViewerColumn(Messages.ChooseNattableConfigWizardPage_7, 200, viewer);
		colName.setEditingSupport(new EditingSupport(viewer) {

			@Override
			protected void setValue(final Object element, final Object value) {
				tableNames.put((ViewPrototype) element, (String) value);
				viewer.update(element, null);
			}

			@Override
			protected Object getValue(final Object element) {
				if (tableNames.containsKey(element)) {
					return tableNames.get(element);
				}
				return ""; //$NON-NLS-1$
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				return new TextCellEditor(viewer.getTable());
			}

			@Override
			protected boolean canEdit(final Object element) {
				return true;
			}
		});
		colName.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(final Object element) {
				return tableNames.get(element);
			}
		});

		TableViewerColumn colQuantity = createTableViewerColumn(Messages.ChooseNattableConfigWizardPage_9, 30, viewer);
		colQuantity.setEditingSupport(new EditingSupport(viewer) {

			@Override
			protected void setValue(final Object element, final Object value) {
				// When the quantity is not filled, only one table must be created
				if (value.toString().isEmpty()) {
					selectedViewPrototypes.put((ViewPrototype) element, 1);
				} else {
					selectedViewPrototypes.put((ViewPrototype) element, Integer.parseInt((String) value));
				}
				viewer.update(element, null);
			}

			@Override
			protected Object getValue(final Object element) {
				if (selectedViewPrototypes.containsKey(element)) {
					return selectedViewPrototypes.get(element).toString();
				}
				return "0"; //$NON-NLS-1$
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				TextCellEditor textCellEditor = new TextCellEditor(viewer.getTable());
				textCellEditor.setValidator(new ICellEditorValidator() {

					@Override
					public String isValid(final Object value) {
						if (!(value instanceof Integer)) {
							return null;
						}
						return value.toString();
					}
				});
				return textCellEditor;
			}

			@Override
			protected boolean canEdit(final Object element) {
				return true;
			}
		});
		colQuantity.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(final Object element) {
				if (selectedViewPrototypes.containsKey(element)) {
					return selectedViewPrototypes.get(element).toString();
				}
				return ""; //$NON-NLS-1$
			}
		});

		TableViewerColumn colDescription = createTableViewerColumn(Messages.ChooseNattableConfigWizardPage_12, 400, viewer);
		colDescription.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(final Object element) {
				final ViewPrototype viewPrototype = (ViewPrototype) element;
				final TableConfiguration tableConfiguration = tableConfigurations.get(viewPrototype);
				return tableConfiguration.getDescription();
			}

		});
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(final SelectionChangedEvent event) {
				if (selectedViewPrototypes.size() > 0) {
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}
		});
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);
		viewer.setInput(viewPrototypes);
	}

	/**
	 * This allows to create a table viewer column.
	 *
	 * @param title
	 *            The title of the wizard page.
	 * @param bound
	 *            The width.
	 * @param viewer
	 *            The table viewer.
	 * @return The created table viewer column.
	 */
	private TableViewerColumn createTableViewerColumn(final String title, final int bound, final TableViewer viewer) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(false);
		return viewerColumn;
	}

	/**
	 * Get configurations from the configuration registry with their default names
	 *
	 * @return
	 */
	public Collection<ViewPrototype> loadConfigs() {
		final List<ViewPrototype> viewPrototypes = new ArrayList<ViewPrototype>();

		// build a list of all the available prototypes corresponding to the context
		for (final ViewPrototype proto : PolicyChecker.getFor(context).getAllPrototypes()) {
			if (proto.getRepresentationKind() instanceof PapyrusTable) {
				if (NattableConfigurationRegistry.INSTANCE.canCreateTable(proto.getImplementation(), context).isOK()) {
					viewPrototypes.add(proto);
				}
			}
		}

		// sort them
		Collections.sort(viewPrototypes, new ViewPrototype.Comp());

		return viewPrototypes;
	}

	/**
	 * TODO : To remove when the API for table creation is merged
	 * 
	 * @param viewPrototype
	 *            a view {@link TableViewPrototype}, must not be <code>null</code>
	 * @return
	 * 		the {@link URI} of the nattable configuration, or <code>null</code> if not found
	 */
	private TableConfiguration getTableConfiguration(final TableViewPrototype viewPrototype) {
		if (viewPrototype.getRepresentationKind() instanceof PapyrusTable) {
			PapyrusTable papyrusTable = (PapyrusTable) viewPrototype.getRepresentationKind();
			return papyrusTable.getConfiguration();
		}
		return null;
	}

	/**
	 * Getter for selected view prototypes.
	 *
	 * @return The selected view prototypes.
	 */
	public Map<ViewPrototype, Integer> getSelectedViewPrototypes() {
		return selectedViewPrototypes;
	}


	/**
	 * Getter for the selected table names.
	 *
	 * @return The selected table names.
	 */
	public Map<ViewPrototype, String> getTableNames() {
		return tableNames;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.DialogPage#dispose()
	 */
	@Override
	public void dispose() {
		if (null != selectedViewPrototypes) {
			selectedViewPrototypes.clear();
			selectedViewPrototypes = null;
		}

		if (null != tableNames) {
			tableNames.clear();
			tableNames = null;
		}
		super.dispose();
	}


}
