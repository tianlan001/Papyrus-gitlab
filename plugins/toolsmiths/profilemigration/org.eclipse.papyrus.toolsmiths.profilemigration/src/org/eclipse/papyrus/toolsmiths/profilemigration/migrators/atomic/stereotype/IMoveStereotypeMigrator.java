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

package org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.stereotype;

import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.IMoveMigrator;
import org.eclipse.uml2.uml.Stereotype;

/**
 * If a Stereotype is moved to another profile then the migration tool shall focus
 * on the preservation of the stereotype applications available at the profiled model.
 *
 * If the stereotype was moved in a profile that is already applied on the
 * profiled model then the migration is trivial and every application of
 * this stereotype shall remain.
 *
 * If the stereotype was moved in a profile that is not already applied on the
 * profiled model then the migration tool asks the designer if the profile
 * should be applied. If the designer answers 'yes' every application of this
 * stereotype shall remain conversely if the answer is 'no' then every
 * application of this stereotype shall be deleted.
 */
public interface IMoveStereotypeMigrator extends IMoveMigrator {

	/**
	 * Get the moved stereotype
	 *
	 * @return the stereotype
	 */
	public Stereotype getStereotype();
}
