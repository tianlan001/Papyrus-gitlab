/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr  - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.tools.utils.internal.messages;

import org.eclipse.osgi.util.NLS;

/**
 * @author Vincent LORENZO
 * @since 3.3
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.uml.tools.utils.internal.messages.messages"; //$NON-NLS-1$
	public static String NamedElementIndexNamingStrategyEnum_NoIndexDescription;
	public static String NamedElementIndexNamingStrategyEnum_QuickIndexDescription;
	public static String NamedElementIndexNamingStrategyEnum_UniqueIndexDescription;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
