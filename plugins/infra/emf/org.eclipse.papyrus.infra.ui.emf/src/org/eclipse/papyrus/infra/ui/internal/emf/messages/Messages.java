/*************************************************************
 * Copyright (c) 2012, 2016 CEA, Christian W. Damus, ALL4TEC, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) - Initial API and implementation
 *   Christian W. Damus (CEA) - bug 323802
 *   Christian W. Damus - bug 485220
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Bug 500869
 *
 */

package org.eclipse.papyrus.infra.ui.internal.emf.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.papyrus.infra.ui.internal.emf.messages.messages"; //$NON-NLS-1$

	public static String EMFGraphicalContentProvider_CaseSensitiveCheckBoxLabel;

	public static String EMFGraphicalContentProvider_CaseSensitiveCheckBoxTooltip;

	public static String EMFGraphicalContentProvider_CollapseAllTooltip;

	public static String EMFGraphicalContentProvider_ExpandAllTooltip;

	public static String EMFGraphicalContentProvider_FilterFieldTooltip;

	public static String EMFGraphicalContentProvider_historyGroupLabel;

	public static String ResourceFilteredLabelProvider_local;

	public static String ResourceFilteredLabelProvider_localExt;

	public static String ResourceFilteredLabelProvider_system;

	public static String ResourceFilteredLabelProvider_systemExt;

	public static String ResourceFilteredLabelProvider_workspace;

	public static String ResourceFilteredLabelProvider_workspaceExt;

	public static String ReferencedModelReadOnlyHandler_promptMsg;

	public static String ReferencedModelReadOnlyHandler_promptTitle;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
