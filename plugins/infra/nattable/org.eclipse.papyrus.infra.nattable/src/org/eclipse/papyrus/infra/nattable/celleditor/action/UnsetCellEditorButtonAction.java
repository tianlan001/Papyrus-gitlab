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

package org.eclipse.papyrus.infra.nattable.celleditor.action;

import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.nattable.manager.cell.CellManagerFactory;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.swt.events.SelectionEvent;

/**
 * This action provides a button to call unset the cell value on the current edited cell
 *
 * @since 6.6
 *
 */
public class UnsetCellEditorButtonAction extends AbstractCellEditorButtonAction {

	/**
	 * Constructor.
	 *
	 */
	public UnsetCellEditorButtonAction() {
		setTooltipText("Unset Value"); // TODO externalize me, when we will change the major version
		setImage(Activator.getDefault().getImage(org.eclipse.papyrus.infra.nattable.Activator.PLUGIN_ID, "icons/delete_obj.gif")); //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.action.ICellEditorButtonAction#isEnabled()
	 *
	 * @return
	 */
	@Override
	public boolean isEnabled() {
		return isCellEditable() && getCurrentCellValue() != null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.action.ICellEditorButtonAction#runAction()
	 *
	 * @return
	 */
	@Override
	public int runAction(final SelectionEvent e) {
		this.initialCellEditor.close();
		CellManagerFactory.INSTANCE.unsetCellValue(getEditingDomain(), getColumnElement(), getRowElement(), getNattableModelManager());
		return Window.OK;
	}

}
