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
package org.eclipse.papyrus.infra.nattable.properties.provider;

import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;

/**
 *
 * @author Vincent Lorenzo
 *
 */
public class RowPostActionIdsProvider extends AbstractPostActionIdsProvider {

	/**
	 *
	 * Constructor.
	 *
	 * @param tableManager
	 *            the table manager
	 */
	public RowPostActionIdsProvider(final INattableModelManager tableManager) {
		super(tableManager, false);
	}



}
