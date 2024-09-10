/*****************************************************************************
 * Copyright (c) 2013, 2020 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 562864
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.handler;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.nebula.widgets.nattable.ui.NatEventData;
import org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager;

/**
 * The handler used to destroy the element represented by the selected rows
 *
 * @author Vincent Lorenzo
 *
 */
public class RowDestroyAxisHandler extends AbstractTableHandler {

	/**
	 *
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 *
	 * @param event
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IAxisManager axisManager = getRowAxisManager();
		NatEventData eventData = getNatEventData();
		if (axisManager != null && eventData != null) {
			axisManager.destroyAxis(getFullSelectedRowsIndex(eventData));
		}
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractTreeTableHandler#computeEnable(Object)
	 *
	 * @return
	 */
	@Override
	protected boolean computeEnable(Object evaluationContext) {
		boolean calculatedValue = super.computeEnable(evaluationContext);
		if (calculatedValue) {
			final NatEventData eventData = getNatEventData();
			final IAxisManager axisManager = getRowAxisManager();
			calculatedValue = eventData != null && axisManager != null;
			if (calculatedValue) {
				final List<Integer> rows = getFullSelectedRowsIndex(eventData);
				calculatedValue = axisManager.canDestroyAxis(rows);
			}
		}
		return calculatedValue;
	}
}
