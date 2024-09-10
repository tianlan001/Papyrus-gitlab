/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.properties.constraints;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.papyrus.infra.constraints.constraints.JavaQuery;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IMultiPageEditorPart;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.ui.util.EditorHelper;
import org.eclipse.ui.IEditorPart;

/**
 * The java constraint to check if the table from the active nattable editor.
 * 
 * @since 2.2
 */
public class EObjectInTableJavaConstraint implements JavaQuery {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.constraints.constraints.JavaQuery#match(java.lang.Object)
	 */
	@Override
	public boolean match(Object selection) {
		boolean result = false;

		if (null != EditorHelper.getActivePart()) {
			final IEditorPart currentEditor = EditorHelper.getCurrentEditor();
			if (currentEditor instanceof IMultiPageEditorPart && null != ((IMultiPageEditorPart) currentEditor).getActiveEditor()) {
				final IMultiPageEditorPart multiDiagramEditor = (IMultiPageEditorPart) currentEditor;
				final Table table = multiDiagramEditor.getActiveEditor().getAdapter(Table.class);
				if (null != table) {
					result = checkMoreConstraints(table);
				}
			} else if (currentEditor instanceof IAdaptable) {
				final Table table = ((IAdaptable) currentEditor).getAdapter(Table.class);
				result = checkMoreConstraints(table);
			}
		}

		return result;
	}

	/**
	 * This allows to check more constraint for the match.
	 * 
	 * @param table
	 *            the current table.
	 * @return <code>true</code> if the constraints are correctly managed, <code>false</code> otherwise.
	 */
	protected boolean checkMoreConstraints(final Table table) {
		return true;
	}

}
