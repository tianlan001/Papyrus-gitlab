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

package org.eclipse.papyrus.infra.types.core.internal.ui;

import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.infra.core.log.LogHelper;

/**
 * Logging utility.
 */
public class LogUtil extends LogHelper {

	public static final LogHelper LOG = new LogUtil();

	/**
	 * Not instantiable by clients.
	 */
	private LogUtil() {
		super(Platform.getBundle("org.eclipse.papyrus.infra.types.ui")); //$NON-NLS-1$
	}

}
