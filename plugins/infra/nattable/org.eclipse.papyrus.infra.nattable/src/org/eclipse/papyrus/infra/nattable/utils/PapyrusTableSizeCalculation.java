/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.utils;

import org.eclipse.papyrus.infra.nattable.manager.table.AbstractNattableWidgetManager;

/**
 * This allows to manage the size calculation of header, rows and columns.
 * 
 * @since 2.0
 */
public class PapyrusTableSizeCalculation {

	/**
	 * Get the row header width of the current table.
	 * 
	 * @param nattableModelManager The nattable model manager.
	 * @return The row header width.
	 */
	public static int getRowHeaderWidth(final AbstractNattableWidgetManager nattableModelManager){
		int headerWidth = 0;
		if (null != nattableModelManager.getRowHeaderLayerStack()) {
			for (int headerColumnIndex = 0; headerColumnIndex < nattableModelManager.getRowHeaderLayerStack().getColumnCount(); headerColumnIndex++) {
				headerWidth += nattableModelManager.getRowHeaderLayerStack().getColumnWidthByPosition(headerColumnIndex);
			}
		}
		return headerWidth;
	}
	
	/**
	 * This allows to calculate the column width when it is needed to fill the column to the parent.
	 * 
	 * @param nattableWidgetManager The nattable model manager.
	 * @param parentSize The parent size.
	 * @return The optimized column width.
	 */
	public static int getColumnFillWidth(final AbstractNattableWidgetManager nattableWidgetManager, final int parentSize){
		int columnSize=0;
		
		if(0 < parentSize){
			int headerWidth = getRowHeaderWidth(nattableWidgetManager);
	
			// Remove the rows header size from the parent size
			final int allColumnsSize = parentSize - headerWidth;
			
			// Divide the width of all columns by the number of column to calculate the width by column
			columnSize = allColumnsSize / nattableWidgetManager.getBodyLayerStack().getColumnHideShowLayer().getColumnCount();
		}

		return columnSize;
	}
	
}
