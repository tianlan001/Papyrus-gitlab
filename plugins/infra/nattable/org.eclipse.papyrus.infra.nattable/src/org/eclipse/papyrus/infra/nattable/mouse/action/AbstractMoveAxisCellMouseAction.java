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
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.mouse.action;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.swt.events.MouseEvent;

/**
 * Abstract method to move Axis Element with a single click in a cell
 *
 * @since 6.7
 */
public abstract class AbstractMoveAxisCellMouseAction extends AbstractCellMouseAction {

	/**
	 * @see org.eclipse.papyrus.infra.nattable.mouse.action.AbstractCellMouseAction#doRun(org.eclipse.nebula.widgets.nattable.NatTable, org.eclipse.swt.events.MouseEvent, java.lang.Object, java.lang.Object)
	 *
	 * @param natTable
	 * @param event
	 * @param rowElement
	 * @param columnElement
	 */
	@Override
	public void doRun(NatTable natTable, MouseEvent event, Object rowElement, Object columnElement) {
		final Object realRow = AxisUtils.getRepresentedElement(rowElement);
		final Object realColumn = AxisUtils.getRepresentedElement(columnElement);
		EObject toMove = null;
		if (realColumn instanceof String && realRow instanceof EObject) {
			toMove = (EObject) realRow;
		}
		if (realRow instanceof String && realColumn instanceof EObject) {
			toMove = (EObject) realColumn;
		}
		if (toMove != null) {
			final EObject container = toMove.eContainer();
			final EStructuralFeature feature = toMove.eContainingFeature();
			if (feature.isMany()) {
				final List<?> coll = (List<?>) container.eGet(feature);
				int index = coll.indexOf(toMove);
				doMove(toMove, container, feature, index);
			}
		}

	}

	/**
	 * This method do the move
	 *
	 * @param toMove
	 *            the element to move
	 * @param parent
	 *            the parent element
	 * @param feature
	 *            the feature of the parent owning the element to move
	 * @param currentIndex
	 *            the current index of the element in the feature value
	 */
	abstract void doMove(final EObject toMove, final EObject parent, final EStructuralFeature feature, final int currentIndex);

}
