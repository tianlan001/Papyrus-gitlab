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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.selectors;


/**
 * A Selector for Real values
 *
 * @author Vincent Lorenzo
 *
 */
public class RealSelector extends StringSelector {

	/**
	 * Constructs a Selector for Real values
	 */
	public RealSelector() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double[] getSelectedElements() {
		Double[] result;
		try {
			result = new Double[] { Double.parseDouble((text.getText())) };
			text.setText(""); //$NON-NLS-1$
		} catch (NumberFormatException ex) {
			result = new Double[0];
		}
		return result;
	}
}
