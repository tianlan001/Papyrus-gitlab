/******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, Artal
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
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : Remove reference to gmfgraph and ModelViewMap
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.common;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;


public class Activator extends Plugin {
	private static Activator anInstance;

	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		anInstance = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		anInstance = null;
		super.stop(context);
	}

	public static String getID() {
		return anInstance.getBundle().getSymbolicName();
	}

	public static void log(IStatus s) {
		anInstance.getLog().log(s);
	}

	public static void logError(String message, Exception ex) {
		assert ex != null;
		if (message == null) {
			message = ex.getMessage() == null ? new String() : ex.getMessage();
		}
		log(new Status(IStatus.ERROR, getID(), 0, message, ex));
	}
	

	public static IStatus createStatus(int statusCode, String message, Throwable ex) {
		return new Status(statusCode, getID(), 0, message, ex);
	}

	public static IStatus createError(String message, Throwable ex) {
		return createStatus(IStatus.ERROR, message, ex);
	}

}
