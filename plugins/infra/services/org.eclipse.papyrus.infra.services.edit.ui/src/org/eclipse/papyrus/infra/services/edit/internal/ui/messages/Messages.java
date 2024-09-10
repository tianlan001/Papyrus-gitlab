/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 *	CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.edit.internal.ui.messages;

import org.eclipse.osgi.util.NLS;

/**
 * Internationalization messages class.
 */
public class Messages extends NLS {

	public static String ElementTypeValidator_NoSelection;
	public static String ElementTypeValidator_ValidSelection;
	public static String ElementTypeValidator_InvalidSelection;

	static {
		NLS.initializeMessages("messages", Messages.class); //$NON-NLS-1$
	}

	private Messages() {
	}
}
