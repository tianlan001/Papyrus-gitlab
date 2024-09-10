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
 *   Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.customization.nattableconfiguration.helper;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.DisplayStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.TableDisplayStyle;

/**
 * This class is a helper to get and edit TableConfiguration values easily, with no command and not editing domain
 */
public class TableConfigurationHelper {

	/**
	 * the table configuration we are manipulating
	 */
	private final TableConfiguration configuration;

	/**
	 * String for not yet supported operation
	 */
	private static final String NOT_YET_SUPPORTED = "not yet supported"; //$NON-NLS-1$
	/**
	 * the empty string
	 */
	public static final String EMPTY_STRING = ""; //$NON-NLS-1$

	/**
	 * Flat style for axis
	 * Please, notice, than
	 * <ul>
	 * <li>the real implementation works only for Tree Row</li>
	 * <li>this style is declared on the tree, and not on the axis in the metamodel</li>
	 * </ul>
	 * 
	 */
	public static final String AXIS_FLAT_STYLE = "Flat"; //$NON-NLS-1$

	/**
	 * Tree style for axis
	 * Please, notice, than
	 * <ul>
	 * <li>the real implementation works only for Tree Row</li>
	 * <li>this style is declared on the tree, and not on the axis in the metamodel</li>
	 * </ul>
	 * 
	 */
	public static final String AXIS_TREE_STYLE = "Tree"; //$NON-NLS-1$

	/**
	 * Compact Tree style for axis
	 * Please, notice, than
	 * <ul>
	 * <li>the real implementation works only for Tree Row</li>
	 * <li>this style is declared on the tree, and not on the axis in the metamodel</li>
	 * </ul>
	 * 
	 */
	public static final String AXIS_COMPACT_TREE_STYLE = "Compact"; //$NON-NLS-1$

	/**
	 * Large Tree style for axis
	 * Please, notice, than
	 * <ul>
	 * <li>the real implementation works only for Tree Row</li>
	 * <li>this style is declared on the tree, and not on the axis in the metamodel</li>
	 * </ul>
	 * 
	 */
	public static final String AXIS_LARGE_TREE_STYLE = "Large"; //$NON-NLS-1$



	/**
	 * Constructor.
	 *
	 */
	public TableConfigurationHelper(final TableConfiguration configuration) {
		this.configuration = configuration;
	}


	/**
	 * Declare row axis as a flat axis
	 */
	public void setRowAxisAsFlat() {
		TableDisplayStyle displayStyle = (TableDisplayStyle) this.configuration.getStyle(NattablestylePackage.eINSTANCE.getTableDisplayStyle());
		if (null == displayStyle) {
			displayStyle = (TableDisplayStyle) this.configuration.createStyle(NattablestylePackage.eINSTANCE.getTableDisplayStyle());
		}
		displayStyle.setDisplayStyle(DisplayStyle.NORMAL);
	}

	/**
	 * Declare row axis as a tree displayed with the compact style
	 */
	public void setRowAxisAsCompactTree() {
		TableDisplayStyle displayStyle = (TableDisplayStyle) this.configuration.getStyle(NattablestylePackage.eINSTANCE.getTableDisplayStyle());
		if (null == displayStyle) {
			displayStyle = (TableDisplayStyle) this.configuration.createStyle(NattablestylePackage.eINSTANCE.getTableDisplayStyle());
		}
		displayStyle.setDisplayStyle(DisplayStyle.HIERARCHIC_SINGLE_TREE_COLUMN);
	}

	/**
	 * Declare row axis as a tree displayed with the large style
	 */
	public void setRowAxisAsLargeTree() {
		TableDisplayStyle displayStyle = (TableDisplayStyle) this.configuration.getStyle(NattablestylePackage.eINSTANCE.getTableDisplayStyle());
		if (null == displayStyle) {
			displayStyle = (TableDisplayStyle) this.configuration.createStyle(NattablestylePackage.eINSTANCE.getTableDisplayStyle());
		}
		displayStyle.setDisplayStyle(DisplayStyle.HIERARCHIC_MULTI_TREE_COLUMN);
	}

	/**
	 * set the row axis as tree, displayed as a compact tree by default
	 */
	public void setRowAxisAsTree() {
		setRowAxisAsCompactTree();
	}


	/**
	 * Declare column axis as a flat axis
	 * 
	 * @not_yet_supported
	 */
	public void setColumnAxisAsFlat() {
		throw new UnsupportedOperationException(NOT_YET_SUPPORTED);
	}


	/**
	 * Declare column axis as a tree displayed with the compact style
	 * 
	 * @not_yet_supported
	 */
	public void setColumnAxisAsCompactTree() {
		throw new UnsupportedOperationException(NOT_YET_SUPPORTED);
	}


	/**
	 * Declare column axis as a tree displayed with the large style
	 * 
	 * @not_yet_supported
	 */
	public void setColumnAxisAsLargeTree() {
		throw new UnsupportedOperationException(NOT_YET_SUPPORTED);
	}

	/**
	 * set the row axis as tree, displayed as a compact tree by default
	 */
	public void setColumnAxisAsTree() {
		throw new UnsupportedOperationException(NOT_YET_SUPPORTED);
	}


	/**
	 * 
	 * @return
	 *         <code>null</code> if the value has never been initialized, <code>true</code> if the row axis is flat, and <code>false</code> in other cases
	 */
	public Boolean isRowAxisFlat() {
		final TableDisplayStyle displayStyle = (TableDisplayStyle) this.configuration.getStyle(NattablestylePackage.eINSTANCE.getTableDisplayStyle());
		Boolean returnedValue = null;
		if (null != displayStyle) {
			returnedValue = Boolean.valueOf(DisplayStyle.NORMAL.equals(displayStyle.getDisplayStyle()));
		}
		return returnedValue;
	}

	/**
	 * 
	 * @return
	 *         <code>null</code> if the value has never been initialized, <code>true</code> if the row axis is flat, and <code>false</code> in other cases
	 * 
	 * @not_yet_supported
	 */
	public Boolean isColumnAxisFlat() {
		return null;
	}

	/**
	 * 
	 * @return
	 *         <code>null</code> if the value has never been initialized, <code>true</code> if the row axis is a tree, and <code>false</code> in other cases
	 */
	public Boolean isRowAxisTree() {
		final TableDisplayStyle displayStyle = (TableDisplayStyle) this.configuration.getStyle(NattablestylePackage.eINSTANCE.getTableDisplayStyle());
		Boolean returnedValue = null;
		if (null != displayStyle) {
			returnedValue = Boolean.valueOf(DisplayStyle.HIERARCHIC_SINGLE_TREE_COLUMN.equals(displayStyle.getDisplayStyle()) || DisplayStyle.HIERARCHIC_MULTI_TREE_COLUMN.equals(displayStyle.getDisplayStyle()));
		}
		return returnedValue;
	}

	/**
	 * 
	 * @return
	 *         <code>null</code> if the value has never been initialized, <code>true</code> if the row axis is a tree, and <code>false</code> in other cases
	 * 
	 * @not_yet_supported
	 */
	public Boolean isColumnAxisTree() {
		return null;
	}

	/**
	 * 
	 * @return
	 *         <code>null</code> if the value has never been initialized, <code>true</code> if the row axis is a compact tree, and <code>false</code> in other cases
	 */
	public Boolean isRowAxisCompactTree() {
		final TableDisplayStyle displayStyle = (TableDisplayStyle) this.configuration.getStyle(NattablestylePackage.eINSTANCE.getTableDisplayStyle());
		Boolean returnedValue = null;
		if (null != displayStyle) {
			returnedValue = Boolean.valueOf(DisplayStyle.HIERARCHIC_SINGLE_TREE_COLUMN.equals(displayStyle.getDisplayStyle()));
		}
		return returnedValue;
	}

	/**
	 * 
	 * @return
	 *         <code>null</code> if the value has never been initialized, <code>true</code> if the row axis is a compact tree, and <code>false</code> in other cases
	 * 
	 * @not_yet_supported
	 */
	public Boolean isColumnAxisCompactTree() {
		return null;
	}

	/**
	 * 
	 * @return
	 *         <code>null</code> if the value has never been initialized, <code>true</code> if the row axis is a large tree, and <code>false</code> in other cases
	 */
	public Boolean isRowAxisLargeTree() {
		final TableDisplayStyle displayStyle = (TableDisplayStyle) this.configuration.getStyle(NattablestylePackage.eINSTANCE.getTableDisplayStyle());
		Boolean returnedValue = null;
		if (null != displayStyle) {
			returnedValue = Boolean.valueOf(DisplayStyle.HIERARCHIC_MULTI_TREE_COLUMN.equals(displayStyle.getDisplayStyle()));
		}
		return returnedValue;
	}

	/**
	 * 
	 * @return
	 *         <code>null</code> if the value has never been initialized, <code>true</code> if the row axis is a compact tree, and <code>false</code> in other cases
	 * 
	 * @not_yet_supported
	 */
	public Boolean isColumnAxisLargeTree() {
		return null;
	}

	/**
	 * 
	 * @return
	 * 		the type of the table, never <code>null</code>
	 */
	public String getTableType() {
		String type = this.configuration.getType();
		if (null == type) {
			type = EMPTY_STRING;
		}
		return type;
	}

	/**
	 * 
	 * @return
	 * 		the default table name, never <code>null</code>
	 */
	public String getDefaultTableName() {
		String name = this.configuration.getName();
		if (null == name) {
			name = EMPTY_STRING;
		}
		return name;
	}

	/**
	 * 
	 * @return
	 * 		the table configuration description, never <code>null</code>
	 */
	public String getTableConfigurationDescription() {
		String description = this.configuration.getDescription();
		if (null == description) {
			description = EMPTY_STRING;
		}
		return description;
	}

	/**
	 * Get the table configuration.
	 * 
	 * @return The table configuration.
	 */
	public TableConfiguration getTableConfiguration() {
		return configuration;
	}

	/**
	 * 
	 * @param name
	 *            the default name of the table
	 */
	public void setDefaultTableName(final String name) {
		this.configuration.setName(name);
	}

	/**
	 * 
	 * @param type
	 *            the type of the table
	 */
	public void setTableType(final String type) {
		this.configuration.setType(type);
	}

	/**
	 * 
	 * @param iconPath
	 *            the path of the icon to use for the table
	 */
	public void setTableIcon(final String iconPath) {
		this.configuration.setIconPath(iconPath);
	}

	/**
	 * 
	 * @param description
	 *            the table configuration description
	 */
	public void setTableConfigurationDescription(final String description) {
		this.configuration.setDescription(description);
	}

}
