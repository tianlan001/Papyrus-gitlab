/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net- Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.views.modelexplorer.newchild.messages;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Externalisation of string.
 * 
 * @author Gabriel Pascual
 *
 */
public class Messages {
	private static final String BUNDLE_NAME = "messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
