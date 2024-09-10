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
 *   CEA LIST - Initial API and implementation
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 559975
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.manager.axis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.papyrus.infra.nattable.manager.cell.CellManagerFactory;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisManagerRepresentation;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.FillingConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableHelper;

/**
 * This comparator is used to sort the ITreeItemAxis in the table according to the UML Model and the table model (order of the filling configuration).
 * The sort of the row selecting the header IS NOT DONE HERE!
 * This comparator sort the empty line {@link ITreeItemAxis#getElement()}==null at the end of the table.
 *
 */
public class ITreeItemAxisComparator implements Comparator<ITreeItemAxis> {

	/**
	 * the table manager
	 */
	private INattableModelManager manager;

	/**
	 * the axis manager
	 */
	private IAxisManager axisManager;

	/**
	 * Constructor.
	 * 
	 * @param representedAxisManager
	 *
	 */
	public ITreeItemAxisComparator(INattableModelManager tableManager) {
		this(tableManager, null);
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param tableManager
	 *            the table manager
	 * @param axisManager
	 *            the axis manager representation, if <code>null</code>, we will deduce it from the compared axis. If you are during the opening of the table, you MUST define it, because
	 *            we won't be able to deduce it from the IAxis.
	 */
	public ITreeItemAxisComparator(INattableModelManager tableManager, AbstractTreeAxisManagerForEventList axisManager) {
		this.manager = tableManager;
		this.axisManager = axisManager;
	}

	private int compareObjectWithDifferentParent(ITreeItemAxis axis1, ITreeItemAxis axis2) {
		ITreeItemAxis parent1 = axis1.getParent();
		ITreeItemAxis parent2 = axis2.getParent();
		Assert.isTrue(parent1 != parent2);

		List<ITreeItemAxis> path1 = new ArrayList<ITreeItemAxis>();
		List<ITreeItemAxis> path2 = new ArrayList<ITreeItemAxis>();
		TableHelper.getPath(path1, axis1);
		TableHelper.getPath(path2, axis2);
		if (path1.size() == path2.size()) {
			int indexToCompare = path1.size() - 1;
			indexToCompare--;
			// depth is currently not used and has no meaning in this case
			return compare(path1.get(indexToCompare), path2.get(indexToCompare));
		}

		ITreeItemAxis firstCommon = null;
		for (ITreeItemAxis current : path1) {
			if (path2.contains(current)) {
				firstCommon = current;
				break;
			}
		}

		// If there is no common ancestor (when different tree filling) or not really common, don't compare them
		if (null != firstCommon && null != path1 && !path1.isEmpty() && !firstCommon.equals(path1.get(path1.size() - 1))) {
			int index1 = path1.indexOf(firstCommon);
			int index2 = path2.indexOf(firstCommon);
			return compare(path1.get(index1++), path2.get(index2++));
		}
          
		// Bug 501332 : This was replaced by the previous block
		//if (firstCommon == null) {
		//	Activator.log.warn("No common ITreeItemAxis found"); //$NON-NLS-1$
		//	throw new UnsupportedOperationException("Not yet implemented, please submit us a bug, with your example"); //$NON-NLS-1$
		//} else if (path1.get(path1.size() - 1) == firstCommon) {
		//	// Activator.log.warn("no really common");
		//	return 0;
		//} else {
		//	int index1 = path1.indexOf(firstCommon);
		//	int index2 = path2.indexOf(firstCommon);
		//	return compare(path1.get(index1++), path2.get(index2++));
		//}

		return 0;
	}

	/**
	 * 
	 * @param axis1
	 * @param axis2
	 * @return
	 * 		the axis manager representation
	 */
	protected AxisManagerRepresentation getAxisManagerRepresentation(ITreeItemAxis axis1, ITreeItemAxis axis2) {
		if (this.axisManager != null) {
			return this.axisManager.getAxisManagerRepresentation();
		}
		IAxisManager axisManager = manager.getRowAxisManager();// return null when we are opening the table
		Assert.isTrue(axisManager instanceof ICompositeAxisManager);

		IAxisManager subAxisManager1 = ((ICompositeAxisManager) axisManager).getSubAxisManagerFor(axis1);
		IAxisManager subAxisManager2 = ((ICompositeAxisManager) axisManager).getSubAxisManagerFor(axis2);
		if ((subAxisManager1 != null || subAxisManager2 != null) && (subAxisManager1 != subAxisManager2)) {
			throw new UnsupportedOperationException("this case is not yet supported"); //$NON-NLS-1$
		}
		if (subAxisManager1 != null) {
			return subAxisManager1.getAxisManagerRepresentation();
		} else {
			throw new UnsupportedOperationException("this case is not yet supported"); //$NON-NLS-1$
		}
	}

	/**
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 *
	 * @param o1
	 * @param o2
	 * @return
	 */
	@Override
	public int compare(ITreeItemAxis axis1, ITreeItemAxis axis2) {
		if (axis1 == axis2) {
			return 0;
		}

		if (axis1.getParent() != axis2.getParent()) {
			return compareObjectWithDifferentParent(axis1, axis2);
		}
		Object el1 = axis1.getElement();
		Object el2 = axis2.getElement();
		int index1, index2;

		// we are comparing 2 filling configurations
		if (el1 instanceof TreeFillingConfiguration) {
			Assert.isTrue(el2 instanceof TreeFillingConfiguration);
			int depth1 = ((TreeFillingConfiguration) el1).getDepth();
			int depth2 = ((TreeFillingConfiguration) el2).getDepth();
			if (depth1 != depth2) {
				// impossible
				throw new UnsupportedOperationException("Please fill a bug with your example"); //$NON-NLS-1$
			}
			final List<TreeFillingConfiguration> confs = FillingConfigurationUtils.getTreeFillingConfiguration(manager.getTable(), getAxisManagerRepresentation(axis1, axis2));
			index1 = confs.indexOf(el1);
			index2 = confs.indexOf(el2);
		} else {// we must compare values
			// parent1 and parent2 are axis representing filling configuration
			ITreeItemAxis parent1 = axis1.getParent();
			if (parent1 == null) {
				// its root element, filled by DnD

				List<IAxis> axis = getAxisManager(axis1, axis2).getRepresentedContentProvider().getAxis();
				index1 = axis.indexOf(axis1);
				index2 = axis.indexOf(axis2);
			} else {
				TreeFillingConfiguration conf = (TreeFillingConfiguration) parent1.getElement();
				ITreeItemAxis grandFather1 = parent1.getParent();
				Object context = null;
				if (grandFather1 == null) {
					context = manager.getTable().getContext();
				} else {
					context = grandFather1.getElement();
				}
				Collection<?> values = CellManagerFactory.INSTANCE.getCrossValueAsCollection(conf.getAxisUsedAsAxisProvider(), context, this.manager);
				if (values instanceof List<?>) {
					index1 = ((List<?>) values).indexOf(axis1.getElement());
					index2 = ((List<?>) values).indexOf(axis2.getElement());
					if (index1 == -1 && index2 == -1) {
						// no idea about this case
					} else
					// allows to set empty line at the end of the table
					if (index1 == -1) {
						index1 = index2 + 1;
					} else if (index2 == -1) {
						index2 = index1 + 1;
					}
				} else {
					index1 = index2 = 0;
				}
			}
		}

		int res = index1 - index2;
		return Integer.signum(res);
	}

	/**
	 * 
	 * @param axis1
	 *            the first axis
	 * @param axis2
	 *            the second axis
	 * @return
	 * 		the axis manager used
	 */
	private IAxisManager getAxisManager(ITreeItemAxis axis1, ITreeItemAxis axis2) {
		if (this.axisManager != null) {
			return this.axisManager;
		} else {
			IAxisManager axisManager = manager.getRowAxisManager(); // will return null during the opening of the table
			Assert.isTrue(axisManager instanceof ICompositeAxisManager);
			IAxisManager subAxisManager1 = ((ICompositeAxisManager) axisManager).getSubAxisManagerFor(axis1);
			IAxisManager subAxisManager2 = ((ICompositeAxisManager) axisManager).getSubAxisManagerFor(axis2);
			if (subAxisManager1 != null && subAxisManager1 != subAxisManager2) {
				throw new UnsupportedOperationException("this case is not yet supported"); //$NON-NLS-1$
			}
			return subAxisManager1;
		}
	}
}
