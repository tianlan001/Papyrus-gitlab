/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 515967
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * A Widget to manipulate a list of values.
 *
 * The widget is configured with a Map of (String, String) entries (Key -> Label).
 * It returns the list of selected keys.
 *
 * @author Camille Letavernier
 */
public class StringMask extends AbstractListEditor implements SelectionListener, IChangeListener, DisposeListener {

	private Button[] checkboxes;

	private Collection<String> currentValue;

	private final Composite checkboxContainer;

	private boolean refreshCheckboxes = true;

	private boolean isReadOnly = false;

	public static final String DATA_KEY = "stringValue"; //$NON-NLS-1$

	public StringMask(final Composite parent, final int style) {
		super(parent, style);
		checkboxContainer = new Composite(this, style);
		checkboxContainer.setLayoutData(getDefaultLayoutData());
		checkboxContainer.setLayout(new GridLayout(2, true));
		checkboxContainer.addDisposeListener(this);
	}

	@Override
	protected GridData getLabelLayoutData() {
		GridData data = super.getLabelLayoutData();
		data.verticalAlignment = SWT.BEGINNING;
		return data;
	}

	public Collection<String> getValue() {
		Set<String> values = new HashSet<>();
		for (Button button : checkboxes) {
			if (button.getSelection()) {
				String value = (String) button.getData(DATA_KEY);
				values.add(value);
			}
		}
		return values;
	}

	@Override
	public void dispose() {
		if (modelProperty != null) {
			modelProperty.removeChangeListener(this);
		}
		super.dispose();
	}

	@Override
	public Object getEditableType() {
		return String.class;
	}

	@Override
	public void setReadOnly(final boolean readOnly) {
		this.isReadOnly = readOnly;
		for (Button button : checkboxes) {
			button.setEnabled(!readOnly);
		}
	}

	@Override
	public boolean isReadOnly() {
		return isReadOnly;
	}

	public void setMasks(final Map<String, String> values) {
		if (!refreshCheckboxes) {
			return;
		}
		if (checkboxes != null) {
			disposeCheckboxes();
		}

		checkboxes = new Button[values.size()];

		int i = 0;
		for (Entry<String, String> mask : values.entrySet()) {
			String stringValue = mask.getKey();
			String label = mask.getValue();

			checkboxes[i] = new Button(checkboxContainer, SWT.CHECK);
			checkboxes[i].setText(label);
			checkboxes[i].setData(DATA_KEY, stringValue);
			checkboxes[i].addSelectionListener(this);
			checkboxes[i].setToolTipText(stringValue);
			i++;
		}
	}

	protected void disposeCheckboxes() {
		for (Button button : checkboxes) {
			button.removeSelectionListener(this);
			button.dispose();
		}
	}

	public void setNumColumns(final int numColumns) {
		((GridLayout) checkboxContainer.getLayout()).numColumns = numColumns;
		checkboxContainer.layout();
		layout();
	}

	@Override
	public void doBinding() {
		// We don't do a real databinding here
		modelProperty.addChangeListener(this);

		refreshCheckboxes();
	}

	protected void refreshCheckboxes() {
		if (!refreshCheckboxes) {
			return;
		}

		Collection<String> values = getCurrentValue();
		for (Button button : checkboxes) {
			String value = (String) button.getData(DATA_KEY);
			button.setSelection(values.contains(value));
		}
	}

	@Override
	public void setToolTipText(final String text) {
		super.setLabelToolTipText(text);
	}

	@Override
	public void widgetSelected(final SelectionEvent e) {
		Button button = (Button) e.widget;
		String value = (String) button.getData(DATA_KEY);
		Collection<String> values = new HashSet<>(getCurrentValue());
		if (button.getSelection()) {
			values.add(value);
		} else {
			values.remove(value);
		}
		setCurrentValue(values);
	}

	protected void setCurrentValue(final Collection<String> values) {
		refreshCheckboxes = false;
		if (modelProperty != null) {
			modelProperty.clear();
			modelProperty.addAll(values);
		}
		currentValue = values;

		commit();
		refreshCheckboxes = true;
	}

	protected Collection<String> getCurrentValue() {
		if (modelProperty != null) {
			return modelProperty;
		} else {
			return currentValue;
		}
	}

	@Override
	public void widgetDefaultSelected(final SelectionEvent e) {
		// Nothing
	}

	public int getNumColumns() {
		return ((GridLayout) checkboxContainer.getLayout()).numColumns;
	}

	@Override
	public void handleChange(final ChangeEvent event) {
		refreshCheckboxes();
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractEditor#changeColorField()
	 *
	 */

	@Override
	public void changeColorField() {


	}

}
