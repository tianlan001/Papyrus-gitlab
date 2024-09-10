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

package org.eclipse.papyrus.infra.nattable.tree;

import java.util.Comparator;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.sort.ISortModel;
import org.eclipse.nebula.widgets.nattable.tree.SortableTreeComparator;
import org.eclipse.papyrus.infra.nattable.manager.axis.CompositeTreeAxisManagerForEventList;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.utils.TableHelper;

import ca.odell.glazedlists.TreeList;

/**
 * @author Vincent Lorenzo
 *
 */
public class DatumTreeFormat implements TreeList.Format<ITreeItemAxis> {

	/**
	 * the comparator to use to sort the tree
	 */
	private Comparator<ITreeItemAxis> comparator = null;
	
	/**
	 * the axis manager providing comparator
	 */
	private CompositeTreeAxisManagerForEventList axisManager;

	/**
	 * the sort model to use
	 */
	private final ISortModel sortModel;

	/**
	 *
	 * Constructor.
	 *
	 * @param sortModel
	 */
	public DatumTreeFormat(ISortModel sortModel) {
		this.sortModel = sortModel;
	}

	/**
	 *
	 * @see ca.odell.glazedlists.TreeList.Format#getPath(java.util.List, java.lang.Object)
	 *
	 * @param path
	 * @param element
	 */
	@Override
	public void getPath(List<ITreeItemAxis> path, ITreeItemAxis element) {
		TableHelper.getPath(path, element);
	}

	/**
	 *
	 * @see ca.odell.glazedlists.TreeList.Format#allowsChildren(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public boolean allowsChildren(final ITreeItemAxis element) {
		return true;
	}

	/**
	 *
	 * @param axisManager
	 *            the axis
	 */
	public final void setTreeComparatorProvider(final CompositeTreeAxisManagerForEventList comparatorProvider) {
		this.axisManager = comparatorProvider;
	}

	/**
	 *
	 * @see ca.odell.glazedlists.TreeList.Format#getComparator(int)
	 *
	 * @param depth
	 * @return
	 */
	@Override
	public Comparator<ITreeItemAxis> getComparator(final int depth) {
		if (this.axisManager == null) {
			return null;
		}
		if (this.comparator == null) {
			this.comparator = new SortableTreeComparator<ITreeItemAxis>(new Comparator<ITreeItemAxis>() {
				/**
				 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
				 *
				 * @param o1
				 * @param o2
				 * @return
				 */
				@Override
				public int compare(ITreeItemAxis o1, ITreeItemAxis o2) {
					return axisManager.compare(sortModel, depth, o1, o2);
				}

			}, this.sortModel);
		}
		return this.comparator;
	}
}
