/*****************************************************************************
 * Copyright (c) 2013 Cedric Dumoulin.
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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.profile.drafter.tests.exception;


/**
 * Root exception of the package.
 * 
 * @author cedric dumoulin
 *
 */
public class TestUtilsException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The enable suppression. */
	protected boolean enableSuppression;

	/** The writable stack trace. */
	protected boolean writableStackTrace;

	/**
	 * Constructor.
	 *
	 */
	public TestUtilsException() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 */
	public TestUtilsException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 *
	 * @param cause
	 */
	public TestUtilsException(Throwable cause) {
		this(null, cause);
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 * @param cause
	 */
	public TestUtilsException(String message, Throwable cause) {
		this(message, cause, false, false);
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public TestUtilsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause);

		this.enableSuppression = enableSuppression;
		this.writableStackTrace = writableStackTrace;
	}

}
