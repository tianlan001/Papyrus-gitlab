/*****************************************************************************
 * Copyright (c) 2014, 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 562864
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.handler;

import org.eclipse.papyrus.infra.nattable.manager.table.ITreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.TableHelper;

/**
 * @author Vincent Lorenzo
 *
 */
public abstract class AbstractTreeTableHandler extends AbstractTableHandler {

	/**
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractTableHandler#computeEnable(Object)
	 *
	 * @return
	 */
	@Override
	protected boolean computeEnable(Object evaluationContext) {
		boolean calculatedValue = super.computeEnable(evaluationContext);
		if (calculatedValue) {
			calculatedValue = TableHelper.isTreeTable(getCurrentNattableModelManager()) && getTreeNattableModelManager() != null;
		}
		return calculatedValue;
	}

	/**
	 *
	 * @return
	 *         the current tree nattable model manager
	 */
	public ITreeNattableModelManager getTreeNattableModelManager() {
		if (getCurrentNattableModelManager() instanceof ITreeNattableModelManager) {
			return (ITreeNattableModelManager) getCurrentNattableModelManager();
		}
		return null;
	}

}
