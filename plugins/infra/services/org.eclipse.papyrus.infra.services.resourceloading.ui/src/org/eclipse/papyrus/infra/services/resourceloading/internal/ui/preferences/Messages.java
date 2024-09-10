/*****************************************************************************
 * Copyright (c) 2012 Atos.
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
 *  Laurent Devernay (Atos) laurent.devernay@atos.net
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.resourceloading.internal.ui.preferences;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "messages"; //$NON-NLS-1$

	public static String LoadedAssociatedResourceGroup_0;

	public static String LoadedAuthorizedResourceGroup_0;

	public static String LoadedAuthorizedResourceGroup_1;

	public static String LoadedAuthorizedResourceGroup_2;

	public static String LoadinStrategyGroup_0;

	public static String LoadinStrategyGroup_1;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
