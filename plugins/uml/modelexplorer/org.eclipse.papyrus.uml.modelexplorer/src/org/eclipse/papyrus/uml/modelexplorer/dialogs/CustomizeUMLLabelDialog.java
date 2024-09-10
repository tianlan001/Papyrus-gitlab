/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) - mickael.adam@all4tec.net - Bug 500219 - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.modelexplorer.dialogs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.papyrus.uml.modelexplorer.Activator;
import org.eclipse.papyrus.uml.modelexplorer.messages.Messages;
import org.eclipse.papyrus.uml.modelexplorer.preferences.CustomizableLabelPreferences;
import org.eclipse.papyrus.uml.tools.profile.definition.LabelStylersEnum;
import org.eclipse.papyrus.uml.tools.profile.definition.LabelTypesEnum;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.dialogs.SelectionDialog;

/**
 * A dialog to customize UML Label of model explorer.
 *
 */
public final class CustomizeUMLLabelDialog extends SelectionDialog implements SelectionListener {

	/** The Predefined customization "Name and Stereotype". */
	private static final String CUSTO_NAME_STEREOTYPE = "Name and Stereotype";//$NON-NLS-1$

	/** The Predefined customization "Stereotype, Metaclass and Name". */
	private static final String CUSTO_STEREOTYPE_METACLASS_NAME = "Stereotype, Metaclass and Name";//$NON-NLS-1$

	/** The Predefined customization "Name". */
	protected static final String CUSTO_NAME = "Name";//$NON-NLS-1$

	/** The Predefined customization "Customized". */
	protected static final String CUSTO_CUSTOM = "Customized";//$NON-NLS-1$

	/** The table viewer for entries. */
	protected TableViewer tableViewer;

	/** The Add control. */
	protected Button add;

	/** The Remove control. */
	protected Button remove;

	/** The Up control. */
	protected Button up;

	/** The Down control. */
	protected Button down;

	/** A Composite containing the different control buttons (Add, remove, ...). */
	protected Composite controlsSection;

	protected List<String> predefinedCustoList = new ArrayList<String>() {
		{
			add(CUSTO_CUSTOM);
			add(CUSTO_NAME);
			add(CUSTO_STEREOTYPE_METACLASS_NAME);
			add(CUSTO_NAME_STEREOTYPE);
		}
	};

	protected ComboViewer predefinedCustoViewer;

	/**
	 * Constructor.
	 */
	public CustomizeUMLLabelDialog(final Shell parentShell) {
		super(parentShell);
		setTitle(Messages.CustomizeUMLLabelDialog_Title);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		// Create the label
		Label label = new Label(composite, SWT.NONE);
		label.setText(Messages.CustomizeUMLLabelDialog_dialogLabel);

		// Create the control section containing buttons
		createStylesTablePart(composite);

		// Create buttons
		createListControls();

		// Create Predefined customization composite
		createLoadPredefinedCustoPart(composite);


		// Update buttons
		updateControls();

		return composite;
	}

	/**
	 * Create the control section containing buttons.
	 */
	protected void createStylesTablePart(final Composite composite) {
		// Create the control section containing buttons
		controlsSection = new Composite(composite, SWT.NONE);
		controlsSection.setLayout(new FillLayout());
		controlsSection.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));

		// Create the table viewer
		tableViewer = new TableViewer(composite, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());

		// Setthe table
		Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		// Create columns
		createColumns(tableViewer);

		// Set the input
		tableViewer.setInput(getEntriesFromPreferences());

		// define layout for the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.heightHint = 150;
		tableViewer.getControl().setLayoutData(gridData);

		// Add listener on table viewer
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				updateControls();
			}
		});
	}

	/**
	 * Create Predefined customization composite.
	 */
	protected void createLoadPredefinedCustoPart(final Composite composite) {
		Composite predifineComposite = new Composite(composite, SWT.NONE);
		predifineComposite.setLayout(new FillLayout());
		predifineComposite.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 2, 1));

		Label labelSelectStyle = new Label(predifineComposite, SWT.NONE);
		labelSelectStyle.setText(Messages.CustomizeUMLLabelDialog_ComboCustomizationLabel);

		predefinedCustoViewer = new ComboViewer(predifineComposite, SWT.NONE);
		predefinedCustoViewer.setContentProvider(ArrayContentProvider.getInstance());
		predefinedCustoViewer.setLabelProvider(new LabelProvider());
		predefinedCustoViewer.setInput(predefinedCustoList);
		predefinedCustoViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				updateTable(event);
			}
		});
		predefinedCustoViewer.setSelection(new StructuredSelection(CUSTO_CUSTOM), true);
	}

	/**
	 * update the Table when select a new predefined customization.
	 */
	protected void updateTable(final SelectionChangedEvent event) {
		if (null != event) {
			String selection = (String) ((StructuredSelection) event.getSelection()).getFirstElement();
			List<String[]> newInput = new ArrayList<String[]>();
			switch (selection) {
			case CUSTO_NAME:
				newInput.add(new String[] { LabelTypesEnum.LABEL.getLiteral(), LabelStylersEnum.DEFAULT.getLiteral() });
				break;
			case CUSTO_STEREOTYPE_METACLASS_NAME:
				newInput.add(new String[] { LabelTypesEnum.STEREOTYPE.getLiteral(), LabelStylersEnum.DEFAULT.getLiteral() });
				newInput.add(new String[] { LabelTypesEnum.METACLASS.getLiteral(), LabelStylersEnum.DEFAULT.getLiteral() });
				newInput.add(new String[] { LabelTypesEnum.LABEL.getLiteral(), LabelStylersEnum.DEFAULT.getLiteral() });
				break;
			case CUSTO_NAME_STEREOTYPE:
				newInput.add(new String[] { LabelTypesEnum.LABEL.getLiteral(), LabelStylersEnum.DEFAULT.getLiteral() });
				newInput.add(new String[] { LabelTypesEnum.DASH_SEPARATOR.getLiteral(), LabelStylersEnum.GREY.getLiteral() });
				newInput.add(new String[] { LabelTypesEnum.STEREOTYPE.getLiteral(), LabelStylersEnum.GREY.getLiteral() });
				break;
			case CUSTO_CUSTOM:
				break;
			default:
				break;
			}
			if (!newInput.isEmpty()) {
				tableViewer.setInput(newInput);
				tableViewer.refresh();
			}
		}

	}

	/**
	 * Creates the Add/Remove controls,
	 * and the Up/Down controls
	 */
	protected void createListControls() {
		up = createButton(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage("/icons/Up_12x12.gif"), Messages.CustomizeUMLLabelDialog_upButtonTooltip); //$NON-NLS-1$
		down = createButton(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage("/icons/Down_12x12.gif"), Messages.CustomizeUMLLabelDialog_downButtonTooltip); //$NON-NLS-1$
		add = createButton(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage("/icons/Add_12x12.gif"), Messages.CustomizeUMLLabelDialog_createButtonTooltip); //$NON-NLS-1$
		remove = createButton(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage("/icons/Delete_12x12.gif"), Messages.CustomizeUMLLabelDialog_deleteButtonTooltip); //$NON-NLS-1$
	}

	/**
	 * Create the button on the control setction.
	 * 
	 * @param image
	 *            The image of the button.
	 * @param toolTipText
	 *            The tooltipText.
	 * @return the created button.
	 */
	protected Button createButton(Image image, String toolTipText) {
		Button button = new Button(controlsSection, SWT.PUSH);
		button.setImage(image);
		button.addSelectionListener(this);
		button.setToolTipText(toolTipText);
		return button;
	}

	/**
	 * @return the List of type/style entries from preference as a List<String[2]> where each string[2] contains {type,style}.
	 */
	protected List<String[]> getEntriesFromPreferences() {

		String[] types = Activator.getDefault().getPreferenceStore().getString(CustomizableLabelPreferences.CUSTOMIZED_TYPES).split(" ");//$NON-NLS-1$
		String[] styles = Activator.getDefault().getPreferenceStore().getString(CustomizableLabelPreferences.CUSTOMIZED_STYLES).split(" ");//$NON-NLS-1$

		List<String[]> entries = new ArrayList<String[]>();

		for (int i = 0; i < types.length; i++) {
			String type = types[i];
			String style;
			if (i < styles.length) {
				style = styles[i];
			} else {
				style = LabelStylersEnum.BLACK.getLiteral();
			}
			String[] entry = new String[] { type, style };

			entries.add(entry);
		}

		return entries;
	}

	/**
	 * create columns types and styles for the viewer.
	 */
	protected void createColumns(final TableViewer viewer) {

		// Create the types column
		TableViewerColumn colTypes = new TableViewerColumn(viewer, SWT.NONE);
		colTypes.getColumn().setWidth(200);
		colTypes.getColumn().setText(Messages.CustomizeUMLLabelDialog_typesColumnTitle);

		colTypes.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				String text;
				if (element instanceof String[]) {
					String[] entry = (String[]) element;
					text = entry[0];
				} else {
					text = element.toString();
				}
				return text;
			}
		});
		colTypes.setEditingSupport(new ArrayStringWithComboEditingSupport(tableViewer, 0, LabelTypesEnum.values()));

		// Create the styles column
		TableViewerColumn colStyles = new TableViewerColumn(viewer, SWT.NONE);
		colStyles.getColumn().setWidth(200);
		colStyles.getColumn().setText(Messages.CustomizeUMLLabelDialog_stylesColumnTitle);

		colStyles.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				String text;
				if (element instanceof String[]) {
					String[] entry = (String[]) element;
					text = entry[1];
				} else {
					text = element.toString();
				}
				return text;
			}
		});
		colStyles.setEditingSupport(new ArrayStringWithComboEditingSupport(tableViewer, 1, LabelStylersEnum.values()));
	}

	/**
	 * The Class The support edit with with combo and with inputs elements as String[].
	 */
	private class ArrayStringWithComboEditingSupport extends EditingSupport {

		/** The index on the String[]. */
		private int index;

		/** the input for the comboBox. */
		private Object comboInput;

		/**
		 * Constructor.
		 *
		 * @param viewer
		 *            The viewer.
		 * @param index
		 *            The index on the String[].
		 * @param comboInput
		 *            the input for the comboBox.
		 */
		public ArrayStringWithComboEditingSupport(final ColumnViewer viewer, final int index, final Object comboInput) {
			super(viewer);
			this.index = index;
			this.comboInput = comboInput;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.EditingSupport#getCellEditor(java.lang.Object)
		 */
		@Override
		protected CellEditor getCellEditor(final Object element) {
			ComboBoxViewerCellEditor cellEditor = new ComboBoxViewerCellEditor(tableViewer.getTable());
			cellEditor.setContentProvider(new ArrayContentProvider());
			cellEditor.setLabelProvider(new LabelProvider());
			cellEditor.setInput(comboInput);

			return cellEditor;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.EditingSupport#canEdit(java.lang.Object)
		 */
		@Override
		protected boolean canEdit(final Object element) {
			boolean canEdit = false;
			if (element instanceof String[] && index < ((String[]) element).length) {
				canEdit = true;

			}
			return canEdit;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.EditingSupport#getValue(java.lang.Object)
		 */
		@Override
		protected Object getValue(final Object element) {
			String text;
			if (element instanceof String[] && index < ((String[]) element).length) {
				String[] entry = (String[]) element;
				text = entry[index];
			} else {
				text = element.toString();
			}
			return text;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.EditingSupport#setValue(java.lang.Object, java.lang.Object)
		 */
		@Override
		protected void setValue(final Object element, final Object value) {
			if (null != value && element instanceof String[] && index < ((String[]) element).length) {
				String[] entry = (String[]) element;
				entry[index] = value.toString();
				tableViewer.update(element, null);
			}
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {

		Map<String, String> map = getLabelStylesMap();
		setResult(Collections.singletonList(map));

		super.okPressed();
	}

	/**
	 * @return the map to be return to be set in preferences.
	 */
	protected Map<String, String> getLabelStylesMap() {
		// Construct the map to be return to be set in preferences.
		StringBuilder types = new StringBuilder();
		StringBuilder styles = new StringBuilder();

		if (tableViewer.getInput() instanceof List) {
			@SuppressWarnings("unchecked")
			List<String[]> entries = (List<String[]>) tableViewer.getInput();

			for (int i = 0; i < entries.size(); i++) {
				String[] entry = entries.get(i);
				types.append(entry[0]);
				styles.append(entry[1]);
				if (i < entries.size() - 1) {
					types.append(" ");//$NON-NLS-1$
					styles.append(" ");//$NON-NLS-1$
				}
			}
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put(CustomizableLabelPreferences.CUSTOMIZED_TYPES, types.toString());
		map.put(CustomizableLabelPreferences.CUSTOMIZED_STYLES, styles.toString());
		return map;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(final SelectionEvent event) {
		if (event.widget != null) {
			if (event.widget == add) {
				addAction();
			} else if (event.widget == remove) {
				removeAction();
			} else if (event.widget == up) {
				upAction();
			} else if (event.widget == down) {
				downAction();
			}
			updateControls();
			tableViewer.refresh();
			predefinedCustoViewer.setSelection(new StructuredSelection(CUSTO_CUSTOM), true);
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(final SelectionEvent e) {
		// Do nothing
	}

	/**
	 * @return the first selected element of the table viewer.
	 */
	protected Object getSelection() {
		return ((IStructuredSelection) tableViewer.getStructuredSelection()).getFirstElement();
	}

	/**
	 * update controls(buttons...)
	 */
	protected void updateControls() {
		Object selection = getSelection();
		remove.setEnabled(null != selection);
		down.setEnabled(null != selection && !isSelectionLast());
		up.setEnabled(null != selection && !isSelectionFirst());
	}

	/**
	 * Down the selected element.
	 */
	protected void downAction() {
		Object selection = getSelection();
		if (null != selection && !isSelectionLast()) {
			Object input = tableViewer.getInput();
			if (input instanceof List) {
				int index = ((List) input).indexOf(selection);
				((List) input).remove(selection);
				((List) input).add(index + 1, selection);
			}
		}
	}

	/**
	 * Up the selected Element.
	 */
	protected void upAction() {
		Object selection = getSelection();
		if (null != selection && !isSelectionFirst()) {
			Object input = tableViewer.getInput();
			if (input instanceof List) {
				int index = ((List) input).indexOf(selection);
				((List) input).remove(selection);
				((List) input).add(index - 1, selection);
			}
		}
	}

	/**
	 * Remove the selected Element
	 */
	protected void removeAction() {
		List<?> selections = tableViewer.getStructuredSelection().toList();

		for (Object selection : selections) {
			Object input = tableViewer.getInput();
			if (input instanceof List) {
				((List) input).remove(selection);
			}
		}
	}

	/**
	 * Add a new entry at the end of the list.
	 */
	protected void addAction() {
		Object input = tableViewer.getInput();
		if (input instanceof List) {
			String[] newEntry = new String[2];

			newEntry[0] = LabelTypesEnum.LABEL.getLiteral();
			newEntry[1] = LabelStylersEnum.BLACK.getLiteral();

			((List<String[]>) input).add(newEntry);
			tableViewer.refresh();
			tableViewer.setSelection(new StructuredSelection(newEntry));
			tableViewer.refresh();
		}
	}

	/**
	 * @return true if the selection is the last element of the list.
	 */
	protected boolean isSelectionLast() {
		boolean isLast = false;
		Object selection = getSelection();
		Object input = tableViewer.getInput();
		if (input instanceof List) {
			isLast = ((List<?>) input).indexOf(selection) == ((List<?>) input).size() - 1;
		}
		return isLast;
	}

	/**
	 * @return true if the selection is the first element of the list.
	 */
	protected boolean isSelectionFirst() {
		boolean isLast = false;
		Object selection = getSelection();
		Object input = tableViewer.getInput();
		if (input instanceof List) {
			isLast = ((List<?>) input).indexOf(selection) == 0;
		}
		return isLast;
	}

}