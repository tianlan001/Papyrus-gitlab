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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.utils;

/**
 * This allows to define the constants for the user action.
 */
public interface UserActionConstants {

	/**
	 * The integer representing the undefined user action.
	 */
	public static final int UNDEFINED_USER_ACTION = -1;

	/**
	 * The integer representing the replace user action.
	 */
	public static final int REPLACE_USER_ACTION = 0;

	/**
	 * The integer representing the add user action.
	 */
	public static final int ADD_USER_ACTION = 1;

	/**
	 * The integer representing the skip user action.
	 */
	public static final int SKIP_USER_ACTION = 2;
	
	/**
	 * The integer representing the cancel user action.
	 */
	public static final int CANCEL_USER_ACTION = 3;
	
}
