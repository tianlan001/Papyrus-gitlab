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
 * The Interface INotification.
 * offers some services for a notification
 * 
 * @since 2.0
 */
public interface INotification {

	/** delete the current notification */
	void delete();

	/**
	 * whether the current notification is deleted
	 *
	 * @return true if notification is deleted
	 */
	boolean isDeleted();
}
