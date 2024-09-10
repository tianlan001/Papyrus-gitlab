/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.m2m.qvto.common.utils;


import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.m2m.qvt.oml.blackbox.java.Operation;
import org.eclipse.m2m.qvt.oml.blackbox.java.Operation.Kind;
import org.eclipse.m2m.qvt.oml.util.IContext;


public class TransformationUI {

	public static final String MONITOR = "monitor";

	@Operation(withExecutionContext = true, kind = Kind.HELPER)
	public void beginTask(IContext context, String name, int totalWork) {
		getMonitor(context).beginTask(name, totalWork);
	}

	@Operation(withExecutionContext = true, kind = Kind.HELPER)
	public void worked(IContext context, int worked) {
		getMonitor(context).worked(worked);
	}

	@Operation(withExecutionContext = true, kind = Kind.HELPER)
	public boolean isCanceled(IContext context) {
		return getMonitor(context).isCanceled();
	}





	private IProgressMonitor getMonitor(IContext context) {
		Object monitor = context.getConfigProperty(MONITOR);

		if(monitor instanceof IProgressMonitor) {
			return (IProgressMonitor)monitor;
		}

		return new NullProgressMonitor();
	}

}
