/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.messages;

import org.eclipse.osgi.util.NLS;

/**
 * Messages class for org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.messages.messages"; //$NON-NLS-1$
	public static String ConfigurationModelElement_ErrorNotString;
	public static String ConfigurationModelElement_WarningNotSet;
	public static String IconDescriptorObservableValue_Undefined;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
