/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *  Saadia Dhouib saadia.dhouib@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.communication.custom.messages;

import org.eclipse.osgi.util.NLS;

/**
 * The Class Messages.
 *
 * @since 3.0
 */
public class Messages extends NLS {

	/** The Constant BUNDLE_NAME. */
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.uml.diagram.communication.custom.messages.messages"; //$NON-NLS-1$

	/**
	 *
	 */
	public static String CommandHelper_PropertySelection;

	/**
	 *
	 */
	public static String CommandHelper_SelectProperty;

	/**
	 *
	 */
	public static String CommandHelper_SelectSignature;

	/**
	 *
	 */
	public static String CommandHelper_signatureslection;

	/** The Message parser. */
	public static String MessageParser_undefined;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	/**
	 * Instantiates a new messages.
	 */
	private Messages() {
	}
}
