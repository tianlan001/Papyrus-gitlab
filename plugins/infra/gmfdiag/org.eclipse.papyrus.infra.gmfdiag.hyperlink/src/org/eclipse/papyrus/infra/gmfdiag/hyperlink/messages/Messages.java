/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus (CEA LIST) - Consolidate all hyperlink helper contributions into one tab
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.hyperlink.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.papyrus.infra.gmfdiag.hyperlink.messages.messages"; //$NON-NLS-1$

	public static String EditorHyperLinkEditorShell_ICanFindTheHyperLinkEditorObject;

	public static String EditorHyperLinkEditorShell_View;

	public static String EditorHyperLinkHelper_Editor;

	public static String HyperlinkNavigationMenuEditPolicy_CreateDiagramDialogLabel;

	public static String HyperlinkNavigationMenuEditPolicy_CreateDiagramDialogMessage;

	public static String HyperlinkNavigationMenuEditPolicy_CreateDiagramhyperlinkLabel;

	public static String HyperlinkNavigationMenuEditPolicy_CreateDiagramhyperlinkTooltip;

	public static String HyperlinkNavigationMenuEditPolicy_CreateTableDialogMessage;

	public static String HyperlinkNavigationMenuEditPolicy_CreateTableDialogTitle;

	public static String HyperlinkNavigationMenuEditPolicy_CreateTableHyperLinkLabel;

	public static String HyperlinkNavigationMenuEditPolicy_CreateTableHyperLinkTooltip;

	public static String HyperlinkNavigationMenuEditPolicy_EditHyperLinkTooltip;

	public static String HyperlinkNavigationMenuEditPolicy_EditHyperLinkTooltipLabel;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
