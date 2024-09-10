/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.celleditor.action;

import org.eclipse.jface.window.Window;
import org.eclipse.nebula.widgets.nattable.edit.DialogEditHandler;
import org.eclipse.nebula.widgets.nattable.edit.gui.AbstractDialogCellEditor;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer.MoveDirectionEnum;
import org.eclipse.nebula.widgets.nattable.widget.EditModeEnum;
import org.eclipse.swt.events.SelectionEvent;

/**
 * Abstract implementation for {@link ICellEditorButtonAction} which opens a dialog
 *
 * @since 6.6
 */
public abstract class AbstractOpenDialogCellEditorButtonAction extends AbstractCellEditorButtonAction {

	/**
	 *
	 * @return
	 *         the created dialog
	 */
	public abstract AbstractDialogCellEditor createDialogCellEditor();


	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.action.AbstractCellEditorButtonAction#runAction(org.eclipse.swt.events.SelectionEvent)
	 *
	 * @param e
	 * @return
	 */
	@Override
	public int runAction(SelectionEvent e) {
		AbstractDialogCellEditor editor = createDialogCellEditor();
		editor.activateCell(parent, originalCanonicalValue, EditModeEnum.DIALOG, new DialogEditHandler(), cell, configRegistry);
		int res = editor.open();
		if (Window.OK == res) {
			windowOKAction(editor);
		} else {
			windowCancelAction(editor);
		}
		return res;
	}

	/**
	 * @param editor
	 */
	protected void windowOKAction(final AbstractDialogCellEditor editor) {
		this.initialCellEditor.setCanonicalValue(editor.getEditorValue());
		this.initialCellEditor.commit(MoveDirectionEnum.NONE);
	}

	/**
	 * @param editor
	 */
	protected void windowCancelAction(final AbstractDialogCellEditor editor) {
		this.initialCellEditor.close();
	}

}
