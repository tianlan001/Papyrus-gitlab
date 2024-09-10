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
 *   Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - bug 517617, 532452
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.utils;

import java.io.Reader;
import java.util.Map;

import org.eclipse.papyrus.infra.nattable.layerstack.RowHeaderHierarchicalLayerStack;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.parsers.CSVParser;
import org.eclipse.papyrus.infra.nattable.parsers.CellIterator;
import org.eclipse.papyrus.infra.nattable.parsers.RowIterator;

/**
 * The helper for the paste.
 */
public class PasteHelperUtils {

	/**
	 * The character of the indentation for the single column.
	 */
	private static final char INDENTATION_CHARACTER = ' '; // $NON-NLS-1$

	/**
	 * Constructor.
	 */
	private PasteHelperUtils() {
		// to prevent instanciation
	}

	/**
	 * Manage if this is a paste with overwrite or just a basic paste.
	 * 
	 * @param tableManager
	 *            The nattable model manager.
	 * @param pasteHelper
	 *            The paste helper.
	 * @param reader
	 *            The reader of the file.
	 * @return
	 */
	public static final boolean isPasteWithOverwrite(final INattableModelManager tableManager, final CSVPasteHelper pasteHelper, final Reader reader) {
		boolean isPasteWithOverwrite = false;

		final CSVParser parser = pasteHelper.createParser(reader);

		// Get the pasted text
		final RowIterator rowIter = parser.parse();
		int nbColumnRead = -1;

		// Calculate the number of column pasted
		while (rowIter.hasNext()) {
			final CellIterator cellIter = rowIter.next();
			int nbColumnReadByRow = 0;
			if (cellIter.hasNext()) {
				while (cellIter.hasNext()) {
					cellIter.next();
					nbColumnReadByRow++;
				}
			}
			nbColumnRead = nbColumnRead >= nbColumnReadByRow ? nbColumnRead : nbColumnReadByRow;
		}

		// Calculate the number of column in the table (including rows header number column and excluding index column)
		int nbExpectedColumn = tableManager.getColumnCount();
		if (TableHelper.isSingleColumnTreeTable(tableManager)) {
			nbExpectedColumn++;
		} else {
			if(TableHelper.isTreeTable(tableManager)){
				nbExpectedColumn = nbExpectedColumn + ((RowHeaderHierarchicalLayerStack)((NattableModelManager) tableManager).getRowHeaderLayerStack()).getRowHeaderColumnHideShowLayer().getColumnCount();
			}else{
				nbExpectedColumn = nbExpectedColumn + ((NattableModelManager) tableManager).getRowHeaderLayerStack().getRowHeaderLayerLabel().getColumnCount();
			}
		}

		// If the number of column read is equals to the number of column in table, this is a basic paste (PasteEObject...)
		isPasteWithOverwrite = nbColumnRead != nbExpectedColumn;
		return isPasteWithOverwrite;
	}

	/**
	 * This allows to get the max depth available in the pasted text (to define which paste configuration must be verified).
	 * 
	 * @param tableManager
	 *            The nattable model manager.
	 * @param pasteHelper
	 *            The paste helper.
	 * @param reader
	 *            the reader.
	 * @return The max depth available in the pasted text.
	 */
	public static final int getMaxDepthToPaste(final INattableModelManager tableManager, final CSVPasteHelper pasteHelper, final Reader reader) {
		int maxDepth = -1;

		if(TableHelper.isTreeTable(tableManager)){
			final Table table = tableManager.getTable();
			final boolean isSingleHeaderColumnTreeTable = TableHelper.isSingleColumnTreeTable(table);
	
			final CSVParser parser = pasteHelper.createParser(reader, isSingleHeaderColumnTreeTable);
	
			// Get the pasted text
			final RowIterator rowIter = parser.parse();
	
			while (rowIter.hasNext()) {
				final CellIterator cellIter = rowIter.next();
	
				if (!cellIter.hasNext()) {
					continue;// to avoid blank line
				}
	
				String valueAsString = cellIter.next();
				int nbReadCell = 1;
	
				if (isSingleHeaderColumnTreeTable && !valueAsString.isEmpty()) {
					// If the table is a single header column, parse the value string to manage the correct depth
					// (manage each separator character as empty cell)
					while (INDENTATION_CHARACTER == valueAsString.charAt(0)) {
						nbReadCell++;
						valueAsString = valueAsString.substring(1);
					}
				} else {
					// test if the value is empty (we are in the tree header)
					while (cellIter.hasNext() && valueAsString.isEmpty()) {
						valueAsString = cellIter.next();
						nbReadCell++;
					}
				}
	
				final int currentDepth = PasteTreeUtils.getDepth(nbReadCell, FillingConfigurationUtils.getMaxDepthForTree(table), StyleUtils.getHiddenDepths(table), FillingConfigurationUtils.hasTreeFillingConfigurationForDepth(table, 0));
	
				if (maxDepth < currentDepth) {
					maxDepth = currentDepth;
				}
	
				while (cellIter.hasNext()) {
					cellIter.next();
				}
			}
		}

		return -1 == maxDepth ? Integer.MAX_VALUE : maxDepth;
	}

	public static final int getMinDepthOfSelection(final INattableModelManager tableManager, final TableSelectionWrapper tableSelectionWrapper) {
		int minDepth = 0;

		if (null != tableSelectionWrapper && !tableSelectionWrapper.getFullySelectedRows().isEmpty() && tableSelectionWrapper.getFullySelectedColumns().isEmpty()) {
			minDepth = Integer.MAX_VALUE;

			final Map<Integer, Object> selectedRows = tableSelectionWrapper.getFullySelectedRows();

			for (int rowPosition : selectedRows.keySet()) {
				if (selectedRows.get(rowPosition) instanceof TreeFillingConfiguration) {
					if (minDepth > ((TreeFillingConfiguration) selectedRows.get(rowPosition)).getDepth()) {
						minDepth = ((TreeFillingConfiguration) selectedRows.get(rowPosition)).getDepth();
					}
				} 
			}
		}

		return minDepth;
	}
}
