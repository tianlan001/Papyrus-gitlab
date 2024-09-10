/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Nicolas FAUVERGUe (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.contentprovider;

import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;

/**
 * The content provider for the row axis identifier.
 */
public class RowAxisIdentifierContentProvider extends AbstractAxisIdentifierContentProvider {

	/**
	 * Constructor.
	 *
	 * @param tableManager
	 *            the table manager to get the column axis.
	 */
	public RowAxisIdentifierContentProvider(final INattableModelManager tableManager) {
		super(tableManager, false);
	}

}
