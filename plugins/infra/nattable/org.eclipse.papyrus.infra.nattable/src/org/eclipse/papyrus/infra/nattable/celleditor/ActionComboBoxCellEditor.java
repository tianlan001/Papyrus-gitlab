/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.celleditor;

import java.lang.reflect.Method;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.edit.editor.ComboBoxCellEditor;
import org.eclipse.nebula.widgets.nattable.edit.editor.IComboBoxDataProvider;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer.MoveDirectionEnum;
import org.eclipse.nebula.widgets.nattable.widget.NatCombo;
import org.eclipse.papyrus.infra.nattable.celleditor.action.ICellEditorButtonAction;
import org.eclipse.papyrus.infra.nattable.widget.ButtonNatCombo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

/**
 * A {@link ComboBoxCellEditor} with an additional button provided by a {@link ICellEditorButtonAction}
 *
 * @since 6.6
 *
 */
public class ActionComboBoxCellEditor extends ComboBoxCellEditor implements IActionCellEditor {

	/**
	 * additional button action for the ComboBox
	 */
	private ICellEditorButtonAction action;

	/**
	 * Constructor.
	 *
	 * @param dataProvider
	 * @param maxVisibleItems
	 */
	public ActionComboBoxCellEditor(IComboBoxDataProvider dataProvider, int maxVisibleItems) {
		super(dataProvider, maxVisibleItems);
	}

	/**
	 * Constructor.
	 *
	 * @param dataProvider
	 */
	public ActionComboBoxCellEditor(IComboBoxDataProvider dataProvider) {
		super(dataProvider);
	}

	/**
	 * Constructor.
	 *
	 * @param canonicalValues
	 * @param maxVisibleItems
	 */
	public ActionComboBoxCellEditor(List<?> canonicalValues, int maxVisibleItems) {
		super(canonicalValues, maxVisibleItems);
	}

	/**
	 * Constructor.
	 *
	 * @param canonicalValues
	 */
	public ActionComboBoxCellEditor(List<?> canonicalValues) {
		super(canonicalValues);
	}

	/**
	 * Constructor.
	 *
	 * @param canonicalValues
	 */
	public ActionComboBoxCellEditor(Object... canonicalValues) {
		super(canonicalValues);
	}

	/**
	 *
	 * @param action
	 */
	@Override
	public void setCellEditorButtonAction(final ICellEditorButtonAction action) {
		this.action = action;
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.edit.editor.ComboBoxCellEditor#createEditorControl(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 * @return
	 */
	@Override
	public NatCombo createEditorControl(Composite parent) {
		int style = SWT.NONE;
		if (!this.freeEdit) {
			style |= SWT.READ_ONLY;
		}
		if (this.multiselect) {
			style |= SWT.MULTI;
		}
		if (this.useCheckbox) {
			style |= SWT.CHECK;
		}
		final ButtonNatCombo combo = (this.iconImage == null)
				? new ButtonNatCombo(parent, this.cellStyle, this.maxVisibleItems, style, this.showDropdownFilter, this.action)
				: new ButtonNatCombo(parent, this.cellStyle, this.maxVisibleItems, style, this.iconImage, this.showDropdownFilter, this.action);

		combo.setCursor(new Cursor(Display.getDefault(), SWT.CURSOR_IBEAM));

		if (this.multiselect) {
			combo.setMultiselectValueSeparator(this.multiselectValueSeparator);
			combo.setMultiselectTextBracket(this.multiselectTextPrefix, this.multiselectTextSuffix);
		}

		addNatComboListener(combo);
		return combo;
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.edit.editor.AbstractCellEditor#commit(org.eclipse.nebula.widgets.nattable.selection.SelectionLayer.MoveDirectionEnum, boolean, boolean)
	 *
	 * @param direction
	 * @param closeAfterCommit
	 * @param skipValidation
	 * @return
	 */
	@Override
	public boolean commit(MoveDirectionEnum direction, boolean closeAfterCommit, boolean skipValidation) {
		boolean result = super.commit(direction, closeAfterCommit, skipValidation);
		close();
		return result;
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.edit.editor.ComboBoxCellEditor#activateCell(org.eclipse.swt.widgets.Composite, java.lang.Object)
	 *
	 * @param parent
	 * @param originalCanonicalValue
	 * @return
	 */
	@Override
	protected Control activateCell(Composite parent, Object originalCanonicalValue) {
		this.action.configureAction(this, parent, originalCanonicalValue, layerCell, configRegistry);
		return super.activateCell(parent, originalCanonicalValue);
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.edit.editor.AbstractCellEditor#addEditorControlListeners()
	 *
	 */
	@Override
	public void addEditorControlListeners() {
		super.addEditorControlListeners();
		// to avoid to lost focus and make a commit when the action button open a dialog
		getEditorControl().removeFocusListener(this.focusListener);
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.edit.editor.ComboBoxCellEditor#setEditorValue(java.lang.Object)
	 *
	 * @param value
	 */
	@Override
	public void setEditorValue(Object value) {
		// we call the fillCombo method in case of the action create a new element (to update the combo contents)
		try {
			final String FILL_COMBO = "fillCombo"; //$NON-NLS-1$
			final Method m = ComboBoxCellEditor.class.getDeclaredMethod(FILL_COMBO, new Class<?>[] {});
			m.setAccessible(true);
			m.invoke(this, new Object[] {});
		} catch (Exception e) {
			org.eclipse.papyrus.infra.nattable.Activator.log.error(e);
		}
		super.setEditorValue(value);
	}
}
