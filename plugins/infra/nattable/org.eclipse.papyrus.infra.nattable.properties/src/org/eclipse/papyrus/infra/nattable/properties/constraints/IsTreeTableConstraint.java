/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
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
 *   Christian W. Damus - bug 485220
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.properties.constraints;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.constraints.constraints.AbstractConstraint;
import org.eclipse.papyrus.infra.constraints.constraints.Constraint;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.utils.TableHelper;

/**
 * The constraint which allow to determinate if this is a tree table.
 */
public class IsTreeTableConstraint extends AbstractConstraint {

	/**
	 * {@inheritDoc}
	 *
	 * The default implementation matches a selection iff the constraint matches
	 * each object of the selection.
	 */
	@Override
	public boolean match(final Collection<?> selection) {
		boolean result = false;

		if (!selection.isEmpty()) {
			final int elementMultiplicity = display.getElementMultiplicity();

			final int selectionSize = selection.size();
			if (elementMultiplicity == 1) {
				if (selectionSize == 1) {
					if (match(selection.iterator().next())) {
						result = true;
					}
				}
				// Manage the multiple elements only if the selection is multiple too
			} else if ((elementMultiplicity == selectionSize) || (elementMultiplicity < 0 && selection.size() > 1)) {
				result = true;

				final Iterator<?> selectionIterator = selection.iterator();
				while (selectionIterator.hasNext() && result) {
					final Object selectedItem = selectionIterator.next();
					if (!match(selectedItem)) {
						result = false;
					}
				}
			}
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.constraints.constraints.AbstractConstraint#match(java.lang.Object)
	 */
	@Override
	public boolean match(final Object selection) {
		final boolean expectedValue = Boolean.parseBoolean(getValue("expectedValue")); //$NON-NLS-1$
		final EObject table = EMFHelper.getEObject(selection);
		return table instanceof Table && expectedValue == TableHelper.isTreeTable((Table) table);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.constraints.constraints.AbstractConstraint#equivalent(org.eclipse.papyrus.infra.constraints.constraints.Constraint)
	 */
	@Override
	protected boolean equivalent(final Constraint constraint) {
		return constraint == this || constraint instanceof IsTreeTableConstraint;
	}

}
