/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.tools.util;

import java.util.concurrent.Executor;

import org.eclipse.papyrus.infra.tools.Activator;

/**
 * A provider of {@link Executor}s offering various synchronous and asynchronous
 * execution characteristics.
 * 
 * @since 2.0
 */
public class CoreExecutors {

	// Not instantiable by clients
	private CoreExecutors() {
		super();
	}

	/**
	 * Obtains a service that posts tasks for asynchronous execution on the
	 * SWT display thread, if there is one. If there is no display, then
	 * a default background-thread executor is supplied by the Java platform.
	 * 
	 * @return an executor service on the UI thread (if there is a UI). Never
	 *         {@code null} and always the same instance. Clients may not shut down
	 *         this executor; attempting to do so will result in {@link IllegalStateException}s
	 */
	public static IExecutorService getUIExecutorService() {
		return Activator.getDefault().getUIExecutorService();
	}
}
