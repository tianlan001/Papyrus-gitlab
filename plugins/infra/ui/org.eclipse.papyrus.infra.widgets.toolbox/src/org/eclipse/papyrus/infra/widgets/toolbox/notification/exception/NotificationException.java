/*****************************************************************************
 * Copyright (c) 2010 ATOS ORIGIN.
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
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.toolbox.notification.exception;


/**
 * An exception for the notification
 *
 * @author tfaure
 *
 */
public class NotificationException extends RuntimeException {

	/** serialization UID */
	private static final long serialVersionUID = 3538113727604912133L;

	private final String message;

	public NotificationException(String message) {
		this.message = message;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMessage() {
		return message;
	}

}
