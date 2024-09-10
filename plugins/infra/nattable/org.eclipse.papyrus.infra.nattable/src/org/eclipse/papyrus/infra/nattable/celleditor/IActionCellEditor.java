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
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr>  - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.celleditor;

import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.papyrus.infra.nattable.celleditor.action.ICellEditorButtonAction;

/**
 *
 * @since 6.6
 *
 *        Common interface for all {@link ICellEditor} on which we can declare an additional button
 *
 */
public interface IActionCellEditor extends ICellEditor {

	/**
	 *
	 * @param action
	 *            the button action provided by the cell editor
	 */
	public void setCellEditorButtonAction(ICellEditorButtonAction action);
}

