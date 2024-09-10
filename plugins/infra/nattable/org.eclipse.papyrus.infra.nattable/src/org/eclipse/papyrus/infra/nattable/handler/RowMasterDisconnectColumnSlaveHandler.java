/*****************************************************************************
 * Copyright (c) 2013, 2015 CEA LIST.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.handler;

import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AbstractAxisProvider;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;

/**
 *
 * @author Vincent Lorenzo
 *
 */
public class RowMasterDisconnectColumnSlaveHandler extends AbstractDisconnectSlaveHandler {

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractDisconnectSlaveHandler#getAxisProviderToEdit()
	 *
	 * @return
	 */
	@Override
	protected AbstractAxisProvider getAxisProviderToEdit() {
		INattableModelManager currentNattableModelManager = getCurrentNattableModelManager();
		if (currentNattableModelManager == null ) {
			return null;
		}
		return AxisUtils.getAxisProviderUsedForRows(currentNattableModelManager);
	}

}
