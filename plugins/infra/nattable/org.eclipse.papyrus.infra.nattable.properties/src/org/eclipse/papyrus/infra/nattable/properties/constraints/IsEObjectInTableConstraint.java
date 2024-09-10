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

import java.util.Collection;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.papyrus.infra.constraints.constraints.AbstractConstraint;
import org.eclipse.papyrus.infra.constraints.constraints.Constraint;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IMultiPageEditorPart;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.ui.util.EditorHelper;
import org.eclipse.ui.IEditorPart;

/**
 * The constraint which allow to determinate if this is a nattable editor as current editor part.
 * 
 * @since 2.2
 */
public class IsEObjectInTableConstraint extends AbstractConstraint {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.constraints.constraints.AbstractConstraint#match(java.util.Collection)
	 */
	@Override
	public boolean match(final Collection<?> selection) {
		return match();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.constraints.constraints.AbstractConstraint#match(java.lang.Object)
	 */
	@Override
	public boolean match(final Object selection) {
		return match();
	}

	/**
	 * This allows to determinate if this is match with the current nattable editor.
	 * 
	 * @return <code>true</code> if the current nattable editor is open and matching with the expected value, <code>false</code> otherwise.
	 */
	protected boolean match() {
		boolean result = false;

		final boolean expectedValue = Boolean.parseBoolean(getValue("expectedValue")); //$NON-NLS-1$

		if (null != EditorHelper.getActivePart()) {
			final IEditorPart currentEditor = EditorHelper.getCurrentEditor();
			if (currentEditor instanceof IMultiPageEditorPart && null != ((IMultiPageEditorPart) currentEditor).getActiveEditor()) {
				final IMultiPageEditorPart multiDiagramEditor = (IMultiPageEditorPart) currentEditor;
				result = EditorHelper.getActivePart().equals(currentEditor)
						&& null != multiDiagramEditor.getActiveEditor().getAdapter(NatTableEditor.class)
						&& checkMoreTableConstraint(((NatTableEditor) multiDiagramEditor.getActiveEditor()).getTable());
			} else if (currentEditor instanceof IAdaptable) {
				final Table table = ((IAdaptable) currentEditor).getAdapter(Table.class);
				result = null != table
						&& EditorHelper.getActivePart().equals(currentEditor)
						&& checkMoreTableConstraint(table);
			}
		}

		return expectedValue == result;
	}

	/**
	 * This allows to check more constraint for the match.
	 * 
	 * @param table
	 *            the current table.
	 * @return <code>true</code> if the constraints are correctly managed, <code>false</code> otherwise.
	 */
	protected boolean checkMoreTableConstraint(final Table table) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.constraints.constraints.AbstractConstraint#equivalent(org.eclipse.papyrus.infra.constraints.constraints.Constraint)
	 */
	@Override
	protected boolean equivalent(final Constraint constraint) {
		return constraint == this || constraint instanceof IsEObjectInTableConstraint;
	}

}
