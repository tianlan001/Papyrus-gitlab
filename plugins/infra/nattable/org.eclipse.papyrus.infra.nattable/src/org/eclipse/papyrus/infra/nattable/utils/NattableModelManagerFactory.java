/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 508175
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.utils;

import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.MatrixTableWidgetModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.selection.ISelectionExtractor;

/**
 * This class allows to create a {@link INattableModelManager} from a table model
 *
 */
public class NattableModelManagerFactory {

	public static final NattableModelManagerFactory INSTANCE = new NattableModelManagerFactory();

	private NattableModelManagerFactory() {
		// to prevent instanciation
	}

	/**
	 * 
	 * @param table
	 *            The table model.
	 * @param selectionExtractor
	 *            The selection extrator.
	 * @return
	 *         the INattableModelManager to use to manipulate the table.
	 */
	public INattableModelManager createNatTableModelManager(final Table table, final ISelectionExtractor selectionExtractor) {
		return createNatTableModelManager(table, selectionExtractor, true);
	}
	
	/**
	 * 
	 * @param table
	 *            The table model.
	 * @param selectionExtractor
	 *            The selection extrator.
	 * @param initializeListeners
	 *            Boolean to determinate if the listeners have to be initialized or not (example: properties view doesn't it)
	 * @return
	 * 		the INattableModelManager to use to manipulate the table
	 * @since 3.0
	 */
	public INattableModelManager createNatTableModelManager(final Table table, final ISelectionExtractor selectionExtractor, final boolean initializeListeners) {
		if(TableHelper.isMatrixTreeTable(table)) {
			return new MatrixTableWidgetModelManager(table, selectionExtractor, initializeListeners);
		}
		if (TableHelper.isTreeTable(table)) {
			return new TreeNattableModelManager(table,selectionExtractor, initializeListeners);
		}
		return new NattableModelManager(table, selectionExtractor, initializeListeners);
	}

}
