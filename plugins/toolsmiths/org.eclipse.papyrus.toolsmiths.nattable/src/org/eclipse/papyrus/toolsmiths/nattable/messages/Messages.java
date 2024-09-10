/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.nattable.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.toolsmiths.nattable.messages.messages"; //$NON-NLS-1$
	public static String DefineOutputPluginWizardPage_Browse;
	public static String DefineOutputPluginWizardPage_DefineTheOuput;
	public static String DefineOutputPluginWizardPage_Output;
	public static String DefineOutputPluginWizardPage_OUTPUT_PAGE_TILE;
	public static String DefineOutputPluginWizardPage_ProjectMustBeSet;
	public static String DefineOutputPluginWizardPage_SelectOutputPluginProject;
	public static String DefineOutputPluginWizardPage_SelectOutput;
	public static String DefineOutputPluginWizardPage_SelectOutputPlugin;
	public static String DefineTableConfigurationDataWizardPage_ConfigureTableConfiguration;
	public static String DefineTableConfigurationDataWizardPage_DataPageTitle;
	public static String DefineTableConfigurationDataWizardPage_DefineTableConfigurationDescription;
	public static String DefineTableConfigurationDataWizardPage_DefineTableTable;
	public static String DefineTableConfigurationDataWizardPage_DefineTableType;
	public static String DefineTableConfigurationDataWizardPage_EnterNewTableConfigurationInformation;
	public static String DefineTableConfigurationDataWizardPage_PleaseSetAName;
	public static String DefineTableConfigurationDataWizardPage_PleaseSetAType;
	public static String DefineTableConfigurationDataWizardPage_TableTypeAlreadyExists;
	public static String TableChecker_InformWhichKindOfTableToUseToCreateNewOne;
	public static String TableChecker_NotRecommendedColumnAxis;
	public static String TableChecker_NotRecommendedRowAxis;
	public static String TableChecker_Warning_TableIsInverted;
	public static String TableConfigurationUtils_PluginXMLCanBeCreated;
	public static String WarningOnCurrentTableWizardPage_OKMessageRead;
	public static String WarningOnCurrentTableWizardPage_PleaseCheckDocumentation;
	public static String ExportAsTableConfigurationWizard_CheckTableConfigurationDependencies;
	public static String ExportAsTableConfigurationWizard_ErrorDuringTableConfigurationCreation;
	public static String ExportAsTableConfigurationWizard_IconFolderCantBeCreated;
	public static String ExportAsTableConfigurationWizard_ManifestEditorCantRegisterDependencies;
	public static String ExportAsTableConfigurationWizard_OutputFolderCantBeCreateed;
	public static String ExportAsTableConfigurationWizard_ResourceFileCantBeSaved;
	public static String ExportAsTableConfigurationWizard_TableIconFileCantBeCreated;
	public static String ExportAsTableConfigurationWizard_TableIconFileCantBeSaved;
	public static String ExportAsTableConfigurationWizard_WarningCreatedTableConfigurationDependsOnSeveralOthersModel;
	public static String ExportAsTableConfigurationWizard_WeCantFoundTheTableToExport;
	public static String ExportAsTableConfigurationWizard_WizardTitle;
	public static String RegisterTableConfigurationInArchitectureFrameworkHandler_AddExistingTableConfiguration;
	public static String RegisterTableConfigurationInArchitectureFrameworkHandler_SelectTheTableconfiguration;
	public static String RegisterTableConfigurationInArchitectureFrameworkHandler_SelectViewPointsToContribute;
	public static String RegisterTableConfigurationInArchitectureFrameworkHandler_SeveralViewpointsAreAvailable;
	public static String RegisterTableConfigurationInArchitectureFrameworkHandler_SelectConcernsToContribute;
	public static String RegisterTableConfigurationInArchitectureFrameworkHandler_SeveralConcernsAreAvailable;
	public static String ContributionToNewChildMenu_RegisterExitingTableConfigurationMenuItem;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
