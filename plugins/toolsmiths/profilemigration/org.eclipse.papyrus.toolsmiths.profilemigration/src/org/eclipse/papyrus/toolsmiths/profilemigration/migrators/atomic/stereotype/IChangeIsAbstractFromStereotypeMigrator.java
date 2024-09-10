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

import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.IChangeMigrator;
import org.eclipse.uml2.uml.Stereotype;

/**
 * If a Stereotype becomes abstract the migration tool shall ask the designer
 * if he wants to replace applications of this stereotype by one or many
 * Stereotypes pick in a list. This list is defined by all specializations of the
 * abstract Stereotype available on applied profiles. If the answer is "no"
 * then the application of this stereotype is delete, otherwise selected
 * stereotypes are applied and inherited slot are preserved.
 *
 * If a Stereotype becomes concrete then the migration tool should not do anything.
 *
 */
public interface IChangeIsAbstractFromStereotypeMigrator extends IChangeMigrator {

	/**
	 * Get the stereotype becoming abstract
	 *
	 * @return the stereotype
	 */
	public Stereotype getStereotype();

	/**
	 * Get the new value of the feature isAbstract
	 *
	 * @return the isAbstract
	 */
	public boolean getValue();
}
