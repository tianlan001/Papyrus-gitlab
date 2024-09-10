/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.palette;

import org.eclipse.osgi.util.NLS;

/**
 * Messages for the plugin
 */
public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.papyrus.toolsmiths.palette.messages"; //$NON-NLS-1$

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}

	public static String BundleIconExplorerDialog_Message;

	public static String BundleIconExplorerDialog_Title;

	public static String BundleIconExplorerDialog_UnknownFileName;

	public static String ExportPaletteConfigurationWizard_error_cant_read_file;

	public static String ExportPaletteConfigurationWizard_error_file_not_existe;

	public static String ExportPaletteConfigurationWizard_Export_description;

	public static String ExportPaletteConfigurationWizard_ExportWiazrdLabel;

	public static String ExportPaletteConfigurationWizard_export_palette;

	public static String PaletteConfigurationWizard_EditionPaletteInfoPageWizard_Tilte;

	public static String PaletteConfigurationWizard_EditPaletteWizardLabel;

	public static String PaletteConfigurationWizard_NewPaletteContentPageWizard_Description;

	public static String PaletteConfigurationWizard_NewPaletteContentPageWizard_Title;

	public static String PaletteConfigurationWizard_NewPaletteDefaultName;

	public static String PaletteConfigurationWizard_PaletteInfoPageWizard_Description;

	public static String PaletteConfigurationWizard_NewPaletteInfoPageWizard_Title;

	public static String PaletteConfigurationWizard_NewPaletteWizardLabel;

	public static String PaletteConfigurationWizard_ImpossibleToReadDefinitionOfTheFile;

	public static String PaletteConfigurationWizard_FileDontExist;

	public static String PaletteConfigurationContentPage_NewTool;

	public static String PaletteConfigurationContentPage_Separator;

	public static String PaletteConfigurationContentPage_Stack;

	public static String PaletteConfigurationContentPage_UMLTools;

	public static String PaletteConfigurationContentPage_UnknownElement;

	public static String PaletteConfigurationContentPage_CreateAnElementWithAStereotype;

	public static String PaletteConfigurationContentPage_Drawer;

	// ///////////////////////// Palette Customisation
	// /////////////////////////////////////

	public static String Dialog_Export_Palette_Tooltip;

	/** Tooltip for the add button */
	public static String PapyrusPaletteCustomizerDialog_AddButtonTooltip;

	/** Tooltip for the remove button */
	public static String PapyrusPaletteCustomizerDialog_RemoveButtonTooltip;

	public static String AbstractDeployPaletteConfigurationHandler_ConfigureDeploymentOfThePalette;

	public static String AbstractDeployPaletteConfigurationHandler_Deploy;

	public static String AbstractDeployPaletteConfigurationHandler_Editor;

	public static String AbstractDeployPaletteConfigurationHandler_Error_UserCancelDialog;

	public static String AbstractDeployPaletteConfigurationHandler_Error_ContentDialogNotValid;

	public static String AbstractDeployPaletteConfigurationHandler_Error_SomeErrorsOccured;

	public static String AbstractDeployPaletteConfigurationHandler_Identifier;

	public static String AbstractDeployPaletteConfigurationHandler_Priority;

	public static String AbstractDeployPaletteConfigurationHandler_Profiles;

	public static String AbstractDeployPaletteConfigurationHandler_Success;

	public static String AbstractDeployPaletteConfigurationHandler_ThePaletteConfigurationHasBeenSuccessfullyDeployedAndActivated;

	public static String AbstractDeployPaletteConfigurationHandler_TheSelectedElementIsNotAFile;

	/** label for the available palettes viewer */
	public static String Available_Palettes;

	/** label for the palette tree viewer */
	public static String Palette_Viewer;

	/** label for the palette name in the wizard */
	public static String Local_Palette_Name;

	/** label for the palette id in the wizard */
	public static String Local_Palette_Id;

	/** label for the editor id in the palette wizard */
	public static String Local_Palette_Editor_Id;

	/** tooltip for the palette name in the palette wizard */
	public static String Local_Palette_Name_Tooltip;

	/** tooltip for the palette id in the palette wizard */
	public static String Local_Palette_Id_Tooltip;

	/** tooltip for the editor id in the palette wizard */
	public static String Local_Palette_Editor_Id_Tooltip;

	/** label for the provider priority in the palette wizard */
	public static String Local_Palette_Priority;

	/** tooltip for the provider priority in the palette wizard */
	public static String Local_Palette_Priority_Tooltip;

	/** label for the advanced button, when advanced are not shown */
	public static String Dialog_Advanced_Button_Closed;

	/** label for the advanced button, when advanced are shown */
	public static String Dialog_Advanced_Button_Opened;

	/** Error message for the wizard page, when priority is not correctly filled */
	public static String Local_Palette_Error_Priority;

	/**
	 * Error message for the wizard page, when palette ID is not correctly
	 * filled
	 */
	public static String Local_Palette_Error_PaletteId;

	/** Error message for the wizard page, when name is not correctly filled */
	public static String Local_Palette_Error_Name;

	/**
	 * Error message for the wizard page, when Editor ID is not correctly filled
	 */
	public static String Local_Palette_Error_EditorId;

	/** Name of the information page */
	public static String Local_Palette_InfoPage_Name;

	/** Title of the information page */
	public static String Local_Palette_InfoPage_Title;

	/** Name of the content page */
	public static String Local_Palette_ContentPage_Name;

	/** Title of the content page */
	public static String Local_Palette_ContentPage_Title;

	/** name of the available tools group */
	public static String Local_Palette_Available_Tools;

	/** tooltip for the show/hide drawer button */
	public static String Local_Palette_ShowDrawers_Tooltip;

	/** tooltip for the show/hide drawer button */
	public static String Local_Palette_ShowTools_Tooltip;

	/** palette preview label */
	public static String Local_Palette_Palette_Preview;

	/** Title for dialog box when palette to delete is not a local palette */
	public static String Dialog_Not_Local_Palette_Title;

	/** Message for dialog box when palette to delete is not a local palette */
	public static String Dialog_Not_Local_Palette_Message;

	/** Title for dialog box when palette to restore is not an extended palette */
	public static String Dialog_Not_Extended_Palette_Title;

	/** Message for dialog box when palette to restore is not an extended palette */
	public static String Dialog_Not_Extended_Palette_Message;

	/** Tooltip for the restore palette button. */
	public static String Dialog_Restore_Palette_Tooltip;

	/** tooltip for the delete palette button */
	public static String Dialog_Delete_Palette_Tooltip;

	/** tooltip for the create palette button */
	public static String Dialog_Create_Palette_Tooltip;

	/** tooltip for the edit palette button */
	public static String Dialog_Edit_Palette_Tooltip;

	/** tooltip for the create drawer button */
	public static String Local_Palette_Create_Drawer_Tooltip;

	/** tooltip for the create separator button */
	public static String Local_Palette_Create_Separator_Tooltip;

	/** tooltip for the create separator button */
	public static String Local_Palette_Create_Tool_Tooltip;

	/** tooltip for the create stack button */
	public static String Local_Palette_Create_Stack_Tooltip;

	/** name of the drawer wizard page */
	public static String Wizard_Drawer_Page_Name;

	/** title of the drawer wizard page */
	public static String Wizard_Drawer_Page_Title;

	/** error on the name in the drawer wizard */
	public static String Wizard_Drawer_Error_Name;

	/** error on the id in the drawer wizard */
	public static String Wizard_Drawer_Error_Id;

	/** error on the icon in the drawer wizard */
	public static String Wizard_Drawer_Error_Icon;

	/** label for the id field */
	public static String Wizard_Drawer_Id;

	/** tooltip for the id field */
	public static String Wizard_Drawer_Id_Tooltip;

	/** label for the name field */
	public static String Wizard_Drawer_Name;

	/** tooltip for the name field */
	public static String Wizard_Drawer_Name_Tooltip;

	/** tooltip for the icon field */
	public static String Wizard_Drawer_Icon_Tooltip;

	/** name for the icon field */
	public static String Wizard_Drawer_Icon;

	/** tooltip for the tool item */
	public static String Local_Palette_SwitchToolsContentProvider_Tooltip;

	/**
	 * tooltip for the edit local palette action disabled because of bad
	 * selection
	 */
	public static String PapyrusPaletteCustomizerDialog_EditButtonTooltip_LocalPaletteNotSelected;

	/** tooltip for the edit local palette action */
	public static String PapyrusPaletteCustomizerDialog_EditButtonTooltip_LocalPaletteSelected;

	/**
	 * tooltip for the edit local palette action disabled because of missing
	 * profiles
	 */
	public static String PapyrusPaletteCustomizerDialog_EditButtonTooltip_MissingProfile;

	/** label for the name text editor for a given proxy */
	public static String Local_Palette_Entry_Name;

	/** label for the description editor for a given proxy */
	public static String Local_Palette_Entry_Description;

	/** label for the group information about entry */
	public static String Local_Palette_Entry_Information;

	/** label for the icon information about entry */
	public static String Local_Palette_Entry_Icon;

	/** label for the referenced entry information about entry */
	public static String Local_Palette_Entry_Reference;

	/** label for the composite displaying the lis of aspect actions */
	public static String Aspect_Action_Information_List_Label;

	/** label for the tooltip of the edit drawer icon */
	public static String PapyrusPaletteCustomizerDialog_EditButtonTooltip;

}
