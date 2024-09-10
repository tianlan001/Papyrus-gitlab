/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.papyrus.infra.widgets.providers.CollectionContentProvider;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * Automated pin derivation for AcceptEventAction and AcceptCallAction
 * 
 * This class provide graphical interface for Automated Model Completion Preferences Page.
 * Sub classes must initialize the contentProvider property
 */
public abstract class AbstractAutomatedModelCompletionPreferencesPage extends PreferencePage implements IWorkbenchPreferencePage {

	final static String FIRST_COLUMN_NAME = org.eclipse.papyrus.uml.diagram.common.messages.Messages.AbstractAutomatedModelCompletionPreferencesPage_Elements;

	final static String SECOND_COLUMN_NAME = org.eclipse.papyrus.uml.diagram.common.messages.Messages.AbstractAutomatedModelCompletionPreferencesPage_Accelerators;

	final static int ROW_HEIGHT = 25;

	/**
	 * Save the associate comboBox to the AutomatedModelCompletionDescriptor
	 */
	private HashMap<AutomatedModelCompletionPreferenceDescriptor, Combo> mapInstanceComboBox;

	/**
	 * The content of the table
	 */
	protected List<AutomatedModelCompletionPreferenceDescriptor> automatedModelCompletionDescriptorsList;

	/**
	 * Constructor.
	 *
	 */
	public AbstractAutomatedModelCompletionPreferencesPage() {
		automatedModelCompletionDescriptorsList = new ArrayList<>();
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 *
	 * @param workbench
	 */
	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	/**
	 * Perform apply.
	 *
	 * @see org.eclipse.jface.preference.PreferencePage#performApply()
	 */
	@Override
	protected void performApply() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		// save preferences
		for (Map.Entry<AutomatedModelCompletionPreferenceDescriptor, Combo> entry : mapInstanceComboBox.entrySet()) {
			String value = entry.getValue().getText();
			store.setValue(entry.getKey().getPreferenceConstant(), value);
		}
	}

	/**
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 *
	 * @return
	 */
	@Override
	public boolean performOk() {
		performApply();
		return super.performOk();
	}

	/**
	 * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
	 *
	 */
	@Override
	protected void performDefaults() {
		IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();

		for (Map.Entry<AutomatedModelCompletionPreferenceDescriptor, Combo> entry : mapInstanceComboBox.entrySet()) {
			String selected = preferences.getDefaultString(entry.getKey().getPreferenceConstant());
			entry.getValue().setText(selected);
		}
		super.performDefaults();
	}

	/**
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 * @return
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite self = new Composite(parent, SWT.NONE);
		self.setLayout(new GridLayout(1, true));
		self.setLayoutData(new GridData(GridData.FILL_BOTH));

		// TableViewer
		final TableViewer tableViewer = new TableViewer(self, SWT.BORDER);

		// Table
		Table table = tableViewer.getTable();
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		table.setLayoutData(gridData);

		tableViewer.setContentProvider(CollectionContentProvider.instance);
		tableViewer.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public void update(ViewerCell cell) {
				if (cell.getColumnIndex() == 0) {
					super.update(cell);
				} else {
					return;
				}
			}

			@Override
			public String getText(Object element) {
				if (element instanceof AutomatedModelCompletionPreferenceDescriptor) {
					String text = ((AutomatedModelCompletionPreferenceDescriptor) element).getElement().toString();
					text = text.replaceAll("org.eclipse.uml2.uml.", ""); //$NON-NLS-1$ //$NON-NLS-2$
					text = text.replaceFirst("interface\\s", ""); //$NON-NLS-1$ //$NON-NLS-2$
					return text;
				}
				return super.getText(element);
			}
		});

		TableLayout layout = new TableLayout();

		new TableColumn(table, SWT.LEFT);
		layout.addColumnData(new ColumnWeightData(100, 250, true));

		new TableColumn(table, SWT.LEFT);
		layout.addColumnData(new ColumnWeightData(100, 250, true));

		table.setLayout(layout);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		table.getColumn(0).setText(FIRST_COLUMN_NAME);
		table.getColumn(1).setText(SECOND_COLUMN_NAME);

		// Set content
		tableViewer.setInput(automatedModelCompletionDescriptorsList);

		// Create comboBox
		mapInstanceComboBox = new HashMap<AutomatedModelCompletionPreferenceDescriptor, Combo>();
		for (TableItem item : table.getItems()) {
			if (item.getData() instanceof AutomatedModelCompletionPreferenceDescriptor) {
				TableEditor editor = new TableEditor(table);

				final Combo comboBox = new Combo(table, SWT.DROP_DOWN);
				List<String> list = ((AutomatedModelCompletionPreferenceDescriptor) item.getData()).getListOfAccelerator();
				java.util.Collections.sort(list);
				for (String updater : list) {
					comboBox.add(updater);
				}

				// set selection
				IPreferenceStore store = Activator.getDefault().getPreferenceStore();
				comboBox.setText(store.getString(((AutomatedModelCompletionPreferenceDescriptor) item.getData()).getPreferenceConstant()));

				final TableItem currentItem = item;

				final AutomatedModelCompletionPreferenceDescriptor strategy = (AutomatedModelCompletionPreferenceDescriptor) currentItem.getData();

				mapInstanceComboBox.put(strategy, comboBox);

				editor.setEditor(comboBox, item, 1);
				editor.horizontalAlignment = SWT.CENTER;
				editor.grabHorizontal = true;

			}
		}

		// resize the row height
		table.addListener(SWT.MeasureItem, new Listener() {
			@Override
			public void handleEvent(Event event) {
				event.height = ROW_HEIGHT;

			}
		});

		return self;
	}
}
