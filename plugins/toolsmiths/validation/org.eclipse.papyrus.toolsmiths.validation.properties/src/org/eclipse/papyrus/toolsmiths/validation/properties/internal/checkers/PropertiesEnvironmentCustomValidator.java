/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.properties.internal.checkers;

import org.eclipse.papyrus.toolsmiths.validation.common.checkers.CustomModelChecker;

/**
 * Custom validation rules for <em>Properties Environment</em> models.
 */
public class PropertiesEnvironmentCustomValidator extends CustomModelChecker.SwitchValidator {

	public PropertiesEnvironmentCustomValidator(String nsURI) {
		super(nsURI);
	}

}
