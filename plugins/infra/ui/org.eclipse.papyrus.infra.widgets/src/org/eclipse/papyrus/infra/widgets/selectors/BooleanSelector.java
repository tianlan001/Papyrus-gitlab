/*****************************************************************************
 * Copyright (c) 2010, 2017 CEA LIST.
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
 *  Pierre GAUTIER (CEA LIST) - bug 521857
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.selectors;

import org.eclipse.papyrus.infra.tools.util.BooleanHelper;



/**
 * A Selector for Integer values
 *
 * @author Camille Letavernier
 *
 */
public class BooleanSelector extends StringSelector {

	/**
	 * Constructs a Selector for Integer values
	 */
	public BooleanSelector() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean[] getSelectedElements() {
		Boolean[] result = new Boolean[0];
		if (BooleanHelper.isBoolean(text.getText())) {
			result = new Boolean[] { new Boolean(text.getText()) };
			text.setText(""); //$NON-NLS-1$
		}
		return result;
	}
}
