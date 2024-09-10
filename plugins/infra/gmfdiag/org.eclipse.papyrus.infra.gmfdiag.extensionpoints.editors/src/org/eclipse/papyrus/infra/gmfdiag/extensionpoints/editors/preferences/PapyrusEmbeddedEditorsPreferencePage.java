/****************************************************************************
 * Copyright (c) 2008 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *	Remi Schnekenburger (CEA LIST) - Initial API and implementation
 *	Gabriel Pascual (ALL4TEC) gabriel.pascual -  Bug 441962
 *	Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 497289
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.Activator;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IDirectEditorConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IDirectEditorConstraint;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.definition.DirectEditorExtensionPoint;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.definition.IDirectEditorConfigurationIds;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.definition.IDirectEditorExtensionPoint;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.preferences.provider.DirectEditorContentProvider;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.preferences.provider.DirectEditorLabelProvider;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.preferences.provider.DirectEditorTreeItem;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.utils.IDirectEditorsIds;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * The file editors page presents the collection of file names and extensions for which the user has
 * registered editors. It also lets the user add new internal or external (program) editors for a
 * given file name and extension.
 *
 * The user can add an editor for either a specific file name and extension (e.g. report.doc), or
 * for all file names of a given extension (e.g. *.doc)
 *
 * The set of registered editors is tracked by the EditorRegistery available from the workbench
 * plugin.
 */
public class PapyrusEmbeddedEditorsPreferencePage extends PreferencePage implements IWorkbenchPreferencePage, Listener {

	/**
	 *
	 */
	private static final String PRIORITY_COLUMN_TITLE = "Priority";

	/** The Constant ELEMENTS_TO_EDIT_VIEWER_LABEL. */
	private static final String ELEMENTS_TO_EDIT_VIEWER_LABEL = "Elements to edit";

	/** The Constant DEFAULT_BUTTON_LABEL. */
	private static final String DEFAULT_BUTTON_LABEL = "Default";

	/** The Constant DIRECT_EDITOR_PART_LABEL. */
	private static final String DIRECT_EDITOR_PART_LABEL = "Associated editor";

	/** The Constant PREFERENCE_KEY_SEPARATOR. */
	private static final String PREFERENCE_KEY_SEPARATOR = "."; //$NON-NLS-1$

	/** The Constant DEFAULT_EDITOR_LABEL. */
	protected static final String DEFAULT_EDITOR_LABEL = " (Default Editor)"; //$NON-NLS-1$

	protected TableViewer editorTable;

	protected Button defaultEditorButton;

	protected Label editorLabel;

	protected IWorkbench workbench;

	private TreeViewer elementTypeViewer;

	/**
	 * Creates the page's UI content.
	 */
	@Override
	protected Control createContents(Composite parent) {


		// define container & its layout
		Composite pageComponent = createMainComposite(parent);

		// layout the top table & its buttons
		createElementTypePart(pageComponent);


		// layout the bottom table & its buttons
		createDirectEditorPart(pageComponent);

		createButtonsPanel(pageComponent);


		fillEditorTree();

		fillEditorTable();
		updateEnabledState();

		applyDialogFont(pageComponent);

		return pageComponent;
	}

	/**
	 * Creates the buttons panel.
	 *
	 * @param parent
	 *            the parent
	 */
	private void createButtonsPanel(Composite parent) {
		Composite groupComponent;
		GridLayout groupLayout;
		GridData data;
		groupComponent = new Composite(parent, SWT.NULL);
		groupLayout = new GridLayout();
		groupLayout.marginWidth = 0;
		groupLayout.marginHeight = 0;
		groupComponent.setLayout(groupLayout);
		data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		groupComponent.setLayoutData(data);

		defaultEditorButton = new Button(groupComponent, SWT.PUSH);
		defaultEditorButton.setText(DEFAULT_BUTTON_LABEL);
		defaultEditorButton.addListener(SWT.Selection, this);
		setButtonLayoutData(defaultEditorButton);
	}

	/**
	 * Creates the direct editor part.
	 *
	 * @param pageComponent
	 *            the page component
	 */
	private void createDirectEditorPart(Composite pageComponent) {

		editorLabel = new Label(pageComponent, SWT.LEFT);
		editorLabel.setText(DIRECT_EDITOR_PART_LABEL);

		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.horizontalSpan = 2;
		editorLabel.setLayoutData(data);

		editorTable = new TableViewer(pageComponent, SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER);
		editorTable.getTable().addListener(SWT.Selection, this);
		editorTable.getTable().addListener(SWT.DefaultSelection, this);
		editorTable.getTable().setLinesVisible(true);
		editorTable.getTable().setHeaderVisible(true);
		editorTable.setContentProvider(ArrayContentProvider.getInstance());

		createEditorNameColumn();
		createEditorPriorityColumn();


		data = new GridData(GridData.FILL_BOTH);
		data.heightHint = editorTable.getTable().getItemHeight() * 7;
		editorTable.getTable().setLayoutData(data);
	}

	/**
	 * Creates the editor priority column.
	 */
	private void createEditorPriorityColumn() {
		TableViewerColumn editorPriorityColumn = new TableViewerColumn(editorTable, SWT.NONE);
		editorPriorityColumn.getColumn().setWidth(200);
		editorPriorityColumn.getColumn().setText(PRIORITY_COLUMN_TITLE);
		editorPriorityColumn.setLabelProvider(new ColumnLabelProvider() {
			/**
			 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
			 *
			 * @param element
			 * @return
			 */
			@Override
			public String getText(Object element) {
				String priorityLabel = "<Unknown>";
				if (element instanceof IDirectEditorExtensionPoint) {
					switch (((IDirectEditorExtensionPoint) element).getPriority()) {
					case 0:
						priorityLabel = IDirectEditorConfigurationIds.PRIORITY_HIGHEST;
						break;
					case 1:
						priorityLabel = IDirectEditorConfigurationIds.PRIORITY_HIGH;
						break;
					case 2:
						priorityLabel = IDirectEditorConfigurationIds.PRIORITY_MEDIUM;
						break;
					case 3:
						priorityLabel = IDirectEditorConfigurationIds.PRIORITY_LOW;
						break;
					case 4:
						priorityLabel = IDirectEditorConfigurationIds.PRIORITY_LOWEST;
						break;

					default:
						break;
					}
					;
				}
				return priorityLabel;
			}
		});

		PriorityEditorEditingSupport editingSupport = new PriorityEditorEditingSupport(editorTable);
		editorPriorityColumn.setEditingSupport(editingSupport);
	}

	/**
	 * Creates the editor name column.
	 */
	private void createEditorNameColumn() {
		TableViewerColumn editorNameColumn = new TableViewerColumn(editorTable, SWT.NONE);
		editorNameColumn.getColumn().setWidth(200);
		editorNameColumn.getColumn().setText("Name");
		editorNameColumn.setLabelProvider(new ColumnLabelProvider() {
			/**
			 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
			 *
			 * @param element
			 * @return
			 */
			@Override
			public String getText(Object element) {
				String label = "<Unknown>";
				if (element instanceof IDirectEditorExtensionPoint) {
					String elementType = getSelectedElementType();
					String preferedLanguage = getPreferenceStore().getString(IDirectEditorsIds.EDITOR_FOR_ELEMENT + elementType);
					// retrieves if this editor is the default one or not.
					label = ((IDirectEditorExtensionPoint) element).getLanguage();
					if (preferedLanguage.equals(((IDirectEditorExtensionPoint) element).getLanguage())) {
						label += DEFAULT_EDITOR_LABEL;
					}
				}
				return label;
			}

		});
	}

	/**
	 * Creates the element type part.
	 *
	 * @param pageComponent
	 *            the page component
	 */
	private void createElementTypePart(Composite pageComponent) {
		Label label = new Label(pageComponent, SWT.LEFT);
		label.setText(ELEMENTS_TO_EDIT_VIEWER_LABEL);
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.horizontalSpan = 2;
		label.setLayoutData(data);

		elementTypeViewer = new TreeViewer(pageComponent);
		elementTypeViewer.setContentProvider(new DirectEditorContentProvider());
		elementTypeViewer.setLabelProvider(new DirectEditorLabelProvider());
		elementTypeViewer.getTree().addListener(SWT.Selection, this);
		elementTypeViewer.getTree().addListener(SWT.DefaultSelection, this);

		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalAlignment = GridData.FILL;
		data.heightHint = elementTypeViewer.getTree().getItemHeight() * 10; // ten lines
		// shown
		elementTypeViewer.getTree().setLayoutData(data);

	}

	/**
	 * Creates the main composite.
	 *
	 * @param parent
	 *            the parent
	 * @return the composite
	 */
	private Composite createMainComposite(Composite parent) {
		Composite pageComponent = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		pageComponent.setLayout(layout);
		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		pageComponent.setLayoutData(data);
		return pageComponent;
	}



	/**
	 * Hook method to get a page specific preference store. Reimplement this method if a page don't
	 * want to use its parent's preference store.
	 */
	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return Activator.getDefault().getPreferenceStore();
	}

	/**
	 * Fill editor table.
	 */
	protected void fillEditorTable() {
		editorTable.getTable().removeAll();

		List<IDirectEditorExtensionPoint> editors = getAssociatedEditors();
		editors.add(SimpleDirectEditorExtension.getInstance());
		editorTable.setInput(editors.toArray());

	}

	/**
	 * Fill editor tree.
	 */
	private void fillEditorTree() {
		Map<String, List<IDirectEditorExtensionPoint>> elements = new HashMap<String, List<IDirectEditorExtensionPoint>>();

		// Populate the table with the items
		IDirectEditorExtensionPoint[] extensionPoints = DirectEditorExtensionPoint.getInstance().getDirectEditorConfigurations();
		List<IDirectEditorExtensionPoint> configurations;
		for (IDirectEditorExtensionPoint extensionPoint : extensionPoints) {
			if (!elements.containsKey(extensionPoint.getObjectToEdit())) {
				// no configuration yet for this element.
				configurations = new ArrayList<IDirectEditorExtensionPoint>();
			} else {
				configurations = elements.get(extensionPoint.getObjectToEdit());
			}
			configurations.add(extensionPoint);
			// replace configuration list
			elements.put(extensionPoint.getObjectToEdit(), configurations);
		}

		// Build the input to display
		Set<String> keys = elements.keySet();
		List<DirectEditorTreeItem> preferencesItemsList = new ArrayList<DirectEditorTreeItem>(keys.size());

		for (String key : keys) {
			preferencesItemsList.add(new DirectEditorTreeItem(elements.get(key)));
		}
		elementTypeViewer.setInput(preferencesItemsList);

		// Set selection
		elementTypeViewer.setSelection(new StructuredSelection(preferencesItemsList.get(0)));
	}


	/**
	 * Gets the selected element type.
	 *
	 * @return the selected element type
	 */
	protected String getSelectedElementType() {
		String selectedElementType = null;
		ISelection selection = elementTypeViewer.getSelection();
		if (!selection.isEmpty()) {
			if (selection instanceof IStructuredSelection) {
				// Tree is single select
				Object selectedElement = ((IStructuredSelection) selection).getFirstElement();
				if (selectedElement instanceof DirectEditorTreeItem) {

					// Preference key is the qualified name of edited Meta Class
					selectedElementType = ((DirectEditorTreeItem) selectedElement).getMetaClassToEdit();
				} else if (selectedElement instanceof IDirectEditorConstraint) {
					Object parentElement = getParentTreeItem();
					if (parentElement instanceof DirectEditorTreeItem) {

						// Preference key is the qualified name of edited Meta Class with Constraint name
						selectedElementType = ((DirectEditorTreeItem) parentElement).getMetaClassToEdit() + PREFERENCE_KEY_SEPARATOR + ((IDirectEditorConstraint) selectedElement).getLabel();
					}
				}

			}
		}
		return selectedElementType;
	}

	/**
	 * Gets the parent tree item.
	 *
	 * @return the parent tree item
	 */
	private Object getParentTreeItem() {
		// Tree is single select
		TreeItem parentItem = elementTypeViewer.getTree().getSelection()[0].getParentItem();
		Object parentElement = parentItem.getData();
		return parentElement;
	}

	/**
	 * Returns all {@link DirectEditorExtensionPoint} for the current selected element type
	 *
	 * @return all {@link DirectEditorExtensionPoint} for the current selected element type or <code>null</code.
	 */
	protected List<IDirectEditorExtensionPoint> getAssociatedEditors() {
		List<IDirectEditorExtensionPoint> associatedEditors = new ArrayList<IDirectEditorExtensionPoint>();

		if (getSelectedElementType() != null) {

			ISelection selection = elementTypeViewer.getSelection();

			// Handle the selection
			if (selection instanceof IStructuredSelection) {
				Object selectedElement = ((IStructuredSelection) selection).getFirstElement();
				if (selectedElement instanceof IDirectEditorConstraint) {

					Object parentElement = getParentTreeItem();
					if (parentElement instanceof DirectEditorTreeItem) {
						associatedEditors.addAll(((DirectEditorTreeItem) parentElement).getConstrainedEditor((IDirectEditorConstraint) selectedElement));
					}

				} else {
					DirectEditorTreeItem treeItem = (DirectEditorTreeItem) selectedElement;
					associatedEditors.addAll(treeItem.getConfigurations());
				}
			}
		}
		return associatedEditors;
	}

	public void handleEvent(Event event) {
		/*
		 * if (event.widget == upEditorButton) { promptForEditor(); } else if (event.widget ==
		 * downEditorButton) { removeSelectedEditor(); } else
		 */if (event.widget == defaultEditorButton) {
			setSelectedEditorAsDefault();
		} else if (event.widget == elementTypeViewer.getTree()) {
			fillEditorTable();
		}

		updateEnabledState();

	}

	/**
	 * @see IWorkbenchPreferencePage
	 */
	public void init(IWorkbench aWorkbench) {
		this.workbench = aWorkbench;
		noDefaultAndApplyButton();
	}

	/**
	 * Add the selected editor to the default list.
	 */
	public void setSelectedEditorAsDefault() {

		ISelection selection = editorTable.getSelection();
		IDirectEditorExtensionPoint extensionPoint = null;
		if (!selection.isEmpty()) {
			// First change the label of the old default
			// Now set the new default
			if (selection instanceof IStructuredSelection) {
				Object selectedElement = ((IStructuredSelection) selection).getFirstElement();
				if (selectedElement instanceof IDirectEditorExtensionPoint) {
					extensionPoint = (IDirectEditorExtensionPoint) selectedElement;
				}
			}
			// retrieve current object to edit name
			getPreferenceStore().setValue(IDirectEditorsIds.EDITOR_FOR_ELEMENT + getSelectedElementType(), (extensionPoint != null) ? extensionPoint.getLanguage() : IDirectEditorsIds.SIMPLE_DIRECT_EDITOR);
			editorTable.refresh();


		}
	}

	/**
	 * Update the enabled state.
	 */
	public void updateEnabledState() {
		// Update enabled state
		boolean resourceTypeSelected = elementTypeViewer.getTree().getSelection() != null;
		boolean editorSelected = editorTable.getTable().getSelectionIndex() != -1;

		editorLabel.setEnabled(resourceTypeSelected);
		defaultEditorButton.setEnabled(editorSelected);
	}

	/**
	 * Extension point for Simple Editor.
	 */
	private static class SimpleDirectEditorExtension implements IDirectEditorExtensionPoint {

		public static IDirectEditorExtensionPoint getInstance() {
			return new SimpleDirectEditorExtension();
		}

		/**
		 * Constructor.
		 *
		 */
		private SimpleDirectEditorExtension() {
			priority = 3;
		}

		private Integer priority;

		public void setPriority(Integer priority) {
			this.priority = priority;
		}

		public void setDirectEditorConfiguration(IDirectEditorConfiguration directEditorConfiguration) {

		}

		public Integer getPriority() {
			return priority;
		}

		public String getObjectToEdit() {
			return null;
		}

		public Class<? extends EObject> getObjectClassToEdit() {
			return null;
		}

		public String getLanguage() {
			return IDirectEditorsIds.SIMPLE_DIRECT_EDITOR;
		}

		public Image getIcon() {
			return null;
		}

		public IDirectEditorConfiguration getDirectEditorConfiguration() {
			return null;
		}

		public IDirectEditorConstraint getAdditionalConstraint() {
			return null;
		}

		public boolean isSuperType() {
			return false;
		}
	};

}
