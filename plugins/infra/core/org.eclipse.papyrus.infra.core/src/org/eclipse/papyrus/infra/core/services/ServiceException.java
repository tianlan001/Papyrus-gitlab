/*****************************************************************************
 * Copyright (c) 2011, 2015 LIFL, Christian W. Damus, and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  LIFL - Initial API and implementation
 *  Christian W. Damus - bug 468030
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.services;

/**
 * Root Exception of Services Exception.
 *
 * @author dumoulin
 *
 */
public class ServiceException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public ServiceException() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 */
	public ServiceException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 *
	 * @param cause
	 */
	public ServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 * @param cause
	 */
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException chain(Throwable next) {
		return (next == null) ? this : new ServiceMultiException().chain(next);
	}

	public ServiceException chain(String identifier, Throwable next) {
		return (next == null) ? this : new ServiceMultiException().chain(identifier, next);
	}

	public static ServiceException chain(ServiceException serviceException, Throwable next) {
		if (serviceException != null) {
			return serviceException.chain(next);
		} else if (next instanceof ServiceException) {
			return (ServiceException) next;
		} else if (next != null) {
			return new ServiceMultiException().chain(next);
		} else {
			return serviceException;
		}
	}

	public static ServiceException chain(ServiceException serviceException, String identifier, Throwable next) {
		if (serviceException != null) {
			return serviceException.chain(identifier, next);
		} else if (next != null) {
			return new ServiceMultiException().chain(identifier, next);
		} else {
			return serviceException;
		}
	}
}
