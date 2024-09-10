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
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.contentprovider;

import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;

/**
 * The content provider for the column axis identifier.
 */
public class ColumnAxisIdentifierContentProvider extends AbstractAxisIdentifierContentProvider {

	/**
	 * Constructor.
	 *
	 * @param tableManager
	 *            the table manager to get the column axis.
	 */
	public ColumnAxisIdentifierContentProvider(final INattableModelManager tableManager) {
		super(tableManager, true);
	}

}
