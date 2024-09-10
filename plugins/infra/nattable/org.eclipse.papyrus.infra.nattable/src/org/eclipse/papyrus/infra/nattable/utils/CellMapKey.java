/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.utils;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecell.ICellAxisWrapper;


/**
 * This class is used as key to find easily the cell for a row and a column in the table metamodel
 *
 * @author Vincent Lorenzo
 *
 */
public class CellMapKey {



	/**
	 * the column element
	 */
	private final Object columnElement;

	/**
	 * the row element
	 */
	private final Object rowElement;

	/**
	 *
	 * Constructor.
	 *
	 * @param columnElement
	 *            the columnElement
	 * @param rowElement
	 *            the rowElement
	 */
	public CellMapKey(final Object columnElement, final Object rowElement) {
		if (columnElement instanceof ICellAxisWrapper) {
			this.columnElement = AxisUtils.getRepresentedElement(((ICellAxisWrapper) columnElement).getElement());
		} else {
			this.columnElement = AxisUtils.getRepresentedElement(columnElement);
		}
		if (rowElement instanceof ICellAxisWrapper) {
			this.rowElement = AxisUtils.getRepresentedElement(((ICellAxisWrapper) rowElement).getElement());
		} else {
			this.rowElement = AxisUtils.getRepresentedElement(rowElement);
		}
	}

	/**
	 *
	 * @see java.lang.Object#hashCode()
	 *
	 * @return
	 */
	@Override
	public int hashCode() {
		int hashCode = this.columnElement.hashCode() + 10 * this.rowElement.hashCode();
		return hashCode;
	}

	/**
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 *
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CellMapKey) {
			return obj.hashCode() == hashCode();
		}
		return false;
	}
}
