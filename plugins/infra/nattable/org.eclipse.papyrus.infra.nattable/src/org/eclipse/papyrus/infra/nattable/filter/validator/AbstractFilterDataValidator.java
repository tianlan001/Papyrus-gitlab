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
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.filter.validator;

import org.eclipse.nebula.widgets.nattable.data.validate.DataValidator;
import org.eclipse.nebula.widgets.nattable.filterrow.TextMatchingMode;

/**
 * @author Vincent Lorenzo
 *
 */
public abstract class AbstractFilterDataValidator extends DataValidator {

	/**
	 * the value separator
	 */
	protected final String valueSeparator;

	/**
	 * the matching mode
	 */
	protected final TextMatchingMode matchingMode;

	protected static final String EMPTY_STRING = ""; //$NON-NLS-1$

	/**
	 * Constructor.
	 *
	 */
	public AbstractFilterDataValidator(String valueSeparator, TextMatchingMode matchingMode) {
		this.valueSeparator = valueSeparator;
		this.matchingMode = matchingMode;
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.data.validate.DataValidator#validate(int, int, java.lang.Object)
	 *
	 * @param columnIndex
	 * @param rowIndex
	 * @param newValue
	 * @return
	 */
	@Override
	public boolean validate(int columnIndex, int rowIndex, Object newValue) {
		return false;
	}

}
