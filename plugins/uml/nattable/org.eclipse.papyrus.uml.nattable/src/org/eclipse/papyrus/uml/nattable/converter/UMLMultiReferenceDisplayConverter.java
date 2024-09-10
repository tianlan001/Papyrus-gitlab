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
package org.eclipse.papyrus.uml.nattable.converter;

import java.util.Collections;

import org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager;

/**
 * 
 * This class allows us to convert UML Object into string and string to UML Object
 *
 */
public class UMLMultiReferenceDisplayConverter extends UMLSingleReferenceDisplayConverter {


	/**
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.data.convert.DisplayConverter#canonicalToDisplayValue(java.lang.Object)
	 *
	 * @param canonicalValue
	 * @return
	 */
	@Override
	public Object canonicalToDisplayValue(Object canonicalValue) {
		if (canonicalValue == null) {
			return ICellManager.EMPTY_STRING;
		}
		return super.canonicalToDisplayValue(canonicalValue);
	}

	/**
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.data.convert.DisplayConverter#displayToCanonicalValue(java.lang.Object)
	 *
	 * @param displayValue
	 * @return
	 */
	@Override
	public Object displayToCanonicalValue(Object displayValue) {
		if (ICellManager.NULL_VALUE.equals(displayValue) || ICellManager.EMPTY_STRING.equals(displayValue)) {
			return Collections.EMPTY_LIST;
		} else {
			return super.displayToCanonicalValue(displayValue);
		}
	}
}
