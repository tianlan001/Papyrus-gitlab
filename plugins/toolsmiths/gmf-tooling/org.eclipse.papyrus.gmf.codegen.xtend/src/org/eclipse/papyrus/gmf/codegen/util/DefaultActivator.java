/******************************************************************************
 * Copyright (c) 2013, 2020 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Michael Golubev (Borland) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.codegen.util;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class DefaultActivator extends Plugin {

	private BundleContext myContext = null;

	public void start(BundleContext context) throws Exception {
		super.start(context);
		myContext = context;
	}

	public void stop(BundleContext context) throws Exception {
		myContext = null;
		super.stop(context);
	}

	public BundleContext getContext() {
		return myContext;
	}
}
