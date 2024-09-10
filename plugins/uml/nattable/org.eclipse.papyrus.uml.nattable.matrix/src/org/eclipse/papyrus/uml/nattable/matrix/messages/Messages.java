/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) - vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.matrix.messages;

import org.eclipse.osgi.util.NLS;

/**
 * The messages used in this plugin
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.uml.nattable.matrix.messages.messages"; //$NON-NLS-1$
	public static String AbstractUMLGenericMatrixRelationshipCellManager_CreateRelationshipMessageDialogTitle;
	public static String AbstractUMLGenericMatrixRelationshipCellManager_RelationshipCanBeCreated;
	public static String GenericMatrixRelationshipCellEditorConfiguration_description;
	public static String RelationshipOwnerValidator_ChosenRelationshipIsAbstract;
	public static String RelationshipOwnerValidator_ColumnCantbeUsedAsOwner;
	public static String RelationshipOwnerValidator_ColumnOwnerCantbeUsedAsOwner;
	public static String RelationshipOwnerValidator_ElementCantBeResolvedAsEObject;
	public static String RelationshipOwnerValidator_ItIsAPapyrusBug;
	public static String RelationshipOwnerValidator_NoRelationshipDefined;
	public static String RelationshipOwnerValidator_RowCantbeUsedAsOwner;
	public static String RelationshipOwnerValidator_RowOwnerCantbeUsedAsOwner;
	public static String RelationshipOwnerValidator_TableContextCantbeUsedAsOwner;
	public static String RelationshipOwnerValidator_TheChosenElementCantBeUsedAsOwner;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
