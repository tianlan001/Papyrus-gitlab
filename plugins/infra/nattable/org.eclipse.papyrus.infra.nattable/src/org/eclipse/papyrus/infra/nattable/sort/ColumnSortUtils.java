/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.sort;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StringValueStyle;
import org.eclipse.papyrus.infra.nattable.sort.copy.NatTableComparatorChooser;
import org.eclipse.papyrus.infra.nattable.utils.NamedStyleConstants;

/**
 * Utility class for sort column feature
 *
 * @since 7.0
 */
public class ColumnSortUtils {

	private static final String SEPARATOR = "_"; //$NON-NLS-1$

	/**
	 * Constructor.
	 *
	 */
	private ColumnSortUtils() {
		// to prevent instantiation
	}

	/**
	 *
	 * @param chooser
	 *            the comparator chooser
	 * @param columnIndex
	 *            the index of the column
	 * @return
	 *         the string value to save in the {@link StringValueStyle} to store the sort state of the column identified by its index
	 *         the string must be respect this syntax: number_direction, where number is the order sequence of the column and direction is {@link SortDirectionEnum}
	 *
	 */
	public static final String buildSortStringValue(final NatTableComparatorChooser<?> chooser, int columnIndex) {
		final int clickSequence = chooser.getClickSequence(columnIndex);
		final SortDirectionEnum direction = chooser.getSortDirectionForColumnIndex(columnIndex);
		final StringBuilder builder = new StringBuilder();
		builder.append(clickSequence);
		builder.append(SEPARATOR);
		builder.append(direction.toString());
		return builder.toString();
	}

	/**
	 *
	 * @param manager
	 *            the table manager
	 * @return
	 *         the string to use to init the comparator.
	 *         the returned string is conform to the grammar defined defined in the java doc of
	 *         {@link org.eclipse.papyrus.infra.nattable.glazedlists.copy.AbstractTableComparatorChooser.fromString(String)}
	 *
	 */
	public static final String buildComparatorValues(final INattableModelManager manager) {
		final Map<StringValueStyle, IAxis> sortedAxis = new TreeMap<>(new SortComparator());
		for (final Object current : manager.getColumnElementsList()) {
			if (current instanceof IAxis) {
				final StringValueStyle style = (StringValueStyle) ((IAxis) current).getNamedStyle(NattablestylePackage.eINSTANCE.getStringValueStyle(), NamedStyleConstants.SORT); // $NON-NLS-1$
				if (style != null) {
					sortedAxis.put(style, (IAxis) current);
				}
			}
		}

		final StringBuilder builder = new StringBuilder();
		final Iterator<Entry<StringValueStyle, IAxis>> iter = sortedAxis.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<StringValueStyle, IAxis> current = iter.next();
			int colIndex = manager.getColumnElementsList().indexOf(current.getValue());
			SortDirectionEnum direction = SortDirectionEnum.valueOf(current.getKey().getStringValue().split(SEPARATOR)[1]);
			builder.append("column "); //$NON-NLS-1$
			builder.append(colIndex);
			if (SortDirectionEnum.DESC.equals(direction)) {
				builder.append(" reversed"); //$NON-NLS-1$
			}
			if (iter.hasNext()) {
				builder.append(", "); //$NON-NLS-1$
			}
		}
		return builder.toString();
	}

	/**
	 *
	 * Comparator used to organized the sorted column by sort order
	 *
	 */
	private static final class SortComparator implements Comparator<StringValueStyle> {

		/**
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 *
		 * @param o1
		 * @param o2
		 * @return
		 */
		@Override
		public int compare(StringValueStyle o1, StringValueStyle o2) {
			Integer order1 = Integer.valueOf(o1.getStringValue().split(SEPARATOR)[0]);
			Integer order2 = Integer.valueOf(o2.getStringValue().split(SEPARATOR)[0]);
			return order1.compareTo(order2);
		}

	}
}
