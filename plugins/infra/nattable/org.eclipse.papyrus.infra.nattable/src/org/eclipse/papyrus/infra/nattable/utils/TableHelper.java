/*****************************************************************************
 * Copyright (c) 2013, 2016, 2018, 2019 CEA LIST, Esterel Technologies SAS and others.
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
 *  Calin Glitia (Esterel Technologies SAS) - Bug 497654
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Bug 535935
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Bug 551566
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.utils;

import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.NattableFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.NattablePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AbstractAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.IMasterAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.NattablecelleditorFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.CellEditorDeclaration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.DisplayStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.IntListValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.TableDisplayStyle;

/**
 *
 * @author Vincent Lorenzo
 *
 */
public class TableHelper {

	private TableHelper() {
		// to prevent instantiation
	}

	/**
	 *
	 * @param configuration
	 *            the configuration used to create the table
	 * @return
	 *         the table created from this configuration
	 */
	public static final Table createTable(final TableConfiguration configuration) {
		return createTable(configuration, null);
	}

	/**
	 *
	 * @param configuration
	 *            the configuration used to create the table, cannot be <code>null</code>
	 * @param context
	 *            the context of the table
	 * @return
	 *         the table created from these parameters
	 */
	public static final Table createTable(final TableConfiguration configuration, final EObject context) {
		return createTable(configuration, context, null);
	}

	/**
	 *
	 * @param configuration
	 *            the configuration used to create the table, cannot be <code>null</code>
	 * @param context
	 *            the context of the table
	 * @param name
	 *            the name for the table
	 * @return
	 *         the table created from these parameters
	 */
	public static final Table createTable(final TableConfiguration configuration, final EObject context, final String name) {
		return createTable(configuration, context, name, null);
	}

	/**
	 *
	 * @param configuration
	 *            the configuration used to create the table, cannot be <code>null</code>
	 * @param context
	 *            the context of the table
	 * @param name
	 *            the name for the table
	 * @param description
	 *            the description for the table
	 * @return
	 *         the table created from these parameters
	 *
	 * @deprecated since 6.5.0
	 */
	@Deprecated
	public static final Table createTable(final TableConfiguration configuration, final EObject context, final String name, final String description) {
		return createTable(configuration, context, name, description, null);
	}

	/**
	 *
	 * @param configuration
	 *            the configuration used to create the table, cannot be <code>null</code>
	 * @param context
	 *            the context of the table
	 * @param name
	 *            the name for the table
	 * @param description
	 *            the description for the table
	 * @param tableKindId
	 *            the table kind id
	 *
	 * @return
	 *         the table created from these parameters
	 */
	public static final Table createTable(final TableConfiguration configuration, final EObject context, final String name, final String description, String tableKindId) {
		Assert.isNotNull(configuration);

		Table table = NattableFactory.eINSTANCE.createTable();
		table.setTableConfiguration(configuration);
		table.setDescription(description);
		table.setName(name);
		table.setContext(context);
		table.setTableKindId(tableKindId);

		// the configuration always provides axis provider
		AbstractAxisProvider rowProvider = configuration.getDefaultRowAxisProvider();
		rowProvider = EcoreUtil.copy(rowProvider);

		AbstractAxisProvider columnProvider = configuration.getDefaultColumnAxisProvider();
		columnProvider = EcoreUtil.copy(columnProvider);

		table.setCurrentRowAxisProvider(rowProvider);
		table.setCurrentColumnAxisProvider(columnProvider);
		table.getRowAxisProvidersHistory().add(rowProvider);
		table.getColumnAxisProvidersHistory().add(columnProvider);

		IntListValueStyle style = (IntListValueStyle) configuration.getNamedStyle(NattablestylePackage.eINSTANCE.getIntListValueStyle(), NamedStyleConstants.HIDDEN_CATEGORY_FOR_DEPTH);
		// we copy this style, because the solution to override it in the table required to have a style to store displayed depth and another one to store hidden depth.
		if (style != null) {
			IntListValueStyle copy = EcoreUtil.copy(style);
			table.getStyles().add(copy);
		}


		if (isMatrixTreeTable(table)) {
			if (null != configuration.getOwnedCellEditorConfigurations()) {
				table.setOwnedCellEditorConfigurations(EcoreUtil.copy(configuration.getOwnedCellEditorConfigurations()));
			} else {
				// we can do it because currently, we only have one possible type for that!
				GenericRelationshipMatrixCellEditorConfiguration conf = NattablecelleditorFactory.eINSTANCE.createGenericRelationshipMatrixCellEditorConfiguration();
				conf.setCellEditorId("GenericRelationshipMatrixEditorConfiguration"); //$NON-NLS-1$
				table.setOwnedCellEditorConfigurations(conf);
			}
			Assert.isNotNull(table.getOwnedCellEditorConfigurations(), "A matrix must own a CellEditorConfiguration"); //$NON-NLS-1$
		}

		return table;
	}

	/**
	 *
	 * @param table
	 *            a table
	 * @return
	 *         the display style to use for the table, the return value is never <code>null</code>
	 */
	public static final DisplayStyle getTableDisplayStyle(final Table table) {
		DisplayStyle result = DisplayStyle.NORMAL;
		if (null != table) {
			TableDisplayStyle displayStyle = (TableDisplayStyle) table.getStyle(NattablestylePackage.eINSTANCE.getTableDisplayStyle());
			if (null == displayStyle && null != table.getTableConfiguration()) {
				displayStyle = (TableDisplayStyle) table.getTableConfiguration().getStyle(NattablestylePackage.eINSTANCE.getTableDisplayStyle());
			}
			if (displayStyle != null) {
				result = displayStyle.getDisplayStyle();
			}
		}
		return result;
	}


	/**
	 *
	 * @param table
	 *            a table manager
	 * @return
	 *         the display style to use for the managed table, the return value is never <code>null</code>
	 */
	public static final DisplayStyle getTableDisplayStyle(final INattableModelManager tableManager) {
		return tableManager != null ? getTableDisplayStyle(tableManager.getTable()) : null;
	}

	/**
	 *
	 * @param tableManager
	 *            a table manager
	 * @return
	 *         <code>true</code> if the managed table is a tree table
	 */
	public static final boolean isTreeTable(final INattableModelManager tableManager) {
		return tableManager != null ? isTreeTable(tableManager.getTable()) : false;
	}

	/**
	 *
	 * @param table
	 *            a table
	 * @return
	 *         <code>true</code> if the table is a tree table
	 */
	public static final boolean isTreeTable(final Table table) {
		TableDisplayStyle style = (TableDisplayStyle) table.getStyle(NattablestylePackage.eINSTANCE.getTableDisplayStyle());
		if (style == null) {
			style = (TableDisplayStyle) table.getTableConfiguration().getStyle(NattablestylePackage.eINSTANCE.getTableDisplayStyle());
		}
		if (style != null) {
			final DisplayStyle displayStyle = style.getDisplayStyle();
			return DisplayStyle.HIERARCHIC_MULTI_TREE_COLUMN.equals(displayStyle) || DisplayStyle.HIERARCHIC_SINGLE_TREE_COLUMN.equals(displayStyle);
		}
		return false;
	}

	/**
	 * This allows to check if this is a tree table.
	 *
	 * @param tableConfiguration
	 *            The table configuration.
	 * @return <code>true</code> if this is a tree table, <code>false</code> otherwise.
	 */
	public static final boolean isTreeTable(final TableConfiguration tableConfiguration) {
		final TableDisplayStyle style = (TableDisplayStyle) tableConfiguration.getStyle(NattablestylePackage.eINSTANCE.getTableDisplayStyle());
		if (null != style) {
			final DisplayStyle displayStyle = style.getDisplayStyle();
			return DisplayStyle.HIERARCHIC_MULTI_TREE_COLUMN.equals(displayStyle) || DisplayStyle.HIERARCHIC_SINGLE_TREE_COLUMN.equals(displayStyle);
		}
		return false;
	}

	/**
	 * This allows to check if the tree table contains a single column for the row header.
	 *
	 * @param tableConfiguration
	 *            The table configuration.
	 * @return <code>true</code> if this is a single column, <code>false</code> otherwise.
	 */
	public static final boolean isSingleColumnTreeTable(final TableConfiguration tableConfiguration) {
		final TableDisplayStyle style = (TableDisplayStyle) tableConfiguration.getStyle(NattablestylePackage.eINSTANCE.getTableDisplayStyle());
		Assert.isNotNull(style);
		final DisplayStyle displayStyle = style.getDisplayStyle();
		return DisplayStyle.HIERARCHIC_SINGLE_TREE_COLUMN.equals(displayStyle);
	}

	/**
	 *
	 * @param table
	 *            a table
	 * @return
	 *         <code>true</code> if the table is a tree table displayed on single column
	 */
	public static final boolean isSingleColumnTreeTable(final Table table) {
		TableDisplayStyle style = (TableDisplayStyle) table.getStyle(NattablestylePackage.eINSTANCE.getTableDisplayStyle());
		if (style == null) {
			style = (TableDisplayStyle) table.getTableConfiguration().getStyle(NattablestylePackage.eINSTANCE.getTableDisplayStyle());
		}
		if (style != null) {
			final DisplayStyle displayStyle = style.getDisplayStyle();
			return DisplayStyle.HIERARCHIC_SINGLE_TREE_COLUMN.equals(displayStyle);
		}
		return false;
	}

	/**
	 *
	 * @param table
	 *            a table
	 * @return
	 *         <code>true</code> if the tableManager manages a tree table displayed on single column
	 */
	public static final boolean isSingleColumnTreeTable(final INattableModelManager tableManager) {
		return tableManager != null ? isSingleColumnTreeTable(tableManager.getTable()) : false;
	}


	/**
	 *
	 * @param table
	 *            a table
	 * @return
	 *         <code>true</code> if the table is a tree table displayed on multi column
	 */
	public static final boolean isMultiColumnTreeTable(Table table) {
		if (table != null) {
			TableDisplayStyle style = (TableDisplayStyle) table.getStyle(NattablestylePackage.eINSTANCE.getTableDisplayStyle());
			if (style == null) {
				style = (TableDisplayStyle) table.getTableConfiguration().getStyle(NattablestylePackage.eINSTANCE.getTableDisplayStyle());
			}
			if (style != null) {
				return DisplayStyle.HIERARCHIC_MULTI_TREE_COLUMN.equals(style.getDisplayStyle());
			}
		}
		return false;
	}

	/**
	 *
	 * @param table
	 *            a table
	 * @return
	 *         <code>true</code> if the tableManager manages a tree table displayed on multi column
	 */
	public static final boolean isMultiColumnTreeTable(final INattableModelManager tableManager) {
		return tableManager != null && isMultiColumnTreeTable(tableManager.getTable());
	}

	/**
	 *
	 * @param anEObject
	 * @return
	 * @throws Exception
	 */
	public static final Table findTable(final EObject anEObject) throws Exception { // TODO : move me
		if (EcoreUtil.getRootContainer(anEObject.eClass()) != NattablePackage.eINSTANCE) {
			throw new Exception("The eobject is not an element of the Papyrus table metamodel"); //$NON-NLS-1$
		}
		EObject container = anEObject;
		while (container != null && !(container instanceof Table)) {
			container = container.eContainer();
		}
		if (container == null) {
			return null;
			// throw new Exception("Table not found, this method should be completed"); //$NON-NLS-1$
		}

		return (Table) container;
	}

	/**
	 *
	 * @param tableManager
	 *            a table manager
	 * @return
	 *         the way to use to declare cell editor for the current table manager, according to invert axis property
	 */
	public static final CellEditorDeclaration getCellEditorDeclaration(final INattableModelManager tableManager) {
		return tableManager != null ? getCellEditorDeclaration(tableManager.getTable()) : null;
	}


	/**
	 *
	 * @param tableManager
	 *            a table manager
	 * @return
	 *         the way to use to declare cell editor for the current table, according to invert axis property
	 */
	public static final CellEditorDeclaration getCellEditorDeclaration(final Table table) {
		CellEditorDeclaration declaration = table.getTableConfiguration().getCellEditorDeclaration();
		if (table.isInvertAxis()) {
			switch (declaration) {
			case COLUMN:
				return CellEditorDeclaration.ROW;
			case ROW:
				return CellEditorDeclaration.COLUMN;
			default:
				break;
			}
		}
		return declaration;
	}


	/**
	 *
	 * @see ca.odell.glazedlists.TreeList.Format#getPath(java.util.List, java.lang.Object)
	 *
	 * @param path
	 * @param element
	 */
	public static final void getPath(List<ITreeItemAxis> path, ITreeItemAxis element) {
		path.add(element);
		ITreeItemAxis parent = element.getParent();
		while (parent != null) {
			path.add(0, parent);
			parent = parent.getParent();
		}
	}

	/**
	 *
	 * @param table
	 *            a table
	 * @return
	 *         <code>true</code> if the table is a matrix. That is to say
	 * @since 3.0
	 */
	public static final boolean isMatrixTreeTable(final Table table) {
		final AbstractAxisProvider rowsAxisProvider = table.getCurrentRowAxisProvider();
		final AbstractAxisProvider columnsAxisProvider = table.getCurrentColumnAxisProvider();
		return isTreeTable(table) && rowsAxisProvider instanceof IMasterAxisProvider && columnsAxisProvider instanceof IMasterAxisProvider;

	}

}
