/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *   Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 455311 : Refactor Stereotype Display
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.stereotype.migration;

/**
 * This class regroups all the constant required for the Migration of the Stereotype Display User Preferences.
 *
 * @author Céline JANSSENS
 *
 */
public final class StereotypeDisplayMigrationConstant {
	/**
	 * Stereotype EAnnotation (Old Structure to migrate to the new one)
	 */
	public static final String STEREOTYPE_ANNOTATION = "Stereotype_Annotation";//$NON-NLS-1$
	/**
	 * the list of stereotype to display
	 */
	public static final String STEREOTYPE_LIST = "StereotypeList";//$NON-NLS-1$

	/**
	 * the list of stereotype to display
	 */
	public static final String STEREOTYPE_WITHQN_LIST = "StereotypeWithQualifiedNameList";//$NON-NLS-1$

	/**
	 * Property location Key
	 */
	public static final String STEREOTYPE_PROPERTY_LOCATION = "StereotypePropertyLocation";//$NON-NLS-1$

	/**
	 * Property display Key
	 *
	 */
	public static final String PROPERTY_STEREOTYPE_DISPLAY = "PropStereoDisplay";//$NON-NLS-1$

	/**
	 * the kind of presentation for stereotype
	 */
	public static final String STEREOTYPE_PRESENTATION_KIND = "Stereotype_Presentation_Kind";//$NON-NLS-1$

	/**
	 * Old Type of Views
	 */
	public static final String OLD_COMMENT_TYPE = "AppliedStereotypesComment";//$NON-NLS-1$
	public static final Object OLD_COMMENT_LINK_TYPE = "AppliedStereotypesCommentLink";//$NON-NLS-1$

	/**
	 * Separator Constants
	 */
	public static final String EANNOTATION_LIST_SEPARATOR = ","; //$NON-NLS-1$
	public static final String EANNOTATION_PROPERTY_SEPARATOR = "."; //$NON-NLS-1$
}
