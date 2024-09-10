/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.emf.appearance.helper;

//Refactoring 0.9: Some constants have been moved to UMLVisualInformationPapyrusConstant
public interface VisualInformationPapyrusConstants {

	/** The qualified name. */
	public static String QUALIFIED_NAME = "QualifiedName"; //$NON-NLS-1$

	/** The qualified name depth. */
	public static String QUALIFIED_NAME_DEPTH = "QualifiedNameDepth"; //$NON-NLS-1$

	public static String SHADOWFIGURE = "ShadowFigure"; //$NON-NLS-1$

	public static String SHADOWFIGURE_VALUE = "ShadowFigure_Value"; //$NON-NLS-1$

	public static String DISPLAY_NAMELABELICON = "displayNameLabelIcon"; //$NON-NLS-1$

	public static String DISPLAY_NAMELABELICON_VALUE = "displayNameLabelIcon_value"; //$NON-NLS-1$

	public static String LAYOUTFIGURE = "layoutFigure"; //$NON-NLS-1$

	public static String LAYOUTFIGURE_VALUE = "layoutFigure_value"; //$NON-NLS-1$

	/**
	 * key for the appearance of properties or other specific display
	 *
	 * @deprecated Use a StringListValueStyle instead. See {{@link #CUSTOM_MASK_LABEL}
	 */
	@Deprecated
	public static final String CUSTOM_APPEARENCE_ANNOTATION = "CustomAppearance_Annotation"; //$NON-NLS-1$

	/**
	 * The name of the StringListValueStyle used for storing MaskLabels
	 *
	 * Replaces the former int-based EAnnotation ({@link #CUSTOM_APPEARENCE_ANNOTATION}, {@link #CUSTOM_APPEARANCE_MASK_VALUE})
	 */
	public static final String CUSTOM_MASK_LABEL = "maskLabel"; //$NON-NLS-1$

	/**
	 * this is a key of eAnnnotation that contains hypertext link or referenced document
	 **/
	public static final String HYPERLINK_DIAGRAM = "PapyrusHyperLink_Diagram"; //$NON-NLS-1$

	public static final String HYPERLINK_DIAGRAM_NAME = "diagram_name"; //$NON-NLS-1$

	/**
	 * key for the appearance of properties or other specific display
	 *
	 * @deprecated Use a StringListValueStyle instead. See {{@link #CUSTOM_MASK_LABEL}
	 */
	@Deprecated
	public static final String CUSTOM_APPEARANCE_MASK_VALUE = "CustomAppearance_MaskValue"; //$NON-NLS-1$
}
