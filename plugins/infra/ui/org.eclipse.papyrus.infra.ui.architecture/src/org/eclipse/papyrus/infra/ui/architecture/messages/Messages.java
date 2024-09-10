/*****************************************************************************
 * Copyright (c) 2019, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   Christian W. Damus - bug 573788
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.architecture.messages;

import org.eclipse.osgi.util.NLS;

/**
 * Messages for architecture framework.
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.infra.ui.architecture.messages.messages"; //$NON-NLS-1$
	public static String AddRecommendedElementTypesConfigurationsHandler_1;
	public static String AddRecommendedElementTypesConfigurationsHandler_2;
	public static String ArchitecturePageAddValidator_diagleTitle;
	public static String ArchitecturePageAddValidator_dialogLabel;
	public static String ChangeArchitectureContextHandler_dialogLabel;
	public static String ChangeArchitectureContextHandler_dialogTitle;
	public static String CustomAdapterFactoryContentProvider_0;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
