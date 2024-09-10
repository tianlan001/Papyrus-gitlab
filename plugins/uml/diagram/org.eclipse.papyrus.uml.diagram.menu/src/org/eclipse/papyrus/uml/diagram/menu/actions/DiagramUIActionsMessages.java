/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) - Initial API and implementation
 /*****************************************************************************/
package org.eclipse.papyrus.uml.diagram.menu.actions;

import org.eclipse.osgi.util.NLS;

public class DiagramUIActionsMessages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.papyrus.uml.diagram.menu.actions.messages"; //$NON-NLS-1$

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, DiagramUIActionsMessages.class);
	}

	private DiagramUIActionsMessages() {
	}

	public static String PropertyDescriptorFactory_Font;
}
