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

package org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.property;

import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.IChangeMigrator;
import org.eclipse.uml2.uml.Property;

/**
 * If the loverValue feature of a property is modified then the migration tool shall maintain the validity of the model.
 *
 * If the lower value is changed from 0 to something else, means that as many as the lowerValue values are mandatory
 * the migration tool shall upgrade each application of the stereotype owning this property. The upgrade consist in
 * asking the designer which value he want to set on the slot corresponding of this property.
 *
 * If the lower value is changed from something to 0 the migration tool shall not do anything.
 *
 */
public interface IChangeLowerMultiplicityFromPropertyMigrator extends IChangeMigrator {

	/**
	 * Get the new multiplicity
	 *
	 * @return the newValue
	 */
	public int getValue();

	/**
	 * Get the old multiplicity
	 *
	 * @return the oldValue
	 */
	public int getOldValue();

	/**
	 * Get the modified property
	 *
	 * @return the property
	 */
	public Property getProperty();
}
