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
 * @author cedric dumoulin
 *
 */
public class BadStateException extends ServiceException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 *
	 */
	public BadStateException(ServiceState expectedState, ServiceState state, ServiceDescriptor descriptor) {
		super("Bad state for service '" + descriptor.getKey() + "'. Expected '" + expectedState + "' found '" + state + "'.");
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param text
	 * @param state
	 * @param serviceDescriptor
	 */
	public BadStateException(String text, ServiceState state, ServiceDescriptor descriptor) {
		super(text + " (Service= '" + descriptor.getKey() + "', state= " + state + ")");
	}

}
