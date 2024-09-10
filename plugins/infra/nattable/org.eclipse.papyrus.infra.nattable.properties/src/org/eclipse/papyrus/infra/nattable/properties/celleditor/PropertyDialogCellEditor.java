/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr>
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.properties.celleditor;

import org.eclipse.jface.window.Window;
import org.eclipse.nebula.widgets.nattable.edit.gui.AbstractDialogCellEditor;
import org.eclipse.papyrus.infra.properties.ui.creation.PropertyEditorFactory;


/**
 *
 * This class doesn't contribute to Property View, but contribute a CellEditor for Nattable that allows to open a PropertyView in the NatTable context
 *
 * @since 2.5
 */
public class PropertyDialogCellEditor extends AbstractDialogCellEditor {

	/**
	 * The edited element
	 */
	private Object editedElement;

	/**
	 * boolean indicating if the cell editor is closed or not
	 */
	private boolean isClosed = false;

	/**
	 * @see org.eclipse.nebula.widgets.nattable.edit.gui.AbstractDialogCellEditor#open()
	 *
	 * @return
	 */
	@Override
	public int open() {
		final Object result = ((PropertyEditorFactory) this.dialog).edit(this.parent.getShell(), getEditorValue());
		this.isClosed = true;
		if (result != null) {
			return Window.OK;
		}
		return Window.CANCEL;
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.edit.gui.AbstractDialogCellEditor#createDialogInstance()
	 *
	 * @return
	 */
	@Override
	public Object createDialogInstance() {
		return new PropertyEditorFactory();
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.edit.gui.AbstractDialogCellEditor#getDialogInstance()
	 *
	 * @return
	 */
	@Override
	public Object getDialogInstance() {
		return dialog;
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.edit.gui.AbstractDialogCellEditor#getEditorValue()
	 *
	 * @return
	 */
	@Override
	public Object getEditorValue() {
		return this.editedElement;
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.edit.gui.AbstractDialogCellEditor#setCanonicalValue(java.lang.Object)
	 *
	 * @param canonicalValue
	 */
	@Override
	public void setCanonicalValue(Object canonicalValue) {
		this.editedElement = canonicalValue;
		super.setCanonicalValue(canonicalValue);
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.edit.gui.AbstractDialogCellEditor#setEditorValue(java.lang.Object)
	 *
	 * @param value
	 */
	@Override
	public void setEditorValue(Object value) {
		// no usage here
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.edit.gui.AbstractDialogCellEditor#close()
	 *
	 */
	@Override
	public void close() {
		// nothing to do
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.edit.gui.AbstractDialogCellEditor#isClosed()
	 *
	 * @return
	 */
	@Override
	public boolean isClosed() {
		return this.isClosed;
	}

}