/*****************************************************************************
 * Copyright (c) 2010, 2016 ATOS ORIGIN, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Tristan Faure (ATOS ORIGIN INTEGRATION) tristan.faure@atosorigin.com - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.tools.notify;

/**
 * a runnable
 * 
 * @since 2.0
 *
 */
public interface NotificationRunnable {

	/**
	 * Run the runnable
	 *
	 * @param context
	 *            , used to fill properties, can contain data
	 */
	void run(IContext context);

	/**
	 * The label of the runnable
	 *
	 * @return the label
	 */
	String getLabel();
}
