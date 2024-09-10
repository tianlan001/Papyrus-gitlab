/*****************************************************************************
 * Copyright (c) 2013, 2021 CEA LIST.
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
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Bug 571948
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.papyrus.infra.ui.messages.messages"; //$NON-NLS-1$

	public static String AbstractPreferenceKeyDialog_Level;

	public static String AbstractPreferenceKeyDialog_Localization;

	public static String AbstractPreferenceKeyDialog_Pref_Kind;

	public static String AbstractPreferenceKeyDialog_WouldYouLikeOverloadPreferences;

	public static String AbstractStringValueConverter_NoXReprensentedByYHaveBeenFound;

	public static String AbstractStringValueConverter_SomeStringsAreNotValidToCreateY;

	public static String AbstractStringValueConverter_SomeStringsCantBeResolvedToFindY;

	public static String AbstractStringValueConverter_TheFeatureXCantBeResolved;

	public static String AbstractStringValueConverter_TheStringValueXCantBeResolved;

	public static String AbstractStringValueConverter_TheStringXIsNotValidToCreateY;

	public static String RichtextPreferencePage_Description;

	public static String RichtextPreferencePage_FirstBooleanEditorName;

	public static String RichtextPreferencePage_SecondBooleanEditorName;

	public static String IReloadableEditor_do_not_save_do_not_reload;
	public static String IReloadableEditor_Resources_Deleted;
	public static String IReloadableEditor_Interrupted_in_determining;
	public static String IReloadableEditor_Some_resources_used_by;
	public static String IReloadableEditor_Save_and_Close;
	public static String IReloadableEditor_Resources_Changed;
	public static String IReloadableEditor_Some_resources_used_by_have_changed;
	public static String IReloadableEditor_Save_and_Reopen;
	public static String IReloadableEditor_continue_to_work;
	public static String IReloadableEditor_Failed_to_determine;

	public static String CoreMultiDiagramEditor_StatisDialog_CreatePartControlMessage;
	public static String CoreMultiDiagramEditor_StatisDialog_LoadingPapyrusMessage;
	public static String CoreMultiDiagramEditor_StatisDialog_LoadNestedEditorMessage;
	public static String CoreMultiDiagramEditor_StatisDialog_LoadServicesAndModelMessage;
	public static String CoreMultiDiagramEditor_StatisDialog_Title;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
