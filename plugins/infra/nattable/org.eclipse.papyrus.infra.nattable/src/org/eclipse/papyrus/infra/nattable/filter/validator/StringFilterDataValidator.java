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

import org.eclipse.core.runtime.Assert;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.validate.IDataValidator;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.papyrus.infra.nattable.filter.PapyrusTextMatchingMode;

/**
 * The validator used for all string editor in the filter row header
 *
 */
public class StringFilterDataValidator implements IDataValidator {

	/**
	 * validator used for regex
	 */
	private RegexFilterDataValidator regexValidator = new RegexFilterDataValidator();


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
		if (newValue == null) {
			return true;
		}
		Assert.isTrue(newValue instanceof String);
		String value = (String) newValue;

		if (value.startsWith(PapyrusTextMatchingMode.NUM.getMode())) {
			String num = value.replaceFirst(PapyrusTextMatchingMode.NUM.getMode(), ""); //$NON-NLS-1$
			// TODO
		} else if (value.startsWith(PapyrusTextMatchingMode.REGEX_FIND.getMode())) {
			String regex = value.replaceFirst(PapyrusTextMatchingMode.REGEX_FIND.getMode(), ""); //$NON-NLS-1$
			return regexValidator.validate(cell, configRegistry, regex);
		} else if (value.startsWith(PapyrusTextMatchingMode.REGEX_MATCH.getMode())) {
			String regex = value.replaceFirst(PapyrusTextMatchingMode.REGEX_MATCH.getMode(), ""); //$NON-NLS-1$
			return regexValidator.validate(cell, configRegistry, regex);
		}
		return true;
	}

}
