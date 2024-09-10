/******************************************************************************
 * Copyright (c) 2005, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0 which
 * accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:  Radek Dvorak (Borland) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.common.migrate;

import java.util.ResourceBundle;

import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.gmf.internal.common.Activator;

public class Messages {

	public static String oldModelVersionLoadedMigrationRequired;
	public static String oldModelVersionLoadErrorMigrationMayBeRequired;	
	public static String modelLoadedWithProblems;
	
	static {
		// FIXME rename methods
		ResourceBundle rb = Platform.getResourceBundle(Platform.getBundle(Activator.getID()));
		oldModelVersionLoadedMigrationRequired = rb.getString("oldModelVersionWarn");
		oldModelVersionLoadErrorMigrationMayBeRequired = rb.getString("oldModelVersionErr");
		modelLoadedWithProblems = rb.getString("modelLoadedWithProblems");
	}

	private Messages() {
	}
}
