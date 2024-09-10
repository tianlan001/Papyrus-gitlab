/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
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
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.profilemigration.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.toolsmiths.profilemigration.ui.messages"; //$NON-NLS-1$
	public static String AbstractProfileMigrationDialog_description;
	public static String AbstractProfileMigrationDialog_DoNotShowItAgain;
	public static String AbstractProfileMigrationDialog_MigrateAction;
	public static String AddPropertyToStereotypeDialog_Description;
	public static String AddPropertyToStereotypeDialog_Title;
	public static String ChangeIsAbstractFromStereotypeDialog_Description;
	public static String ChangeIsAbstractFromStereotypeDialog_Title;
	public static String ChangeIsStaticFeatureDialog_Description;
	public static String ChangeIsStaticFeatureDialog_Title;
	public static String ChangeLowerMultiplicityDialog_Description;
	public static String ChangeLowerMultiplicityDialog_Title;
	public static String ChangeUpperMultiplicityDialog_Description;
	public static String ChangeUpperMultiplicityDialog_Title;
	public static String DeleteEnumerationLiteralFromEnumerationDialog_Description;
	public static String DeleteEnumerationLiteralFromEnumerationDialog_Title;
	public static String MovePackageDialog_description;
	public static String MovePackageDialog_title;
	public static String MoveProfileDialog_description;
	public static String MoveProfileDialog_title;
	public static String MoveStereotypeDialog_content;
	public static String MoveStereotypeDialog_description;
	public static String MoveStereotypeDialog_No;
	public static String MoveStereotypeDialog_title;
	public static String MoveStereotypeDialog_Yes;
	public static String ProfileMigrationPreferenceConstants_AddPropertyToStereotype;
	public static String ProfileMigrationPreferenceConstants_ChangeIsStaticFeatureOfProperty;
	public static String ProfileMigrationPreferenceConstants_ChangeLowerMultiplicityOfProperty;
	public static String ProfileMigrationPreferenceConstants_ChangeUpperMultiplicityOfProperty;
	public static String ProfileMigrationPreferenceConstants_DeleteEnumLiteralFromEnumeration;
	public static String ProfileMigrationPreferenceConstants_PackageMove;
	public static String ProfileMigrationPreferenceConstants_ProfileMove;
	public static String ProfileMigrationPreferenceConstants_StereotypeMove;
	public static String ProfileMigrationPreferenceConstants_SuperStereotypeBecomingAbstract;
	public static String ProfileMigrationPreferencePage_deleteCachedFileTooptip;
	public static String ProfileMigrationPreferencePage_FileInCached;
	public static String ProfileMigrationPreferencePage_ShowDialogs;
	public static String ProfileMigrationToolConfigurationDialog_FileSelectionSelectionDescription;
	public static String ProfileMigrationToolConfigurationDialog_FileSelection;
	public static String ProfileMigrationToolConfigurationDialog_FileSelectionSelectionTitle;
	public static String ProfileMigrationToolConfigurationDialog_ImportButtonName;
	public static String ProfileMigrationToolConfigurationDialog_ImportButtonTooltip;
	public static String ProfileMigrationToolConfigurationDialog_SkipButtonName;
	public static String ProfileMigrationToolConfigurationDialog_SkipButtonTooltip;
	public static String ProfileMigrationToolConfigurationDialog_Title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
