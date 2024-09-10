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

import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.IAddMigrator;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * If a Stereotype is updated with a new property then a slot for this
 * property shall be available for each application of the stereotype
 * in the profile model.
 *
 * If the new property has a default value then each application of the modified
 * stereotype in the profiled model has its corresponding slot set with the default value.
 *
 * If the new property has no default value but a value is required (the minimal
 * multiplicity is greater or equal than 1) then each application of the modified
 * stereotype in the profiled model has its corresponding slot, the migration tool
 * shall ask the designer to know which value he wants to set.
 *
 * If the new property has no default value and a value is optional (the minimal
 * multiplicity is equal to 0) then each application of the modified stereotype
 * in the profiled model has it corresponding slot set to null.
 */
public interface IAddPropertyToStereotypeMigrator extends IAddMigrator {

	/**
	 * Get the stereotype owning the new property
	 *
	 * @return the stereotype
	 */
	public Stereotype getStereotype();

	/**
	 * Get the Added property
	 *
	 * @return the property
	 */
	public Property getProperty();
}
