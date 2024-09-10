/*****************************************************************************
 * Copyright (c) 2019, 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 563983
 *****************************************************************************/

package org.eclipse.papyrus.uml.expressions.edit.internal.messages;

import org.eclipse.osgi.util.NLS;

/**
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.uml.expressions.edit.internal.messages.messages"; //$NON-NLS-1$
	public static String SingleProfileURIEditorFactory_SelectARootProfileURI;
	public static String SinglePropertyAttributeValidator_SelectOnePropertyTypeWithAPrimitiveTypeOrEnumeration;
	public static String SingleRootProfileValidator_SelectARootProfileWithAURI;
	public static String SingleStereotypeAttributeEditorFactory_SelectAStereotypePropertyTypedWithAPrimitiveTypeOrEnumeration;
	public static String SingleStereotypeValidator_SelectAStereotype;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
