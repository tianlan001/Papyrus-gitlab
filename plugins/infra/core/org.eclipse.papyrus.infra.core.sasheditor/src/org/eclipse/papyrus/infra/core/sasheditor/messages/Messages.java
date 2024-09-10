/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sasheditor.messages;

import org.eclipse.osgi.util.NLS;

/**
 * @author VL222926
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.infra.core.sasheditor.messages.messages"; //$NON-NLS-1$
	public static String ErrorComponentPart_InvalidTab;
	public static String TabTooltipPreferencePage_AutoCloseAndDelay;
	public static String TabTooltipPreferencePage_IsTooltipEnable;
	public static String TabTooltipPreferencePage_IsTooltipForCurrentTabShown;
	public static String TabTooltipPreferencePage_SashWindowsTabsTooltipPreferencePageName;
	public static String TabTooltipPreferencePage_TooltipScale;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
