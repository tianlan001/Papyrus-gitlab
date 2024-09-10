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
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation, Bug 521908
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.widgets.selectors;

import org.eclipse.papyrus.infra.widgets.util.Constants;

/**
 * Selector for the UnlimitedNaturalEditor. It accepts "-1", "*" and all integer >=0.
 *
 * @since 3.1
 */
public class UnlimitedNaturalSelector extends StringSelector {

	/**
	 * Constructs a Selector for Integer values
	 */
	public UnlimitedNaturalSelector() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer[] getSelectedElements() {
		Integer[] result = new Integer[0];
		String newText = text.getText();
		if (isInteger(newText)) {
			int value = Integer.parseInt(newText);
			if (value >= -1) {
				result = new Integer[] { value };
				text.setText(Constants.EMPTY_STRING);
			}
		} else {
			if (Constants.INFINITY_STAR.equals(newText)) {
				result = new Integer[] { new Integer(-1) };
				text.setText(Constants.EMPTY_STRING);
			}
		}

		return result;
	}

	/**
	 * 
	 * @param textToCheck
	 * @return
	 */
	private boolean isInteger(final String textToCheck) {
		boolean isInteger = true;
		try {
			Integer.parseInt(textToCheck);
		} catch (NumberFormatException e) {
			isInteger = false;
		}
		return isInteger;
	}

}
