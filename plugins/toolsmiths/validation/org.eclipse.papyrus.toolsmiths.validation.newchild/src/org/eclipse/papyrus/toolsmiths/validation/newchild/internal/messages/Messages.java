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

package org.eclipse.papyrus.toolsmiths.validation.newchild.internal.messages;

import org.eclipse.osgi.util.NLS;

/**
 * Externalized messages for the <em>New Child Menu</em> plug-in validation bundle.
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.toolsmiths.validation.newchild.internal.messages.messages"; //$NON-NLS-1$
	public static String MissingNewChildMenuExtension_0;
	public static String MissingNewChildMenuExtension_1;
	public static String NewChildMenuPluginChecker_0;
	public static String NewChildMenuPluginChecker_1;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
