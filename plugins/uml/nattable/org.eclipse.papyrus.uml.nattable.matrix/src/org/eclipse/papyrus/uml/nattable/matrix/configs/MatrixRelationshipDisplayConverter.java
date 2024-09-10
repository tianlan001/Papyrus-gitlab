/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) - vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.matrix.configs;

import java.util.List;

import org.eclipse.nebula.widgets.nattable.data.convert.DisplayConverter;
import org.eclipse.papyrus.uml.tools.helper.UMLRelationshipHelper;
import org.eclipse.uml2.uml.Element;

/**
 * DisplayConvert used by Relationship's Matrix
 *
 */
public class MatrixRelationshipDisplayConverter extends DisplayConverter {

	/**
	 * this class provide the method to known the number of ends of a relationship
	 */
	private UMLRelationshipHelper helper = new UMLRelationshipHelper();

	/**
	 * @see org.eclipse.nebula.widgets.nattable.data.convert.DisplayConverter#canonicalToDisplayValue(java.lang.Object)
	 *
	 * @param canonicalValue
	 * @return
	 */
	@Override
	public CellMatrixRelationshipEnum canonicalToDisplayValue(final Object canonicalValue) {
		CellMatrixRelationshipEnum returnedValue = CellMatrixRelationshipEnum.UNKNOWN_VALUE;
		if (canonicalValue instanceof List<?>) {
			final List<?> list = (List<?>) canonicalValue;
			if (list.size() == 0) {
				returnedValue = CellMatrixRelationshipEnum.UNCHECKED;
			} else if (list.size() == 1) {
				final Object value = list.get(0);
				if (value instanceof Element) {
					int ends = this.helper.getNumberOfEnds((Element) value);
					if (ends == 2) {
						return CellMatrixRelationshipEnum.CHECKED;
					} else {
						return CellMatrixRelationshipEnum.CHECKED_MORE_THAN_2_ENDS;
					}
				}
			} else {
				returnedValue = CellMatrixRelationshipEnum.CHECKED_MORE_THAN_ONE_LINK;
			}
		}
		return returnedValue;
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.data.convert.DisplayConverter#displayToCanonicalValue(java.lang.Object)
	 *
	 * @param displayValue
	 * @return
	 */
	@Override
	public Object displayToCanonicalValue(final Object displayValue) {
		if(displayValue instanceof Boolean) {
			return (Boolean)displayValue;
		}
		return null;
	}

}
