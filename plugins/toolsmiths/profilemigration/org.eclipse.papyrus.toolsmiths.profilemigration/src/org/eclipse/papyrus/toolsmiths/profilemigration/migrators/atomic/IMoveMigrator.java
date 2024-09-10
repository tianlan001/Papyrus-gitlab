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
 * This is a migrator for the action of moving an element from a container to an other container
 */
public interface IMoveMigrator extends IAtomicMigrator {

	/**
	 * Get the new container of the element
	 * 
	 * @return the new container of the element
	 */
	public Element getTargetContainer();

	/**
	 * Get the old container of the element (before the move)
	 * 
	 * @return the old container of the element
	 */
	public Element getSourceContainer();

	/**
	 * Get the moved element
	 * 
	 * @return the moved element
	 */
	public Element getMovedElement();

}
