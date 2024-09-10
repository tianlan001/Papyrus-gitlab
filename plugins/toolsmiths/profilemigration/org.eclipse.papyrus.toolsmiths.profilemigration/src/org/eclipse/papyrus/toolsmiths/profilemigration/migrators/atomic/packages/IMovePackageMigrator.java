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

package org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.packages;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.IMoveMigrator;
import org.eclipse.uml2.uml.Package;

/**
 * 
 * If a package is moved to another profile then the migration tool shall focus
 * on the preservation of the stereotype applications available at the profiled model.
 *
 * If the package was moved in a profile that is already applied on the profiled
 * model then the migration is trivial and every stereotype application shall remain.
 *
 * If the package was moved in a profile that is not already applied on the
 * profiled model then the migration tool asks the designer if the profile should
 * be applied. If the designer answers 'yes' every stereotype application shall remain
 * conversely if the answer is 'no' then every stereotype application shall be deleted.
 * 
 */
public interface IMovePackageMigrator extends IMoveMigrator {


	/**
	 * Get the moved package
	 * 
	 * @return the movedPackage
	 */
	public Package getMovedPackage();

	/**
	 * Get the new container
	 * 
	 * @return the newContainer
	 */
	public EObject getNewContainer();

}
