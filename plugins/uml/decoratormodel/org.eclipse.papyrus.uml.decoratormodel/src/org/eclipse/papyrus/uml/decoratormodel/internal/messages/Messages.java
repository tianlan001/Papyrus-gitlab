/*****************************************************************************
 * Copyright (c) 2014 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.decoratormodel.internal.messages;

import org.eclipse.osgi.util.NLS;

/**
 * @author damus
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.uml.decoratormodel.internal.messages.messages"; //$NON-NLS-1$
	public static String CreateDecoratorModelCommand_0;
	public static String DecoratorModelIndex_0;
	public static String DeleteDecoratorModelCommand_0;
	public static String ReclaimProfileApplicationsCommand_0;
	public static String SaveDecoratorModelCommand_0;
	public static String SeparateProfileApplicationsCommand_0;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
