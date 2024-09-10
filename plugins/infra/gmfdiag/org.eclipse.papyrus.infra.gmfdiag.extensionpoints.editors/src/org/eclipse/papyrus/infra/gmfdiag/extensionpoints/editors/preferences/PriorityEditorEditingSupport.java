/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.preferences;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.definition.IDirectEditorConfigurationIds;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.definition.IDirectEditorExtensionPoint;

/**
 * Editing Support for cell of Priority editor.
 * 
 * @author Gabriel Pascual
 *
 */
public class PriorityEditorEditingSupport extends EditingSupport {

	/** The Constant priorityItems. */
	private static final String[] priorityItems = new String[] { IDirectEditorConfigurationIds.PRIORITY_HIGHEST,
			IDirectEditorConfigurationIds.PRIORITY_HIGH, IDirectEditorConfigurationIds.PRIORITY_MEDIUM,
			IDirectEditorConfigurationIds.PRIORITY_LOW, IDirectEditorConfigurationIds.PRIORITY_LOWEST };

	/** The cell editor. */
	private CellEditor cellEditor;

	/**
	 * Constructor.
	 *
	 * @param viewer
	 *            the viewer
	 */
	public PriorityEditorEditingSupport(TableViewer viewer) {
		super(viewer);

		cellEditor = new ComboBoxCellEditor(viewer.getTable(), priorityItems);
	}

	/**
	 * @see org.eclipse.jface.viewers.EditingSupport#setValue(java.lang.Object, java.lang.Object)
	 *
	 * @param element
	 * @param value
	 */
	@Override
	protected void setValue(Object element, Object value) {
		if (element instanceof IDirectEditorExtensionPoint) {
			((IDirectEditorExtensionPoint) element).setPriority((Integer) value);
			getViewer().refresh();
		}
	}

	/**
	 * @see org.eclipse.jface.viewers.EditingSupport#getValue(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	protected Object getValue(Object element) {
		Object value = 0;
		if (element instanceof IDirectEditorExtensionPoint) {
			value = ((IDirectEditorExtensionPoint) element).getPriority();
		}
		return value;
	}

	/**
	 * @see org.eclipse.jface.viewers.EditingSupport#getCellEditor(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	protected CellEditor getCellEditor(Object element) {
		return cellEditor;
	}

	/**
	 * @see org.eclipse.jface.viewers.EditingSupport#canEdit(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	protected boolean canEdit(Object element) {
		return element instanceof IDirectEditorExtensionPoint;

	}


}
