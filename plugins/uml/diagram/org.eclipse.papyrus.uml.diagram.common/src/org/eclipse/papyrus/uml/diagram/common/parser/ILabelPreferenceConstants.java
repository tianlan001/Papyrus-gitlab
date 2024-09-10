/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 *		CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.parser;

import org.eclipse.papyrus.uml.tools.utils.ICustomAppearance;

/**
 * Label constants for label mask preferences.
 */
public interface ILabelPreferenceConstants extends ICustomAppearance {

	/** preference id for display labels */
	public String LABEL_DISPLAY_PREFERENCE = "label.display"; //$NON-NLS-1$

	/** Style constant for id info display in labels. */
	public static final String DISP_ID = "id"; //$NON-NLS-1$

	/** Style constant for dimension info display in labels. */
	public static final String DISP_DIMENSION = "dimension"; //$NON-NLS-1$

	/** Style constant for specification info display in labels. */
	public static final String DISP_SPECIFICATION = "specification"; //$NON-NLS-1$

	/** Style constant for signal info display in labels. */
	public static final String DISP_SIGNAL = "signal"; //$NON-NLS-1$

	/** Style constant forcing multiplicity display in labels. */
	public static final String DISP_DEFAULT_MULTIPLICITY = "defaultMultiplicity"; //$NON-NLS-1$

	/** Style constant forcing type display in labels. */
	public static final String DISP_UNDEFINED_TYPE = "undefinedType"; //$NON-NLS-1$

	/** Style constant forcing name display in labels. */
	public static final String DISP_NON_NAVIGABLE_ROLE = "nonNavigableRole"; //$NON-NLS-1$
}
