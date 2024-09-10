/******************************************************************************
 * Copyright (c) 2005, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Artem Tikhomirov (Borland) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.common.codegen;

import org.eclipse.osgi.util.NLS;

// XXX try package-local class as it doesn't need to be exposed
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.gmf.internal.common.codegen.messages"; //$NON-NLS-1$

	private Messages() {
	}

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	public static String merge;

	public static String interrupted;

	public static String unexpected;

	public static String problems; 

	public static String initproject;

	public static String start;

	public static String exception;

	public static String organizeImportsFail;
}
