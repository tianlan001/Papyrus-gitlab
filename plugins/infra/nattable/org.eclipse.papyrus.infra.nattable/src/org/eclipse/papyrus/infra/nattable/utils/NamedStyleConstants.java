/*****************************************************************************
 * Copyright (c) 2014, 2017, 2020 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Quentin Le Menez (CEA LIST) quentin.lemenez@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE(ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 504077
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 459220, 515737
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 486733
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.utils;

import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.BooleanValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StringValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.Style;

public class NamedStyleConstants {

	private NamedStyleConstants() {
		// to prevent instantiation
	}

	/**
	 *
	 * The following constants are used during the initialization and retrieval of the Axis and Header dimensions in the tables
	 */

	public static final String ROW_INDEX_WIDTH = "rowIndexWidth"; //$NON-NLS-1$

	public static final String ROW_LABEL_WIDTH = "rowLabelWidth"; //$NON-NLS-1$

	/**
	 * The prefix of the name style to calculate the width of each row header.
	 * This prefix is followed by the position of the column to edit width.
	 */
	public static final String ROW_LABEL_POSITION_PREFIX_WIDTH = "rowPosition"; //$NON-NLS-1$

	/**
	 * The suffix of the name style to calculate the width of each row header
	 */
	public static final String ROW_LABEL_POSITION_SUFFIX_WIDTH = "LabelWidth"; //$NON-NLS-1$

	/**
	 * The named style id for the slider composite width.
	 */
	public static final String ROW_HEADER_WIDTH = "rowHeaderWidth"; //$NON-NLS-1$


	public static final String COLUMN_INDEX_HEIGHT = "columnIndexHeight"; //$NON-NLS-1$

	public static final String COLUMN_LABEL_HEIGHT = "columnLabelHeight"; //$NON-NLS-1$

	public static final String AXIS_WIDTH = "axisWidth"; //$NON-NLS-1$

	public static final String AXIS_HEIGHT = "axisHeight"; //$NON-NLS-1$

	/**
	 *
	 * The following constants are used during the initialization and retrieval of the merge options in the tables
	 */

	// public static final String TABLEMERGE = "tableMerge"; //$NON-NLS-1$

	public static final String MERGE_ROWS = "mergeRows"; //$NON-NLS-1$

	public static final String MERGE_COLUMNS = "mergeColumns"; //$NON-NLS-1$

	public static final String MERGE_SELECTED_ROWS = "mergeSelectedRows"; //$NON-NLS-1$

	public static final String MERGE_SELECTED_COLUMNS = "mergeSelectedColumns"; //$NON-NLS-1$

	public static final String MERGE_IN_SELECTED_ROWS = "mergeInSelectedRows"; //$NON-NLS-1$

	public static final String MERGE_IN_SELECTED_COLUMNS = "mergeInSelectedColumns"; //$NON-NLS-1$

	// the merge of all the elements in the table is not yet supported
	// public static final String MERGE_TABLE = "mergeTable"; //$NON-NLS-1$


	public static final String HIDDEN_CATEGORY_FOR_DEPTH = "hiddenCategoriesByDepth"; //$NON-NLS-1$

	/**
	 * key used to save a filter configuration id state when the system use the default filter provided by the configuration
	 * It is only used as name for a {@link StringValueStyle}
	 */
	public static final String FILTER_SYSTEM_ID = "filterId"; //$NON-NLS-1$

	/**
	 * key used to save filter state (the value typed by the user to filter the rows)
	 * This key used as name for a {@link Style} without more precision
	 */
	public static final String FILTER_VALUE_TO_MATCH = "filterValueToMatch"; //$NON-NLS-1$

	/**
	 * key used to save a filter configuration id state when the system use a filter choosen by the user
	 * It is only used as name for a {@link StringValueStyle}, we use a specific key in order to not destroyed it when we unapply filter on a column where filter has been definied by the user
	 *
	 *
	 */
	public static final String FILTER_FORCED_BY_USER_ID = "filterForcedByUserId"; //$NON-NLS-1$

	/**
	 * This is for boolean value style {@link BooleanValueStyle}. This is used to expand all the rows in a tree table during the opening of the table.
	 */
	public static final String EXPAND_ALL = "expandAll"; //$NON-NLS-1$

	/**
	 * This allows to manage the fill columns size to take all the container space.
	 *
	 * @deprecated since 3.0
	 */
	@Deprecated
	public static final String FILL_COLUMNS_SIZE = "fillColumnsSize"; //$NON-NLS-1$

	/**
	 * This allows to determinate if the columns width must be managed as percentage.
	 * This named style is not compatible with 'fillColumnsSize'.
	 *
	 * @since 3.0
	 */
	public static final String COLUMNS_WIDTH_AS_PERCENTAGE = "columnsWidthAsPercentage"; //$NON-NLS-1$

	/**
	 * This allows to determinate if the columns width must be saved or not.
	 * This named style is not compatible with 'fillColumnsSize'.
	 *
	 * @since 3.0
	 */
	public static final String SAVE_COLUMNS_WIDTH = "saveColumnsWidth"; //$NON-NLS-1$

	/**
	 * Enable the drag behavior regions from the natTable to an other widget/editor (diagram, table, ...) which can accept it.
	 *
	 * @since 3.0
	 */
	public static final String DRAG_REGIONS = "dragRegions"; //$NON-NLS-1$

	/**
	 * A {@link BooleanValueStyle} used to enable wrap text in the NatTable.
	 *
	 * @since 3.0
	 */
	public static final String WRAP_TEXT = "wrapText"; //$NON-NLS-1$

	/**
	 * A default value used to enable the wraptext function in the NatTable.
	 * It is enabled by default.
	 *
	 * @since 5.0
	 */
	public static final boolean ENABLE_WRAP_TEXT = true;

	/**
	 * A {@link BooleanValueStyle} used to enable auto-resize cell height in the NatTable.
	 *
	 * @since 3.0
	 */
	public static final String AUTO_RESIZE_CELL_HEIGHT = "autoResizeCellHeight"; //$NON-NLS-1$

	/**
	 * A default value used to enable the auto resize cell height function in the NatTable.
	 * It is enabled by default.
	 *
	 * @since 5.0
	 */
	public static final boolean ENABLE_AUTO_RESIZE_CELL_HEIGHT = true;

	/**
	 * A {@link BooleanValueStyle} used to display list on separated rows in the NatTable.
	 *
	 * @since 4.0
	 */
	public static final String DISPLAY_LIST_ON_SEPARATED_ROWS = "displayListOnSeparatedRows"; //$NON-NLS-1$

	/**
	 * A default value used to enable the display list on separated rows function in the NatTable.
	 * It is disable by default.
	 *
	 * @since 5.0
	 */
	public static final boolean ENABLE_DISPLAY_LIST_ON_SEPARATED_ROWS = false;

	/**
	 * A {@link StringValueStyle} used to save the the sort.
	 * The value must be respect this syntax: number_direction, where number is the order sequence of the column and direction is {@link SortDirectionEnum}
	 *
	 * @since 7.0
	 */
	public static final String SORT = "sort"; //$NON-NLS-1$
}
