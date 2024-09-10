/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr>
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.properties.messages;

import org.eclipse.osgi.util.NLS;

/**
 * @author Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr>
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.infra.nattable.properties.messages.messages"; //$NON-NLS-1$
	public static String CreateNewElementCellEditorButtonAction_CreateNewElement;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
