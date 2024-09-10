/*****************************************************************************
 * Copyright (c) 2010, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 574592
 *
 *****************************************************************************/


package org.eclipse.papyrus.uml.diagram.profile.custom.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.papyrus.uml.diagram.profile.custom.messages.messages"; //$NON-NLS-1$

	public static String CustomSemanticCreateCommand_CreateCustomSemanticCommandForMetaclass;
	public static String CustomSemanticCreateCommand_LabelProviderServiceNotFound;
	public static String CustomSemanticCreateCommand_SelectMetaclass;
	public static String PreSaveProfileListener_0;
	public static String PreSaveProfileListener_1;
	public static String PreSaveProfileListener_2;
	public static String PreSaveProfileListener_3;
	public static String PreSaveProfileListener_4;
	public static String PreSaveProfileListener_5;
	public static String PreSaveProfileListener_6;
	public static String PreSaveProfileListener_7;
	public static String PreSaveProfileListener_8;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
