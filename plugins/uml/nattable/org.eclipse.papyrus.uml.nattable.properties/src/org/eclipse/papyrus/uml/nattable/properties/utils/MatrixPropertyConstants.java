/*****************************************************************************
 * Copyright (c) 2017, 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA-LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   Vincent Lorenzo (CEA LIST) - vincent.lorenzo@cea.fr - Bug 532639
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.properties.utils;

/**
 * 
 * These constants are used in the property view model to identify the edited feature
 *
 */
public class MatrixPropertyConstants {

	private MatrixPropertyConstants() {
		// to prevent instanciation
	}

	public static final String MATRIX_ROW_SOURCES = "rowSources";//$NON-NLS-1$

	public static final String MATRIX_COLUMN_SOURCES = "columnSources";//$NON-NLS-1$

	public static final String MATRIX_ROW_FILTER = "rowFilter";//$NON-NLS-1$

	public static final String MATRIX_COLUMN_FILTER = "columnFilter";//$NON-NLS-1$

	public static final String MATRIX_CELL_TYPE = "managedElementType";//$NON-NLS-1$

	public static final String MATRIX_CELL_FILTER = "cellContentsFilter";//$NON-NLS-1$

	public static final String MATRIX_RELATIONSHIP_DIRECTION = "relationshipDirection";//$NON-NLS-1$

	public static final String MATRIX_RELATIONSHIP_OWNER = "relationshipOwner";//$NON-NLS-1$

	public static final String MATRIX_RELATIONSHIP_OWNER_STRATEGY = "relationshipOwnerStrategy";//$NON-NLS-1$

	// defined but not yet used
	public static final String MATRIX_RELATIONSHIP_OWNER_FEATURE = "relationshipOwnerFeature";//$NON-NLS-1$

}
