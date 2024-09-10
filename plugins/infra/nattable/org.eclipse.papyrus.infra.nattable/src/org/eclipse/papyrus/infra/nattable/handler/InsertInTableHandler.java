/*****************************************************************************
 * Copyright (c) 2015, 2020 CEA LIST and others.
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
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 562864
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.provider.TableStructuredSelection;
import org.eclipse.papyrus.infra.nattable.utils.AbstractPasteInsertInTableHandler;
import org.eclipse.papyrus.infra.nattable.utils.PasteInsertUtil;
import org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Insert Handler.
 */
public class InsertInTableHandler extends AbstractPasteInsertInTableHandler {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		return PasteInsertUtil.insert(getCurrentNattableModelManager(), HandlerUtil.getCurrentSelection(event), event.getParameters());
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
			final NattableModelManager manager = (NattableModelManager) getCurrentNattableModelManager();
			if (manager == null) {
				calculatedValue = false;
			} else {
				final ISelection currentSelection = manager.getSelectionInTable();
				if (null == currentSelection) {
					calculatedValue = true;
				} else if (currentSelection instanceof TableStructuredSelection) {
					TableSelectionWrapper tableSelectionWrapper = (TableSelectionWrapper) ((TableStructuredSelection) currentSelection).getAdapter(TableSelectionWrapper.class);
					calculatedValue = tableSelectionWrapper != null
							&& (tableSelectionWrapper.getSelectedCells().isEmpty()
									|| (!tableSelectionWrapper.getFullySelectedRows().isEmpty()
											&& tableSelectionWrapper.getFullySelectedColumns().isEmpty()));
				} else {
					calculatedValue = false;
				}
			}
		}
		return calculatedValue;
	}
}
