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

package org.eclipse.papyrus.uml.nattable.matrix.configs;

/**
 * 
 * Enumeration used by the class {@link MatrixRelationshipDisplayConverter}
 * It allows to know how the check box must be displayed, and which is the optional message to display in the cell too.
 *
 */
public enum CellMatrixRelationshipEnum {

	CHECKED(Boolean.TRUE.toString()),

	UNCHECKED(Boolean.FALSE.toString()),

	CHECKED_MORE_THAN_2_ENDS(">2 ends"), //$NON-NLS-1$

	CHECKED_MORE_THAN_ONE_LINK(">1 link"), //$NON-NLS-1$

	UNKNOWN_VALUE("Unknown value"); //$NON-NLS-1$

	/**
	 * the name of the enum
	 */
	private String name = ""; //$NON-NLS-1$

	/**
	 * 
	 * Constructor.
	 *
	 * @param name
	 */
	private CellMatrixRelationshipEnum(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @see java.lang.Enum#toString()
	 *
	 * @return
	 */
	public String toString() {
		return name;
	}
}