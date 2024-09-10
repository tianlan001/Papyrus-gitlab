/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
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
 *		CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.edit.messages;

import org.eclipse.osgi.util.NLS;

/**
 * Internationalization messages class.
 */
public class Messages extends NLS {

	public static String ElementTypeValidator_NoSelection;
	public static String ElementTypeValidator_ValidSelection;
	public static String ElementTypeValidator_InvalidSelection;

	public static String TypeContext_ContextNotFound;

	public static String ElementEditServiceProvider_UnexpectedParameterType;
	public static String ElementEditServiceProvider_NoIElementTypeFound;

	public static String ElementEditServiceUtils_UnableToFindElementType;
	public static String ElementEditServiceUtils_UnableToFindServiceProvider;
	public static String ElementEditServiceUtils_CantGetEditingDomainProvider;

	static {
		NLS.initializeMessages("messages", Messages.class); //$NON-NLS-1$
	}

	private Messages() {
	}
}
