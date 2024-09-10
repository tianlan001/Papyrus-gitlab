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
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 515806
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.filter.validator;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.validate.IDataValidator;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.papyrus.infra.nattable.utils.CellHelper;

/**
 * The validator used for boolean editor in the filter row header.
 *
 * @since 3.0
 */
public class BooleanFilterDataValidator implements IDataValidator {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.nebula.widgets.nattable.data.validate.IDataValidator#validate(int, int, java.lang.Object)
	 */
	@Override
	public boolean validate(int columnIndex, int rowIndex, Object newValue) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.nebula.widgets.nattable.data.validate.IDataValidator#validate(org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object)
	 */
	@Override
	public boolean validate(ILayerCell cell, IConfigRegistry configRegistry, Object newValue) {
		if (null == newValue) {
			return true;
		}
		if (newValue instanceof Boolean) {
			return true;
		} else if (newValue instanceof String && newValue.equals(CellHelper.getUnsupportedCellContentsText())) {
			return true;
		} else if (newValue instanceof Collection<?>) {
			boolean result = true;
			final Iterator<?> values = ((Collection<?>) newValue).iterator();
			while (values.hasNext() && result) {
				final Object value = values.next();
				result = validate(cell, configRegistry, value);
			}
			return result;
		}
		return false;
	}

}
