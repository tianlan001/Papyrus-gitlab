/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.utils;

/**
 * @author Vincent Lorenzo
 * 
 *         This class provides code which can be provided by Paste Action in the table
 */
public class PasteSeverityCode {


	/**
	 * Constructor.
	 *
	 */
	private PasteSeverityCode() {
		// to prevent instanciation
	}
	
	/**
	 * the severity code used when there is no containment feature declared in the configuration
	 */
	public static final int PASTE_ERROR__NO_CONTAINMENT_FEATURE = 0;
	
	/**
	 * the severity code used when there is no paste configuration found
	 */
	public static final int PASTE_ERROR__NO_PASTE_CONFIGURATION = PASTE_ERROR__NO_CONTAINMENT_FEATURE+1;

	/**
	 * no element type defined for the paste
	 */
	public static final int PASTE_ERROR__NO_ELEMENT_TYPE_IN_PASTE_CONFIGURATION = PASTE_ERROR__NO_PASTE_CONFIGURATION + 1;

	/**
	 * A depth is hidden, but there is more than one category for the depth (we can't know which category choose for the paste)
	 */
	public static final int PASTE_ERROR__MORE_THAN_ONE_CATEGORY_FOR_A_HIDDEN_DEPTH = PASTE_ERROR__NO_ELEMENT_TYPE_IN_PASTE_CONFIGURATION + 1;

	/**
	 * Element type has not be definedS
	 */
	public static final int PASTE_ERROR__UNKNOWN_ELEMENT_TYPE = PASTE_ERROR__MORE_THAN_ONE_CATEGORY_FOR_A_HIDDEN_DEPTH + 1;

	/**
	 * the severity code used when we try to paste columns in a tree table
	 */
	public static final int PASTE_ERROR__CANT_PASTE_COLUMNS_IN_TREE_TABLE = PASTE_ERROR__UNKNOWN_ELEMENT_TYPE + 1;
	
	/**
	 * the severity code used when more lines is pasted than the number of depth available
	 */
	public static final int PASTE_ERROR__MORE_LINES_THAN_DEPTH = PASTE_ERROR__CANT_PASTE_COLUMNS_IN_TREE_TABLE + 1;

	/**
	 * the severity code used when there are too many cells on a rows
	 */
	public static final int PASTE_WARNING__TOO_MANY_CELLS_ON_ROWS = PASTE_ERROR__MORE_LINES_THAN_DEPTH + 1;

}
