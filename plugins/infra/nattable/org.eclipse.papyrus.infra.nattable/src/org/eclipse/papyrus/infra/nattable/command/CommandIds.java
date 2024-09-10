/*****************************************************************************
 * Copyright (c) 2013, 2017 CEA LIST.
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
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 459220
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.command;

/**
 * This class references the ids of the commands declared for tables.
 *
 * @author Vincent Lorenzo
 *
 */
public class CommandIds {

	private CommandIds() {
		// to prevent instanciation
	}

	public static final String COMMAND_COLUMN_DISPLAY_INDEX_ID = "org.eclipse.papyrus.infra.nattable.column.display.index"; //$NON-NLS-1$

	public static final String COMMAND_COLUMN_DISPLAY_LABEL_ID = "org.eclipse.papyrus.infra.nattable.column.display.label"; //$NON-NLS-1$

	public static final String COMMAND_COLUMN_DISPLAY_FILTER_ID = "org.eclipse.papyrus.infra.nattable.column.display.filter"; //$NON-NLS-1$

	public static final String COMMAND_COLUMN_DISPLAY_INDEX_STYLE_ID = "org.eclipse.papyrus.infra.nattable.column.index.style"; //$NON-NLS-1$

	public static final String COMMAND_ROW_DISPLAY_INDEX_ID = "org.eclipse.papyrus.infra.nattable.row.display.index"; //$NON-NLS-1$

	public static final String COMMAND_ROW_DISPLAY_LABEL_ID = "org.eclipse.papyrus.infra.nattable.row.display.label"; //$NON-NLS-1$

	public static final String COMMAND_ROW_DISPLAY_INDEX_STYLE_ID = "org.eclipse.papyrus.infra.nattable.row.index.style"; //$NON-NLS-1$

	public static final String COMMAND_COLUMN_LABEL_DISPLAY_ICON = "org.eclipse.papyrus.infra.nattable.column.label.display.icon"; //$NON-NLS-1$

	public static final String COMMAND_ROW_LABEL_DISPLAY_ICON = "org.eclipse.papyrus.infra.nattable.row.label.display.icon"; //$NON-NLS-1$

	public static final String COMMAND_COLUMN_LABEL_DISPLAY_LABEL = "org.eclipse.papyrus.infra.nattable.column.label.display.label"; //$NON-NLS-1$

	public static final String COMMAND_ROW_LABEL_DISPLAY_LABEL = "org.eclipse.papyrus.infra.nattable.row.label.display.label"; //$NON-NLS-1$

	public static final String COMMAND_ROW_LABEL_FEATURE_DISPLAY_IS_DERIVED = "org.eclipse.papyrus.infra.nattable.row.label.feature.display.isderived"; //$NON-NLS-1$

	public static final String COMMAND_ROW_LABEL_FEATURE_DISPLAY_MULTIPLICITY = "org.eclipse.papyrus.infra.nattable.row.label.feature.display.multiplicity"; //$NON-NLS-1$

	public static final String COMMAND_ROW_LABEL_FEATURE_DISPLAY_TYPE = "org.eclipse.papyrus.infra.nattable.row.label.feature.display.type"; //$NON-NLS-1$

	public static final String COMMAND_COLUMN_LABEL_FEATURE_DISPLAY_IS_DERIVED = "org.eclipse.papyrus.infra.nattable.column.label.feature.display.isderived"; //$NON-NLS-1$

	public static final String COMMAND_COLUMN_LABEL_FEATURE_DISPLAY_MULTIPLICITY = "org.eclipse.papyrus.infra.nattable.column.label.feature.display.multiplicity"; //$NON-NLS-1$

	public static final String COMMAND_COLUMN_LABEL_FEATURE_DISPLAY_TYPE = "org.eclipse.papyrus.infra.nattable.column.label.feature.display.type"; //$NON-NLS-1$

	public static final String COMMAND_ROW_LABEL_FEATURE_DISPLAY_NAME = "org.eclipse.papyrus.infra.nattable.row.label.feature.display.name"; //$NON-NLS-1$

	public static final String COMMAND_COLUMN_LABEL_FEATURE_DISPLAY_NAME = "org.eclipse.papyrus.infra.nattable.column.label.feature.display.name"; //$NON-NLS-1$

	public static final String COMMAND_ROW_DISCONNECT_SLAVE = "org.eclipse.papyrus.infra.nattable.row.disconnect.slave"; //$NON-NLS-1$

	public static final String COMMAND_COLUMN_DISCONNECT_SLAVE = "org.eclipse.papyrus.infra.nattable.column.disconnect.slave"; //$NON-NLS-1$

	public static final String COMMAND_INVERT_AXIS = "org.eclipse.papyrus.infra.nattable.invert.axis"; //$NON-NLS-1$

	public static final String COMMAND_MERGE_ROWS = "org.eclipse.papyrus.infra.nattable.mergeRows"; //$NON-NLS-1$

	public static final String COMMAND_MERGE_COLUMNS = "org.eclipse.papyrus.infra.nattable.mergeColumns"; //$NON-NLS-1$

	public static final String COMMAND_MERGE_SELECTED_ROWS = "org.eclipse.papyrus.infra.nattable.mergeSelectedRows"; //$NON-NLS-1$

	public static final String COMMAND_MERGE_SELECTED_COLUMNS = "org.eclipse.papyrus.infra.nattable.mergeSelectedColumns"; //$NON-NLS-1$

	// public static final String COMMAND_MERGE_TABLE = "org.eclipse.papyrus.infra.nattable.mergeTable"; //$NON-NLS-1$

	public static final String COMMAND_HIERARCHIC_DISPLAY_STYLE = "org.eclipse.papyrus.infra.nattable.table.hierarchic.style";////$NON-NLS-1$

	/**
	 * @since 3.0
	 */
	public static final String COMMAND_WRAP_TEXT = "org.eclipse.papyrus.infra.nattable.wraptext"; //$NON-NLS-1$

	/**
	 * @since 3.0
	 */
	public static final String COMMAND_AUTO_RESIZE_CELL_HEIGHT = "org.eclipse.papyrus.infra.nattable.autoresize.cellheight"; //$NON-NLS-1$

	/**
	 * @since 5.0
	 */
	public static final String COMMAND_DISPLAY_LIST_ON_SEPARATED_ROWS_COLUMNHEADER = "org.eclipse.papyrus.infra.nattable.displaylist.separatedrows.columnheader"; //$NON-NLS-1$

	/**
	 * @since 5.0
	 */
	public static final String COMMAND_DISPLAY_LIST_ON_SEPARATED_ROWS_ROWHEADER = "org.eclipse.papyrus.infra.nattable.displaylist.separatedrows.rowheader"; //$NON-NLS-1$


}
