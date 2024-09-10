/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
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
 *   Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 455311 : Refactor Stereotypes Display
 *   Christian W. Damus - bug 492482
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper;

import java.util.Set;

import org.eclipse.papyrus.uml.diagram.common.Activator;

import com.google.common.collect.ImmutableSet;



/**
 * This Class regroups the Constants required for the Stereotype Display
 * 
 * @author Celine JANSSENS
 *
 */
public class StereotypeDisplayConstant {

	// Depth Separator of the Stereotype Name
	public static final String STEREOTYPE_LABEL_DEPTH_SEPARATOR = "::";//$NON-NLS-1$

	// Name of Style for Comment Node to point to related main Object
	public static final String STEREOTYPE_COMMENT_RELATION_NAME = "BASE_ELEMENT";//$NON-NLS-1$

	// Type Notation Value
	public static final String STEREOTYPE_LABEL_TYPE = "StereotypeLabel"; //$NON-NLS-1$

	public static final String STEREOTYPE_COMPARTMENT_TYPE = "StereotypeCompartment";//$NON-NLS-1$
	public static final String STEREOTYPE_BRACE_TYPE = "StereotypeBrace";//$NON-NLS-1$

	public static final String STEREOTYPE_PROPERTY_TYPE = "StereotypeProperty";//$NON-NLS-1$
	public static final String STEREOTYPE_PROPERTY_BRACE_TYPE = "StereotypePropertyBrace";//$NON-NLS-1$

	public static final String STEREOTYPE_COMMENT_TYPE = "StereotypeComment";//$NON-NLS-1$
	public static final String STEREOTYPE_COMMENT_LINK_TYPE = "StereotypeCommentLink";//$NON-NLS-1$

	/**
	 * The applied-stereotype view types.
	 */
	public static final Set<String> APPLIED_STEREOTYPE_VIEW_TYPES = ImmutableSet.of(
			STEREOTYPE_LABEL_TYPE,
			STEREOTYPE_BRACE_TYPE,
			STEREOTYPE_COMPARTMENT_TYPE,
			STEREOTYPE_COMMENT_TYPE, STEREOTYPE_COMMENT_LINK_TYPE,
			STEREOTYPE_PROPERTY_TYPE, STEREOTYPE_PROPERTY_BRACE_TYPE);


	// Name notation value
	public static final String STEREOTYPE_LABEL_NAME = "stereotype";//$NON-NLS-1$
	public static final String STEREOTYPE_COMPARTMENT_NAME = "stereotype";//$NON-NLS-1$
	public static final String STEREOTYPE_PROPERTY_NAME = "property";//$NON-NLS-1$

	// Depth Named Style
	public static final String STEREOTYPE_LABEL_DEPTH = "depth";//$NON-NLS-1$

	// Depth Constants
	public static final String DEPTH_MAX = "full";//$NON-NLS-1$
	public static final String DEPTH_MIN = "none";//$NON-NLS-1$
	public static final String DEPTH_AUTO = "auto";//$NON-NLS-1$
	public static final String DEFAULT_DEPTH_VALUE = "full";//$NON-NLS-1$


	// Special Char Constants
	public static final String STEREOTYPE_LABEL_SEPARATOR = ", ";//$NON-NLS-1$
	public static final String STEREOTYPE_PROPERTY_SEPARATOR = "\n";//$NON-NLS-1$
	public static final String STEREOTYPE_PROPERTY_VALUE_SEPARATOR = " = ";//$NON-NLS-1$
	public static final String STEREOTYPE_PROPERTIES_SEPARATOR = " ";//$NON-NLS-1$

	// Ornament Constants
	public static final String QUOTE_LEFT = Activator.ST_LEFT;
	public static final String QUOTE_RIGHT = Activator.ST_RIGHT;
	public static final String BRACE_LEFT = "{";//$NON-NLS-1$
	public static final String BRACE_RIGHT = "}";//$NON-NLS-1$


	// Define if Stereotype View are Persistent or not.
	public static final boolean PERSISTENT = false;

	// Icon NamedStyle Property
	public static final String DISPLAY_ICON = "displayIcon";//$NON-NLS-1$

	// NamedStyle Name for Property Location
	public static final String STEREOTYPE_PROPERTY_LOCATION = "StereotypePropertyLocation";//$NON-NLS-1$

	// NamedStyle Name for Label Alignment
	public static final String STEREOTYPE_LABEL_ALIGNMENT = "stereotypeAlignment";//$NON-NLS-1$

	/**
	 * String represent the location of stereotype properties within a comment
	 */
	public static final String STEREOTYPE_COMMENT_LOCATION = "Comment";//$NON-NLS-1$

	/**
	 * String represent the location of stereotype properties within a compartment of a graph node
	 */
	public static final String STEREOTYPE_COMPARTMENT_LOCATION = "Compartment";//$NON-NLS-1$

	/**
	 * properties of applied stereotypes are enclose in braces
	 */
	public static final String STEREOTYPE_BRACE_LOCATION = "With brace";//$NON-NLS-1$


}
