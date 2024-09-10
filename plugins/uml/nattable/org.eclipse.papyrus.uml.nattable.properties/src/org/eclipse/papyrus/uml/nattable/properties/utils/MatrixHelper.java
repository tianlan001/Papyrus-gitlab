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
 *   Vincent LORENZO (CEA-LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.properties.utils;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IMultiPageEditorPart;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.IMatrixTableWidgetManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.MatrixTableWidgetModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.ICellEditorConfiguration;
import org.eclipse.papyrus.infra.ui.util.EditorHelper;
import org.eclipse.ui.IEditorPart;

/**
 * TODO : maybe the dependency on >NatTableEditor could be remove ?
 * Same method also exist in infra NatTable property
 */
public class MatrixHelper {

	/**
	 * 
	 * Constructor.
	 *
	 */
	private MatrixHelper() {
		// to prevent instanciation
	}


	/**
	 * This allows to get the current nattable editor when this is available.
	 * 
	 * @return The current nattable editor if available or <code>null</code>.
	 */
	public static final NatTableEditor getCurrentNatTableEditor() {
		NatTableEditor result = null;

		final IEditorPart currentEditor = EditorHelper.getCurrentEditor();
		if (currentEditor instanceof IMultiPageEditorPart && null != ((IMultiPageEditorPart) currentEditor).getActiveEditor()) {
			result = ((IMultiPageEditorPart) currentEditor).getActiveEditor().getAdapter(NatTableEditor.class);
		} else if (currentEditor instanceof IAdaptable) {
			result = ((IAdaptable) currentEditor).getAdapter(NatTableEditor.class);
		}
		return result;
	}

	/**
	 * 
	 * @return
	 * 		the {@link MatrixTableWidgetModelManager} from the current editor or <code>null</code> when the editor doesn't represent a matrix
	 */
	public static final IMatrixTableWidgetManager getMatrixTableWidgetModelManagerFromCurrentEditor() {
		final NatTableEditor editor = getCurrentNatTableEditor();
		if (null != editor && editor.getAdapter(INattableModelManager.class) instanceof MatrixTableWidgetModelManager) {
			final INattableModelManager manager = (INattableModelManager) editor.getAdapter(INattableModelManager.class);
			if (manager instanceof IMatrixTableWidgetManager) {
				return (IMatrixTableWidgetManager) manager;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param table
	 *            a table
	 * @return
	 * 		<code>true</code> if the table owns a cell editor configuration
	 */
	public static final boolean isGenericRelationshipMatrixCellEditorConfiguration(final Table table) {
		final ICellEditorConfiguration cellEditor = table.getOwnedCellEditorConfigurations();
		return cellEditor instanceof GenericRelationshipMatrixCellEditorConfiguration;
	}
}

