/*******************************************************************************
 * Copyright (c) 2014 CEA LIST.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Juan Cadavid <juan.cadavid@cea.fr> implementation
 ******************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode.tests;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.papyrus.infra.services.controlmode.tests.messages"; //$NON-NLS-1$

	public static String AbstractControlModeTest_1;

	public static String AbstractUncontrolModelTest_1;

	public static String ControlModelTest_4;

	public static String ControlModelWithProfileTest_4;

	public static String ExistingResourceControlModeTest_5;

	public static String UncontrolModelTest_4;

	public static String UncontrolModelWithProfileTest_5;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
