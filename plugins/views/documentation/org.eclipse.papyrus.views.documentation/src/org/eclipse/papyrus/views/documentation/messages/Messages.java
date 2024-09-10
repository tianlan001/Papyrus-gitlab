/*****************************************************************************
 * Copyright (c) 2016 CEA LIST, ALL4TEC and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.views.documentation.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.views.documentation.messages.messages"; //$NON-NLS-1$

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}

	public static String DocumentationResourceEditor_AddButtonTooltip;
	public static String DocumentationResourceEditor_AddFileSystemDialogTitle;
	public static String DocumentationResourceEditor_AddRemoteDialogMessage;
	public static String DocumentationResourceEditor_AddRemoteDialogTitle;
	public static String DocumentationResourceEditor_AddWSDialogTitle;
	public static String DocumentationResourceEditor_BrowseSystemButtonTooltip;
	public static String DocumentationResourceEditor_BrowseWSButtonTooltip;
	public static String DocumentationResourceEditor_EditResourceDialogMessage;
	public static String DocumentationResourceEditor_EditResourceDialogTitle;
	public static String DocumentationResourceEditor_Message;
	public static String DocumentationResourceEditor_RemoveButtonTootip;
	public static String DocumentationView_CommentTabLabel;
	public static String DocumentationView_EditButtonLabel;
	public static String DocumentationView_EditButtonTooltip;
	public static String DocumentationView_NoCommentCreatedMessage;
	public static String DocumentationView_notProfileAppliedDialogMessage;
	public static String DocumentationView_notProfileAppliedDialogTitle;
	public static String DocumentationView_ResourcesTabLabel;
	public static String DocumentationView_SyncActionTooltip;
	public static String DocumentationViewPreferencePage_chooseUseProfile;
	public static String DocumentationViewPreferencePage_description;
	public static String DocumentationViewPreferencePage_toolbarInitialExpandedLabel;
	public static String DocumentationViewPreferencePage_useFirstCommentChoice;
	public static String DocumentationViewPreferencePage_useProfileChoice;



}
