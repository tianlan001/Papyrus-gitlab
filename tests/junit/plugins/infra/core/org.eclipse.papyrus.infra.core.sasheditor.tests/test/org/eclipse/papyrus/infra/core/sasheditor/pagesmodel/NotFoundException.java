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

package org.eclipse.papyrus.infra.core.sasheditor.pagesmodel;


/**
 * @author cedric dumoulin
 *
 */
public class NotFoundException extends PagesModelException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 *
	 */
	public NotFoundException() {
		super();
		
	}

	/**
	 * Constructor.
	 *
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public NotFoundException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		
	}

	/**
	 * Constructor.
	 *
	 * @param arg0
	 * @param arg1
	 */
	public NotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}

	/**
	 * Constructor.
	 *
	 * @param arg0
	 */
	public NotFoundException(String arg0) {
		super(arg0);
		
	}

	/**
	 * Constructor.
	 *
	 * @param arg0
	 */
	public NotFoundException(Throwable arg0) {
		super(arg0);
		
	}

}
