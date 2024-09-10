/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;

/**
 *
 * @author Vincent Lorenzo
 *
 */
public class SelectAllHandler extends AbstractTableHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final INattableModelManager manager = getCurrentNattableModelManager();
		if (manager != null) {
			manager.selectAll();
		}
		return null;
	}

}
