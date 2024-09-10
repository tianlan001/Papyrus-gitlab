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
package org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic;

import org.eclipse.uml2.uml.Element;

/**
 * This is a migrator for the action of adding an element to a container
 */
public interface IAddMigrator extends IAtomicMigrator {

	/**
	 * Get the added element
	 * 
	 * @return the added element
	 */
	public Element getAddedElement();

	/**
	 * Get the added element container
	 * 
	 * @return the added element container
	 */
	public Element getAddedElementContainer();

}
