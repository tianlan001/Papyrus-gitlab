/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
package org.eclipse.papyrus.infra.nattable.utils;

import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Utilities for INattableModelManager
 *
 * @author Vincent Lorenzo
 *
 */
public class INattableModelManagerUtils {


	private INattableModelManagerUtils() {
		// to prevent instanciation
	}

	/**
	 *
	 *
	 * @param activeWorkbenchPart
	 * @return
	 *         the table manager from the workbench part
	 */
	public static INattableModelManager getTableManagerFromWorkbenchPart(final IWorkbenchPart activeWorkbenchPart) {
		if (activeWorkbenchPart != null) {
			return (INattableModelManager) activeWorkbenchPart.getAdapter(INattableModelManager.class);
		}
		return null;
	}


}
