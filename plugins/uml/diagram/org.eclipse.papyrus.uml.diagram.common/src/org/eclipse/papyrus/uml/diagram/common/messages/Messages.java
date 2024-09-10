/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *	Fanch Bonnabesse (ALL4TEC) fanch.bonnabesse@alltec.net - Bug 419357
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.papyrus.uml.diagram.common.messages.messages"; //$NON-NLS-1$

	public static String AbstractAutomatedModelCompletionPreferencesPage_Accelerators;

	public static String AbstractAutomatedModelCompletionPreferencesPage_Elements;

	public static String ShowHideRelatedLinkSelectionDialog_Comment_;

	public static String ShowHideRelatedLinkSelectionDialog_CommentLink;

	public static String ShowHideRelatedLinkSelectionDialog_ConstraintLink;

	public static String ShowHideRelatedLinkSelectionDialog_Targets;

	public static String ShowHideRelatedLinkSelectionDialog_Sources;

	public static String ShowHideRelatedLinkSelectionDialog_StereotypePropertyReferenceLabel;

	public static String ShowHideRelatedLinkSelectionDialog_Ends;

	public static String ShowHideRelatedLinkSelectionDialog_LinksToDisplay;

	public static String ShowHideRelatedLinkSelectionDialog_LinkKind;

	public static String CreateOrShowExistingElementHelper_CreateOrRestoreX;

	public static String CreateOrShowExistingElementHelper_XBetweenTheseElementAlreadyExists;

	public static String CreateOrShowExistingElementHelper_AlwaysCreateNewLink;

	public static String CreateOrShowExistingLinkDialog_Create;

	public static String CreateOrShowExistingLinkDialog_Ends;

	public static String CreateOrShowExistingLinkDialog_Name;

	public static String CreateOrShowExistingLinkDialog_RestoreSelection;

	public static String CreateOrShowExistingLinkDialog_ShowOrCreate;

	public static String CreateOrShowExistingLinkDialog_Sources;

	public static String CreateOrShowExistingLinkDialog_Targets;

	public static String CreateOrSelectTypeDialog_DialogTitle;

	public static String CreateOrSelectTypeDialog_SelectionSectionTitle;

	public static String CreateOrSelectTypeDialog_SelectionSectionRadioLabel;

	public static String CreateOrSelectTypeDialog_CreationSectionTitle;

	public static String CreateOrSelectTypeDialog_CreationSectionRadioLabel;

	public static String CreateOrSelectTypeDialog_NewTypeNameLabel;

	public static String CreateOrSelectTypeDialog_NewTypeContainerNameLabel;

	public static String CreateOrSelectTypeDialog_SelectTypeDialogTitle;

	public static String CreateOrSelectTypeDialog_SelectTypeDialogMessage;

	public static String CreateOrSelectTypeDialog_SelectNewTypeContainerDialogTitle;

	public static String CreateOrSelectTypeDialog_SelectNewTypeContainerDialogMessage;

	public static String UMLPropertySource_ElementSelection;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
