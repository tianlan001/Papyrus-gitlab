/*******************************************************************************
 * Copyright (c) 2008 Conselleria de Infraestructuras y Transporte,
 * Generalitat de la Comunitat Valenciana .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: Francisco Javier Cano MuÃ±oz (Prodevelop) - initial API implementation
 * Emilien Perico (Atos Origin) - add tags for links with keywords
 * Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Initial API and implementation
 ******************************************************************************/
package org.eclipse.papyrus.uml.diagram.common;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "messages"; //$NON-NLS-1$

	public static String CreateUmlElementDialog_New_Element_Name;

	public static String ElementImportParser_undefined_value;

	public static String IconStylePreferencePage_cheerful_style;

	public static String IconStylePreferencePage_eclipse_style;

	public static String IconStylePreferencePage_icon_style_group;

	public static String InterfaceManagerDialog_CreateANewInterface;

	public static String InterfaceManagerDialog_CreateNewInterface;

	public static String InterfaceManagerDialog_SelectedTheProvidedInterfaces;

	public static String InterfaceManagerDialog_SelectionHasToBeAPackageOrAClassifier;

	public static String InterfaceManagerDialog_SelectTheParentOfThisNewInterface;

	public static String InterfaceManagerDialog_SelectTheRequiredInterfaces;

	public static String InterfaceManagerDialog_ThePortIsTypedWithAnInterfaceWarningMessage;

	public static String InterfaceManagerDialog_Title;

	public static String ModelElementSelectionPageMessage;

	public static String ManageProvidedInterfacesHandler_OK;

	public static String ManageProvidedInterfacesHandler_TheTypeOfThePortIsNotDefined;

	public static String SelectionValidated;

	public static String NoSelectionFound;

	public static String UMLNewDiagramFileWizard_RootSelectionPageSelectionTitle;

	public static String UMLNewDiagramFileWizard_RootSelectionPageNoSelectionMessage;

	public static String UMLNewDiagramFileWizard_RootSelectionPageInvalidSelectionMessage;

	public static String UMLNewDiagramFileWizard_CreationPageName;

	public static String UMLNewDiagramFileWizard_CreationPageTitle;

	public static String UMLNewDiagramFileWizard_CreationPageDescription;

	public static String UMLNewDiagramFileWizard_RootSelectionPageName;

	public static String UMLNewDiagramFileWizard_RootSelectionPageTitle;

	public static String UMLNewDiagramFileWizard_RootSelectionPageDescription;

	public static String UMLDiagramEditorUtil_CreateDiagramProgressTask;

	public static String UMLDiagramEditorUtil_CreateDiagramCommandLabel;

	public static String UMLInitDiagramFileAction_InitDiagramFileResourceErrorDialogTitle;

	public static String UMLInitDiagramFileAction_InitDiagramFileResourceErrorDialogMessage;

	public static String UMLInitDiagramFileAction_InitDiagramFileWizardTitle;

	public static String UMLNewDiagramFileWizard_DiagramKindTitle;

	public static String UMLNewDiagramFileWizard_DiagramKindDescription;

	public static String RotateAction_rotate_command;

	public static String ICustomAppearance_PIN_DISP_TYPE;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}



	/** message when many resources have changed (including main) */
	public static String PartActivationListener_ChangedMainMsg_many;

	/** message when main resource has changed */
	public static String PartActivationListener_ChangedMainMsg_single;

	/** message when main resource is conflicting */
	public static String PartActivationListener_ChangedMainWarning;

	/** message when many resources have changed */
	public static String PartActivationListener_ChangedMsg_many;

	/** message when single resource has changed */
	public static String PartActivationListener_ChangedMsg_single;

	/** title when resources have changed */
	public static String PartActivationListener_ChangedTitle;

	/** message when resource is conflicting */
	public static String PartActivationListener_ChangedWarning;

	/** message when many resources have been removed */
	public static String PartActivationListener_RemovedMsg_many;

	/** message when single resource has been removed */
	public static String PartActivationListener_RemovedMsg_single;

	/** title when resources have been removed */
	public static String PartActivationListener_RemovedTitle;

	/** BooleanEditor Title */
	public static String PropertyEditors_BooleanTitle;

	/** the "Choice" String */
	public static String PropertyEditors_Choice;

	/** DataTypeEditor Title */
	public static String PropertyEditors_DataTypeTitle;

	/** EnumerationEditor Title */
	public static String PropertyEditors_EnumerationLiteralTitle;

	/** IntegerEditor Title */
	public static String PropertyEditors_IntegerTitle;

	/** RealEditor Title */
	public static String PropertyEditors_RealTitle;

	/** MetaclassEditor Title */
	public static String PropertyEditors_MetaclassTitle;

	/** warning message for metaclass editor */
	public static String PropertyEditors_NoFoundElementMetaclass;

	/** warning message for stereotype editor */
	public static String PropertyEditors_NoFoundElementStereotype;

	/** StereotypeEditor Title */
	public static String PropertyEditors_StereotypeTitle;

	/** StringEditor Title */
	public static String PropertyEditors_StringTitle;

	/** UnlimitedNaturalEditor Title */
	public static String PropertyEditors_UnlimitedNaturalTitle;

	/** the "Value" String */
	public static String PropertyEditors_Value;

	/** message for the runtime dialog */
	public static String SetDynamicValueCommand_DialogMessage;

	/** title for the runtime dialog */
	public static String SetDynamicValueCommand_DialogTile;

	/** Message for horizontal routing by bottom action */
	public static String RoutingConstants_Bottom;

	/** Message for horizontal routing by left */
	public static String RoutingConstants_HorizontalByLeft;

	/** Message for horizontal routing by right */
	public static String RoutingConstants_HorizontalByRight;

	/** Message for horizontal routing by left action */
	public static String RoutingConstants_Left;

	/** Menu title for the routing action */
	public static String RoutingConstants_MenuTitle;

	/** Menu ToolTip for the routing action */
	public static String RoutingConstants_MenuToolTip;

	/** Message for horizontal routing by right action */
	public static String RoutingConstants_Right;

	/** Message for horizontal routing by top action */
	public static String RoutingConstants_Top;

	/** Message for horizontal routing by bottom */
	public static String RoutingConstants_VerticalByBottom;

	/** Message for horizontal routing by top */
	public static String RoutingConstants_VerticalByTop;

	/** Message for Diagram Not Found */
	public static String DiagramNotFound;

	/** Message Set Name Diagram */
	public static String SetNameDiagram;

	/** The string No Name for the ShowHideCompartment Action */
	public static String EditorLabelProvider_No_name;

	public static String ShowHideContentsAction_Message;

	public static String ShowHideContentsAction_Title;

	public static String CommonDeferredCreateConnectionViewCommand_NullConnectionCommand;

	/** Message for horizontal distribution */
	public static String DistributionConstants_Distribute_Horizontally;

	/** Message for horizontal distribution between nodes */
	public static String DistributionConstants_Distribute_Horizontally_Between_Nodes;

	/** Message for vertical distribution */
	public static String DistributionConstants_Distribute_Vertically;

	/** Message for vertical distribution between nodes */
	public static String DistributionConstants_Distribute_Vertically_Between_Nodes;

	/** Title for the distribution menu */
	public static String DistributionConstants_DistributionTitle;

}
