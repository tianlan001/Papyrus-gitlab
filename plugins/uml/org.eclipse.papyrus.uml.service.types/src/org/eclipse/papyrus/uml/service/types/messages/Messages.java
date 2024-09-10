/*****************************************************************************
 * Copyright (c) 2013, 2016 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Juan Cadavid (CEA LIST) juan.cadavid@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.papyrus.uml.service.types.messages.messages"; //$NON-NLS-1$

	public static String ActivityPartitionEditHelper_0;

	public static String ActivityPartitionEditHelper_1;

	public static String AssociationReorientCommand_0;

	public static String AssociationReorientCommand_1;

	public static String ConnectorReorientSemanticCommand_0;

	public static String GeneralizationSetCreationCommand_0;

	public static String GeneralizationSetCreationCommand_1;

	public static String GeneralizationSetCreationCommand_2;

	public static String GeneralizationSetCreationCommand_4;

	public static String GeneralizationSetCreationCommand_5;

	public static String InterruptibleActivityRegionHelper_0;

	public static String NamedElementHelper_0;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
