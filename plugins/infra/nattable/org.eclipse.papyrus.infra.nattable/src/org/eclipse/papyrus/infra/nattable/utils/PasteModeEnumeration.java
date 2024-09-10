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
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.utils;

/**
 * Literals used to describe the paste mode
 *
 * @author Vincent Lorenzo
 *
 */
public enum PasteModeEnumeration {

	/**
	 * the user want paste rows
	 */
	PASTE_EOBJECT_ROW,
	/**
	 * the user wants paste columns
	 */
	PASTE_EOBJECT_COLUMN,
	/**
	 * the system can't decide if the user want paste rows or columns
	 */
	PASTE_EOBJECT_ROW_OR_COLUMN,
	/**
	 * configuration exist but the configuration is not valid
	 */
	CANT_PASTE,

	/**
	 * the table have any configuration for paste
	 */
	PASTE_NO_CONFIGURATION;

}
