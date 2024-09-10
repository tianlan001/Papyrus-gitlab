package org.eclipse.papyrus.infra.nattable.filter.validator;

/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
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


import java.util.List;

import org.eclipse.nebula.widgets.nattable.filterrow.ParseResult;
import org.eclipse.nebula.widgets.nattable.filterrow.TextMatchingMode;
import org.eclipse.papyrus.infra.nattable.filter.FilterRowUtils;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;

/**
 *
 * Validator for Double values used in filter rows
 *
 */
public class DoubleFilterDataValidator extends AbstractFilterDataValidator {

	/**
	 * Constructor.
	 *
	 * @param valueSeparator
	 * @param matchingMode
	 */
	public DoubleFilterDataValidator(String valueSeparator, TextMatchingMode matchingMode) {
		super(valueSeparator, matchingMode);
	}

	/**
	 *
	 * @see org.eclipse.nebula.widgets.nattable.data.validate.DataValidator#validate(int, int, java.lang.Object)
	 *
	 * @param columnIndex
	 * @param rowIndex
	 * @param newValue
	 * @return
	 */
	@Override
	public boolean validate(int columnIndex, int rowIndex, Object newValue) {
		if (TypeUtils.isDoubleValue(newValue.toString())) {
			return true;
		}
		List<ParseResult> res;
		try {
			res = FilterRowUtils.parse((String) newValue, valueSeparator, matchingMode);
		} catch (Exception e) {
			return false;
		}
		for (ParseResult current : res) {
			String valueToMatch = current.getValueToMatch();
			if (valueToMatch == null || valueToMatch.equals(EMPTY_STRING)) {
				continue;
			}
			boolean tmp = TypeUtils.isDoubleValue(valueToMatch);
			if (!tmp) {
				return false;
			}
		}
		return true;
	}

}