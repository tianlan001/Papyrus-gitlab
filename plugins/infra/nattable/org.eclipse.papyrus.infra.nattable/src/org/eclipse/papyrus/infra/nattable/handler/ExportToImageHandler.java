/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 417095
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;

/**
 * Handler to export a NatTable to an image.
 *
 * <p>
 * Warning: Using this class is risky as it could cause severe damage to
 * Papyrus NatTables that show huge data sets (for example: a table with more than 20k
 * rows).
 * </p>
 * 
 * @since 3.0
 */
public class ExportToImageHandler extends AbstractTableHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object execute(final ExecutionEvent arg0) throws ExecutionException {
		final INattableModelManager manager = getCurrentNattableModelManager();
		if (null != manager) {
			manager.exportToImage();
		}

		return null;
	}
}
