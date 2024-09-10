/*******************************************************************************
 * Copyright (c) 2014, 2015 CEA LIST, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 464647
 *     
 ******************************************************************************/
package org.eclipse.papyrus.mwe2.utils.messages;

import org.eclipse.osgi.util.NLS;

/**
 * Localized strings for the bundle.
 */
public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.papyrus.mwe2.utils.messages.messages"; //$NON-NLS-1$

	public static String DeleteFile_0;

	public static String DeleteFile_1;

	public static String DeleteFile_2;

	public static String DeleteFile_3;

	public static String DeleteFile_4;

	public static String DeleteFile_5;

	public static String QvtoTransformationWorkflowComponent_1;

	public static String QvtoTransformationWorkflowComponent_4;

	public static String QvtoTransformationWorkflowComponent_5;

	public static String RegisterUmlProfile_1;

	public static String RegisterUmlProfile_2;

	public static String RegisterUmlProfile_3;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
