/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.selectors;


/**
 * A Selector for Integer values
 *
 * @author Camille Letavernier
 *
 */
public class IntegerSelector extends StringSelector {

	/**
	 * Constructs a Selector for Integer values
	 */
	public IntegerSelector() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer[] getSelectedElements() {
		Integer[] result;
		try {
			result = new Integer[] { Integer.parseInt(text.getText()) };
			text.setText(""); //$NON-NLS-1$
		} catch (NumberFormatException ex) {
			result = new Integer[0];
		}
		return result;
	}
}
