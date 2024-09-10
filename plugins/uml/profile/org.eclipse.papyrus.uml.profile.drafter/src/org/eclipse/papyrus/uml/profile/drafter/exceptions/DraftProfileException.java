/*****************************************************************************
 * Copyright (c) 2014 Cedric Dumoulin.
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

package org.eclipse.papyrus.uml.profile.drafter.exceptions;


/**
 * Root Exception from Draft Profile package
 * @author cedric dumoulin
 *
 */
public class DraftProfileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 *
	 */
	public DraftProfileException() {
		
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 */
	public DraftProfileException(String message) {
		super(message);
		
	}

	/**
	 * Constructor.
	 *
	 * @param cause
	 */
	public DraftProfileException(Throwable cause) {
		super(cause);
		
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 * @param cause
	 */
	public DraftProfileException(String message, Throwable cause) {
		super(message, cause);
		
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public DraftProfileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
