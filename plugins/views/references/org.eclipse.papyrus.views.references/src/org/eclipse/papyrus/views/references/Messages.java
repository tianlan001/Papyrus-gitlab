/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.views.references;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.views.references.messages"; //$NON-NLS-1$

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}

	public static String ReferencesView_LinkWithEditorLabel;

	public static String ReferencesView_ClearAllLabel;

	public static String ReferencesView_RefreshLabel;

	public static String ReferencesView_ExpandAllLabel;

	public static String ReferencesView_CollapseAllLabel;

	public static String ReferencesView_EmptyComment;

	public static String ReferencesView_GoTo;

	public static String ReferencesView_MultiSelection;

	public static String ReferencesView_TitleSectionIncoming;

	public static String ReferencesView_TitleSectionOutgoing;

	public static String ReferencesView_TheFileDoesntExists;

	public static String ReferencesView_FailedToOpenModelSet;
}
