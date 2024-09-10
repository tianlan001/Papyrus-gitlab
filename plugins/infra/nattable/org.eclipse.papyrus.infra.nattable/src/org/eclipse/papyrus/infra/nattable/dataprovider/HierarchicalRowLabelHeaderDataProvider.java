/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
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
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.dataprovider;

import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.ITreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.DisplayStyle;
import org.eclipse.papyrus.infra.nattable.utils.FillingConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableHelper;

/**
 * @author Vincent Lorenzo
 *
 */
public class HierarchicalRowLabelHeaderDataProvider extends RowLabelHeaderDataProvider {

	/**
	 * Constructor.
	 *
	 * @param tableModelManager
	 */
	public HierarchicalRowLabelHeaderDataProvider(INattableModelManager tableModelManager) {
		super(tableModelManager);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.dataprovider.AbstractDataProvider#getColumnCount()
	 *
	 * @return
	 */
	@Override
	public int getColumnCount() {
		if (displayLabel) {
			final DisplayStyle style = TableHelper.getTableDisplayStyle(this.manager);
			if (DisplayStyle.NORMAL.equals(style) || DisplayStyle.HIERARCHIC_SINGLE_TREE_COLUMN.equals(style)) {
				return 1;
			} else if (DisplayStyle.HIERARCHIC_MULTI_TREE_COLUMN.equals(style)) {
				boolean hasConfForFirstLevel = FillingConfigurationUtils.hasTreeFillingConfigurationForDepth(this.manager.getTable(), 0);
				int maxDepth = FillingConfigurationUtils.getMaxDepthForTree(this.manager.getTable());
				int depth = maxDepth * 2;
				if (!hasConfForFirstLevel) {
					depth += 1;
				} else {
					depth += 2;
				}
				return depth;
			}
		}
		return 0;
	}

	/**
	 *
	 * @param axis
	 *            a tree item axis
	 * @return
	 * 		the depth of the element
	 */
	protected final int getDepth(final ITreeItemAxis axis) {
		if (this.manager instanceof ITreeNattableModelManager) {
			return ((ITreeNattableModelManager) this.manager).getTreeItemDepth(axis);
		}
		return 0;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.dataprovider.AbstractIndexHeaderDataProvider#getDa_itaValue(int, int)
	 *
	 * @param columnIndex
	 * @param rowIndex
	 * @return
	 */
	@Override
	public Object getDataValue(int columnIndex, int rowIndex) {
		if (rowIndex >= 0) {
			Object rowElement = this.manager.getRowElement(rowIndex);
			final DisplayStyle style = TableHelper.getTableDisplayStyle(this.manager.getTable());
			if (DisplayStyle.NORMAL.equals(style) || DisplayStyle.HIERARCHIC_SINGLE_TREE_COLUMN.equals(style)) {
				return rowElement;
			} else if (DisplayStyle.HIERARCHIC_MULTI_TREE_COLUMN.equals(style)) {
				if (rowElement instanceof ITreeItemAxis) {
					int depth = getDepth((ITreeItemAxis) rowElement);
					if (depth == columnIndex) {
						return rowElement;
					}
				}
			}
		}
		return ""; //$NON-NLS-1$
	}
}
