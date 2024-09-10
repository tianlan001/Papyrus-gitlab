/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
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
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.enumeration;

import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.IDeleteMigrator;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;

/**
 * 
 * If an enumeration literal is deleted from an enumeration in the profile specification and this
 * enumeration is used to type one or many properties of stereotypes then all stereotype
 * applications corresponding to the affected stereotype shall be upgraded. The upgrade
 * consists for each stereotype application in providing the designer with the possibility to
 * update the enumeration literal affected to the property whose specification was changed.
 *
 */
public interface IDeleteEnumerationLiteralFromEnumeration extends IDeleteMigrator {
	/**
	 * Get the enumeration owning the deleted EnumerationLiteral
	 * 
	 * @return the enumeration
	 */
	public Enumeration getEnumeration();

	/**
	 * Get the deleted EnumerationLiteral
	 * 
	 * @return the enumerationLiteral
	 */
	public EnumerationLiteral getEnumerationLiteral();
}
