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

import java.util.regex.Pattern;

import org.eclipse.core.runtime.Assert;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.validate.IDataValidator;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;

/**
 * The validator used by filter for the regex
 *
 */
public class RegexFilterDataValidator implements IDataValidator {

	/**
	 * @see org.eclipse.nebula.widgets.nattable.data.validate.IDataValidator#validate(int, int, java.lang.Object)
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

	/**
	 * @see org.eclipse.nebula.widgets.nattable.data.validate.IDataValidator#validate(org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object)
	 *
	 * @param cell
	 * @param configRegistry
	 * @param newValue
	 * @return
	 */
	@Override
	public boolean validate(ILayerCell cell, IConfigRegistry configRegistry, Object newValue) {
		Assert.isTrue(newValue instanceof String);
		String regex = (String) newValue;
		if (regex.isEmpty()) {
			return true;
		}
		try {
			Pattern.compile(regex);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

}
